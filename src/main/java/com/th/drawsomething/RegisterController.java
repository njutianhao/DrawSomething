package com.th.drawsomething;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
public class RegisterController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/register")
    public String register(String name,String password,HttpServletResponse response, HttpSession session) throws IOException {
        try{
            jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE name = ?", new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new User(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("password"));
                }
            }, new Object[]{name});
        }
        catch (EmptyResultDataAccessException exception){
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into users(name,password) values (?,?)", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,password);
                return preparedStatement;
            },keyHolder);
            session.setAttribute("userid",keyHolder.getKey().longValue());
            response.sendRedirect("/hall");
            return null;
        }
        return "Repeated UserName";
    }

}
