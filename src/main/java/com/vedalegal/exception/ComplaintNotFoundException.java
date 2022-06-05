package com.vedalegal.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"cause","stackTrace","suppressed","localizedMessage"})

public class ComplaintNotFoundException extends AppException{

	private static final long serialVersionUID = 1L;

	public ComplaintNotFoundException(String errorType, String errorCode, String message) {
		super(errorType, errorCode, message);
	}
}
