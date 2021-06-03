package com.th.drawsomething;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Player {
    private String name;
    private AtomicInteger score = new AtomicInteger();
    private boolean hasAnswered;
    public Player(String name){
        this.name = name;
        score.set(0);
    }

    public void init(){
        score.set(0);
        hasAnswered = false;
    }

    public void enterNextTurn(){
        hasAnswered = false;
    }

    public void enterNextRound(){
        hasAnswered = false;
    }

}
