package com.vedalegal.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.vedalegal.enums.LoginModeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialMediaSigninRequest {
	@Email
	@NotEmpty(message = "Email cannot be empty")	
	private String email;

	@NotEmpty(message = "Name can not be empty")
	private String name;

	@NotEmpty
	private String token;
	
	private LoginModeEnum singupMode;
	
	private String mobileNumber;
}
