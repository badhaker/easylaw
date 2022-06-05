package com.vedalegal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.NewsEntity;
import com.vedalegal.exception.EnquiryNotFoundException;
import com.vedalegal.exception.NoEntityInDatabaseException;
import com.vedalegal.repository.NewsRepository;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.NewsGetList;

@Service
public class NewsService {
	@Autowired
	private NewsRepository newsRepo;
	
//	public CommonSuccessResponse addNewsHeadlines(NewsList newsList) {
//		newsRepo.deleteAll();
//		newsList.getNewsList().stream().map(news->convertToEntityAndSave(news)).collect(Collectors.toList());
//		return new CommonSuccessResponse(true);
//	}

//	private NewsEntity convertToEntityAndSave(NewsHeadline news) {
//		
//		NewsEntity newsEntity= NewsEntity.builder().headline(news.getHeadline()).build();
//		System.out.println("added");
//		return newsRepo.save(newsEntity);
//		
//		
//		
//	}

	public List<NewsGetList> getNewsHeadlines() {
		if(newsRepo.findAll().size()<=0)
		{
			throw new EnquiryNotFoundException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		List<NewsGetList> news=newsRepo.findAll().stream().map(n->convertToNewsGet(n)).collect(Collectors.toList());
		return news;
	}

	private NewsGetList convertToNewsGet(NewsEntity n) {
		return NewsGetList.builder()
				.id(n.getId())
				.headline(n.getHeadline())
				.createdAt(n.getCreated())
				.isActive(n.getActive())
				.updatedDate(n.getUpdated())
				.build();
	}

	public CommonSuccessResponse updateNewsHeadline(long newsId, String headline) {
		NewsEntity existNewEntity = newsRepo.findById(newsId).orElse(null);
		if(existNewEntity.getActive().equals(false)) {
			existNewEntity.setActive(true);
		}
		existNewEntity.setHeadline(headline);
		newsRepo.save(existNewEntity);
			return new CommonSuccessResponse(true);
		
	}

	public CommonSuccessResponse deleteNewsHeadline(long newsId) {
		NewsEntity existNewsEntity = newsRepo.findById(newsId).orElse(null);
		if(existNewsEntity==null)
		{
			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		if(existNewsEntity.getActive().equals(true)) {
			existNewsEntity.setActive(false);
		}
		newsRepo.save(existNewsEntity);
		return new CommonSuccessResponse(true);
	}

	public CommonSuccessResponse addNewHeadline(NewsEntity newsEntity) {
		newsRepo.save(newsEntity);
		return new CommonSuccessResponse(true);
	}


}
