
package com.vedalegal.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.entity.PolicyEntity;
import com.vedalegal.repository.PolicyRepository;
import com.vedalegal.request.Policies;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.PolicyGet;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.PolicyService;

@RestController
@RequestMapping("/policy")
public class PolicyController {
	
	@Autowired
	PolicyService policyService;
	@Autowired
	PolicyRepository policyRepo;
	

	@PostMapping("/add")
	public ResponseEntity<BaseApiResponse> addPolicy(@RequestBody Policies policy) throws ParseException {
		PolicyEntity msg = policyService.addPolicy(policy);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

	}
	@GetMapping("/get")
	public ResponseEntity<BaseApiResponse> getPolicy() {
		PolicyGet msg = policyService.getPolicy();	
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
/*		
	@DeleteMapping("/delete/{policyId}")
	public ResponseEntity<BaseApiResponse> deletePolicy(@PathVariable("policyId") Long id) {
		String msg = policyService.deletePolicyById(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
*/	
	@PutMapping("/update/{policyId}")
	public ResponseEntity<BaseApiResponse> updatepolicy(@RequestBody Policies policy ,@PathVariable("policyId") Long id) {
		String msg = policyService.updatePolicyById(id, policy );
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
}
