package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.MasterServiceEntity;
import com.vedalegal.entity.TopServiceEntity;

public interface TopServiceRepository extends JpaRepository<TopServiceEntity, Long> {
	public TopServiceEntity findByMasterServiceId(MasterServiceEntity master);
	
}
