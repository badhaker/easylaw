package com.vedalegal.request;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLogInRequest {
	@NotEmpty(message = "Email can not be empty")
	private String email;

	@NotEmpty
	private String password;
	
	@NotEmpty(message = "Role type can not be empty")
	private String roleType;
	
}
