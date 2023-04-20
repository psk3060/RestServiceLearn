package com.example.restservice.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.restservice.core.ResponseDataVo;

@RestControllerAdvice
public class ResponseDataAdvice {
	
	private final Logger logger = LoggerFactory.getLogger(ResponseDataAdvice.class);
	
	/**
	 * Pointcut 내부에서 ResponseDataVo만 입력할 경우 warning no match for this type name 발생하여, 패키지명까지 함께 입력
	 */
	@Pointcut("within(com.example.restservice.controller..*) && execution(com.example.restservice.core.ResponseDataVo *(..))")
	public void restControllerPointCut() { }
	
	@ResponseBody
	@Around("restControllerPointCut()")
	public ResponseEntity<ResponseDataVo> wrapResponseData(ProceedingJoinPoint joinPoint) throws Throwable {
		
		logger.error(joinPoint.toShortString());
		
		Object result = joinPoint.proceed();
		
		if( !(result instanceof ResponseDataVo) ) {
			throw new Exception("NOT ResponseDataVo IS result");
			
		}
		
		ResponseDataVo responseData = (ResponseDataVo)result;
		
		logger.error(responseData.toString());
		
		return new ResponseEntity<ResponseDataVo>(responseData, HttpStatusCode.valueOf(200));

	}
}
