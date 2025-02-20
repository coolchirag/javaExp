package com.example.springjpa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.validation.constraints.Min;

import org.apache.logging.log4j.spi.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjpa.bean.Company;
import com.example.springjpa.service.CompanyHierarchyService;
import com.example.springjpa.service.CompanyService;
import com.example.springjpa.service.EmployeeService;

@RestController
@Validated
public class TestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private CompanyService cs;
	
	//@Autowired
	private EmployeeService es;

	//@Autowired
	private CompanyHierarchyService companyHierarchyService;
	
	@GetMapping("/exp/{id}")
	public String testExp(@PathVariable("id") @Min(value = 5, message = "Add more than 5") Integer id) throws InterruptedException {
		int i =0;
		int b=10/id;
		return "";
	}
	
	@GetMapping("/log")
	public String testLog(HttpServletRequest request) throws InterruptedException {
		cs.insertDuplicateCOmpany();
		System.out.println("----------------------------------Done");
		LOG.trace("Inside trace");
		LOG.debug("Inside debug");
		LOG.info("Inside info");
		LOG.warn("Inside warn");
		LOG.error("Inside error");
		return "done";
	}
	
	@GetMapping("/")
	public String test(HttpServletRequest request) throws InterruptedException {
		MDC.put("corel", "testc1"+System.currentTimeMillis());
		//LoggerContext context = org.apache.logging.log4j.LogManager.getContext();
		LOG.isDebugEnabled();
		long startTime = System.currentTimeMillis();
		LOG.info("======================Start");
		System.out.println("Start fetching company");
		cs.getCompanysByCity();
		//cs.insertMultipleCompany();
		//cs.checkTrasection();
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
		//cs.compareCmpBean();
		//cs.getCompanysByCity();
		
		
		
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
	public String changeDsDetail(@RequestParam("newPass")  String newPass) throws InterruptedException {
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

	@GetMapping("/exception")
	public String checkException() {
		LOG.warn("Test inside controller");
		cs.checkException();
		/*
		 * try {
		 * 
		 * } catch (Exception e) { //LOG.warn("Error : "+e.getMessage(),e);
		 * //e.printStackTrace(); //StackTraceElement[] stackTrace = e.getStackTrace();
		 * System.out.println(e); throw e; }
		 */
		
		return "";
	}
	
}
