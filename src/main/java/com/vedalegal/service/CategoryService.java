package com.vedalegal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.CategoryEntity;
import com.vedalegal.entity.MasterServiceEntity;
import com.vedalegal.exception.CategoryNotFoundException;
import com.vedalegal.exception.MasterServiceNotExistException;
import com.vedalegal.modal.Category;
import com.vedalegal.modal.CategoryResponse;
import com.vedalegal.modal.SubServiceWebResponse;
import com.vedalegal.repository.CategoryRepository;
import com.vedalegal.resource.AppConstant;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private MasterSrvcService masterSrvcService;
	@Autowired
	private SubSrvcService subServService;
	

	/*
	 * @Autowired private SubServiceRepository subServiceRepository;
	 */

	@Autowired
	SubSrvcService subSrvcService;

	public Category findCategoryById(Long id) {
		return convertToModal(categoryRepository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException(
						AppConstant.ErrorTypes.CATEGORY_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.CATEGORY_NOT_EXIST_CODE,
						AppConstant.ErrorMessage.CATEGORY_NOT_EXIST_MESSAGE)));
	}

	
	  public CategoryEntity findByIds(Long id) { 
		  return categoryRepository.findById(id).orElse(new CategoryEntity()); 
		  }
	 

	public Category findById(Long id) {
		return convertToModal(categoryRepository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException(AppConstant.ErrorTypes.CATEGORY_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.CATEGORY_NOT_EXIST_CODE,
						AppConstant.ErrorMessage.CATEGORY_NOT_EXIST_MESSAGE)));
	}

	public Category deleteCategory(Long id) {
		Category category = findById(id);
		//category.setStatus(AppConstant.DeleteStatus.STATUS_DELETE); 
		return convertToModal(categoryRepository.save(convertToEntity(category)));

	}

	public List<Category> getCategoryByMasterService(Long masterServiceId) {
		MasterServiceEntity serviceEntity = masterSrvcService.findById(masterServiceId);
		//System.out.println(serviceEntity);
		if(serviceEntity.getId()==null || serviceEntity.getActive()==false)
		{
			throw new MasterServiceNotExistException(AppConstant.ErrorTypes.MASTER_SERVICE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.MASTER_SERVICE_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.MASTER_SERVICE_NOT_EXIST_MESSAGE);
		}
		return categoryRepository.findAllByServiceEntity(serviceEntity).stream().filter(c-> c.getActive()!=false).map(entity -> convertToModal(entity))
				.collect(Collectors.toList());
	}

	public Category addCategory(Category modal) {
		return convertToModal(categoryRepository.save(convertToEntity(modal)));
	}

	public CategoryEntity getCategoryByCategoryName(String categoryName) {
		return categoryRepository.findByName(categoryName);
		
	}

	
	
	public CategoryEntity convertToEntity(Category modal) {
		// List<SubServiceEntity> subServiceEntityList =
		// subServiceRepository.findAllByCategory(convertToEntity(modal));
			return CategoryEntity.builder()
				.name(modal.getName())
				//.status(modal.getStatus())
				//.serviceEntity(modal.getMasterService())
				// .subServiceList(subServiceEntityList)
				.build();
	}

	public CategoryEntity convertToEntity(Category modal, MasterServiceEntity masterEntity) {
			return CategoryEntity.builder()
				.name(modal.getName())
				//.status(modal.getStatus())
				.serviceEntity(masterEntity)
				// .subServiceList(subServiceEntityList)
				.build();
	}
	public Category convertToModal(CategoryEntity entity) {
		return Category.builder()
				.id(entity.getId())
				.name(entity.getName())
				//.status(entity.getStatus())
				// .subService(entity.getSubServiceList().stream().map(subSurviceEntity->subSrvcService.convertToModal(subSurviceEntity)).collect(Collectors.toList()))
				.build();
	}


	public List<Category> findAllByCategory(MasterServiceEntity convertToEntity) {

		return categoryRepository.findAllByServiceEntity(convertToEntity).stream()
		.map(masterServiceEntity -> convertToModal(masterServiceEntity)).collect(Collectors.toList());
	}


	public CategoryResponse convertToResponseModal(CategoryEntity cat) {

		List<SubServiceWebResponse> subServiceList=cat.getSubServiceList().stream().filter(s->s.getActive()==true).map(sub->subServService.convertToWebResponse(sub)).collect(Collectors.toList());
		 return CategoryResponse.builder()
		.id(cat.getId())
		.name(cat.getName())
		.subServices(subServiceList)
		.build();

	}


	}
