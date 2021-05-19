package com.th.drawsomething;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class RoomInfo{
        private String name;
        private long id;
        private int playerNum;
    }

    @Autowired
    GameManager gameManager;

    @GetMapping("/hall")
    public String hall(){
        return "forward:/hall.html";
    }

    @ResponseBody
    @GetMapping("/rooms")
    public RoomInfo[] rooms(){
        ConcurrentHashMap<Long, Game> games = gameManager.getGames();
        RoomInfo[] rooms = new RoomInfo[games.size()];
        int i = 0;
        for(Map.Entry<Long, Game> gameEntry: games.entrySet()){
            rooms[i++] = new RoomInfo(gameEntry.getValue().getRoomName(),gameEntry.getValue().getRoomId(),gameEntry.getValue().getPlayers().size());
        }
        return rooms;
    }

    @ResponseBody
    @GetMapping("/createRoom")
    public Long createRoom(String roomName, HttpSession session){
        if(roomName.equals(""))
            roomName = session.getAttribute("userName") +"'s room";
        return gameManager.addGame(roomName);
    }

    @GetMapping("/room/{id}")
    public String room(@PathVariable("id") String id,HttpSession session){
        if(!gameManager.getGames().containsKey(Long.valueOf(id)))
            return "redirect:/hall";
        session.setAttribute("roomId",Long.valueOf(id));
        return "forward:/game.html";
    }

}
