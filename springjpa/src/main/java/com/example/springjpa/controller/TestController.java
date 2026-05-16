package com.example.springjpa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.sql.DataSource;

import org.apache.logging.log4j.spi.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjpa.bean.Company;
import com.example.springjpa.service.CompanyHierarchyService;
import com.example.springjpa.service.CompanyReadService;
import com.example.springjpa.service.CompanyService;
import com.example.springjpa.service.EmployeeService;
import com.example.springjpa.service.FileHandlingService;
import com.example.springjpa.service.TestService;

@RestController
@Validated
public class TestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private CompanyService cs;
	
	@Autowired
	private CompanyReadService crs;
	
	@Autowired
	private EmployeeService es;

	@Autowired
	private CompanyHierarchyService companyHierarchyService;
	
	@Autowired
	private TestService testService;

	@Autowired
	private FileHandlingService fileHandlingService;
	
	@GetMapping("/exp")
	public String testExp(@RequestParam(name = "data", required = false) @Nonnull @Nonnegative Integer data) throws InterruptedException {
		int i =0;
		int b=10/i;
		return "";
	}
	@GetMapping("/read")
	public String testRead() throws InterruptedException {
		MDC.put("corel", "testc1"+System.currentTimeMillis());
		crs.fetchAllCompanyRead();
		return "Hello";
	}
	
	@GetMapping("/")
	public String test() throws InterruptedException {
		MDC.put("corel", "testc1"+System.currentTimeMillis());
		//LoggerContext context = org.apache.logging.log4j.LogManager.getContext();
		LOG.isDebugEnabled();
		long startTime = System.currentTimeMillis();
		LOG.info("======================Start");
		//es.findEmp();
		//threadExecution();
		//testService.testTrasaction();
		//cs.testConcurrentConnections(1);
		
		//cs.getData("hello");
		//cs.getCompanyDetailByJPQL();
		//cs.getCompanyFullDetails();
		//es.countEmpsByCmp();
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
		cs.updateCompanyAtTwoTransaction();
		//cs.fetchAllCompany();
		//cs.criteriaQueryJoin();
		//cs.insertCompanyWithEmp();
		//fileHandlingService.loadFile();
		//cs.getCompanyDetailByCriteriaBuilder();
		//cs.compareCmpBean();
		//cs.getCompanysByCity();
		//cs.findByCt();
		//cs.checkInternalManagementForInsert();
		
		//companyHierarchyService.getChildCompanyes();
		//cs.getCompany();
		
		//cs.updateCompany();
		
		
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
		int threadSize = 2000;
		long threadSleep = 200;
		List<Thread> threads = new ArrayList<>();
		for(int i=0; i<threadSize; i++) {
			final int count = i;
			Thread t = new Thread(() -> cs.testConcurrentConnections(count));
			t.start();
			threads.add(t);
		}
		
		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
