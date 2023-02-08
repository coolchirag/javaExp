package com.chirag.spring.experiment.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chirag.spring.experiment.dto.FileWithDataRequest;

@Configuration
public class Config1 {
	
	@Value("${pro1:hello}")
	private String data;

	@ConditionalOnProperty(name = "pro1", matchIfMissing = false)
	@Bean
	public FileWithDataRequest getBean1() {
		return new FileWithDataRequest();
	}
	
	@PostConstruct
	public void init() {
		System.out.println(data);
	}
}
