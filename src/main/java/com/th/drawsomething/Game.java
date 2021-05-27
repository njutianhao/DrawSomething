package com.th.drawsomething;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Data
public class Game {
    private volatile String roomName;
    private volatile long roomId;
    private volatile boolean begin = false;
    private volatile int round = 0;
    private final int maxRound = 2;
    private volatile int tmpPlayer = 0;

    private volatile WebSocketSession painter;
    private volatile ConcurrentHashMap<WebSocketSession,Player> players = new ConcurrentHashMap<>();
    private volatile ConcurrentHashMap<WebSocketSession,Player> observers = new ConcurrentHashMap<>();

    private volatile ConcurrentHashMap<WebSocketSession,Player> waiting = new ConcurrentHashMap<>();

    private volatile WebSocketSession owner = null;

    private volatile int answeredNum;

    private volatile String problem;
    private volatile List<String> hints = Arrays.asList(new String[]{"", "", ""});

    private volatile ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(8);



    public Game(String roomName,long roomId){
        this.roomName = roomName;
        this.roomId = roomId;
    }

    public synchronized void startGame(){
        if(!begin && players.size() > 0)
        {
            begin = true;
            answeredNum = 0;
            round = 0;
            waiting.putAll(players);
        }
    }

    public synchronized void endGame(){
        if(begin) {
            begin = false;
            for(Map.Entry<WebSocketSession,Player> entry:players.entrySet())
                entry.getValue().setScore(0);
        }
    }

    private synchronized Map.Entry<WebSocketSession,Player> pollWaiting(){
        Iterator<Map.Entry<WebSocketSession, Player>> iterator = waiting.entrySet().iterator();
        Map.Entry<WebSocketSession,Player> res = iterator.next();
        iterator.remove();
        return res;
    }

    public synchronized void addPlayer(String name, WebSocketSession session){
        if(players.size() >= DrawsomethingApplication.maxPlayer)
            return ;
        players.put(session,new Player(name));
    }

    public synchronized void removePlayer(WebSocketSession session){
        players.remove(session);
        waiting.remove(session);
    }

    public synchronized void addObserver(String name, WebSocketSession session){
        observers.put(session,new Player(name));
    }

    public synchronized void removeObserver(WebSocketSession session){
        observers.remove(session);
    }

    public synchronized boolean beginNextRound(){
        if(round == DrawsomethingApplication.maxRound)
            return false;
        round++;
        waiting.putAll(players);
        return true;
    }

    public synchronized Map.Entry<WebSocketSession,Player> beginNextTurn(JdbcTemplate jdbcTemplate){
        tmpPlayer = 0;
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

    private synchronized void prepareProblem(JdbcTemplate jdbcTemplate){
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
