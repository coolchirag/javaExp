package com.example.springjpa.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestService {
	
	@Autowired
	private ApplicationContext applicationContext;

	public void doTask() throws InterruptedException {
		  System.out.println("Inside service task");
		  Thread.sleep(2000);
		  applicationContext.publishEvent("Data");
		  Thread.sleep(2000);
		  System.out.println("Exit service task");
	  }
}
