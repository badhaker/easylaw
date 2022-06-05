package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.NewsEntity;

public interface NewsRepository extends JpaRepository<NewsEntity, Long>{

}
