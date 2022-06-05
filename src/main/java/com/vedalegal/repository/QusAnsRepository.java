package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.FAQEntity;
public interface QusAnsRepository extends JpaRepository<FAQEntity, Long> {

}
 