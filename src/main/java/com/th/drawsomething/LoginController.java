package com.th.drawsomething;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class LoginController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/login")
    public String login(String name, String password, HttpServletResponse response, HttpSession session) throws IOException {
        Integer id;
        try{
            id = jdbcTemplate.queryForObject("SELECT id FROM USERS WHERE name = ? AND password = ?",Integer.class,new Object[]{name,password});
        }
        catch (EmptyResultDataAccessException exception){
            return "Wrong UserName or Password";
        }
        session.setAttribute("userid",id.intValue());
        response.sendRedirect("/hall");
        return null;
    }
}
