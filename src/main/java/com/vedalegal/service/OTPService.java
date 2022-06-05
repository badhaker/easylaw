package com.vedalegal.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vedalegal.resource.AppConstant;

@Service
public class OTPService {
	
private LoadingCache<String,Integer> otpCache = null;
	
	public OTPService() {
		super();
		otpCache = CacheBuilder.newBuilder()
					.expireAfterAccess(AppConstant.OtpService.OTP_EXPIRY_TIME, TimeUnit.MINUTES)
					.build(new CacheLoader<String,Integer>(){
						public Integer load(String key) {
							return 0;
						}
					});	
	}
	public int generateOTP(String key) {
		Random random = new Random();
		int otp = random.nextInt(900000)+ 100000;
		otpCache.put(key, otp);
	
		return otp;
		
	}
	public int getOTP(String key) {
		try {
			
			return otpCache.get(key);
			
			
		}
		catch(Exception e) {
			
			return 0;
			
		}
	}
	public void clearOTP(String key) {
		otpCache.invalidate(key);
		
	}


}
