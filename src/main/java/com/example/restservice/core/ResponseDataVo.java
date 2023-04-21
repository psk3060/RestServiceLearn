package com.example.restservice.core;

import java.util.Map;

public class ResponseDataVo extends ResponseVo {
		
	public ResponseDataVo() {
		super();
	}
	
	public ResponseDataVo(String code) {
		super(code);
	}
	
	public ResponseDataVo(String code, String message) {
		super(code, message);
	}
	
	public ResponseDataVo(String code, String message, Map<String, Object> data ) {
		this(code, message);
		this.data = data;
	}
	
	private Map<String, Object> data;
	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "code[" + getCode() + "] " + ", message[" + getMessage() + "], data : " + data.toString(); 
	}
	
}
