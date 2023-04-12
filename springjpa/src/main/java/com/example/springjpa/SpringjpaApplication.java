package com.example.springjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration(exclude={ErrorMvcAutoConfiguration.class})
@EnableScheduling
public class SpringjpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringjpaApplication.class, args);
	}

}
