package com.chirag.spring.experiment.config;

import java.util.Arrays;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.chirag.spring.experiment.filter.CustomFilter;

@Configuration
public class FilterConfig {
	
	@Autowired
	private CustomFilter customFilter;

	@Bean
	public FilterRegistrationBean mdcRegistration()
	{
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(customFilter);
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 41);
		//registration.setUrlPatterns(Arrays.asList(ezloggingProperties.getUrlMatchers()));
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		return registration;
	}
}
