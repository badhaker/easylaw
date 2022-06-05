package com.vedalegal.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vedalegal.entity.CategoryEntity;
import com.vedalegal.entity.MasterServiceEntity;
import com.vedalegal.exception.MasterServiceNotExistException;
import com.vedalegal.modal.Category;
import com.vedalegal.modal.CategoryResponse;
import com.vedalegal.modal.MasterService;
import com.vedalegal.modal.MasterServiceDetailResponse;
import com.vedalegal.modal.MasterServiceResponse;
import com.vedalegal.repository.CategoryRepository;
import com.vedalegal.repository.MasterServiceRepository;
import com.vedalegal.resource.AppConstant;

@Service
public class MasterSrvcService {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MasterServiceRepository masterServiceRepository;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private FileUploadService fileUploadService;
	

	public MasterService createMasterService(MasterService masterService, MultipartFile masterServiceImage) throws IOException {
		
		String originalFileName=masterServiceImage.getOriginalFilename();
		String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
		String filePath="masterService/service_"+masterService.getName()+extension;
		fileUploadService.uploadFile(masterServiceImage, filePath);
		MasterServiceEntity entity= masterServiceRepository.save(convertToEntity(masterService,filePath));
	
		List<Category> categoryList=masterService.getCategories();
		categoryList.forEach(c-> categoryRepo.save(categoryService.convertToEntity(c,entity )));
		return convertTomodal(entity);
		
		/*
		 * return
		 * convertToModal(masterServiceRepository.save(convertToEntity(masterService)
		 * .orElseThrow(() -> new
		 * MasterServiceNotCreatedException(AppConstant.ErrorTypes.
		 * MASTER_SERVICE_NOT_CREATED_ERROR,
		 * AppConstant.ErrorCodes.MASTER_SERVICE_NOT_CREATED_ERROR_CODE,
		 * AppConstant.ErrorMessage.MASTER_SERVICE_NOT_CREATED_MESSAGE))));
		 */
	}

	public MasterServiceEntity findById(Long masterServiceId) {
		return masterServiceRepository.findById(masterServiceId).orElse(new MasterServiceEntity());
	}

	public List<MasterServiceResponse> getServices() {
		
		if (masterServiceRepository.findAll().isEmpty()) {
			throw new MasterServiceNotExistException(AppConstant.ErrorTypes.MASTER_SERVICE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.MASTER_SERVICE_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.MASTER_SERVICE_NOT_EXIST_MESSAGE);
		}
		/*return masterServiceRepository.findAll().stream()
				.map(masterServiceEntity -> convertTomodal(masterServiceEntity)).collect(Collectors.toList());*/
		List<MasterServiceEntity> entityList=masterServiceRepository.findAll();
		List<MasterServiceResponse> responseList=entityList.stream().filter(m->m.getActive()==true).map(res-> convertToResponse(res)).collect(Collectors.toList());
		return responseList;
	}
	
	public List<MasterServiceDetailResponse> getServicesDetails()
	{
		if (masterServiceRepository.findAll().isEmpty()) {
			throw new MasterServiceNotExistException(AppConstant.ErrorTypes.MASTER_SERVICE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.MASTER_SERVICE_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.MASTER_SERVICE_NOT_EXIST_MESSAGE);
		}
		List<MasterServiceEntity> entityList=masterServiceRepository.findAll();
		List<MasterServiceDetailResponse> responseList=entityList.stream().filter(m->m.getActive()==true).map(res-> convertToDetailResponse(res)).collect(Collectors.toList());

		return responseList;
	}
	
