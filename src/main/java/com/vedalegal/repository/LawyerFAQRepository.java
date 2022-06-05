package com.vedalegal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.AllExpertiseEntity;
import com.vedalegal.entity.LawyerFAQEntity;

public interface LawyerFAQRepository extends JpaRepository<LawyerFAQEntity, Long> {

	List<LawyerFAQEntity> findAllByExpertise(AllExpertiseEntity allExpertiseEntity);

}
