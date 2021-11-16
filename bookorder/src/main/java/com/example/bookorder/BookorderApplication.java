package com.example.bookorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
public class BookorderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookorderApplication.class, args);
	}

}
