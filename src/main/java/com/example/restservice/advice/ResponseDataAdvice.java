package com.example.restservice.advice;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.restservice.core.ResponseDataVo;

import jakarta.servlet.http.HttpServletResponse;

@Aspect
@Component
public class ResponseDataAdvice {
	
	private final Logger logger = LoggerFactory.getLogger(ResponseDataAdvice.class);
	
	@ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        
    }
	
	/**
	 * Pointcut 내부에서 ResponseDataVo만 입력할 경우 warning no match for this type name 발생하여, 패키지명까지 함께 입력
	 */
	@Pointcut("within(com.example.restservice.controller..*)")
	public void restControllerPointCut() { }
	
	@Around("restControllerPointCut()")
	public Object wrapResponseData(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object returnValue = joinPoint.proceed();
		
		// ResponseEntity가 아닐 경우
		if( !(returnValue instanceof ResponseEntity) ) {
			
			if( !(returnValue instanceof ResponseDataVo) ) {
				throw new IllegalArgumentException("올바르지 않은 Response");
				
			}
			
			ResponseDataVo data = (ResponseDataVo) returnValue;
			
			return ResponseEntity.ok(data);
			
		}
		
		logger.info("returnValue value is {}", returnValue.toString());
		
		return returnValue;
		
	}
}
