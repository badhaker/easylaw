package com.vedalegal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.entity.TopServiceEntity;
import com.vedalegal.modal.MasterServiceDetailResponse;
import com.vedalegal.request.TopService;
import com.vedalegal.request.TopServicesList;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.TopServService;

@RestController
@RequestMapping("/topServices")
public class TopServiceController {
	
	@Autowired
	private TopServService topServService;


	@PostMapping("/add")
	 public ResponseEntity<BaseApiResponse> addTopServices(@RequestBody TopServicesList topServices){
		CommonSuccessResponse msg =  topServService.addTopServices(topServices);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getList")
	public  ResponseEntity<BaseApiResponse> getTopServicesList()
	{
		List<MasterServiceDetailResponse> list =  topServService.getTopServicesList();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getServiceList")
	public  ResponseEntity<BaseApiResponse> getTopServices()
	{
		List<TopServiceEntity> list =  topServService.getTopServices();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	  
}
