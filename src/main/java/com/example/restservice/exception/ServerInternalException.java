package com.example.restservice.exception;

public class ServerInternalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4171415759096298792L;
	
	
	String errorCode = "";
	String errorMsg = "";
	
	// Default Constructor
	public ServerInternalException() {}
	
	public ServerInternalException(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public ServerInternalException(String errorCode, String errorMsg) {
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
