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

    private void setOwner(Game game,WebSocketSession session) throws IOException {
        game.setOwner(session);
        synchronized (session) {
            session.sendMessage(createMessage("BeOwner", null));
        }
    }

    private void sendToAll(ConcurrentHashMap<WebSocketSession,Player> map,TextMessage message) throws IOException {
        for(Map.Entry<WebSocketSession,Player> entry:map.entrySet())
        {
            synchronized (entry.getKey()) {
                entry.getKey().sendMessage(message);
            }
        }
    }

    private void endGame(Game game) throws IOException {
        game.getScheduledThreadPoolExecutor().getQueue().clear();
        TextMessage message = createMessage("EndGame", null);
        sendToAll(game.getPlayers(),message);
        sendToAll(game.getObservers(),message);
        game.endGame();
        return ;
    }

    private void gameSchedule(Game game) throws IOException {
        ConcurrentHashMap<WebSocketSession, Player> players = game.getPlayers();
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = game.getScheduledThreadPoolExecutor();
        scheduledThreadPoolExecutor.getQueue().clear();
        Map.Entry<WebSocketSession,Player> entry = game.beginNextTurn(jdbcTemplate);
        if(entry == null) {
            endGame(game);
            return ;
        }
        WebSocketSession session = entry.getKey();
        Player player = entry.getValue();
        synchronized (session) {
            session.sendMessage(createMessage("DrawTurnBegin", game.getProblem()));
        }
        game.setPainter(session);
        TextMessage message = createMessage("GuessTurnBegin", game.getProblem().length());
        for(Map.Entry<WebSocketSession,Player> entry1:players.entrySet()) {
            if(!entry1.getKey().equals(session))
            {
                synchronized (entry1.getKey()) {
                    entry1.getKey().sendMessage(message);
                }
            }
        }
        sendToAll(game.getObservers(),message);

        List<String> hints = game.getHints();
        int i = 0;
        for(;i < hints.size();i++)
        {
            int finalI = i;
            if(!game.getHints().get(finalI).equals("")) {
                scheduledThreadPoolExecutor.schedule(() -> {
                    TextMessage message1 = null;
                    try {
                        message1 = createMessage("Hint", game.getHints().get(finalI));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    for (Map.Entry<WebSocketSession, Player> entry1 : players.entrySet()) {
                        Player player1 = entry1.getValue();
                        if (!player.equals(player1)) {
                                synchronized(entry1.getKey()) {
                                    try {
                                        entry1.getKey().sendMessage(message1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                        }
                    }
                    try {
                        sendToAll(game.getObservers(),message1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, 15L * (i+1), TimeUnit.SECONDS);
            }
        }

        scheduledThreadPoolExecutor.schedule(()->{
            TextMessage message1 = null;
            try {
                message1 = createMessage("TurnEnd", game.getPlayers().size() - 1 - game.getAnsweredNum().get());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            try {
                sendToAll(game.getPlayers(),message1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                sendToAll(game.getObservers(),message1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        },(i+1) * 15L,TimeUnit.SECONDS);

        scheduledThreadPoolExecutor.schedule(() -> {
            try {
                gameSchedule(game);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, (i+1) * 15L + 5, TimeUnit.SECONDS);
    }

    private List<PlayerInfo> getPlayerInfo(Game game){
        List<PlayerInfo> list = new ArrayList<>();
        int i = 0;
        for(Map.Entry<WebSocketSession,Player> entry:game.getPlayers().entrySet())
        {
            Player player = entry.getValue();
            if(entry.getKey().equals(game.getPainter()))
                list.add(new PlayerInfo(i++,player.getName(),player.getScore().get(),true));
            else
                list.add(new PlayerInfo(i++,player.getName(),player.getScore().get(),false));
        }
        return list;
    }

    private void sendPlayers(Game game) throws IOException {
        TextMessage message = createMessage("UpdatePlayers",getPlayerInfo(game).toArray());
        sendToAll(game.getPlayers(),message);
        sendToAll(game.getObservers(),message);
    }

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long roomId = (Long) session.getAttributes().get("roomId");
        String userName = (String)session.getAttributes().get("userName");
        Game game = gameManager.getGames().get(roomId);
        if(roomId == null || userName == null || game == null ||game.getPlayers().keySet().contains(session) || game.getObservers().keySet().contains(session))
        {
            session.close();
            return ;
        }
        if(game.getPlayers().size() >= DrawsomethingApplication.maxPlayer)
        {
            game.addObserver(userName,session);
            TextMessage message = createMessage("UpdatePlayers",getPlayerInfo(game).toArray());
            synchronized (session){
                session.sendMessage(createMessage("BeObserver",null));
                session.sendMessage(message);
            }
            return;
        }
        else {
            synchronized (game) {
                if (game.isBegin())
                    game.getTmpPlayer().addAndGet(1);
                game.addPlayer(userName, session);
                if (game.getOwner() == null) {
                    setOwner(game, session);
                }
            }
            sendPlayers(game);
        }
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

    private void judgeTurnEnd(Game game) throws IOException {
        if (game.getAnsweredNum().get()== game.getPlayers().size() -game.getTmpPlayer().get() - 1) {
            TextMessage message = createMessage("TurnEnd", null);
            sendToAll(game.getPlayers(),message );
            sendToAll(game.getObservers(),message);
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = game.getScheduledThreadPoolExecutor();
            scheduledThreadPoolExecutor.getQueue().clear();
            scheduledThreadPoolExecutor.schedule(() -> {
                try {
                    gameSchedule(game);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 5, TimeUnit.SECONDS);
        }
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        System.out.println(message.getPayload());
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonMessage jsonMessage = objectMapper.readValue(payload,JsonMessage.class);
        Long roomId = (Long)session.getAttributes().get("roomId");
        Game game = gameManager.getGames().get(roomId);
        switch (jsonMessage.getCommand()){
            case "StartGame":
                if(!game.isBegin() && game.getOwner().equals(session) && game.getPlayers().size() >= 2)
                {
                    game.startGame();
                    gameSchedule(game);
                }
                break;
            case "Guess":
                if(game.isBegin() && !session.equals(game.getPainter()) && game.getPlayers().keySet().contains(session)) {
                    if (game.getProblem().equals(jsonMessage.getContent())) {
                        Player player = game.getPlayers().get(session);
                        Player painter = game.getPlayers().get(game.getPainter());
                        synchronized (player) {
                            if (!player.isHasAnswered()) {
                                game.getPlayers().get(game.getPainter()).getScore().addAndGet(1);
                                if (game.getAnsweredNum().get() == 0) {
                                    game.getAnsweredNum().set(1);
                                    player.getScore().addAndGet(2);
                                } else {
                                    player.getScore().addAndGet(1);
                                    game.getAnsweredNum().getAndAdd(1);
                                }
                                synchronized (session) {
                                    session.sendMessage(createMessage("AnswerRight", null));
                                }
                                TextMessage message1 = createMessage("Guess", session.getAttributes().get("userName").toString() + "答对了");
                                sendToAll(game.getPlayers(), message1);
                                sendToAll(game.getObservers(), message1);
                                sendPlayers(game);
                                judgeTurnEnd(game);
                            }
                        }
                    }
                    else {
                        TextMessage message1 = createMessage("Guess", session.getAttributes().get("userName").toString() + ":" + jsonMessage.getContent());
                        sendToAll(game.getPlayers(),message1);
                        sendToAll(game.getObservers(),message1);
                    }
                }
                break;
            case "Talk":
                TextMessage message1 = createMessage("Talk", session.getAttributes().get("userName").toString() + ":" + jsonMessage.getContent());
                sendToAll(game.getPlayers(),message1);
                sendToAll(game.getObservers(),message1);
                break;
            case "Draw":
                if(game.isBegin() && session.equals(game.getPainter()))
                {
                    TextMessage message2 = createMessage("Draw", jsonMessage.getContent());
                    sendToAll(game.getPlayers(),message2);
                    sendToAll(game.getObservers(),message2);
                }
                break;
        }
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long roomId = (Long)session.getAttributes().get("roomId");
        String userName = (String)session.getAttributes().get("userName");
        if(roomId == null || userName == null) {
            System.out.println("null property");
            return;
        }
        Game game = gameManager.getGames().get(roomId);
        synchronized (game) {
            if (game.getPlayers().keySet().contains(session)) {
                game.removePlayer(session);
                Iterator<Map.Entry<WebSocketSession, Player>> entryIterator = game.getObservers().entrySet().iterator();
                if (entryIterator.hasNext()) {
                    Map.Entry<WebSocketSession, Player> entry = entryIterator.next();
                    game.addPlayer(entry.getValue().getName(), entry.getKey());
                    synchronized (entry.getKey()) {
                        entry.getKey().sendMessage(createMessage("BePlayer", null));
                    }
                    entryIterator.remove();
                }
                sendPlayers(game);
                if (game.getPlayers().size() == 0) {
                    gameManager.getGames().remove(roomId);
                    gameManager.getIdPool().offer(roomId);
                    return;
                }
                if (game.isBegin()) {
                    if (game.getPlayers().size() == 1)
                        endGame(game);
                    else if (game.getPainter().equals(session)) {
                        gameSchedule(game);
                    } else
                        judgeTurnEnd(game);
                }
                if (game.getOwner().equals(session)) {
                    ConcurrentHashMap<WebSocketSession, Player> players = game.getPlayers();
                    Iterator<Map.Entry<WebSocketSession, Player>> iterator = players.entrySet().iterator();
                    Map.Entry<WebSocketSession, Player> entry = iterator.next();
                    setOwner(game, entry.getKey());
                }
            } else
                game.removeObserver(session);
        }
    }
}

@AllArgsConstructor
@Data
class PlayerInfo{
    private int seat;
    private String name;
    private int score;
    private boolean drawing;
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