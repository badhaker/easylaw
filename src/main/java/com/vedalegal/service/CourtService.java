package com.vedalegal.service;

import org.springframework.stereotype.Service;

import com.vedalegal.entity.LawyerCourtEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.modal.LawyerCourt;

@Service
public class CourtService {


	public LawyerCourtEntity convertToEntity(LawyerCourt modal, UserEntity lawyer) {
		return LawyerCourtEntity.builder()
		.courtName(modal.getCourtName())
		.courtLocation(modal.getCourtLocation())
		.lawyerId(lawyer)
		.build();
	}

}
