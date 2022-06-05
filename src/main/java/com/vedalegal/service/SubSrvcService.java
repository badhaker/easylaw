package com.vedalegal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vedalegal.entity.CategoryEntity;
import com.vedalegal.entity.FAQEntity;
import com.vedalegal.entity.ImageEntity;
import com.vedalegal.entity.PlanDetailsEntity;
import com.vedalegal.entity.SubServiceEntity;
import com.vedalegal.exception.AppException;
import com.vedalegal.exception.SubServiceNotExistException;
import com.vedalegal.modal.SubService;
import com.vedalegal.modal.SubServiceDTO;
import com.vedalegal.modal.SubServiceImageDetails;
import com.vedalegal.modal.SubServiceResponse;
import com.vedalegal.modal.SubServiceWebResponse;
import com.vedalegal.repository.ImageRepository;
import com.vedalegal.repository.PlanRepository;
import com.vedalegal.repository.QusAnsRepository;
import com.vedalegal.repository.SubServiceRepository;
import com.vedalegal.request.UpdateSubServiceImageRequest;
import com.vedalegal.request.UploadSubServiceImageRequest;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.CommonSuccessResponse;

@Service
public class SubSrvcService {

	@Autowired
	private SubServiceRepository subServiceRepository;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private QusAnsRepository qusAnsRepo;
	@Autowired
	private QusAnsService qusAnsService;
	@Autowired
	private PlanService planService;
	@Autowired
	private PlanRepository planRepo;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private FileUploadService fileUploadService;

	public List<SubServiceResponse> getAllSubServices() {
		List<SubServiceEntity> subServiceList = subServiceRepository.findAll().stream().filter(s->s.getActive()==true).collect(Collectors.toList());
		if (subServiceList.size() <= 0) {
			throw new SubServiceNotExistException(AppConstant.ErrorTypes.SUBSERVICE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.SUBSERVICE_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.SUBSERVICE_NOT_EXIST_MESSAGE);
		}
		
		List<SubServiceResponse> response = subServiceList.stream().filter(t->t.getActive()==true).map(sub -> convertToServiceResponse(sub))
				.collect(Collectors.toList());
		return response;
	}

	public SubService addSubService(SubService modal, MultipartFile subServiceImage) throws IOException {
		
		String filePath=null;
		if (subServiceImage!=null&& !subServiceImage.isEmpty()) {
			String originalFileName=subServiceImage.getOriginalFilename();
			String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
			filePath="subService/service_"+modal.getName()+extension;
			fileUploadService.uploadFile(subServiceImage, filePath);
		}
		
		
		CategoryEntity category = categoryService.findByIds(modal.getCategoryId());
		SubServiceEntity subEntity = subServiceRepository.save(convertToEntity(modal, category,filePath));
		//System.out.println("back in old method");
		
		List<FAQEntity> qusAnsList=modal.getQusAns().stream().map(qusAns->qusAnsService.convertToEntity(qusAns, subEntity)).collect(Collectors.toList());
		qusAnsList.forEach(qusAns-> qusAnsRepo.save(qusAns));
		List<PlanDetailsEntity> planList=modal.getPlanDetails().stream().map(plan->planService.convertToEntity(plan,subEntity)).collect(Collectors.toList());
		planList.forEach(plan->planRepo.save(plan));
		
		return convertToModal(subEntity);
	}
	
	public SubService getSubServiceById(Long id)
	{
		//return masterServiceRepository.findById(masterServiceId).orElse(new MasterServiceEntity());
		SubServiceEntity subService=subServiceRepository.findById(id).orElse(null);
		if(subService==null || subService.getActive()==false)
		{
			throw new SubServiceNotExistException(AppConstant.ErrorTypes.SUBSERVICE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.SUBSERVICE_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.SUBSERVICE_NOT_EXIST_MESSAGE);
		}
		return convertToModal(subServiceRepository.findById(id).get());
	} 
	


	private SubServiceEntity convertToEntity(SubService modal, CategoryEntity category,String filePath) {
		SubServiceEntity entity=null;
		
		if (modal.getId()!=null && modal.getId()!=0) {	
			entity=subServiceRepository.findById(modal.getId()).orElse(null);
		}
		if (entity!=null) {
			//System.out.println("old sub service");

			entity.getQuestionAnswers().stream().forEach(q->qusAnsRepo.delete(q) );
			entity.getPlanDetails().stream().forEach(p-> planRepo.delete(p));
			System.out.println("FAQ and PLans deleted");

			entity.setName(modal.getName());
			entity.setCategory(category);
			entity.setMainImage(filePath);
			entity.setQuestionAnswers(modal.getQusAns().stream().map(qusAns->qusAnsService.convertToEntity(qusAns)).collect(Collectors.toList()));
			entity.setPlanDetails(modal.getPlanDetails().stream().map(plan->planService.convertToEntity(plan)).collect(Collectors.toList()));
			entity.setRefundApplicable(modal.isRefundApplicable());
			//System.out.println("returning from existing");
			return entity;		
			
		}
		//System.out.println("new sub service");
		entity= SubServiceEntity.builder()
				.name(modal.getName())
				.category(category)
				.mainImage(filePath)
				.questionAnswers(modal.getQusAns().stream().map(qusAns->qusAnsService.convertToEntity(qusAns)).collect(Collectors.toList()))
				.planDetails(modal.getPlanDetails().stream().map(plan->planService.convertToEntity(plan)).collect(Collectors.toList()))
				.refundApplicable(modal.isRefundApplicable()).build();
		   // System.out.println("returning from new");

		return entity;
	}


