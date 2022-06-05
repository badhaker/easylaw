package com.vedalegal.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.TermAndPolicyEntity;
import com.vedalegal.exception.EnquiryNotFoundException;
import com.vedalegal.repository.TermAndPolicyRepository;
import com.vedalegal.request.TermsAndPolicy;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.TermsAndPolicyGet;

@Service
public class TermAndPolicyService {

	@Autowired
	TermAndPolicyRepository termAndPolicyRepo;
	
	public String addTermsAndPolicy(TermsAndPolicy terms) {
		termAndPolicyRepo.deleteAll();
		 TermAndPolicyEntity entityToAdd=TermAndPolicyEntity.builder().termAndPolicy(terms.getTermsAndPolicy()).build();
		entityToAdd.setUpdated(new Date());
		termAndPolicyRepo.save(entityToAdd);
		return "Terms and policy create successfully";
	}

	public TermsAndPolicyGet getTermsAndPolicy() {
		if(termAndPolicyRepo.findAll().size()<=0)
		{
			throw new EnquiryNotFoundException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
		}
		TermAndPolicyEntity p=termAndPolicyRepo.findAll().get(0);
		TermsAndPolicyGet termsPolicyGet=TermsAndPolicyGet.builder().id(p.getId()).termsAndPolicy(p.getTermAndPolicy()).build();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    String strDate= formatter.format(p.getUpdated()); 		
	    termsPolicyGet.setLastUpdated(strDate);
		return termsPolicyGet;
	}
	
	public String updateTermsAndPolicy(Long id,TermsAndPolicy terms) {
		TermAndPolicyEntity policy = termAndPolicyRepo.findById(id).orElse(null);
		policy.setTermAndPolicy(terms.getTermsAndPolicy());
		termAndPolicyRepo.save(policy);
		return "Terms and policy updated successfully";
	}

}
