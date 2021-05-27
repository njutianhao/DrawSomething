package com.th.drawsomething;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class HallController {

    @Autowired
    JdbcTemplate jdbcTemplate;

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

    @GetMapping({"/","/hall"})
    public String hall(){
        return "forward:/hall.html";
    }

    @ResponseBody
    @GetMapping("/rooms")
    public RoomInfo[] rooms(){
        ConcurrentHashMap<Long, Game> games = gameManager.getGames();
        RoomInfo[] rooms = new RoomInfo[games.size()];
        int i = 0;
        Iterator<Map.Entry<Long, Game>> entries = games.entrySet().iterator();
        while(entries.hasNext())
        {
            Map.Entry<Long,Game> gameEntry = entries.next();
            int size = gameEntry.getValue().getPlayers().size();
            if(size == 0)
            {
                entries.remove();
            }
            else
                rooms[i++] = new RoomInfo(gameEntry.getValue().getRoomName(),gameEntry.getValue().getRoomId(), size);
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

    @ResponseBody
    @GetMapping("/addProblem")
    public String addProblem(String key,String problem,String hint1,String hint2,String hint3,HttpSession session){
        if(problem == "")
            return "问题不能为空";
        if(!key.equals("c04e80a0-5f20-4e8e-be18-ebe4a3bdc4eb"))
            return "密钥错误";
        String userName = (String)session.getAttribute("userName");
        List<Integer> l = jdbcTemplate.query("SELECT * FROM Problems WHERE problem == ?", (ResultSet resultSet, int i)->{
            return 1;
        }, new Object[]{problem});
        if(l.size() != 0)
            return "该问题已存在";
        jdbcTemplate.update("Insert INTO Problems(author,problem,hint1,hint2,hint3) VALUES(?,?,?,?,?)",(ps)->{
            ps.setString(1,userName);
            ps.setString(2,problem);
            ps.setString(3,hint1);
            ps.setString(4,hint2);
            ps.setString(5,hint3);
        });
        return "添加成功";
    }

}