	public SubServiceEntity convertToEntity(SubService modal) {
		return SubServiceEntity.builder()
				.name(modal.getName())
				.refundApplicable(modal.isRefundApplicable()).build();
	}

	public SubService convertToModal(SubServiceEntity entity) {
		SubService subService= SubService.builder().id(entity.getId()).name(entity.getName())
				
				.mainImage(fileUploadService.generatePreSignedUrlForFileDownload(entity.getMainImage()))
				.masterServiceId(entity.getCategory().getServiceEntity().getId())
				.qusAns(entity.getQuestionAnswers().stream().filter(q->q.getActive()!=false).map(qusAns->qusAnsService.convertToModal(qusAns)).collect(Collectors.toList()))
				.planDetails(entity.getPlanDetails().stream().filter(p->p.getActive()!=false).map(plan->planService.convertToModal(plan)).collect(Collectors.toList()))
				.refundApplicable(entity.isRefundApplicable()).build();

		if (entity.getImages()!=null && !entity.getImages().isEmpty()) {
			subService.setImageDescr(entity.getImages().stream().filter(aa -> aa.getActive()==true).map(il->SubServiceImageDetails.builder()
					.id(il.getId())
					.type(il.getType())
					.imgDescription(il.getImgDescription())
					.imageURI(fileUploadService.generatePreSignedUrlForFileDownload(il.getImageURI()))
					.build()).collect(Collectors.toList()));
		}
		
		if (entity.getCategory()!=null) {
			subService.setCategoryId(entity.getCategory().getId());
			subService.setCategoryName(entity.getCategory().getName());
		}
		return subService;
	}

	public SubServiceResponse convertToServiceResponse(SubServiceEntity entity) {
		return SubServiceResponse.builder().id(entity.getId()).subService(entity.getName())
				.category(entity.getCategory().getName())
				.masterSercice(entity.getCategory().getServiceEntity().getName()).build();
	}

	public SubServiceWebResponse convertToWebResponse(SubServiceEntity sub) {

		return SubServiceWebResponse.builder()
		.id(sub.getId())
		.name(sub.getName()).build();
		
	}
	
	public String deleteSubservice(Long id) {
		SubServiceEntity entity=subServiceRepository.findById(id).orElse(null);
		if(entity==null || entity.getActive()==false)
		{
			throw new SubServiceNotExistException(AppConstant.ErrorTypes.SUBSERVICE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.SUBSERVICE_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.SUBSERVICE_NOT_EXIST_MESSAGE);
		}
		
		entity.setActive(false);
		entity.getQuestionAnswers().stream().forEach(q-> q.setActive(false));
		entity.getPlanDetails().stream().forEach(p-> p.setActive(false));
		subServiceRepository.save(entity);
		return "SubService Status changed to Inactive";
	}

	public CommonSuccessResponse uploadSubServiceImage(UploadSubServiceImageRequest uploadSubServiceImageRequest
			) throws IOException {
		Long subServiceImgId=uploadSubServiceImageRequest.getSubServiceImgId();
		
		SubServiceEntity entity=subServiceRepository.findById(uploadSubServiceImageRequest.getSubServiceId()).orElseThrow(()->
				new AppException(AppConstant.ErrorTypes.ENTITY_NOT_EXISTS_ERROR,
						AppConstant.ErrorCodes.ENTITY_NOT_EXISTS_ERROR_CODE,
						AppConstant.ErrorMessage.ENTITY_NOT_EXISTS_ERROR_MESSAGE));
		String filePath="";
		
        
//		for (MultipartFile multipleFile : subServiceImage) {
//           int flag;
//			int flagg = flag+1;
//		if (multipleFile!=null&&multipleFile.getSize()>0) {
//			String originalFileName=multipleFile.getOriginalFilename();
//			String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
//			//filePath="subService/service_"+entity.getName()+"/"+uploadSubServiceImageRequest.getType()+extension;
//			filePath="subService/service_"+entity.getName()+"/" +originalFileName;
//			fileUploadService.uploadFile(multipleFile, filePath);
//			
//		}
	
		if (subServiceImgId!=null&&subServiceImgId!=0) {
			ImageEntity updatImageEntity=imageRepository.findById(subServiceImgId).orElseThrow(()->
					new AppException(AppConstant.ErrorTypes.ENTITY_NOT_EXISTS_ERROR,
							AppConstant.ErrorCodes.ENTITY_NOT_EXISTS_ERROR_CODE,
							AppConstant.ErrorMessage.ENTITY_NOT_EXISTS_ERROR_MESSAGE));
			
			String imageDescription=uploadSubServiceImageRequest.getImageDescription();
			String type=uploadSubServiceImageRequest.getType();
			
			if (imageDescription!=null&&!imageDescription.isEmpty()) {
				updatImageEntity.setImgDescription(imageDescription);
			}
			if (type!=null) {
				updatImageEntity.setType(type);
			}
			
			if (filePath!="") {
				updatImageEntity.setImageURI(imageDescription);
			}
			
		}
		
		ImageEntity imageEntity=ImageEntity.builder()
				.imageURI(filePath)
				.type(uploadSubServiceImageRequest.getType())
				.imgDescription(uploadSubServiceImageRequest.getImageDescription())
				.subService(entity)
				.build();
		
		
		imageRepository.save(imageEntity);

		
		return new CommonSuccessResponse(true);
	}
	public String updateSubservice(Long id, SubService subService) {
		SubServiceEntity entity=subServiceRepository.findById(id).orElse(null) ;
		if(entity==null ||entity.getActive()==false)
			throw new SubServiceNotExistException(AppConstant.ErrorTypes.SUBSERVICE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.SUBSERVICE_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.SUBSERVICE_NOT_EXIST_MESSAGE);
		
		//entity.setDescription(subService.getDescription());
		entity.setName(subService.getName());
		entity.setRefundApplicable(subService.isRefundApplicable());
	    //entity.se
		return null;
	}

