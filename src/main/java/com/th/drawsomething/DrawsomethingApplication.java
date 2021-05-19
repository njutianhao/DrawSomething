package com.th.drawsomething;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DrawsomethingApplication {

	public static final int maxPlayer = 8;

	public static final int maxRound = 3;

	public static void main(String[] args) {
		SpringApplication.run(DrawsomethingApplication.class, args);
	}

}
