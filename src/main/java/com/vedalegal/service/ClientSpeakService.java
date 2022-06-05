package com.vedalegal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vedalegal.entity.ClientReviewEntity;
import com.vedalegal.exception.LawyerOrUserNotFoundException;
import com.vedalegal.exception.NoEntityInDatabaseException;
import com.vedalegal.repository.ClientSpeakRepository;
import com.vedalegal.request.ClientReview;
import com.vedalegal.request.ClientReviews;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.ClientReviewList;
import com.vedalegal.response.CommonSuccessResponse;

@Service
public class ClientSpeakService {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private ClientSpeakRepository clientSpeakRepo;
	
	public CommonSuccessResponse addReview(ClientReviews modal, MultipartFile reviewImage) throws IOException {
		String filePath = null;
		if (reviewImage!=null&&reviewImage.getSize()>0) {
				String originalFileName=reviewImage.getOriginalFilename();
				String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
				 filePath="Client/review"+extension;
				fileUploadService.uploadFile(reviewImage, filePath);
		}
				clientSpeakRepo.save(ClientReviewEntity.builder().clientName(modal.getClientName()).review(modal.getReview()).imageUrl(filePath).build());
		
		return new CommonSuccessResponse(true);
	}

	public List<ClientReviewList> getReviewList() {
		List<ClientReviewList> list= clientSpeakRepo.findAll().stream().filter(rev->rev.getActive()!=false).map(rev->convertToModal(rev)).collect(Collectors.toList());
		return list;
	}

	private ClientReviewList convertToModal(ClientReviewEntity rev) {
		return ClientReviewList.builder()
				.review(rev.getReview())
				.clientName(rev.getClientName())
				.reviewId(rev.getId())
				.imageUrl(fileUploadService.generatePreSignedUrlForFileDownload(rev.getImageUrl()))
				.isActive(rev.getActive())
				.build() ;
	}

	public List<ClientReviewList> getReviewListAdmin() {
		List<ClientReviewList> list= clientSpeakRepo.findAll().stream().map(rev->convertToModal(rev)).collect(Collectors.toList());
		return list;
	}

	private ClientReviewList convertToModel(ClientReviewEntity rev) {
		return ClientReviewList.builder()
				.review(rev.getReview())
				.clientName(rev.getClientName())
				.reviewId(rev.getId())
				.imageUrl(fileUploadService.generatePreSignedUrlForFileDownload(rev.getImageUrl()))
				.build() ;
	}
	
	private ClientReviewList convertToModals(ClientReviewEntity rev) {
		return ClientReviewList.builder()
				.review(rev.getReview())
				.clientName(rev.getClientName())
				.reviewId(rev.getId())
				.build() ;
	}
	
	public String deleteClientReview(Long id) {
		ClientReviewEntity rev=clientSpeakRepo.findById(id).orElse(null);
		if(rev==null)
		{
			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		fileUploadService.deletefile(rev.getImageUrl());

		rev.setActive(false);
		clientSpeakRepo.delete(rev);
		
		return "Review deleted successfully";
	}
	
	public ClientReviewEntity setReviewAsSuspend(Long reviewId) {
		ClientReviewEntity review=clientSpeakRepo.findById(reviewId).orElse(null);
		
		List<ClientReviewList> list= clientSpeakRepo.findAll().stream().filter(rev -> rev.getActive()==true).map(rev->convertToModals(rev)).collect(Collectors.toList());
		System.out.println(list.size());
		if(review==null)
		{
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.REVIEW_NOT_EXIST,
					AppConstant.ErrorCodes.REVIEW_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.REVIEW_NOT_EXIST_MESSAGE);
		}
		if(review.getActive())
		{
			
			review.setActive(false);
			
		}
	
		else
		{
			if(list.size()>=10) {
				throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.REVIEW_LIMIT_EXHAUSTED,
						AppConstant.ErrorCodes.REVIEW_LIMIT_CODE,
						AppConstant.ErrorMessage.REVIEW_NOT_EXAUHSTED_MESSAGE);
			}
			review.setActive(true);
			
		}
		clientSpeakRepo.save(review);
		return review;
	}
	
	public CommonSuccessResponse updateReview(ClientReview modal, MultipartFile reviewImage) throws IOException {
		//System.out.println("Existing"+clientSpeakRepo.findById(modal.getReviewId()));
			ClientReviewEntity entity=clientSpeakRepo.findById(modal.getReviewId()).orElse(null);
			if(entity==null)
			{
				
				throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
						AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
			}
			entity.setClientName(modal.getClientName());
			entity.setReview(modal.getReview());
			//clientSpeakRepo.save(entity);
			
			fileUploadService.deletefile(entity.getImageUrl());
			if (reviewImage!=null&&reviewImage.getSize()>0) {

				String originalFileName=reviewImage.getOriginalFilename();
				String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
				String filePath="Client/review_"+entity.getId()+extension;
				fileUploadService.uploadFile(reviewImage, filePath);
				entity.setImageUrl(filePath);
			}
			entity.setActive(true);
			clientSpeakRepo.save(entity);

		return new CommonSuccessResponse(true);
		}
}
