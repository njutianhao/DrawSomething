package com.th.drawsomething;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

@Component
@Data
public class GameManager {
    private ConcurrentHashMap<Integer,Game> games = new ConcurrentHashMap<>();

    private final int idPoolSizeBase = 100;

    private int idPoolSize = 0;

    private PriorityBlockingQueue<Integer> idPool = new PriorityBlockingQueue<>();

    private boolean enlargeIdPool(){
        if(idPoolSize+idPoolSizeBase < 0)
        {
            return false;
        }
        for (int i = idPoolSize;i<idPoolSize+idPoolSizeBase;i++)
        {
            idPool.offer(i);
        }
        idPoolSize += idPoolSizeBase;
        return true;
    }

    public int addGame(String name){
        Integer i;
        i = idPool.poll();
        if(i == null)
        {
            if(enlargeIdPool())
                i = idPool.poll();
            else
                return -1;
        }
        games.put(i,new Game(new Room(name,i,0)));
        return i;
    }

    @PostConstruct
    public void init(){
        for(int i = 0;i < idPoolSize;i++)
            idPool.offer(i);
        for(int i = 0;i < 100;i++){
            addGame(String.valueOf(i));
        }
    }
}
