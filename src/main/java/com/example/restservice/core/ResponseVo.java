package com.example.restservice.core;

/**
 * Wrapper로 사용 
 */
public class ResponseVo {
	
	private String code;
	
	private String message;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public ResponseVo() {}
	
	public ResponseVo(String code) {
		this.code = code;
	}
	
	public ResponseVo(String code, String message) {
		this(code);
		this.message = message;
	}
	
}
