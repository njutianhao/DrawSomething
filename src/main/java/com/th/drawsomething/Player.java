package com.th.drawsomething;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class Player {
    private String name;
    private int score;
    private boolean hasAnswered;
    public Player(String name){
        this.name = name;
        score = 0;
    }

    public void init(){
        score = 0;
        hasAnswered = false;
    }

    public void enterNextTurn(){
        hasAnswered = false;
    }

    public void enterNextRound(){
        hasAnswered = false;
    }

}
