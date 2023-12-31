package com.example.springjpa.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class AspectClass {
	
	String name="";

	/*@Before("allMethod()")
	public void beforeLog(JoinPoint jp) {
		if (jp.getTarget()!=null) {
			@SuppressWarnings("rawtypes")
			Class className = jp.getTarget().getClass();
			Signature signature = jp.getSignature();
			String methodName = signature.getName();
			final Logger log = LoggerFactory.getLogger(className);
			final StringBuilder logStr = new StringBuilder("Inside : " + className.getName() + "." + methodName);

			if (className.getPackage().getName().contains(".controller")) {
				
				Object[] args = jp.getArgs();
				name = methodName;
				logStr.append("Args are");
				if (args != null) {
					for (Object arg : args) {
						logStr.append(" : " + arg);
					}
				}

			}
			logStr.append(" startTime : " + System.currentTimeMillis()+" : "+name);
			log.debug(logStr.toString());
			// System.out.println(logStr.toString());
		}
	}

	@After("allMethod()")
	public void afterLog(JoinPoint jp) {
		if (null != jp.getTarget()) {
			@SuppressWarnings("rawtypes")
			Class className = jp.getTarget().getClass();
			Signature signature = jp.getSignature();
			String methodName = signature.getName();
			final Logger log = LoggerFactory.getLogger(className);
			// System.out.println("Exit from :
			// "+className.getName()+"."+methodName);
			log.debug("Exit from : " + className.getName() + "." + methodName + " endTime : "
					+ System.currentTimeMillis()+" : "+name);

		}
	}
	
	@AfterReturning(pointcut = "allMethod()", returning = "result") 
	public void afterReturnung(JoinPoint jp, Object result) {
		System.out.println(jp+":"+result+" : "+name);
	}
	
	@AfterThrowing(pointcut = "allMethod()", throwing = "ex") 
	public void afterReturnung(JoinPoint jp, Throwable ex) {
		System.out.println(jp+":"+ex+" : "+name);
	}*/
	
	/*@Around("allMethod()")
	public void aroundAspect(ProceedingJoinPoint jp) throws Throwable {
		
		  System.out.println("Inside around1");
		  
		  Class className = jp.getTarget().getClass(); Signature signature =
		  jp.getSignature(); String methodName = signature.getName(); 
		  final Logger
		  log = LoggerFactory.getLogger(className); 
		  final StringBuilder logStr = new
		  StringBuilder("Inside : " + className.getName() + "." + methodName);
		  
			*
			 * if (className.getPackage().getName().contains(".controller")) {
			 * 
			 * }
			  
		  try {
			  jp.proceed(); 
			  System.out.println("around2");
			  } catch (Throwable e) {
				  System.out.println("Around exception : "+e); 
				  throw e;
				  }
		  
		  
		 }*/
	@Pointcut("execution(* com.example.springjpa..*(..)) && !execution(* com.example.springjpa.SpringjpaApplication.*(..)) ")
	public void allMethod() {
		//No need to provide implementation
	}
}
