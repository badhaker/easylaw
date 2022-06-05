package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedalegal.entity.ClientReviewEntity;

@Repository
public interface ClientSpeakRepository extends JpaRepository<ClientReviewEntity, Long>{

}
