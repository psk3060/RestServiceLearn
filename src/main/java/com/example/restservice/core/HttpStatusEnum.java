package com.example.restservice.core;

public enum HttpStatusEnum {
	
	OK(200, "OK", false)
	, CREATED(201, "Created", false)
	, NO_CONTENT(204, "No Content", false)
	, BAD_REQUEST(400, "Bad Request", true)
	, UNAUTHORIZED(401, "Unauthorized", true)
	, FORBIDDEN(403, "Forbidden", true)
	, NOT_FOUND(404, "Not Found", true)
	, METHOD_NOT_ALLOWED(405, "Method Not Allowed", true)
	, INTERNAL_SERVER_ERROR(500, "Internal Server Error", true);
	
	 private int code;
	private String message;
	private boolean isError;
	
	
	 HttpStatusEnum(int code, String message, boolean isError) {
		this.code = code;
		this.message = message;
		this.isError = isError;
	}
	
	
	public String getMessage() {
		return message;
	}
	
	public boolean isError() {
		return isError;
	}
	
	public static HttpStatusEnum valueOf( int code ) {
		switch(code) {
			case 200:
				return OK;
			case 201:
				return CREATED;
			case 204:
				return NO_CONTENT;
			case 400:
				return BAD_REQUEST;
			case 401:
				return UNAUTHORIZED;
			case 403:
				return FORBIDDEN;
			case 404:
				return NOT_FOUND;
			case 405:
				return METHOD_NOT_ALLOWED;
			default:
				return INTERNAL_SERVER_ERROR;
				
		}
		
	}
	
	
}
