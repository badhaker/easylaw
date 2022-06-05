package com.vedalegal.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
	private String verificationTocken;
	private String password;
}
