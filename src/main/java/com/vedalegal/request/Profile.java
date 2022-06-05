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
public class Profile {

	private Long contactNo;
	
	@Email
	@NotEmpty(message = "Email cannot be empty")
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",message = "Please Enter vailed email")
	private String email;
	private String name;
	private String password;
	private String oldpassword;
	private boolean box;
	
}
