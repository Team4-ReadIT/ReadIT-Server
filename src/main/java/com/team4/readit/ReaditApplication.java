package com.team4.readit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ReaditApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReaditApplication.class, args);
	}

}
