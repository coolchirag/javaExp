package com.example.springjpa.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class AspectClass {

	//@Before("allMethod()")
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

				logStr.append("Args are");
				if (args != null) {
					for (Object arg : args) {
						logStr.append(" : " + arg);
					}
				}

			}
			logStr.append(" startTime : " + System.currentTimeMillis());
			log.debug(logStr.toString());
			// System.out.println(logStr.toString());
		}
	}

	//@After("allMethod()")
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
					+ System.currentTimeMillis());

		}
	}
	
	//@AfterReturning(pointcut = "allMethod()", returning = "result") 
	public void afterReturnung(JoinPoint jp, Object result) {
		System.out.println(jp+":"+result);
	}
	
	 //@AfterThrowing(pointcut = "allMethod()", throwing = "ex") 
	public void afterReturnung(JoinPoint jp, Throwable ex) {
		System.out.println(jp+":"+ex);
	}

	@Pointcut("execution(* com.example..*(..))  && !execution(* com.ezdi.kpmg.apigateway.capc.dao..*(..)) " +
			"&& !execution(* com.ezdi.kpmg.apigateway.capc.repo..*(..)) && !execution(* com.ezdi.kpmg.apigateway.capc.config..*(..)) " +
			"&& !execution(* com.ezdi.kpmg.apigateway.capc..*Config*.*(..)) && !execution(* com.ezdi.kpmg.apigateway.capc.metrics..*(..)) " +
			"&& !execution(* com.ezdi.kpmg.apigateway.capc.eventlisteners..*(..)) && !execution(* com.ezdi.kpmg.apigateway.capc.properties..*(..)) " +
			"&& !execution(* com.ezdi.kpmg.apigateway.capc.message..*(..)) && !execution(* com.ezdi.kpmg.apigateway.capc.security..*(..)) " +
			"&& !execution(* com.ezdi.kpmg.apigateway.capc.dto..*(..)) && !execution(* com.ezdi.kpmg.apigateway.capc.dtos..*(..)) " + 
			"&& !execution(* com.ezdi.kpmg.apigateway.capc.service.dos..*(..)) ")
	public void allMethod() {
		//No need to provide implementation
	}
}
