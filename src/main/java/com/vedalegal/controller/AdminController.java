package com.vedalegal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.enums.AdminDashboardFilterEnum;
import com.vedalegal.resource.AdminDashboardDataResponse;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.LawyerOrdersResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.AdminService;

@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@GetMapping("/LawyerOrdersList")
	public ResponseEntity<BaseApiResponse> getLawyerDetail(
			@RequestParam(value = AppConstant.Commons.PAGE_NUMBER, defaultValue = AppConstant.Commons.PAGE_DEFAULT_VALUE) int page,
			@RequestParam(value = AppConstant.Commons.PAGE_LIMIT, defaultValue = AppConstant.Commons.PAGE_LIMIT_VALUE) int limit) {
		   LawyerOrdersResponse response =  adminService.getOrderList(page,limit);
	       BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
	       return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/adminDashboard")
	public ResponseEntity<BaseApiResponse> getAdminDashboardData(
			@RequestParam AdminDashboardFilterEnum filterType) {
		   AdminDashboardDataResponse response =  adminService.getAdminDashboardData(filterType);
	       BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
	       return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

}
