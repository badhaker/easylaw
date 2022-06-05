package com.vedalegal.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vedalegal.entity.BannerEntity;
import com.vedalegal.entity.ClientReviewEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.exception.NoEntityInDatabaseException;
import com.vedalegal.repository.BannerRepository;
import com.vedalegal.request.BannerModal;
import com.vedalegal.resource.AppConstant;

@Service
public class BannerService {

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private BannerRepository bannerRepo;

//	public String addBanners(MultipartFile banner1, MultipartFile banner2, MultipartFile banner3, MultipartFile banner4,
//			MultipartFile banner5, MultipartFile banner6, MultipartFile banner7, MultipartFile banner8, Long serviceId1,
//			Long serviceId2, Long serviceId3, Long serviceId4, Long serviceId5, Long serviceId6, Long serviceId7,
//			Long serviceId8) throws IOException {
//
//		bannerRepo.findAll().stream().forEach(banner -> fileUploadService.deletefile(banner.getBannerUrl()));
//		bannerRepo.deleteAll();
//		if (banner1 != null && !(banner1.getSize() <= 0)) {
//			String originalFileName = banner1.getOriginalFilename();
//			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//			String filePath = "Banner/banner1" + extension;
//			fileUploadService.uploadFile(banner1, filePath);
//			bannerRepo.save(BannerEntity.builder().bannerUrl(filePath).serviceId(serviceId1).build());
//		}
//		if (banner2 != null && !(banner2.getSize() <= 0)) {
//			String originalFileName = banner2.getOriginalFilename();
//			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//			String filePath = "Banner/banner2" + extension;
//			fileUploadService.uploadFile(banner2, filePath);
//			bannerRepo.save(BannerEntity.builder().bannerUrl(filePath).serviceId(serviceId2).build());
//
//		}
//		if (banner3 != null && !(banner3.getSize() <= 0)) {
//			String originalFileName = banner3.getOriginalFilename();
//			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//			String filePath = "Banner/banner3" + extension;
//			fileUploadService.uploadFile(banner3, filePath);
//			bannerRepo.save(BannerEntity.builder().bannerUrl(filePath).serviceId(serviceId3).build());
//
//		}
//		if (banner4 != null && !(banner4.getSize() <= 0)) {
//			String originalFileName = banner4.getOriginalFilename();
//			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//			String filePath = "Banner/banner4" + extension;
//			fileUploadService.uploadFile(banner4, filePath);
//			bannerRepo.save(BannerEntity.builder().bannerUrl(filePath).serviceId(serviceId4).build());
//
//		}
//		if (banner5 != null && !(banner5.getSize() <= 0)) {
//			String originalFileName = banner5.getOriginalFilename();
//			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//			String filePath = "Banner/banner5" + extension;
//			fileUploadService.uploadFile(banner5, filePath);
//			bannerRepo.save(BannerEntity.builder().bannerUrl(filePath).serviceId(serviceId5).build());
//		}
//		if (banner6 != null && !(banner6.getSize() <= 0)) {
//			String originalFileName = banner6.getOriginalFilename();
//			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//			String filePath = "Banner/banner6" + extension;
//			fileUploadService.uploadFile(banner6, filePath);
//			bannerRepo.save(BannerEntity.builder().bannerUrl(filePath).serviceId(serviceId6).build());
//		}
//		if (banner7 != null && !(banner7.getSize() <= 0)) {
//			String originalFileName = banner7.getOriginalFilename();
//			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//			String filePath = "Banner/banner7" + extension;
//			fileUploadService.uploadFile(banner7, filePath);
//			bannerRepo.save(BannerEntity.builder().bannerUrl(filePath).serviceId(serviceId7).build());
//		}
//		if (banner8 != null && !(banner8.getSize() <= 0)) {
//			String originalFileName = banner8.getOriginalFilename();
//			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//			String filePath = "Banner/banner8" + extension;
//			fileUploadService.uploadFile(banner8, filePath);
//			bannerRepo.save(BannerEntity.builder().bannerUrl(filePath).serviceId(serviceId8).build());
//		}
//		return "banners added successfully";

//	}

	public String addBanner(MultipartFile banner, String hyperLink) throws IOException {

		String originalFileName = banner.getOriginalFilename();
		String filePath = originalFileName;
		fileUploadService.uploadFile(banner,filePath);
		bannerRepo.save(BannerEntity.builder().bannerUrl(filePath).hyperLink(hyperLink).build());
		return "banner added successfully";
	}

	public String updateBanner(MultipartFile banner, long id, String hyperLink) throws IOException {
		BannerEntity banners = bannerRepo.findById(id).orElse(null);
		if (banner != null && !(banner.getSize() <= 0)) {
		String originalFileName = banner.getOriginalFilename();
		String filePath = originalFileName;
		fileUploadService.uploadFile(banner, filePath);
		banners.setBannerUrl(filePath);
		}
		banners.setHyperLink(hyperLink);
		if(banners.getActive().equals(false)) {
		banners.setActive(true);
		
	}
		bannerRepo.save(banners);
		return "banner updated successfully";
	}

	public List<BannerModal> getBannerList() {
		if (bannerRepo.findAll().size() <= 0) {
			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE, AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		List<BannerModal> banners = bannerRepo.findAll().stream().map(banner -> convertToModal(banner))
				.collect(Collectors.toList());
		return banners;
	}
	
	public List<BannerModal> getBannerListWeb() {
		if (bannerRepo.findAll().size() <= 0) {
			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE, AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		List<BannerModal> banners = bannerRepo.findAll().stream().filter(rev->rev.getActive()!=false).map(banner -> convertToModal(banner))
				.collect(Collectors.toList());
		return banners;
	}

	private BannerModal convertToModal(BannerEntity banner) {

		return BannerModal.builder()
				.bannerUrl(fileUploadService.generatePreSignedUrlForFileDownload(banner.getBannerUrl()))
				.hyperLink(banner.getHyperLink())
				.id(banner.getId())
				.isActive(banner.getActive())
				.build();
	}

	public String deleteBanner(Long id) {
		BannerEntity rev=bannerRepo.findById(id).orElse(null);
		if(rev==null)
		{
			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		if(rev.getActive().equals(true)) {
		rev.setActive(false);
		}
		bannerRepo.save(rev);
		return "Banner deleted successfully";
	}
}
