package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.LawyerWorkExperienceEntity;

public interface ExperienceRepository extends JpaRepository<LawyerWorkExperienceEntity,Long>{

}
