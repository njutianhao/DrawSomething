package com.th.drawsomething;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Data
public class Game {
    private String roomName;
    private long roomId;
    private boolean begin = false;
    private int round = 0;
    private final int maxRound = 3;

    private WebSocketSession painter;
    private ConcurrentHashMap<WebSocketSession,Player> players = new ConcurrentHashMap<>();
    private ConcurrentHashMap<WebSocketSession,Player> observers = new ConcurrentHashMap<>();

    private ConcurrentHashMap<WebSocketSession,Player> waiting = new ConcurrentHashMap<>();

    private String owner = null;

    private int answeredNum;

    private String problem;
    private List<String> hints = Arrays.asList(new String[]{"", "", ""});

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(8);



    public Game(String roomName,long roomId){
        this.roomName = roomName;
        this.roomId = roomId;
    }

    public void startGame(){
        if(!begin && players.size() > 0)
        {
            begin = true;
            answeredNum = 0;
            round = 0;
            waiting.putAll(players);
        }
    }

    public void endGame(){
        if(begin)
            begin = false;
    }

    private Map.Entry<WebSocketSession,Player> pollWaiting(){
        Iterator<Map.Entry<WebSocketSession, Player>> iterator = waiting.entrySet().iterator();
        Map.Entry<WebSocketSession,Player> res = iterator.next();
        iterator.remove();
        return res;
    }

    public void addPlayer(String name, WebSocketSession session){
        if(players.size() >= DrawsomethingApplication.maxPlayer)
            return ;
        players.put(session,new Player(name));
    }

    public void removePlayer(WebSocketSession session){
        players.remove(session);
    }

    public boolean beginNextRound(){
        if(round == DrawsomethingApplication.maxRound)
            return false;
        round++;
        waiting.putAll(players);
        return true;
    }

    public Map.Entry<WebSocketSession,Player> beginNextTurn(JdbcTemplate jdbcTemplate){
        if(waiting.isEmpty())
        {
            if(!beginNextRound())
                return null;
        }
        answeredNum = 0;
        for(Map.Entry<WebSocketSession,Player> entry:players.entrySet())
        {
            entry.getValue().enterNextTurn();
        }
        prepareProblem(jdbcTemplate);
        return pollWaiting();
    }

    private void prepareProblem(JdbcTemplate jdbcTemplate){
        jdbcTemplate.queryForObject("SELECT * FROM Problems WHERE\n" +
                        "id + 1>= (SELECT MIN(id) from problems) + \n" +
                        "(1 + (SELECT Max(id) from problems) - \n" +
                        "(SELECT MIN(id) from problems)) * rand() LIMIT 1;",
                (resultSet, i) -> {
                    problem = resultSet.getString("problem");
                    for(int j = 0;j < 3;j++) {
                        hints.set(j,resultSet.getString("hint"+ (j + 1)));
                    }
                    return null;
                });
    }
}
