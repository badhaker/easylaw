package com.vedalegal.repository;

import java.util.List;

import com.vedalegal.modal.SubServiceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vedalegal.entity.CategoryEntity;
import com.vedalegal.entity.SubServiceEntity;

public interface SubServiceRepository extends JpaRepository<SubServiceEntity, Long> {

	public List<SubServiceEntity> findAllByCategory(CategoryEntity categoryEntity);
	
	@Query(value="SELECT * FROM sub_service_details WHERE NAME LIKE %:name% AND is_active = 1", 
			nativeQuery = true)
	public List<SubServiceEntity> findByShortName(String name);

	@Query("Select s From SubServiceEntity s where s.name=:subService")
	public List<SubServiceEntity> findBySubservice(String subService);

}
 