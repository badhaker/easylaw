package com.vedalegal.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedalegal.modal.SubService;
import com.vedalegal.modal.SubServiceDTO;
import com.vedalegal.modal.SubServiceResponse;
import com.vedalegal.request.Profile;
import com.vedalegal.request.UpdateSubServiceImageRequest;
import com.vedalegal.request.UploadSubServiceImageRequest;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.PlanService;
import com.vedalegal.service.QusAnsService;
import com.vedalegal.service.SubSrvcService;

@RestController
@RequestMapping("/subService")
public class SubServiceController {

	@Autowired
	private SubSrvcService subSrvcService;
	@Autowired
	private QusAnsService qusAnsService;
	@Autowired
	private PlanService planService;

	@GetMapping
	public ResponseEntity<BaseApiResponse> getAllSubServices() {
		List<SubServiceResponse> subServiceList = subSrvcService.getAllSubServices();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(subServiceList);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

	}

	@PostMapping("add")
	public ResponseEntity<BaseApiResponse> addSubService(@RequestPart String jsonRequest,
			@RequestPart(value = "subServiceImage", required = false) MultipartFile subServiceImage) throws Exception {

		SubService modal = new ObjectMapper().readValue(jsonRequest, SubService.class);
		SubService subService = subSrvcService.addSubService(modal, subServiceImage);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(subService);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

	}

	@GetMapping("get/{id}")
	public ResponseEntity<BaseApiResponse> getSubService(@PathVariable Long id) {
		SubService subService = subSrvcService.getSubServiceById(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(subService);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	/*
	 * @GetMapping("getAllByCategory/{id}") public ResponseEntity<?>
	 * getAllByCategory(@PathVariable Long id){ List<SubService> subService; try {
	 * subService = subSrvcService.getAllByCategory(id); }catch (Exception ex){
	 * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); } return new
	 * ResponseEntity<>(subService,HttpStatus.OK); }
	 */
	@DeleteMapping("delete/{id}")
	public ResponseEntity<BaseApiResponse> delete(@PathVariable Long id) {

		String msg = subSrvcService.deleteSubservice(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<BaseApiResponse> update(@PathVariable Long id, @RequestBody SubService subService) {

		String msg = subSrvcService.updateSubservice(id, subService);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PostMapping(path = "uploadSubServiceImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseApiResponse> uploadSubServiceImage(@RequestPart String jsonRequest
			) throws Exception {
		//@RequestPart(value = "subServiceImage", required = false) MultipartFile[] subServiceImage
		final ObjectMapper mapper = new ObjectMapper();
		UploadSubServiceImageRequest[] upload = mapper.readValue(jsonRequest, UploadSubServiceImageRequest[].class);
		List<UploadSubServiceImageRequest> langList = new ArrayList(Arrays.asList(upload));
		BaseApiResponse baseApiResponse = null;
		for (UploadSubServiceImageRequest uploadSubServiceImageRequest : langList) {

			CommonSuccessResponse response = subSrvcService.uploadSubServiceImage(uploadSubServiceImageRequest);
			baseApiResponse = ResponseBuilder.getSuccessResponse(response);

		}
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/deleteQusAns/{id}")
	public ResponseEntity<BaseApiResponse> deleteQusAns(@PathVariable Long id) {

		String msg = qusAnsService.deleteQusAns(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/deletePlan/{id}")
	public ResponseEntity<BaseApiResponse> deletePlan(@PathVariable Long id) {

		String msg = planService.deletePlan(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	/*
	 * @PutMapping("/editQusAns") public ResponseEntity<BaseApiResponse>
	 * editQusAns(@PathVariable Long id){
	 * 
	 * String msg = qusAnsService.deleteQusAns(id); BaseApiResponse baseApiResponse
	 * = ResponseBuilder.getSuccessResponse(msg); return new
	 * ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK); }
	 */
	@GetMapping("/search")
	public ResponseEntity<BaseApiResponse> searchByShortName(@RequestParam String name) {
		List<SubServiceDTO> subService = subSrvcService.searchByName(name);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(subService);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@DeleteMapping("deleteSubServiceImage/{id}")
	public ResponseEntity<BaseApiResponse> deleteDesc(@PathVariable Long id) {

		String msg = subSrvcService.deleteContent(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	@PutMapping(path = "updateSubServiceImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseApiResponse> updateSubserviceImage(@RequestPart String jsonRequest) throws IOException {
		UpdateSubServiceImageRequest[] profile = new ObjectMapper().readValue(jsonRequest,UpdateSubServiceImageRequest[].class);
		List<UpdateSubServiceImageRequest> langList = new ArrayList(Arrays.asList(profile));
		BaseApiResponse baseApiResponse = null;
		for (UpdateSubServiceImageRequest updateSubServiceImageRequest : langList) {
			
			 Long id=updateSubServiceImageRequest.getId();
			 String imageDescription=updateSubServiceImageRequest.getImageDescription();
			 String type=updateSubServiceImageRequest.getType();
			 Long subServiceId = updateSubServiceImageRequest.getSubServiceId();
			
			System.out.println(" getImageDescription---"+updateSubServiceImageRequest.getImageDescription());
			System.out.println(" type---"+updateSubServiceImageRequest.getType());
			System.out.println(" Id---"+updateSubServiceImageRequest.getId());
			
//			String response = subSrvcService.updateSubserviceImage(id, imageDescription,type);
			String response = subSrvcService.updateSubserviceImage(id, imageDescription,type,subServiceId);
			baseApiResponse = ResponseBuilder.getSuccessResponse(response);

	
	}
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

	}

	@GetMapping("/searchSubservice")
	public ResponseEntity<BaseApiResponse> searchBySubservice(@RequestParam String subService)
	{
		List<SubServiceResponse> subServiceResponse = subSrvcService.searchBySubservice(subService);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(subServiceResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
}
