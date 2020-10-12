package com.example.springjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjpa.service.CompanyService;
import com.example.springjpa.service.EmployeeService;

@RestController
public class TestController {
	@Autowired
	private CompanyService cs;
	
	@Autowired
	private EmployeeService es;
	
	@GetMapping("/")
	public String test() throws InterruptedException {
		//cs.callJpaRepo();
		cs.criteriaBuilderDemo2();
		//cs.insertCompany();
		//System.out.println("test "+Thread.currentThread().getId());
		//Thread.sleep(60*1000);
		
		return "Hello";
	}
	
	@GetMapping("/test2")
	public String test2() throws InterruptedException {
		//cs.insertCompany();
		System.out.println("test2 "+Thread.currentThread().getId());
		Thread.sleep(60*1000);
		return "Hello";
	}
	
	

}
