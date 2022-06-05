package com.vedalegal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.AllExpertiseEntity;
import com.vedalegal.entity.LawyerExpertiseEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.exception.LawyerOrUserNotFoundException;
import com.vedalegal.exception.NoEntityInDatabaseException;
import com.vedalegal.modal.ExpertService;
import com.vedalegal.modal.LawyerExpertise;
import com.vedalegal.modal.ServiceListResponse;
import com.vedalegal.repository.AreaOfExpertiseRepository;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.Expertise;

@Service
public class ExpertiseService {

	@Autowired
	private AreaOfExpertiseRepository areaOfExpertiseRepository;

	public CommonSuccessResponse addService(ExpertService expertService) {
		AllExpertiseEntity entity= new AllExpertiseEntity();
		entity.setAreaOfExpertiseName(expertService.getAreaOfExpertiseName());
		areaOfExpertiseRepository.save(entity);
		return new CommonSuccessResponse(true);
	
	}

	public List<ServiceListResponse> getServiceList() {
		
		List<AllExpertiseEntity> list=areaOfExpertiseRepository.findAll().stream().filter(exp->exp.getActive()!=false).collect(Collectors.toList());
		if(list.size()<=0)
		{
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.EXPERTISE_NAME_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.EXPERTISE_NAME_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.EXPERTISE_NAME_NOT_EXIST_MESSAGE);
		}
		return list.stream().map(serviceList -> convertToModal(serviceList))
				.collect(Collectors.toList());
		
	}
	
	public ServiceListResponse convertToModal(AllExpertiseEntity entity) {
		return ServiceListResponse.builder().id(entity.getId()).areaOfExpertiseName(entity.getAreaOfExpertiseName()).build() ;
	}

	public Expertise convertToExpertiseModal(LawyerExpertiseEntity exp) {
		return Expertise.builder().name(exp.getExpertiseNo().getAreaOfExpertiseName())
				.build();
		 
	}

	public LawyerExpertiseEntity convertToEntity(LawyerExpertise e, UserEntity lawyer) {

		/*if(areaOfExpertiseRepository.findByAreaOfExpertiseName(e.getExpertise())==null)
		{
			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.EXPERTISE_ENTITY_NOT_EXISTS_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}*/
   return LawyerExpertiseEntity.builder()
		   .lawyerId(lawyer)
		   .callPrice(e.getCallPrice())
		   .meetingPrice(e.getMeetingPrice())
//		   .expertiseNo(areaOfExpertiseRepository.findByAreaOfExpertiseName(e.getExpertise()))
		   .build();
	}

	public String deleteLawyerService(Long id) {
		
		AllExpertiseEntity entity= areaOfExpertiseRepository.findById(id).orElse(null);
		if(entity==null || entity.getActive()==false)
		{
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.EXPERTISE_NAME_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.EXPERTISE_NAME_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.EXPERTISE_NAME_NOT_EXIST_MESSAGE);
		}
		entity.setActive(false);
		areaOfExpertiseRepository.save(entity);
		return "Expertise Status changed to Inactive";
	}

}
