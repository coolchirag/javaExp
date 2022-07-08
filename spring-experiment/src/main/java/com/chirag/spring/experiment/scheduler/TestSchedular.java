package com.chirag.spring.experiment.scheduler;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.chirag.spring.experiment.dto.TestRequest;

//@Component
public class TestSchedular {
	
	ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
	
	int count = 0;
	
	//@Bean
	public TestRequest getTestRequest() {
		return new TestRequest();
	}

	public TestSchedular() {
		scheduler.setPoolSize(1);
		scheduler.initialize();
	}

	//@Scheduled(fixedDelay = 5000)
	public void testTask() {
		System.out.println("Task one executed : "+count++);
		scheduler.schedule(() -> testTask(), new Date(System.currentTimeMillis()));
		//testTask();
		System.out.println("Method return");
	}
	
	//@PostConstruct
	public void init() {
		
		scheduler.schedule(new Runnable() {
			
			@Override
			public void run() {
				testTask();
				
			}
		}, new Date(System.currentTimeMillis()+3000));
	}
}
