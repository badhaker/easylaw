package com.vedalegal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.BannerEntity;
import com.vedalegal.entity.MasterServiceEntity;
import com.vedalegal.entity.TopServiceEntity;
import com.vedalegal.exception.EnquiryNotFoundException;
import com.vedalegal.exception.MasterServiceNotExistException;
import com.vedalegal.exception.NoEntityInDatabaseException;
import com.vedalegal.modal.CategoryResponse;
import com.vedalegal.modal.MasterServiceDetailResponse;
import com.vedalegal.repository.MasterServiceRepository;
import com.vedalegal.repository.TopServiceRepository;
import com.vedalegal.request.TopService;
import com.vedalegal.request.TopServicesList;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.TopServicesGet;

@Service
public class TopServService {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private TopServiceRepository topServiceRepo;

	@Autowired
	private MasterServiceRepository masterServiceRepo;

	@Autowired
	private FileUploadService fileUploadService;

	public CommonSuccessResponse addTopServices(TopServicesList topServices) {
		

		topServiceRepo.deleteAll();
		topServices.getTopServices().stream().map(service -> topServiceRepo.save(convertToEntity(service)))
				.collect(Collectors.toList());
		return new CommonSuccessResponse(true);
	}

	private TopServiceEntity convertToEntity(TopService modal) {
		if (masterServiceRepo.findById(modal.getMasterServiceId()).get() == null
				|| masterServiceRepo.findById(modal.getMasterServiceId()).get().getActive() == false) {
			throw new MasterServiceNotExistException(AppConstant.ErrorTypes.MASTER_SERVICE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.MASTER_SERVICE_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.MASTER_SERVICE_NOT_EXIST_MESSAGE);

		}
		return TopServiceEntity.builder().masterServiceId(masterServiceRepo.findById(modal.getMasterServiceId()).get())
				.build();
	}

	public List<MasterServiceDetailResponse> getTopServicesList() {
		if (topServiceRepo.findAll().size() <= 0) {
			throw new EnquiryNotFoundException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE, AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		return topServiceRepo.findAll().stream().map(service -> convertToModal(service.getMasterServiceId()))
				.collect(Collectors.toList());

	}

	public List<TopServiceEntity> getTopServices() {
		if (topServiceRepo.findAll().size() <= 0) {
			throw new EnquiryNotFoundException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE, AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		return topServiceRepo.findAll().stream().collect(Collectors.toList());

	}

	private MasterServiceDetailResponse convertToModal(MasterServiceEntity res) {
		// TODO Auto-generated method stub
		// return TopServicesGet.builder()
		// .masterServiceId(service.getMasterServiceId().getId())
		// .masterServiceName(service.getMasterServiceId().getName()).build();
		List<CategoryResponse> categoryList = res.getCategories().stream().filter(c -> c.getActive() != false)
				.map(cat -> categoryService.convertToResponseModal(cat)).collect(Collectors.toList());
		return MasterServiceDetailResponse.builder().masterService(res.getName()).description(res.getDescription())
				.imgUrl(fileUploadService.generatePreSignedUrlForFileDownload(res.getImage())).categories(categoryList)
				.masterServiceId(res.getId()).build();
	}

}
