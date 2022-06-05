package com.vedalegal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.HomePageFAQEntity;
import com.vedalegal.entity.LawyerFAQEntity;
import com.vedalegal.exception.NoEntityInDatabaseException;
import com.vedalegal.repository.HomepageFAQRepository;
import com.vedalegal.request.HomepageFAQ;
import com.vedalegal.request.HomepageFAQList;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.FAQGetList;

@Service
public class HomepageFAQService {

	@Autowired
	private HomepageFAQRepository homepageFAQRepo;
	
	public CommonSuccessResponse updateFAQ(HomePageFAQEntity entity) {
		HomePageFAQEntity FAQ = homepageFAQRepo.findById(entity.getId()).orElse(null);
		if(!entity.getQuestion().equals(null) && !entity.getQuestion().equals(""))
			FAQ.setQuestion(entity.getQuestion());
			if(!entity.getAnswer().equals(null) && !entity.getAnswer().equals(""))
			FAQ.setAnswer(entity.getAnswer());
			homepageFAQRepo.save(FAQ);
		return new CommonSuccessResponse(true);
		
	}
	
	public CommonSuccessResponse addFAQ(HomepageFAQList list) {
//		homepageFAQRepo.deleteAll();
		list.getFAQList().stream().map(faq->convertToEntityAndSave(faq)).collect(Collectors.toList());
		return new CommonSuccessResponse(true);
		
	}
	private HomePageFAQEntity convertToEntityAndSave(HomepageFAQ faq) {
		
		HomePageFAQEntity entity= HomePageFAQEntity.builder()
				.question(faq.getQuestion())
				.answer(faq.getAnswer())
				.build();
		return homepageFAQRepo.save(entity);
		
		
		
	}

	public List<FAQGetList> getFAQ() {
		if(homepageFAQRepo.findAll().size()<=0)
		{
			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		List<FAQGetList> news=homepageFAQRepo.findAll().stream().map(n->convertToFAQGet(n)).collect(Collectors.toList());
		return news;
	}
	private FAQGetList convertToFAQGet(HomePageFAQEntity n) {
		return FAQGetList.builder().id(n.getId()).question(n.getQuestion()).answer(n.getAnswer()).build();
	}

}
