package com.vedalegal.service;

import org.springframework.stereotype.Service;

import com.vedalegal.entity.LawyerWorkExperienceEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.modal.LawyerWorkExperience;
@Service
public class ExperienceService {

	public LawyerWorkExperienceEntity convertToEntity(LawyerWorkExperience exp, UserEntity lawyer) {
		
		
		return LawyerWorkExperienceEntity.builder()
				.lawyerId(lawyer)
				.organization(exp.getOrganization())
				.designation(exp.getDesignation())
				.startTime(exp.getStartTime())
				.endTime(exp.getEndTime())
				.build();
	}

}
