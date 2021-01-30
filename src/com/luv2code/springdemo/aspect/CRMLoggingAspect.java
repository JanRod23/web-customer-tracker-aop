package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {
	
	// Setup Logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	// Setup pointcut declarations
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	// Do the same for service and DAO
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	
	// Add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		
		// Display method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("======>> in @Bfore: calling method: " + theMethod);
		
		// Get the arguments
		Object[] args = theJoinPoint.getArgs();
		
		// Display arguments
		for (Object tempArg : args) {
			myLogger.info("======>> argument: " + tempArg);
		}
	}
	
	// Add @AfterReturning advice
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult"
			)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		
		// Display method we are returning from
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("======>> in @AfterReturning: from method: " + theMethod);
		
		// Display data returned
		myLogger.info("======>> result: " + theResult);
	}
}
