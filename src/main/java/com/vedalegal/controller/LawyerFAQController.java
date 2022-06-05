package com.vedalegal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.entity.FAQEntity;
import com.vedalegal.entity.LawyerFAQEntity;
import com.vedalegal.modal.FAQDTO;
import com.vedalegal.repository.LawyerFAQRepository;
import com.vedalegal.repository.QusAnsRepository;
import com.vedalegal.request.LawyerFAQList;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.FaqAndReview;
import com.vedalegal.response.LawyerFAQGetList;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.LawyerFAQService;

@RestController
@RequestMapping("/faq")
public class LawyerFAQController {

	@Autowired
	private LawyerFAQService lawyerFAQService;

	@Autowired
	private QusAnsRepository qusAnsRepository;

	@Autowired
	private LawyerFAQRepository lawyerFAQRepo;

	@PostMapping("/add")
	public  ResponseEntity<BaseApiResponse> addLawyerFAQ(@RequestBody LawyerFAQList list)
	{
		CommonSuccessResponse msg =  lawyerFAQService.addLawyerFAQ(list);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/get/{expertiseId}")
	public  ResponseEntity<BaseApiResponse> getExpertiseFAQ(@PathVariable Long expertiseId)
	{
		List<LawyerFAQGetList> news =  lawyerFAQService.getExpertiseFAQ(expertiseId);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(news);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/get/lawyerCompleteDetail")
	public  ResponseEntity<BaseApiResponse> getFAQandReview(@RequestParam Long expertiseId, @RequestParam Long lawyerId)
	{
		FaqAndReview faqAndReview =  lawyerFAQService.getFAQandReview(expertiseId, lawyerId);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(faqAndReview);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/getUserFAQList")
	public  List<FAQDTO> getUserFAQ()
	{
		List<FAQDTO> response = new ArrayList<>();
		List<FAQEntity> list = qusAnsRepository.findAll();
		for(FAQEntity entity : list)
		{
			FAQDTO faq =  FAQDTO.builder()
					.answer(entity.getAnswer())
					.question(entity.getQuestion())
					.active(entity.getActive())
					.id(entity.getId())
					.build();
			response.add(faq);
		}
		return response;
	}

//	@PutMapping("/editUserFAQList")
//	public  FAQDTO updateUserFAQ(@RequestBody FAQEntity entity)
//	{
//		FAQEntity response = lawyerFAQService.updateFAQ(entity);
//		return FAQDTO.builder()
//				.answer(response.getAnswer())
//				.question(response.getQuestion())
//				.active(response.getActive())
//				.id(response.getId())
//				.build();	 
//	}

	@GetMapping("/getLawyerFAQList")
	public  List<FAQDTO> getLawyerFAQ()
	{
		List<FAQDTO> response = new ArrayList<>();
		List<LawyerFAQEntity> list = lawyerFAQRepo.findAll();
		for(LawyerFAQEntity entity : list)
		{
			FAQDTO faq =  FAQDTO.builder()
					.answer(entity.getAnswer())
					.question(entity.getQuestion())
					.active(entity.getActive())
					.id(entity.getId())
					.build();
			response.add(faq);
		}
		return response;
	}

	@PutMapping("/editLawyerFAQList")
	public  ResponseEntity<BaseApiResponse> updateLawyerFAQ(@RequestBody LawyerFAQEntity entity)
	{
		CommonSuccessResponse response = lawyerFAQService.updateLawyerFAQ(entity);
//		return FAQDTO.builder()
//				.answer(response.getAnswer())
//				.question(response.getQuestion())
//				.active(response.getActive())
//				.id(response.getId())
//				.build();	 
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteLawyerFAQ")
	public String deleteLawyerFAQ(@RequestParam Long id)
	{
		lawyerFAQRepo.deleteById(id);
		return " deleted successfully";
	}
	
}
