package com.vedalegal.response;

public enum ErrorMessages {
	
	MISSING_REQUIRED_FIELDS("Missing required fields. please check Documentation for required fields"),
	RECORD_ALREADY_EXISTS("Record already exists"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	NO_RECORD_FOUND("Record with provided ID is not found"),
	AUTHENTICATION_FAILED("Authentication failed"),
	COULD_NOT_UPDATE_RECORD("could not update record"),
	COULD_NOT_DELETE_RECORD("could not delete record"),
	EMAIL_ADDRESS_NOT_VERIFIED("Email address is not verified");
	
	
	private String errorMessage;
	ErrorMessages(String errorMessage){
		this.errorMessage = errorMessage;
	}
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

}
