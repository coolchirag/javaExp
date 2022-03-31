package com.example.springjpa.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		//cs.getCompany();
		//cs.criteriaBuilderDemo2();
		//cs.insertCompanyWithEmp();
		//cs.deleteCompanyWithEmp();
		//System.out.println("test "+Thread.currentThread().getId());
		//Thread.sleep(60*1000);
		
		es.insertEmployeINExistingCompany();
		return "Hello";
	}
	
	@GetMapping("/test2")
	public String test2() throws InterruptedException {
		cs.insertCompanyWithEmp();
		System.out.println("test2 "+Thread.currentThread().getId());
		Thread.sleep(60*1000);
		return "Hello";
	}
	
	@Autowired
	private DataSource ds;
	
	@GetMapping("/ds")
	public String getDsDetail() throws InterruptedException {
		return ds.toString();
	}
	
	@GetMapping("/dsChange")
	public String changeDsDetail(@RequestParam("newPass") String newPass) throws InterruptedException {
		final String response;
		if(ds instanceof org.apache.tomcat.jdbc.pool.DataSource) {
			org.apache.tomcat.jdbc.pool.DataSource apacheDataSource = (org.apache.tomcat.jdbc.pool.DataSource) ds;
			apacheDataSource.getPoolProperties().setPassword(newPass);
			response = "Successfully set new password : "+newPass;
		} else {
			response ="Failed.";
		}
		return response + " : "+ds.toString();
	}

	
}
