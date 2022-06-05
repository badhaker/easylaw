package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.PlanDetailsEntity;
public interface PlanRepository extends JpaRepository<PlanDetailsEntity,Long>{

}
 