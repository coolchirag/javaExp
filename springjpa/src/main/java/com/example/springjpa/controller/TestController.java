package com.example.springjpa.controller;

import javax.sql.DataSource;

import org.apache.logging.log4j.spi.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjpa.bean.Company;
import com.example.springjpa.service.CompanyHierarchyService;
import com.example.springjpa.service.CompanyService;
import com.example.springjpa.service.EmployeeService;

@RestController
public class TestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private CompanyService cs;
	
	@Autowired
	private EmployeeService es;

	@Autowired
	private CompanyHierarchyService companyHierarchyService;
	
	@GetMapping("/")
	public String test() throws InterruptedException {
		MDC.put("corel", "testc1"+System.currentTimeMillis());
		//LoggerContext context = org.apache.logging.log4j.LogManager.getContext();
		LOG.isDebugEnabled();
		long startTime = System.currentTimeMillis();
		LOG.info("======================Start");
		//ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
		//LoggerContext cl = (LoggerContext) iLoggerFactory;
		//cl.getLogger("com.example").setLevel(Level.ERROR);
		/*Logger restClientLogger = (Logger) LoggerFactory.getLogger(TestController.class);
		restClientLogger.setLevel(Level.DEBUG);
		Log4jLoggerFactory obj = (Log4jLoggerFactory) iLoggerFactory;
		Set<org.apache.logging.log4j.spi.LoggerContext> loggerContexts = obj.getLoggerContexts();
		for(org.apache.logging.log4j.spi.LoggerContext ctx : loggerContexts) {
			ExtendedLogger logger = ctx.getLogger("");
			LOG.
		}*/
		
		
		
		LOG.info("======================Start2");
		/*
		 * MDC.put("event", "Test event"); LOG.warn("Inside controller"); int i = 0; int
		 * j = 5 / i;
		 */
		//cs.compareCmpBean();
		//cs.getCompanysByCity();
		
		
		
		//companyHierarchyService.getChildCompanyes();
		//cs.getCompany();
		
		cs.updateCompany();
		
		
		//cs.insertCompanyWithEmp();
		//cs.insertMultipleCompany();
		//cs.getCompanyFullDetails();
		//es.getEmployeeFullDetails();
		
		//cs.getCmpByCityCount();
		//cs.callJpaRepo();
		//threadExecution();
		//cs.getCompany();
		//cs.getCmpDetilaInDto();
		//cs.criteriaBuilderDemo2();
		//cs.insertCompanyWithEmp();
		//cs.deleteCompanyWithEmp();
		//System.out.println("test "+Thread.currentThread().getId());
		//Thread.sleep(60*1000);
		//cs.insertCompanyWithEmp();
		//es.insertEmployeINExistingCompany();
		//es.findEmp();
		System.out.println("================================== C end : "+(System.currentTimeMillis()-startTime));
		return "Hello";
	}
	
	private void threadExecution() {
		int threadSize = 10;
		long threadSleep = 20000;
		for(int i=0; i<threadSize; i++) {
			Thread t = new Thread(() -> cs.getCompany());
			t.start();
		}
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
