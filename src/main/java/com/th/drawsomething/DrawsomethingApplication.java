package com.th.drawsomething;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@SpringBootApplication
public class DrawsomethingApplication {

	public static final int maxPlayer = 8;

	public static void main(String[] args) {
		SpringApplication.run(DrawsomethingApplication.class, args);
	}

}
