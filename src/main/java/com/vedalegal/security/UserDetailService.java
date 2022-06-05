package com.vedalegal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.vedalegal.exception.AppException;
import com.vedalegal.resource.AppConstant;
@Component
public class UserDetailService implements UserDetailsService{
	
	@Autowired
	private UserDetailRepo userDetailRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDetailEntity entity=userDetailRepo.findByEmail(email).orElseThrow(()->
		new AppException(AppConstant.ErrorTypes.ENTITY_NOT_EXISTS_ERROR,
				AppConstant.ErrorCodes.ENTITY_NOT_EXISTS_ERROR_CODE,
				AppConstant.ErrorMessage.ENTITY_NOT_EXISTS_ERROR_MESSAGE));
		return UserDetail.create(entity);
	}
	
	

}
