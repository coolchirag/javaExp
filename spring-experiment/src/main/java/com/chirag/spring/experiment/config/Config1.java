package com.chirag.spring.experiment.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chirag.spring.experiment.dto.FileWithDataRequest;

@Configuration
public class Config1 {

	@ConditionalOnProperty(name = "pro2", matchIfMissing = false)
	@Bean
	public FileWithDataRequest getBean1() {
		return new FileWithDataRequest();
	}
}
