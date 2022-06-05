package com.vedalegal.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedalegal.entity.ClientReviewEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.request.ClientReview;
import com.vedalegal.request.ClientReviews;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ClientReviewList;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.ClientSpeakService;

@RestController
@RequestMapping("/review")
public class ClientSpeakController {

	@Autowired
	ClientSpeakService clientSpeakService;

	@PostMapping("/add")
	public ResponseEntity<BaseApiResponse> addClientReview(@RequestPart String jsonRequest,
			@RequestParam(value= "image",required = false) MultipartFile image) throws IOException {

		ClientReviews review = new ObjectMapper().readValue(jsonRequest, ClientReviews.class);
		CommonSuccessResponse msg = clientSpeakService.addReview(review, image);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/getList")
	public ResponseEntity<BaseApiResponse> getReviewList() {
		List<ClientReviewList> list = clientSpeakService.getReviewList();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/getclientspeaklist")
	public ResponseEntity<BaseApiResponse> getReviewListAdmin() {
		List<ClientReviewList> list = clientSpeakService.getReviewListAdmin();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<BaseApiResponse> deleteClientReview(@PathVariable Long id) {
		String msg = clientSpeakService.deleteClientReview(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	
	@PutMapping("/reviewsuspend")
	public ResponseEntity<BaseApiResponse> suspendReview(@RequestParam Long userId) {

		ClientReviewEntity entity = clientSpeakService.setReviewAsSuspend(userId);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(entity);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<BaseApiResponse> updateClientReview(@RequestPart String jsonRequest,
			@RequestPart(value= "image",required = false) MultipartFile image) throws IOException {

		ClientReview review = new ObjectMapper().readValue(jsonRequest, ClientReview.class);
		CommonSuccessResponse msg = clientSpeakService.updateReview(review, image);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
}
