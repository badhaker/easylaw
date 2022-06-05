package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.PolicyEntity;

public interface PolicyRepository extends JpaRepository<PolicyEntity,Long>{

}
