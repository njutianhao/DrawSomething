package com.th.drawsomething;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataBaseInitializer {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init(){
        jdbcTemplate.update("Create Table if not exists Users(" +
                "id int unsigned auto_increment primary key," +
                "name varchar(40) not null," +
                "password varchar(40) not null" +
                ")");
        jdbcTemplate.update("Create Table if not exists Problems(" +
                "id int unsigned auto_increment primary key," +
                "problem varchar(40) not null," +
                "author varchar(40) not null," +
                "hint1 varchar(100)," +
                "hint2 varchar(100)," +
                "hint3 varchar(100)" +
                ")");
    }
}
