package com.vedalegal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vedalegal.entity.FAQEntity;
import com.vedalegal.entity.SubServiceEntity;
import com.vedalegal.modal.QusAns;
import com.vedalegal.repository.QusAnsRepository;

@Component
public class QusAnsService {

	@Autowired
	private QusAnsRepository qusAnsRepo;
	
	public FAQEntity convertToEntity(QusAns modal , SubServiceEntity entity)
	{
		return FAQEntity.builder()
		.question(modal.getQuestion())
		.answer(modal.getAnswer())
		.subService(entity)
		.build();
	}
	
	public QusAns convertToModal(FAQEntity entity)
	{
		return QusAns.builder()
				.qusAnsId(entity.getId())
				.question(entity.getQuestion())
				.answer(entity.getAnswer())
				.build();
	}

	public FAQEntity convertToEntity(QusAns modal) {
		return FAQEntity.builder()
				.question(modal.getQuestion())
				.answer(modal.getAnswer())
				//.subService(entity)
				.build();
	}

	public String deleteQusAns(Long id) {
		FAQEntity faq=qusAnsRepo.findById(id).orElse(null);
		faq.setActive(false);
		qusAnsRepo.save(faq);
		return "FAQ deleted successfully";
	}
}
 