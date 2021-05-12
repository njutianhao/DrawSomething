package com.th.drawsomething;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Controller
public class RegisterController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @ResponseBody
    @PostMapping("/register")
    public String register(String name,String password,HttpServletResponse response,HttpSession session) throws IOException {
        if(name == null || password == null)
            return "用户名或密码为空";
        try{
            jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE name = ?", (resultSet, i) -> new UserInfo(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("password")), new Object[]{name});
        }
        catch (EmptyResultDataAccessException exception){
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into users(name,password) values (?,?)", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,password);
                return preparedStatement;
            },keyHolder);
            session.setAttribute("userId",keyHolder.getKey().longValue());
            session.setAttribute("userName",name);
            return "success";
        }
        return "用户名已存在";
    }

    @GetMapping("/register")
    public String register() {
        return "forward:/register.html";
    }
}
