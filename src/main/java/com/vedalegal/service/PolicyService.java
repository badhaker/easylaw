package com.vedalegal.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.PolicyEntity;
import com.vedalegal.exception.EnquiryNotFoundException;
import com.vedalegal.repository.PolicyRepository;
import com.vedalegal.request.Policies;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.PolicyGet;

@Service
public class PolicyService {
	
	@Autowired
	private PolicyRepository policyRepo;

	public PolicyEntity addPolicy(Policies policy) {
		
		policyRepo.deleteAll();
		PolicyEntity policytoAdd=PolicyEntity.builder()
				.policy(policy.getPolicy())
				.build();
		policytoAdd.setUpdated(new Date());
		return(policyRepo.save(policytoAdd));
	}
/*	
	public String deletePolicyById(Long id) {
		policyRepo.deleteById(id);
		return "Policy deleted successfully";
	}
*/
	public String updatePolicyById(Long id, Policies policies) {
		PolicyEntity policyToUpdate=policyRepo.findById(id).orElse(null);
		policyToUpdate.setPolicy(policies.getPolicy());
		policyToUpdate.setUpdated(new Date());
		policyRepo.save(policyToUpdate);
		return "Policy Updated Successfully";
	}

	public PolicyGet getPolicy() {
		//PolicyEntity p=policyRepo.findById(id).orElse(null);
		if(policyRepo.findAll().size()<=0)
		{
			throw new EnquiryNotFoundException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		PolicyEntity p=policyRepo.findAll().get(0);
		PolicyGet policyGet=PolicyGet.builder().id(p.getId()).policy(p.getPolicy()).build();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    String strDate= formatter.format(p.getUpdated()); 		
		policyGet.setLastUpdated(strDate);
		return policyGet;
	}

}
