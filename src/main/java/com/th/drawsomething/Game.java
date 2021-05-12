package com.th.drawsomething;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Game {
    private Room room;
    private boolean begin;
    private int turn;
    private int round;
    private final int maxRound = 3;
    private List<Player> players;

    public Game(Room room){
        this.room = room;
        begin = false;
        players = new LinkedList<>();
    }

    public void StartGame(){
        if(begin == false && players.size() > 0)
        {
            begin = true;
            turn = 1;
            round = 0;
        }
    }

    public void addPlayer(String name){
        if(players.size() >= DrawsomethingApplication.maxPlayer)
            return ;
        players.add(new Player(name));
    }

    public void remove(String name){
        players.removeIf(e->e.getName()==name);
    }

    public boolean beginNextTurn(){
        if(round == 3)
        {
            return false;
        }
        turn++;
        if(turn > players.size())
        {
            turn -= players.size();
            round++;
        }
        return true;
    }
}
