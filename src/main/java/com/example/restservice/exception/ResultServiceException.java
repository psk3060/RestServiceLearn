package com.example.restservice.exception;

public class ResultServiceException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5518708945432383326L;
	
	String errorCode = "";
	String errorMsg = "";
	
	// Default Constructor
	public ResultServiceException() {}
	
	public ResultServiceException(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public ResultServiceException(String errorCode, String errorMsg) {
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
