package com.vedalegal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.BannerEntity;

public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
	


}
