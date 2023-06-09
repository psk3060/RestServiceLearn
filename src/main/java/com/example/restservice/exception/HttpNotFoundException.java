package com.example.restservice.exception;

public class HttpNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2919018695310753645L;
	
	String errorCode = "";
	String errorMsg = "";
	
	// Default Constructor
	public HttpNotFoundException() {}
	
	public HttpNotFoundException(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public HttpNotFoundException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	@Override
	public String toString() {
		return "errorCode : [" + errorCode + "], errorMsg : [" + errorMsg + "]";
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
