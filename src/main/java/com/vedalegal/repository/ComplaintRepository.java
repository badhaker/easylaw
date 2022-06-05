package com.vedalegal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vedalegal.entity.ComplaintEntity;
import com.vedalegal.enums.ComplaintStatusEnum;

public interface ComplaintRepository extends JpaRepository<ComplaintEntity, Long>{

	List<ComplaintEntity> findAllByStatus(ComplaintStatusEnum status, Pageable pageable);

	@Query("SELECT COUNT(c) FROM ComplaintEntity c WHERE c.created>=:utilDate AND active=true")
	Long getComplaintCountByCreatedDate(Date utilDate);

	@Query("SELECT c FROM ComplaintEntity c WHERE c.status= ?1 AND "
			+ "CONCAT(c.complaintNo,' ',c.orderId.orderNo,' ',c.serviceName,' ',c.userId.name,' ',c.userId.contactNo) LIKE %?2%")
	List<ComplaintEntity> findAllByStatusWithSearch(ComplaintStatusEnum status, String search, Pageable pageable);

}
