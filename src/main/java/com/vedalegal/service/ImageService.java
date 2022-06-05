package com.vedalegal.service;

import com.vedalegal.entity.PlanDetailsEntity;
import com.vedalegal.entity.SubServiceEntity;
import com.vedalegal.modal.PlanDetails;
import com.vedalegal.modal.SubServiceImageDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.vedalegal.entity.ImageEntity;
import com.vedalegal.repository.ImageRepository;

@Service
public class ImageService {

	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private AmazonS3 amazonS3;

	@Value("${AWS_BUCKET_NAME}")
	private String bucketName;
	
	public String deleteImage(long id) {
		
		ImageEntity image= imageRepository.findById(id).orElse(null);
		String keyName=image.getImageURI();
		deletefileFromAWS(keyName);
		imageRepository.deleteById(id);
		return "Image deleted successfully";
		
	}
	@Async
	public void deletefileFromAWS(String keyName) {
		if (keyName==null || keyName =="") {
			return;
		}
		DeleteObjectRequest deleteRequest=new DeleteObjectRequest(bucketName,keyName);
		amazonS3.deleteObject(deleteRequest);
	}

}
