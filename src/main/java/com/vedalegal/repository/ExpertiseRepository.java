package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.LawyerExpertiseEntity;

public interface ExpertiseRepository extends JpaRepository<LawyerExpertiseEntity,Long>{

}
