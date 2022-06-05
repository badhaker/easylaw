package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedalegal.entity.MasterServiceEntity;

@Repository
public interface MasterServiceRepository  extends JpaRepository<MasterServiceEntity, Long> {

}
