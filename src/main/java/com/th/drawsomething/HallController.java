package com.th.drawsomething;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class HallController {

    @Autowired
    GameManager gameManager;

    @GetMapping("/hall")
    public String hall(){
        return "forward:/hall.html";
    }

    @ResponseBody
    @GetMapping("/rooms")
    public Room[] rooms(){
        ConcurrentHashMap<Integer, Game> games = gameManager.getGames();
        Room[] rooms = new Room[games.size()];
        int i = 0;
        for(Map.Entry<Integer,Game> gameEntry: games.entrySet()){
            rooms[i++] = gameEntry.getValue().getRoom();
        }
        return rooms;
    }

    @ResponseBody
    @GetMapping("/createRoom")
    public int createRoom(String roomName,HttpSession session){
        if(roomName == "")
            roomName = (String) session.getAttribute("userName") +"'s room";
        return gameManager.addGame(roomName);
    }

    @GetMapping("/room/{id}")
    public String room(@PathVariable("id") String id,HttpSession session){
        session.setAttribute("roomId",Long.valueOf(id));
        return "forward:/game.html";
    }

}
