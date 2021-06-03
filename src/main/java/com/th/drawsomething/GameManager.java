package com.th.drawsomething;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

@Component
@Data
public class GameManager {
    private volatile ConcurrentHashMap<Long,Game> games = new ConcurrentHashMap<>();

    private final long idPoolSizeBase = 100;

    private volatile long idPoolSize = 0;

    private volatile PriorityBlockingQueue<Long> idPool = new PriorityBlockingQueue<>();

    private synchronized boolean enlargeIdPool(){
        if(idPoolSize+idPoolSizeBase < 0)
        {
            return false;
        }
        for (long i = idPoolSize;i<idPoolSize+idPoolSizeBase;i++)
        {
            idPool.offer(i);
        }
        idPoolSize += idPoolSizeBase;
        return true;
    }

    public synchronized Long addGame(String name){
        Long i;
        i = idPool.poll();
        if(i == null)
        {
            if(enlargeIdPool())
                i = idPool.poll();
            else
                return null;
        }
        games.put(i,new Game(name,i));
        return i;
    }

    @PostConstruct
    public void init(){
        for(long i = 0;i < idPoolSize;i++)
            idPool.offer(i);
//        for(long i = 0;i < 100;i++){
//            addGame(String.valueOf(i));
//        }
    }
}
