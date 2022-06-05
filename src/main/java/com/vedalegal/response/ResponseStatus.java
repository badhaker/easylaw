package com.vedalegal.response;

public class ResponseStatus {

	private int statusCode;

	public ResponseStatus(int statusCode) {
		this.statusCode = statusCode;
	}

	public ResponseStatus() {
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
