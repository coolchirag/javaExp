package com.chirag.spring.experiment.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chirag.spring.experiment.dto.FileWithDataRequest;
import com.chirag.spring.experiment.dto.TestRequest;

@Configuration
public class Config2 {

	@ConditionalOnProperty(name = "pro2", matchIfMissing = false)
	@Bean
	public TestRequest getBean() {
		return new TestRequest();
	}
}
