package com.th.drawsomething;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @ResponseBody
    @PostMapping("/login")
    public String login(String name, String password, HttpServletResponse response, HttpSession session) throws IOException {
        Integer id;
        try{
            id = jdbcTemplate.queryForObject("SELECT id FROM USERS WHERE name = ? AND password = ?",Integer.class,new Object[]{name,password});
        }
        catch (EmptyResultDataAccessException exception){
            return "用户名或密码错误";
        }
        session.setAttribute("userId",id.intValue());
        session.setAttribute("userName",name);
        return "success";
    }

    @GetMapping({"/", "/login"})
    public String login(){
        return "forward:/login.html";
    }
}
