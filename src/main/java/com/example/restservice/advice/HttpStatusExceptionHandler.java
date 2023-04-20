package com.example.restservice.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.example.restservice.core.HttpStatusEnum;
import com.example.restservice.core.ResponseDataVo;
import com.example.restservice.core.ResponseVo;


/**
 * HTTP 상태코드 
 * - RESTController에서는 무조건 상태코드 200(OK) 반환
 * - 정의할 상태코드들(1~7까지 정의, 나머지는 무조건 500 - 서버문제)
 * 1. 201(Created)
 * 2. 204(No Content)
 * 3. 400(Bad Request)
 * 4. 401(Unauthorized)
 * 5. 403(Forbidden)
 * 6. 404(Not Found)
 * 7. 405(Method Not Allowed)
 * 8. 500(Internal Server Error) 
 * TODO statusCode 기반으로 메세지 설정하는 부분 enum으로 변경하여 코드 제거
 */
@RestControllerAdvice
public class HttpStatusExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResponseVo> httpErrorHandler(Exception ex) {
		
		
		
		String code = "SYS000500";
		String message = "Internal Server Error";
		
		int statusCode = 0;
		
		// 405
		if( ex instanceof HttpRequestMethodNotSupportedException ) {
			statusCode = ((HttpRequestMethodNotSupportedException)ex).getStatusCode().value();
		}
		// 404
		else if( ex instanceof NoHandlerFoundException ) {
			statusCode = ((NoHandlerFoundException)ex).getStatusCode().value();
		}
		else if( ex instanceof HttpClientErrorException ) {
			statusCode = ((HttpClientErrorException)ex).getStatusCode().value();
		}
		else {
			statusCode = 500;
			logger.error("HttpStatusExceptionHandler Exception is ", ex);
			
		}
		
		code = "SYS" + String.format("%06d", statusCode);
		// 2023-04-16
		message = HttpStatusEnum.valueOf(statusCode).getMessage();
		
		return new ResponseEntity<ResponseVo>(new ResponseDataVo(code, message), HttpStatus.OK);
		
	}
	
}
