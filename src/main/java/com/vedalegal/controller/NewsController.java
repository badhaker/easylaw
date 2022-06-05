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

import com.vedalegal.entity.NewsEntity;
import com.vedalegal.request.NewsList;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.NewsGetList;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.NewsService;

@RestController
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
//	@PostMapping("/addHeadlines")
//	public  ResponseEntity<BaseApiResponse> addNewsHeadlines(@RequestBody NewsList newsList)
//	{
//		CommonSuccessResponse msg =  newsService.addNewsHeadlines(newsList);
//		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
//		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
//	}
	@GetMapping("/getHeadlines")
	public  ResponseEntity<BaseApiResponse> getNewsHeadlines()
	{
		List<NewsGetList> news =  newsService.getNewsHeadlines();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(news);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PutMapping("/updateHeadlines")
	public ResponseEntity<BaseApiResponse> updateNewsHeadline(@RequestParam("newsId")  long newsId, @RequestParam String headline)
	{
		CommonSuccessResponse msg=new CommonSuccessResponse(false);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		if(newsId != 0 && headline!=null && !headline.isEmpty()) {
		 msg =  newsService.updateNewsHeadline(newsId, headline);
		 baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		}
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteHeadlines")
	public ResponseEntity<BaseApiResponse> deleteNewsHeadline(@RequestParam("newsId") long newsId)
	{
		CommonSuccessResponse msg=new CommonSuccessResponse(false);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		if(newsId != 0) {
		 msg =  newsService.deleteNewsHeadline(newsId);
		 baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		}
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	@PostMapping("/addHeadlines")
	public ResponseEntity<BaseApiResponse> addNewHeadline(@RequestBody NewsEntity newsEntity)
	{
		CommonSuccessResponse msg=new CommonSuccessResponse(false);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		if(newsEntity != null) {
		 msg =  newsService.addNewHeadline(newsEntity);
		 baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		}
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);	
	}
}
