package com.example.springjpa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.example.springjpa.bean.Company;
import com.example.springjpa.repository.CompanyRepository;

@Service
@Transactional
public class TestService {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	CompanyService cs;
	
	@Autowired
	public CompanyRepository cr;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private EntityManager entityManage;

	public void doTask() throws InterruptedException {
		  System.out.println("==============================Inside service task : "+System.currentTimeMillis());
		  Thread.sleep(2000);
		//  cs.insertMultipleCompany();
		  applicationContext.publishEvent("Data");
		  Thread.sleep(2000);
		  System.out.println("=========================Exit service task "+System.currentTimeMillis());
	  }
	
	public void testTrasaction() {
		System.out.println("Inside f1");
		
		List<Company> byCity = cr.findByCity("c1");
		byCity.get(0).setCompanyName("n11");
		entityManage.flush();
		t1();
		System.out.println("Exit f1");
	}
	
	private void t1() {
		cs.updateCompany();
		
		int i = 0;
		int j = 5/i;
	}
}
