package com.vedalegal.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.vedalegal.resource.AppConstant;

@Service
public class SMSService {
	
	private HttpHeaders headers;
	private RestTemplate rest;

	public void sendVerificationSMS(String mobileNumber, String otp) {
		String message = AppConstant.OtpSmsService.MESSAGE.replace("{var1}", otp);
		
		System.out.println(AppConstant.OtpSmsService.MESSAGE);
		this.headers = new HttpHeaders();
		this.rest = new RestTemplate();

		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

		params.add("user", AppConstant.OtpSmsService.USERID);
		params.add("password", AppConstant.OtpSmsService.PASSWORD);
		params.add("senderid", AppConstant.OtpSmsService.ADMAGISTER_SENDERID);
		params.add("channel", AppConstant.OtpSmsService.ADMAGISTER_CHANNEL);
		params.add("DCS", String.valueOf(AppConstant.OtpSmsService.ADMAGISTER_DCS));
		params.add("flashsms", String.valueOf(AppConstant.OtpSmsService.ADMAGISTER_FLASH_SMS));
		params.add("number", mobileNumber);
		params.add("text", message);
		params.add("route", String.valueOf(AppConstant.OtpSmsService.ADMAGISTER_ROUTE));

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(AppConstant.OtpSmsService.ADMAGISTERURL)
				.queryParams(params).build();

		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		try {
			ResponseEntity<String> t = rest.exchange(uriComponents.toUri(), HttpMethod.GET, requestEntity,
					String.class);

		} catch (HttpStatusCodeException exception) {
			System.out.println(exception);

		}

	}

}
