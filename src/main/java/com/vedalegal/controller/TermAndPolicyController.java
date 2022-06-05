package com.vedalegal.controller;

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

import com.vedalegal.repository.TermAndPolicyRepository;
import com.vedalegal.request.TermsAndPolicy;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.response.TermsAndPolicyGet;
import com.vedalegal.service.TermAndPolicyService;

@RestController
@RequestMapping("/termAndPolicy")
public class TermAndPolicyController {
	
	@Autowired
	TermAndPolicyService termAndPolicyService;
	@Autowired
	TermAndPolicyRepository termAndPolicyRepo;
	
	@PostMapping("/add")
	public ResponseEntity<BaseApiResponse> addTermsAndPolicy(@RequestBody TermsAndPolicy terms)  {
		String msg = termAndPolicyService.addTermsAndPolicy(terms);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

	}
	@GetMapping("/get")
	public ResponseEntity<BaseApiResponse> getTermsAndPolicy() {
		TermsAndPolicyGet msg = termAndPolicyService.getTermsAndPolicy();	
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<BaseApiResponse> updateTermsAndPolicy(@PathVariable("id") Long id, @RequestBody TermsAndPolicy terms)  {
		String msg = termAndPolicyService.updateTermsAndPolicy(id,terms);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

	}

}
