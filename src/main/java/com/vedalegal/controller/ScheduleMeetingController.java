package com.vedalegal.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vedalegal.request.ScheduleMeeting;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.ScheduleMeetService;

@RestController
@RequestMapping("/schedule")
public class ScheduleMeetingController {

	@Autowired
	private ScheduleMeetService scheduleMeetService;
	
	@PostMapping("")
	public ResponseEntity<BaseApiResponse> scheduleMeeting(@RequestBody ScheduleMeeting scheduleReq) throws AddressException, IOException, MessagingException 
	{
		
		CommonSuccessResponse msg =  scheduleMeetService.scheduleMeeting(scheduleReq);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
}
