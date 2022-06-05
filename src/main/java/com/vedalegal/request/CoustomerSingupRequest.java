package com.vedalegal.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Validated
public class CoustomerSingupRequest {
	@Email
	@NotEmpty(message = "Email cannot be empty")	
	private String email;

	@NotEmpty(message = "Name can not be empty")
	private String name;

	@NotEmpty
	private String password;
	
	@Pattern(regexp = "^(0|[1-9][0-9]*)$",message = "Please Enter vailed number")
	@NotEmpty(message = "Mobile Number can not be empty")
	private String mobileNumber;
}
