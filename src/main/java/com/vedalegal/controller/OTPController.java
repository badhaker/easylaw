package com.vedalegal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.exception.InvalidInputException;
import com.vedalegal.exception.InvalidOtpException;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.resource.RestMappingConstant;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.OTPService;
import com.vedalegal.service.SMSService;


@RestController
@RequestMapping(RestMappingConstant.OTPRequestUri.OTP_BASE_URI)
public class OTPController {
	
	@Autowired
	public OTPService otpService;
	
	@Autowired
	SMSService smsService;
	
	@GetMapping(path = RestMappingConstant.OTPRequestUri.VALIDATE_OTP_SMS)
	public ResponseEntity<BaseApiResponse> validateOtpSms(@RequestParam(name = "phoneNumber") String phoneNumber,
			@RequestParam(name = "OTP") int otpClient) {

		BaseApiResponse baseApiResponse = null;

		String username = phoneNumber;

		// Validate the Otp
		if (otpClient >= 0) {

			int serverOtp = otpService.getOTP(username);
			if (serverOtp > 0) {
				if (otpClient == serverOtp) {
					otpService.clearOTP(username);
					baseApiResponse = ResponseBuilder.getSuccessResponse();
					return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

				} else {
					throw new InvalidOtpException(AppConstant.ErrorTypes.INVALID_OTP_ERROR,
							AppConstant.ErrorCodes.INVALID_OTP_ERROR_CODE,
							AppConstant.ErrorMessage.INVALID_OTP_MESSAGE);

				}
			} else {
				throw new InvalidInputException(AppConstant.ErrorTypes.INVALID_INPUT_ERROR,
						AppConstant.ErrorCodes.INVALID_INPUT_ERROR_CODE,
						AppConstant.ErrorMessage.INVALID_INPUT_MESSAGE);
			}
		} else {
			throw new InvalidInputException(AppConstant.ErrorTypes.INVALID_INPUT_ERROR,
					AppConstant.ErrorCodes.INVALID_INPUT_ERROR_CODE, AppConstant.ErrorMessage.INVALID_INPUT_MESSAGE);
		}

	}

	// *** GET API TO SEND OTP VIA SMS

	@GetMapping(path = RestMappingConstant.OTPRequestUri.GET_OTP_SMS + "/{phoneNumber}")
	public ResponseEntity<BaseApiResponse> generateOTPSMS(@PathVariable @NotBlank String phoneNumber,
			HttpServletRequest request)  {
		BaseApiResponse baseApiResponse = null;
		int otp = otpService.generateOTP(phoneNumber);
//		System.out.println(AppConstant.OtpSmsService.MESSAGE);
		String sendOTP = String.valueOf(otp);
		smsService.sendVerificationSMS(phoneNumber, sendOTP);
		baseApiResponse = ResponseBuilder.getSuccessResponse();
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}


}
