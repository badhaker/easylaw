package com.vedalegal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.ImageService;

@RestController
@RequestMapping("/subServiceImage")
public class ImageController {
	
	@Autowired
	private ImageService imageService;

	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BaseApiResponse> deleteImage(@PathVariable long id)
    {
    	String msg= imageService.deleteImage(id);
    	BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	
    }
}
