package com.vedalegal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vedalegal.enums.EnquiryStatus;
import com.vedalegal.modal.GetEnquiryDetailsResponse;
import com.vedalegal.modal.SendEnquiry;
import com.vedalegal.request.AssignAssociateReq;
import com.vedalegal.request.AssignReq;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.EnquiryList;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.EnquiryService;

@RestController
@RequestMapping("/enquiry")
public class EnquiryController {
	
	@Autowired
	EnquiryService enquiryService;
	
	
	@PostMapping("/sendEnquiry")
	 public ResponseEntity<BaseApiResponse> sendEnquiry(@RequestBody SendEnquiry sendEnquiry){
		CommonSuccessResponse msg =  enquiryService.sendEnquiry(sendEnquiry);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@PostMapping("/sendLoginEnquiry")
	 public ResponseEntity<BaseApiResponse> sendLoginEnquiry(@RequestBody SendEnquiry sendEnquiry){
		CommonSuccessResponse msg =  enquiryService.sendLoginEnquiry(sendEnquiry);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getEnquiryDetails/{id}")
	 public ResponseEntity<BaseApiResponse> getEnquiryDetails(@PathVariable Long id){
		GetEnquiryDetailsResponse msg =  enquiryService.getEnquiryDetails(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getList")
	public  ResponseEntity<BaseApiResponse> getEnquiryList(
			@RequestParam(value = AppConstant.Commons.PAGE_NUMBER, defaultValue = AppConstant.Commons.PAGE_DEFAULT_VALUE) int page,
			@RequestParam(required = false) String search,@RequestParam EnquiryStatus status)
	{
		List<EnquiryList> list =  enquiryService.getServiceList(page,page, search,status);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/getLisst")
	public ResponseEntity<BaseApiResponse> getEnquiryList(
			@RequestParam(required = false) String search,
			@RequestParam EnquiryStatus status
	)
	{
		List<EnquiryList> list =  enquiryService.getServiceList(search,status);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@PutMapping("/assignAssociate/{enquiryId}")
	public ResponseEntity<BaseApiResponse> assignEasyLawAssociate(@RequestBody AssignAssociateReq enq, @PathVariable Long enquiryId)
	{
		String msg=enquiryService.assignEasyLawAssociate(enq,enquiryId );
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getEnqueryList")
	public  ResponseEntity<BaseApiResponse> getEnquiryList(String email)
	{
		List<EnquiryList> list =  enquiryService.getUserServiceList(email);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/deleteEnquiry/{enquiryId}")
	public ResponseEntity<BaseApiResponse> deleteEnquiry(@PathVariable Long enquiryId)
	{
		String response = enquiryService.deleteEnquiry(enquiryId);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	@PostMapping("/sendContactEnquiry")
	 public ResponseEntity<BaseApiResponse> sendContactEnquiry(@RequestBody SendEnquiry sendEnquiry){
		CommonSuccessResponse msg =  enquiryService.sendContactEnquiry(sendEnquiry);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

}
