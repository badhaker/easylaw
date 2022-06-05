package com.vedalegal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedalegal.modal.MasterService;
import com.vedalegal.modal.MasterServiceDetailResponse;
import com.vedalegal.modal.MasterServiceResponse;
import com.vedalegal.resource.RestMappingConstant;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.MasterSrvcService;

@RestController
@RequestMapping(RestMappingConstant.MasterServiceInterfaceUri.MASTER_SERVICE_BASE_URI)
public class MasterServiceController {

	@Autowired
	private MasterSrvcService masterSrvcService;

	@PostMapping(RestMappingConstant.MasterServiceInterfaceUri.MASTER_SERVICE_CREATE_URI)
	public ResponseEntity<BaseApiResponse> createMasterService(@RequestPart String jsonRequest,
			@RequestPart(value= "masterServiceImage",required = true) MultipartFile masterServiceImage) throws Exception
	{
		MasterService masterService=new ObjectMapper().readValue(jsonRequest, MasterService.class);
		MasterService service=masterSrvcService.createMasterService(masterService,masterServiceImage);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(service);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
 //api to fetch all the master services list
	@GetMapping
	public ResponseEntity<BaseApiResponse> getServicesList()
	{
		List<MasterServiceResponse> masterServiceList= masterSrvcService.getServices();
		BaseApiResponse baseApiResponse=ResponseBuilder.getSuccessResponse(masterServiceList);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
		/*List<MasterService> masterServiceList=masterSrvcService.getServices();
		BaseApiResponse baseApiResponse=ResponseBuilder.getSuccessResponse(masterServiceList);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);*/
	}
	
	@GetMapping("/getDetails")
	public ResponseEntity<BaseApiResponse> getServicesDetails()
	{
		List<MasterServiceDetailResponse> masterServiceDetail= masterSrvcService.getServicesDetails();
		BaseApiResponse baseApiResponse=ResponseBuilder.getSuccessResponse(masterServiceDetail);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
		/*List<MasterService> masterServiceList=masterSrvcService.getServices();
		BaseApiResponse baseApiResponse=ResponseBuilder.getSuccessResponse(masterServiceList);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);*/
	}
	
	@DeleteMapping("delete/{id}")
    public  ResponseEntity<BaseApiResponse> delete(@PathVariable Long id){

    	String msg =  masterSrvcService.deleteMasterService(id);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
        return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
    }
	

	@PostMapping("update")
    public  ResponseEntity<BaseApiResponse> update(@RequestPart String jsonRequest,
			@RequestPart(value= "masterServiceImage",required = false) MultipartFile masterServiceImage) throws Exception{

		MasterService masterService=new ObjectMapper().readValue(jsonRequest, MasterService.class);
    	String msg =  masterSrvcService.updateMasterService(masterServiceImage, masterService);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
        return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
    }
	
	
	


	

}