	public List<SubServiceDTO> searchByName(String name) {
		List<SubServiceEntity> entity=subServiceRepository.findByShortName(name);
		List<SubServiceDTO> serviceEntity = new ArrayList<>();
		for(SubServiceEntity subEntity : entity)
		{
			SubServiceDTO response = convertToSubService(subEntity);
		serviceEntity.add(response);
		}
		return serviceEntity;
	}
	
	private SubServiceDTO convertToSubService(SubServiceEntity lawyer) {
		return SubServiceDTO.builder()
				.id(lawyer.getId())
				.subService(lawyer.getName())
				.masterSercice(lawyer.getCategory().getServiceEntity().getName())
				.category(lawyer.getCategory().getName())
				.build();
	}

	public String deleteContent(Long id) {
		ImageEntity entity=imageRepository.findById(id).orElse(null);
		if(entity==null)
		{
			throw new SubServiceNotExistException(AppConstant.ErrorTypes.SUBSERVICE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.SUBSERVICE_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.SUBSERVICE_NOT_EXIST_MESSAGE);
		}
		imageRepository.deleteById(id);
		/*entity.setActive(false);
		imageRepository.save(entity);
		return "SubService Content Status changed to Inactive";*/
		return "SubService Image has been deleted";
	}
	
	public String updateSubserviceImage(Long id,String imageDescription,String type,Long subServiceId) throws IOException{

		if(id==null)
		{
			Optional<SubServiceEntity> subService = subServiceRepository.findById(subServiceId);
			ImageEntity imageEntity = new ImageEntity();
			imageEntity.setImgDescription(imageDescription);
			imageEntity.setType(type);
			imageEntity.setSubService(subService.get());
			imageRepository.save(imageEntity);
			return "SubService created sucessfully";
		}
		else {
			ImageEntity entity = imageRepository.findById(id).orElse(null);
			if (entity == null || entity.getActive() == false) {
				throw new SubServiceNotExistException(AppConstant.ErrorTypes.SUBSERVICE_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.SUBSERVICE_NOT_EXIST_CODE,
						AppConstant.ErrorMessage.SUBSERVICE_NOT_EXIST_MESSAGE);
			}


//		String filePath="";
//		for(MultipartFile multipleFile : subServiceImage) {
//		
//		if (multipleFile!=null&&multipleFile.getSize()>0) {
//			String originalFileName=multipleFile.getOriginalFilename();
//			String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
//			//filePath="subService/service /"+type+extension;
//			filePath="subService/service /"+type+"/"+originalFileName;
//			fileUploadService.uploadFile(multipleFile, filePath);
//		
//		   }


			//	entity.setImageURI(filePath);
			else {
				entity.setImgDescription(imageDescription);
				entity.setType(type);
				imageRepository.save(entity);
				//}
				return "SubService updated sucessfully";
			}
		}
	}

	public List<SubServiceResponse> searchBySubservice(String subService)
	{
		List<SubServiceEntity> subServiceEntity = subServiceRepository.findBySubservice(subService);
		List<SubServiceResponse> subServiceResponse = new ArrayList<SubServiceResponse>();
		if(subServiceEntity.size()>0)
		{
			for(SubServiceEntity i : subServiceEntity) {
				SubServiceResponse subServiceResponse1 = new SubServiceResponse();
				subServiceResponse1.setId(i.getId());
				subServiceResponse1.setSubService(i.getName());
				subServiceResponse1.setMasterSercice(i.getCategory().getServiceEntity().getName());
				subServiceResponse1.setCategory(i.getCategory().getName());
				subServiceResponse.add(subServiceResponse1);
			}
		}
		return subServiceResponse;
	}
}
