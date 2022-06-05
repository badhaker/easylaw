package com.vedalegal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.entity.HomePageFAQEntity;
import com.vedalegal.repository.HomepageFAQRepository;
import com.vedalegal.request.HomepageFAQList;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.FAQGetList;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.HomepageFAQService;

@RestController
@RequestMapping("/homepage/FAQs")
public class HomepageFAQController {
	
	@Autowired
	private HomepageFAQService homepageFAQService;
	
	@Autowired
	private HomepageFAQRepository homepageFAQRepo;
	

	@PostMapping("/add")
	public  ResponseEntity<BaseApiResponse> addFAQ(@RequestBody HomepageFAQList list)
	{
		CommonSuccessResponse msg =  homepageFAQService.addFAQ(list);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@PutMapping("/edit")
	public  ResponseEntity<BaseApiResponse> updateFAQ(@RequestBody HomePageFAQEntity entity)
	{
		CommonSuccessResponse msg =  homepageFAQService.updateFAQ(entity);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public  ResponseEntity<BaseApiResponse> getFAQ()
	{
		List<FAQGetList> news =  homepageFAQService.getFAQ();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(news);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/deletereview")
	public String deleteReview(@RequestParam Long id)
	{
		homepageFAQRepo.deleteById(id);
		return "Review Deleted Successfully ";
	}
}
