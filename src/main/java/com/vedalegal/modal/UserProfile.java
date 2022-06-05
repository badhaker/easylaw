package com.vedalegal.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class UserProfile {

	private Long contactNo;
	private String email;
	private String name;
	private String password;
	private String oldpassword;
	
}
