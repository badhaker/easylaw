package com.vedalegal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.AllExpertiseEntity;
import com.vedalegal.entity.LawyerFAQEntity;
import com.vedalegal.exception.LawyerOrUserNotFoundException;
import com.vedalegal.exception.NoEntityInDatabaseException;
import com.vedalegal.modal.LawyerDetailResponse;
import com.vedalegal.repository.AreaOfExpertiseRepository;
import com.vedalegal.repository.LawyerFAQRepository;
import com.vedalegal.repository.QusAnsRepository;
import com.vedalegal.request.LawyerFAQ;
import com.vedalegal.request.LawyerFAQList;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.ClientFeedbackGet;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.FaqAndReview;
import com.vedalegal.response.LawyerFAQGetList;

@Service
public class LawyerFAQService {
	@Autowired
	private LawyerFAQRepository lawyerFAQRepo;
	
	@Autowired
	private AreaOfExpertiseRepository areaOfExpertiseRepo;
	
	@Autowired
	ClientFeedbackService clientFeedbackService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private QusAnsRepository qusAnsRepository;

	public CommonSuccessResponse addLawyerFAQ(LawyerFAQList list) {
		AllExpertiseEntity exp=areaOfExpertiseRepo.findById(list.getExpertiseId()).orElse(null);
		if(exp==null )
		{
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.EXPERTISE_NAME_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.EXPERTISE_NAME_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.EXPERTISE_NAME_NOT_EXIST_MESSAGE);
		}
		
//		lawyerFAQRepo.findAllByExpertise(areaOfExpertiseRepo.findById(list.getExpertiseId()).get()).stream().forEach(faq-> lawyerFAQRepo.delete(faq));
		
		list.getFAQList().stream().map(faq->convertToEntityAndSave(faq, list.getExpertiseId())).collect(Collectors.toList());
		return new CommonSuccessResponse(true);
	}

	private LawyerFAQEntity convertToEntityAndSave(LawyerFAQ faq, Long expId) {
		LawyerFAQEntity entity= LawyerFAQEntity.builder()
				.question(faq.getQuestion())
				.answer(faq.getAnswer())
				.expertise(areaOfExpertiseRepo.findById(expId).get())
				.build();
		return lawyerFAQRepo.save(entity);
	}

	public List<LawyerFAQGetList> getExpertiseFAQ(Long expertiseId) {
		if(lawyerFAQRepo.findAllByExpertise(areaOfExpertiseRepo.findById(expertiseId).get()).size()<=0)
		{
			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		List<LawyerFAQGetList> news=lawyerFAQRepo.findAllByExpertise(areaOfExpertiseRepo.findById(expertiseId).get()).stream().map(n->convertToFAQGet(n)).collect(Collectors.toList());
		return news;
	}
	private LawyerFAQGetList convertToFAQGet(LawyerFAQEntity n) {
		return LawyerFAQGetList.builder()
				.id(n.getId())
				.question(n.getQuestion()).answer(n.getAnswer()).build();
	}

	public FaqAndReview getFAQandReview(Long expertiseId, Long lawyerId) {
		List<LawyerFAQGetList> faqList= getExpertiseFAQ(expertiseId);
		List<ClientFeedbackGet> reviewList=clientFeedbackService.getFeedbackForLawyer(lawyerId);
		LawyerDetailResponse lawyerDetail=userService.getLawyerDetail(lawyerId);
		return FaqAndReview.builder()
				.clientFeedbackList(reviewList)
				.faqList(faqList)
				.lawyerDetail(lawyerDetail)
				.build();
				
		 
	}
//		public FAQEntity updateFAQ(FAQEntity entity) {
//		FAQEntity FAQ = qusAnsRepository.getOne(entity.getId());
//		if(qusAnsRepository.findById(entity.getId()) != null)
//		{
//			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
//					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
//					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
//		}
//		if(!entity.getQuestion().equals(null) && !entity.getQuestion().equals(""))
//		FAQ.setQuestion(entity.getQuestion());
//		if(!entity.getAnswer().equals(null) && !entity.getAnswer().equals(""))
//		FAQ.setAnswer(entity.getAnswer());
//		qusAnsRepository.save(FAQ);
//		return FAQ;
//	}

	public CommonSuccessResponse updateLawyerFAQ(LawyerFAQEntity entity) {
		LawyerFAQEntity FAQ = lawyerFAQRepo.findById(entity.getId()).orElse(null);
		System.out.println(entity.getId());
		if(FAQ == null)
		{
			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
//		System.out.println(entity.getId());
		if(!entity.getQuestion().equals(null) && !entity.getQuestion().equals(""))
		FAQ.setQuestion(entity.getQuestion());
		if(!entity.getAnswer().equals(null) && !entity.getAnswer().equals(""))
		FAQ.setAnswer(entity.getAnswer());
		lawyerFAQRepo.save(FAQ);
		return new CommonSuccessResponse(true);
	}

}
