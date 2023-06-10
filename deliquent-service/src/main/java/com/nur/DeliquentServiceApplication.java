package com.nur;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
@EnableMongoAuditing
public class DeliquentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliquentServiceApplication.class, args);
		/*
		* Just to test what does Duration.parse("PT10M") returns, it returns Duration in seconds
		 */
//		Duration d = Duration.parse("PT10M");
//		System.out.println("Duration in seconds: " + d.get(ChronoUnit.SECONDS));
//		System.out.println("Duration in seconds: " + d.getSeconds());

	}
}
