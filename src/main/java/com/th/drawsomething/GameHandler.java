package com.th.drawsomething;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class GameHandler extends TextWebSocketHandler {

    @Autowired
    private GameManager gameManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private TextMessage createMessage(String command,Object content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return new TextMessage(objectMapper.writeValueAsString(new JsonMessage(command,content)));
    }

    private void setOwner(Game game,WebSocketSession session,String name) throws IOException {
        session.sendMessage(createMessage("BeOwner",null));
        game.setOwner(name);
    }

    private void sendToAll(ConcurrentHashMap<WebSocketSession,Player> map,TextMessage message) throws IOException {
        for(Map.Entry<WebSocketSession,Player> entry:map.entrySet())
        {
            entry.getKey().sendMessage(message);
        }
    }

    private void gameSchedule(Game game) throws IOException {
        ConcurrentHashMap<WebSocketSession, Player> players = game.getPlayers();
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = game.getScheduledThreadPoolExecutor();
        scheduledThreadPoolExecutor.getQueue().clear();//TODO:check
        Map.Entry<WebSocketSession,Player> entry = game.beginNextTurn(jdbcTemplate);
        if(entry == null) {
            for (Map.Entry<WebSocketSession, Player> entry1 : players.entrySet()) {
                synchronized (entry1.getKey()) {
                    entry1.getKey().sendMessage(createMessage("EndGame", null));
                }
            }
            game.endGame();
            return ;
        }
        WebSocketSession session = entry.getKey();
        Player player = entry.getValue();
        synchronized (session) {
            session.sendMessage(createMessage("DrawTurnBegin", game.getProblem()));
        }
        game.setPainter(session);
        for(Map.Entry<WebSocketSession,Player> entry1:players.entrySet()) {
            Player player1 = entry1.getValue();
            if(!player.equals(player1))
            {
                synchronized (entry1.getKey()) {
                    entry1.getKey().sendMessage(createMessage("GuessTurnBegin", game.getProblem().length()));
                }
            }
        }

        List<String> hints = game.getHints();
        int i = 0;
        for(;i < hints.size();i++)
        {
            int finalI = i;
            if(game.getHints().get(finalI) != "") {
                scheduledThreadPoolExecutor.schedule(() -> {
                    for (Map.Entry<WebSocketSession, Player> entry1 : players.entrySet()) {
                        Player player1 = entry1.getValue();
                        if (!player.equals(player1)) {
                            try {
                                synchronized(entry1.getKey()) {
                                    entry1.getKey().sendMessage(createMessage("Hint", game.getHints().get(finalI)));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, 15L * i, TimeUnit.SECONDS);
            }
        }
        scheduledThreadPoolExecutor.schedule(()->{
            for(Map.Entry<WebSocketSession,Player> entry1:game.getPlayers().entrySet())
            {
                try {
                    synchronized (entry1.getKey()) {
                        entry1.getKey().sendMessage(createMessage("TurnEnd", game.getPlayers().size() - 1 - game.getAnsweredNum()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },i * 15L,TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.schedule(() -> {
            try {
                gameSchedule(game);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, i * 15L + 5, TimeUnit.SECONDS);
    }

    private void sendPlayers(Game game) throws IOException {

        List<PlayerInfo> list = new ArrayList<>();
        int i = 0;
        for(Map.Entry<WebSocketSession,Player> entry:game.getPlayers().entrySet())
        {
            Player player = entry.getValue();
            list.add(new PlayerInfo(i++,player.getName(),player.getScore()));
        }
        System.out.println("--");
        TextMessage message = createMessage("UpdatePlayers",list.toArray());
        for(Map.Entry<WebSocketSession,Player> entry:game.getPlayers().entrySet())
        {
            synchronized (entry.getKey()) {
                entry.getKey().sendMessage(message);
            }
        }
    }

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long roomId = (Long) session.getAttributes().get("roomId");
        String userName = (String)session.getAttributes().get("userName");
        if(roomId == null || userName == null)
        {
            session.close();
            return ;
        }
        Game game = gameManager.getGames().get(roomId);
        game.addPlayer(userName,session);

        if(game.getOwner() == null)
        {
            setOwner(game,session,userName);
        }

        sendPlayers(game);
    }

    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            this.handleTextMessage(session, (TextMessage) message);
        } else if (message instanceof BinaryMessage) {
            this.handleBinaryMessage(session, (BinaryMessage) message);
        } else {
            if (!(message instanceof PongMessage)) {
                throw new IllegalStateException("Unexpected WebSocket message type: " + message);
            }
            this.handlePongMessage(session, (PongMessage) message);
        }
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message.getPayload());
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonMessage jsonMessage = objectMapper.readValue(payload,JsonMessage.class);
        Long roomId = (Long)session.getAttributes().get("roomId");
        String userName = (String)session.getAttributes().get("userName");
        Game game = gameManager.getGames().get(roomId);
        switch (jsonMessage.getCommand()){
            case "StartGame":
                if(!game.isBegin() && game.getOwner().equals(userName))
                {
                    game.startGame();
                    gameSchedule(game);
                }
                break;
            case "Guess":
                if(game.isBegin() && session != game.getPainter()) {
                    if (game.getProblem().equals(jsonMessage.getContent())) {
                        Player player = game.getPlayers().get(session);
                        if (!player.isHasAnswered()) {
                            if (game.getAnsweredNum() == 0) {
                                game.setAnsweredNum(1);
                                player.setScore(player.getScore() + 2);
                            } else {
                                player.setScore(player.getScore() + 1);
                                game.setAnsweredNum(game.getAnsweredNum() + 1);
                            }
                            synchronized (session) {
                                session.sendMessage(createMessage("AnswerRight", null));
                            }
                            sendPlayers(game);
                            if (game.getAnsweredNum() == game.getPlayers().size() - 1) {
                                sendToAll(game.getPlayers(), createMessage("TurnEnd", null));
                                ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = game.getScheduledThreadPoolExecutor();
                                scheduledThreadPoolExecutor.getQueue().clear(); //TODO:CHECK
                                scheduledThreadPoolExecutor.schedule(() -> {
                                    try {
                                        gameSchedule(game);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }, 5, TimeUnit.SECONDS);
                            }
                        }
                    }
                    else
                        sendToAll(game.getPlayers(),createMessage("Guess",session.getAttributes().get("userName").toString()+":"+jsonMessage.getContent()));
                }
                break;
            case "Talk":
                sendToAll(game.getPlayers(),createMessage("Talk",session.getAttributes().get("userName").toString()+":"+jsonMessage.getContent()));
                break;
            case "Draw":
                if(game.isBegin() && session == game.getPainter())
                {
                    sendToAll(game.getPlayers(),createMessage("Draw",jsonMessage.getContent()));
                }
                break;
        }
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Connection Closed");
        Long roomId = (Long)session.getAttributes().get("roomId");
        String userName = (String)session.getAttributes().get("userName");
        if(roomId == null || userName == null)
        {
            System.out.println("null property");
            return ;
        }
        Game game = gameManager.getGames().get(roomId);
        game.removePlayer(session);
        if(game.getOwner().equals(userName))
        {
            ConcurrentHashMap<WebSocketSession,Player> players = game.getPlayers();
            if(players.size() == 0)
            {
                gameManager.getGames().remove(roomId);
            }
            else
            {
                Iterator<Map.Entry<WebSocketSession, Player>> iterator = players.entrySet().iterator();
                Map.Entry<WebSocketSession,Player> entry = iterator.next();
                setOwner(game,entry.getKey(),entry.getValue().getName());
            }
        }
    }
}

@AllArgsConstructor
@Data
class PlayerInfo{
    private int seat;
    private String name;
    private int score;
}

@AllArgsConstructor
@Data
class ProblemInfo{
    private String problem;
    private List<String> hints;
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class JsonMessage{
    private String command;
    private Object content;
}