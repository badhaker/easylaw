package com.vedalegal.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"cause","stackTrace","suppressed","localizedMessage"})
public class AppException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String errorType;
	private String errorCode;
	private String message;
	
	public AppException(String message) {
		super(message);
		this.message = message;
	}
}
