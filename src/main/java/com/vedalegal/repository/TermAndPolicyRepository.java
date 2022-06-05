package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedalegal.entity.TermAndPolicyEntity;

@Repository
public interface TermAndPolicyRepository extends JpaRepository<TermAndPolicyEntity, Long> {

}
