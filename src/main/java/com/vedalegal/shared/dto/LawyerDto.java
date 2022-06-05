package com.vedalegal.shared.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LawyerDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String userId;
	private String name;
	private String contactNo;
	private String email;
	private String password;
	private String barCouncilNo;
	private String encryptedPassword;
	private String emailVerification;
	private Boolean emailVerificationStatus = false;
	

}
