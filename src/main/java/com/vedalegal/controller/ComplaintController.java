package com.vedalegal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vedalegal.enums.ComplaintStatusEnum;
import com.vedalegal.request.Complaint;
import com.vedalegal.request.UpdateComplaintRequest;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.ComplaintList;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.ComplaintService;

@RestController
@RequestMapping("/complaint")
public class ComplaintController {
	
	@Autowired
	ComplaintService complaintService;
 
	@PostMapping("/add")
	public ResponseEntity<BaseApiResponse> sendComplaint(@RequestBody Complaint complaint){
		CommonSuccessResponse msg =  complaintService.addComplaint(complaint);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/getList")
	public  ResponseEntity<BaseApiResponse> getComplaintList(@RequestParam ComplaintStatusEnum status,
			@RequestParam(value = AppConstant.Commons.PAGE_NUMBER, defaultValue = AppConstant.Commons.PAGE_DEFAULT_VALUE) int page,
			@RequestParam(value = AppConstant.Commons.PAGE_LIMIT, defaultValue = AppConstant.Commons.PAGE_LIMIT_VALUE) int limit,
			@RequestParam(required = false) String search)
	{
		List<ComplaintList> list =  complaintService.getComplaintList(status,page,limit,search);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	
	/*
	@GetMapping("/getDetails/{id}")
	 public ResponseEntity<BaseApiResponse> getComplaintDetails(@PathVariable Long id){
		ComplaintList msg =  complaintService.getEnquiryDetails(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
*/	
	@PutMapping("/updateComplaint")
	public  ResponseEntity<BaseApiResponse> updateComplaint(@RequestBody UpdateComplaintRequest updateComplaintRequest
			)
	{
		CommonSuccessResponse response =  complaintService.updateComplaint(updateComplaintRequest);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/deleteComplaint/{complaintId}")
	public ResponseEntity<BaseApiResponse> deleteComplaint(@PathVariable Long complaintId)
	{
		String response = complaintService.deleteComplaint(complaintId);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

}
