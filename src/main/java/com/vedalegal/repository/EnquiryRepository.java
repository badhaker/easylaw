package com.vedalegal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vedalegal.entity.EnquiryEntity;
import com.vedalegal.enums.EnquiryStatus;

@Repository
public interface EnquiryRepository extends JpaRepository<EnquiryEntity, Long> {
	
	
	@Query("SELECT COUNT(e) FROM EnquiryEntity e WHERE e.created>=:utilDate AND active=true")
	Long getEnqueryCountByCreatedDate(Date utilDate);
	
	/*@Query("SELECT e FROM EnquiryEntity e WHERE e.status=?1 ")
	List<EnquiryEntity> findAllEnqueryList(EnquiryStatus status, Pageable pageable);*/

	@Query("SELECT e FROM EnquiryEntity e WHERE e.status=?1 ")
	List<EnquiryEntity> findAllEnqueryList(EnquiryStatus status);
	
	/*@Query("SELECT e FROM EnquiryEntity e WHERE e.status=?2 AND (e.userName LIKE %?1% )")
	List<EnquiryEntity> findAllEnqueryListWithSearch(String search, EnquiryStatus status, Pageable pageable);*/

	@Query("SELECT e FROM EnquiryEntity e WHERE e.status=?2 AND (e.userName LIKE %?1% )")
	List<EnquiryEntity> findAllEnqueryListWithSearch(String search, EnquiryStatus status);

	/*@Query("SELECT e FROM EnquiryEntity e WHERE e.status=?2 AND e.assignedTo IS NOT NULL AND (e.assignedTo.name LIKE %?1% )")
	List<EnquiryEntity> findAllEnqueryListAssignedWithSearch(String search, EnquiryStatus status, Pageable pageable);*/

	@Query("SELECT e FROM EnquiryEntity e WHERE e.status=?2 AND e.assignedTo IS NOT NULL AND (e.assignedTo.name LIKE %?1% )")
	List<EnquiryEntity> findAllEnqueryListAssignedWithSearch(String search, EnquiryStatus status);

	List<EnquiryEntity> findByEmail(String email);


}
