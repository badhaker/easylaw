package com.vedalegal.util;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.vedalegal.enums.TokenType;

@Component
public class AppUtil {
	
	public static String generateResetPasswordVerificationToken(String email, TokenType resetPassword) {

		return UUID.randomUUID().toString()+""+email.hashCode()+""+ resetPassword.hashCode()+"$"+new Date().getTime();
		
	}
}
