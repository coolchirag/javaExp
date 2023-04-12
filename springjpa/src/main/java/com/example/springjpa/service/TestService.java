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
	
	@Autowired
	private CompanyService cs;

	public void doTask() throws InterruptedException {
		  System.out.println("==============================Inside service task : "+System.currentTimeMillis());
		  Thread.sleep(2000);
		  cs.insertMultipleCompany();
		  applicationContext.publishEvent("Data");
		  Thread.sleep(2000);
		  System.out.println("=========================Exit service task "+System.currentTimeMillis());
	  }
}
