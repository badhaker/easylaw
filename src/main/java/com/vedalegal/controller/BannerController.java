package com.vedalegal.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vedalegal.repository.BannerRepository;
import com.vedalegal.request.BannerModal;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.BannerService;

@RestController
@RequestMapping("/banners")
public class BannerController {
	
	
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private BannerRepository bannerRepo;
	
//	@PostMapping("/add")
//	public ResponseEntity<BaseApiResponse> addBanners(
//			@RequestPart MultipartFile banner1,@RequestPart MultipartFile banner2,
//			@RequestPart MultipartFile banner3,@RequestPart MultipartFile banner4,
//			@RequestPart MultipartFile banner5,@RequestPart MultipartFile banner6,
//			@RequestPart MultipartFile banner7,@RequestPart MultipartFile banner8,
//			@RequestParam Long serviceId1,@RequestParam Long serviceId2,
//			@RequestParam Long serviceId3,@RequestParam Long serviceId4,
//			@RequestParam Long serviceId5,@RequestParam Long serviceId6,
//			@RequestParam Long serviceId7,@RequestParam Long serviceId8) throws IOException {
//		String msg = bannerService.addBanners(banner1,banner2,banner3,banner4,banner5,banner6,banner7,banner8,
//				serviceId1,serviceId2,serviceId3,serviceId4,serviceId5,serviceId6,serviceId7,serviceId8);
//		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
//		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
//	}
	
	@PostMapping("/addBanners")
	public ResponseEntity<BaseApiResponse> addBanner(
			@RequestPart MultipartFile banner, @RequestParam("hyperLink")String hyperLink ) throws IOException {
		String msg = bannerService.addBanner(banner,hyperLink);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	
	
	@PutMapping("/updateBanners")
	public ResponseEntity<BaseApiResponse> updateBanner(
			@RequestPart(value= "banner",required = false) MultipartFile banner,
			@RequestParam("id") long id, @RequestParam(value= "hyperLink",required = false)String hyperLink ) throws IOException {
		String msg = bannerService.updateBanner(banner,id,hyperLink);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
 
	@GetMapping("/getList")
	public  ResponseEntity<BaseApiResponse> getBannerList()
	{
		List<BannerModal> list =  bannerService.getBannerList();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getListWeb")
	public  ResponseEntity<BaseApiResponse> getBannerListWeb()
	{
		List<BannerModal> list =  bannerService.getBannerListWeb();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(list);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteBanners")
	public ResponseEntity<BaseApiResponse> deleteBanner(@RequestParam Long id) {

		String msg = bannerService.deleteBanner(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
}
