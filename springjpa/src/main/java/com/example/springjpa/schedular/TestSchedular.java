package com.example.springjpa.schedular;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.springjpa.service.TestService;

@Component
public class TestSchedular {
	
	private int count = 0;
	
	@Autowired
	private TestService testService;

	@Scheduled(fixedDelay = 1000)
	public void testTask() {
		System.out.println("Task one executed : "+count++);
		try {
			testService.doTask();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//testTask();
		System.out.println("==================Method return : "+System.currentTimeMillis());
	}
}
