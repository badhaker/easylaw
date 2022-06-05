package com.vedalegal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vedalegal.entity.PlanDetailsEntity;
import com.vedalegal.entity.SubServiceEntity;
import com.vedalegal.modal.PlanDetails;
import com.vedalegal.repository.PlanRepository;

@Component
public class PlanService {

	@Autowired
	private PlanRepository planRepo;
	
	public PlanDetailsEntity convertToEntity(PlanDetails modal,SubServiceEntity service)
	{
		
		 return PlanDetailsEntity.builder()
		.price(modal.getPrice())
		.benefits(modal.getBenefits())
		.planType(modal.getPlanType())
		.subService(service)
		.build();
	}

	public PlanDetailsEntity convertToEntity(PlanDetails modal) {
		return PlanDetailsEntity.builder()
				.price(modal.getPrice())
				.benefits(modal.getBenefits())
				.planType(modal.getPlanType())
				
//				.subService(service)
				.build();
	}
  
	public PlanDetails convertToModal(PlanDetailsEntity entity) {
		return PlanDetails.builder()
				.planId(entity.getId())
				.price(entity.getPrice())
				.benefits(entity.getBenefits())
				.planType(entity.getPlanType())
				//.subService(service)
				.build();
	}

	public String deletePlan(Long id) {
		PlanDetailsEntity plan=planRepo.findById(id).orElse(null);
		plan.setActive(false);
		planRepo.save(plan);
		return "Plan entity deleted successfully";
	}
}
