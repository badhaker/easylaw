package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.LawyerEducationEntity;

public interface EducationRepository  extends JpaRepository<LawyerEducationEntity,Long>{
    
}
