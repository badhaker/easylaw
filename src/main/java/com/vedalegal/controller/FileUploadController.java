package com.vedalegal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.FileUploadService;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<BaseApiResponse> uploadFile(@RequestPart("image") MultipartFile image){
       /* String fileURI;
        try {
            fileURI =  fileUploadService.uploadFile(image);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(fileURI,HttpStatus.OK);*/
    	
    	String fileURI= fileUploadService.uploadFile(image);
    	BaseApiResponse baseApiResponse= ResponseBuilder.getSuccessResponse(fileURI);
    	return new ResponseEntity<BaseApiResponse> (baseApiResponse,HttpStatus.OK);
    }
}
