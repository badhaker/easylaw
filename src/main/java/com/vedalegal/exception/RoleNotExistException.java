package com.vedalegal.exception;

public class RoleNotExistException extends AppException {


	private static final long serialVersionUID = 1L;

	public RoleNotExistException(String errorType, String errorCode, String message) {
		super(errorType, errorCode, message);
		
	}
	

}
