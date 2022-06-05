package com.vedalegal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vedalegal.entity.ImageEntity;

@Repository
public interface ImageRepository extends CrudRepository<ImageEntity,Long> {
}
