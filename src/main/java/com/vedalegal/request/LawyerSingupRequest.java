package com.vedalegal.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerSingupRequest {
	
	@Email
	@NotEmpty(message = "Email cannot be empty")	
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",message = "Please Enter vailed email")
	private String email;

	@NotEmpty(message = "Name can not be empty")
	private String name;

	@NotEmpty
	private String password;
	
	@NotEmpty(message = "Mobile Number can not be empty")
	@Pattern(regexp = "^(0|[1-9][0-9]*)$",message = "Please Enter vailed number")
	private String mobileNumber;
	
	@NotEmpty(message = "Bar Council Number can not be empty")
	private String barCouncilNumber;
	
	@NotEmpty(message = "Bar Council Name can not be empty")
	private String barCouncilName;
	
}
