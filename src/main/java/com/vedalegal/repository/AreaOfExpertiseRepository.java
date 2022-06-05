package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedalegal.entity.AllExpertiseEntity;

@Repository
public interface AreaOfExpertiseRepository extends JpaRepository<AllExpertiseEntity, Long> {

	AllExpertiseEntity findByAreaOfExpertiseName(String expertise);

}
