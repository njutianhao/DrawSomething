package com.th.drawsomething;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HallController {

    @Autowired
    Hall hall;

    @GetMapping("/hall")
    public String hall(){
        return "forward:/hall.html";
    }

    @ResponseBody
    @GetMapping("/rooms")
    public Room[] rooms(){
        HashMap<Integer, Room> hallRooms = hall.getRooms();
        Room[] rooms = new Room[hallRooms.size()];
        int i = 0;
        for(Map.Entry<Integer,Room> roomEntry: hallRooms.entrySet()){
            rooms[i++] = roomEntry.getValue();
        }
        return rooms;
    }
}