	public String deleteMasterService(Long id) {
		MasterServiceEntity entity=masterServiceRepository.findById(id).orElse(null);
		if(entity==null || entity.getActive()==false)
		{
			throw new MasterServiceNotExistException(AppConstant.ErrorTypes.MASTER_SERVICE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.MASTER_SERVICE_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.MASTER_SERVICE_NOT_EXIST_MESSAGE);
		}
		//subServiceRepository.delete(entity);
		entity.setActive(false);
		entity.getCategories().stream().forEach(c-> {c.setActive(false);
													c.getSubServiceList().stream().forEach(s -> {
																								s.setActive(false);
																								s.getQuestionAnswers().stream().forEach(q-> q.setActive(false));
																								s.getPlanDetails().stream().forEach(p-> p.setActive(false));
							
																								});
													
		                                            });
		masterServiceRepository.save(entity);
		return "MasterService Status changed to Inactive";
	}
	
  
	public String updateMasterService(MultipartFile masterServiceImage, MasterService masterService) throws IOException {
	MasterServiceEntity entity=masterServiceRepository.findById(masterService.getId()).get();
	if(entity==null || entity.getActive()==false)
	{
		throw new MasterServiceNotExistException(AppConstant.ErrorTypes.MASTER_SERVICE_NOT_EXIST_ERROR,
				AppConstant.ErrorCodes.MASTER_SERVICE_NOT_EXIST_CODE,
				AppConstant.ErrorMessage.MASTER_SERVICE_NOT_EXIST_MESSAGE);
	}
	
			entity.setName(masterService.getName());
			entity.setDescription(masterService.getDescription());
//			entity.setImage(masterService.getImage());
			if (masterServiceImage!=null && masterServiceImage.getSize()>0) {
				fileUploadService.deletefile(entity.getImage());
				String originalFileName=masterServiceImage.getOriginalFilename();
				String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
				String filePath="masterService/service_"+masterService.getName()+extension;
				fileUploadService.uploadFile(masterServiceImage, filePath);
				entity.setImage(filePath);
			}
			
			
	List<Category> newCategory=masterService.getCategories();
	List<CategoryEntity>  oldCategory= entity.getCategories().stream().filter(c->c.getActive()!=false).collect(Collectors.toList());
	
	//System.out.println("New Size :"+newCategory.size()+ " and Old size: " + oldCategory.size());
	for(int i=0;i<=3; i++)
	{
		
		if(oldCategory.size()>i && newCategory.size()>i)
			{
			oldCategory.get(i).setName(newCategory.get(i).getName());
			//oldCategory.get(i).setActive(true);

			categoryRepo.save(oldCategory.get(i));
			}
		else if (oldCategory.size()>i)
			{
			oldCategory.get(i).setActive(false);
			oldCategory.get(i).getSubServiceList().stream().forEach(s -> {
				s.setActive(false);
				s.getQuestionAnswers().stream().forEach(q-> q.setActive(false));
				s.getPlanDetails().stream().forEach(p-> p.setActive(false));

				});
			categoryRepo.save(oldCategory.get(i));
			}
		else if(newCategory.size()>i) 
			{
			categoryRepo.save(categoryService.convertToEntity(newCategory.get(i),entity ));
			}		
	}
	masterServiceRepository.save(entity);
		return "MasterService updated";
	}


	private MasterServiceDetailResponse convertToDetailResponse(MasterServiceEntity res) {
		
		 List<CategoryResponse> categoryList= res.getCategories().stream().filter(c->c.getActive()!=false).map(cat->categoryService.convertToResponseModal(cat)).collect(Collectors.toList());
		 return MasterServiceDetailResponse.builder()
		.masterService(res.getName())
		.description(res.getDescription())
		.imgUrl(fileUploadService.generatePreSignedUrlForFileDownload(res.getImage()))
		.categories(categoryList)
		.masterServiceId(res.getId()).build();
		
	}

	private MasterServiceResponse convertToResponse(MasterServiceEntity entity){
		
		MasterServiceResponse res=  MasterServiceResponse.builder()
		.masterService(entity.getName())
		.masterServiceId(entity.getId())
		.description(entity.getDescription())
		.imgUrl(fileUploadService.generatePreSignedUrlForFileDownload(entity.getImage()))
		//.category1(entity.getCategories().get(0).getName())
		//.category2(entity.getCategories().get(1).getName())
		//.category3(entity.getCategories().get(2).getName())
		//.category4(entity.getCategories().get(3).getName())
		.build();
		List<CategoryEntity> listCategory=entity.getCategories().stream().filter(c->c.getActive()!=false).collect(Collectors.toList());
		if(listCategory.size()>=1 )
			res.setCategory1(entity.getCategories().get(0).getName());
			else 
				res.setCategory1("-");
		if(listCategory.size()>=2)
			res.setCategory2(entity.getCategories().get(1).getName());
			else 
				res.setCategory2("-");
		if(listCategory.size()>=3)
			res.setCategory3(entity.getCategories().get(2).getName());
			else  
				res.setCategory3("-");
		if(listCategory.size()>=4)
			res.setCategory4(entity.getCategories().get(3).getName());
			else 
				res.setCategory4("-");
		
		return res;
		
	}

	private MasterService convertTomodal(MasterServiceEntity entity) {
		return MasterService.builder()
				.id(entity.getId())
				.name(entity.getName())
				.description(entity.getDescription())
				.image(fileUploadService.generatePreSignedUrlForFileDownload(entity.getImage()))
//				.categories(entity.getCategories().stream() .map(categoryEntity ->categoryService.convertToModal(categoryEntity)).collect(Collectors.toList()))
				.build();
	}

	private MasterServiceEntity convertToEntity(MasterService modal,String imagePath) {
		//List<Category> categoryEntityList = categoryService.findAllByCategory(convertToEntity(modal));
		
		MasterServiceEntity entity=
				MasterServiceEntity.builder()
				.description(modal.getDescription())
//				.image(modal.getImage())
				.name(modal.getName())
				.image(imagePath)
				//.categories(modal.getCategories().stream()
				//.map(serviceModal -> categoryService.convertToEntity(serviceModal))
				//.collect(Collectors.toList()))
				.build();
		
		//entity.setId(modal.getId());
		return 	entity;
	}

	
}
