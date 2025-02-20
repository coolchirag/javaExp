package com.example.springjpa.schedular;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.springjpa.service.CompanyService;
import com.example.springjpa.service.TestService;

@EnableAsync
@Component
public class TestSchedular {
	
	private int count = 0;
	
	@Autowired
	private CompanyService cmpService;
	
	@Autowired
	private TestService testService;

	//@Async
	//@Scheduled(fixedDelay = 1000)
	public void testTask() {
		cmpService.insertDuplicateCOmpany();
		System.out.println("Task one executed : "+count++);
		/*try {
			//testService.doTask();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//testTask();
		System.out.println("==================Method return : "+System.currentTimeMillis());
	}
	
	//@Async
	//@Scheduled(fixedDelay = 1000)
	public void testTask2() {
		System.out.println("Task one executed : "+count++);
		int i = 5/0;
		/*try {
			//testService.doTask();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//testTask();
		System.out.println("==================Method return : "+System.currentTimeMillis());
	}
}
