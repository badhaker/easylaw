package com.vedalegal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.modal.ExpertService;
import com.vedalegal.modal.ServiceListResponse;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.ExpertiseService;

@RestController
@RequestMapping("/area_of_expertise")
public class ExpertiseController {
	
	@Autowired
	ExpertiseService expertiseService;
	
	@PostMapping("/addExpertiseService")
	public  ResponseEntity<BaseApiResponse> addService(@RequestBody ExpertService expertService)
	{
		CommonSuccessResponse msg =  expertiseService.addService(expertService);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getExpertiseService")
	public  ResponseEntity<BaseApiResponse> getService()
	{
		List<ServiceListResponse> ser =  expertiseService.getServiceList();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(ser);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("delete/{id}")
    public  ResponseEntity<BaseApiResponse> deleteLaywerService(@PathVariable Long id){

    	String msg =  expertiseService.deleteLawyerService(id);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
        return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
    }
}