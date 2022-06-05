package com.vedalegal.service;

import org.springframework.stereotype.Service;

import com.vedalegal.entity.LawyerLanguageEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.modal.LawyerLanguage;
@Service
public class LanguageService {

	public LawyerLanguageEntity convertToEntity(LawyerLanguage lang, UserEntity lawyer) {
		return LawyerLanguageEntity
				.builder()
				.lawyerId(lawyer)
				.name(lang.getName())
				.build();
	}
}
