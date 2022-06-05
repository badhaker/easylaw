package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedalegal.entity.LawyerCourtEntity;

@Repository
public interface CourtRepository extends JpaRepository<LawyerCourtEntity,Long>{

}
