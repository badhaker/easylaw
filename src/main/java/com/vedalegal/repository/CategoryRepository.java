package com.vedalegal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vedalegal.entity.CategoryEntity;
import com.vedalegal.entity.MasterServiceEntity;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity,Long> {

    public List<CategoryEntity> findAllByServiceEntity(MasterServiceEntity serviceEntity);
    public CategoryEntity findByName(String name);
}
