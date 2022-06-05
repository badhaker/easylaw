package com.vedalegal.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "cause", "stackTrace", "suppressed", "localizedMessage" })
public class MasterServiceNotExistException extends AppException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8626828751900918874L;
	
	public MasterServiceNotExistException(String errorType, String errorCode, String message) {
		super(errorType, errorCode, message);

	}


}
