package com.chirag.spring.experiment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class ExperimentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExperimentApplication.class, args);
	}

}
