package com.th.drawsomething;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Random;

@Component
@Data
public class Hall {
    private HashMap<Integer,Room> rooms = new HashMap<>();

    public void addRoom(String name){
        Random rd = new Random();
        Integer i;
        do {
            i = rd.nextInt(Integer.MAX_VALUE);
        }while(rooms.get(i) != null);
        rooms.put(i,new Room(name,i,0));
    }

    @PostConstruct
    public void init(){
        for(int i = 0;i < 100;i++){
            addRoom(String.valueOf(i));
        }
    }
}
