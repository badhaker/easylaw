package com.vedalegal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.request.ClientFeedback;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ClientFeedbackGet;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.ClientFeedbackService;

@RestController
@RequestMapping("/feedback")
public class ClientFeedbackController {

	@Autowired
	ClientFeedbackService clientFeedbackService;

	@PostMapping("/send")
	public ResponseEntity<BaseApiResponse> addClientFeedback(@RequestBody ClientFeedback feedback){
		
		CommonSuccessResponse msg =  clientFeedbackService.addFeedback(feedback);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/get/{orderId}")
	public  ResponseEntity<BaseApiResponse> getReviewList(@PathVariable Long orderId)
	{
		ClientFeedbackGet feedback =  clientFeedbackService.getFeedback(orderId);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(feedback);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
 
	@GetMapping("/getFeedback/{lawyerId}")
	public  ResponseEntity<BaseApiResponse> getFeedbackListForLawyer(@PathVariable Long lawyerId)
	{
		List <ClientFeedbackGet> feedbackList =  clientFeedbackService.getFeedbackForLawyer(lawyerId);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(feedbackList);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
}
