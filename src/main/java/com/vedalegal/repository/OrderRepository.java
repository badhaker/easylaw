package com.vedalegal.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vedalegal.entity.OrderEntity;
import com.vedalegal.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

	@Query(value="SELECT * FROM order_user o WHERE o.lawyer_id= ?1 ", 
			nativeQuery = true)
	List<OrderEntity> findAllByLawyer(Long id);
	
	@Query(value="SELECT * FROM order_user o WHERE o.user_id= ?1 ", 
			nativeQuery = true)
	List<OrderEntity> findAllByUser(Long id);
	
	@Query("SELECT DISTINCT o FROM OrderEntity o WHERE o.status=?1 AND o.subServiceId IS NOT NULL")
	List<OrderEntity> findAllByStatus(OrderStatus status);

	@Query("Select COUNT(o) From OrderEntity o WHERE o.orderNo=?1")
	Long getOrderCount(String orderNo);

	OrderEntity findByOrderNo(String orderNo);

	@Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.lawyerId IS NOT NULL  AND o.created>=:utilDate AND active=true")
	Long getLowerOrderCountByCreatedDate(Date utilDate);

	@Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.subServiceId IS NOT NULL  AND o.created>=:utilDate AND active=true")
	Long getServiceOrderCountByCreatedDate(Date utilDate);
	
	@Query("SELECT o FROM OrderEntity o WHERE o.status=?1 AND o.subServiceId IS NOT NULL AND "
			+ "CONCAT(o.userId.name,o.userId.email,o.userId.contactNo,o.assignedTo.name,o.orderNo,o.subServiceId.name,o.remarks) LIKE %?2%")
	List<OrderEntity> findAllByStatusAndSerchValue(OrderStatus status, String search);

	@Query("SELECT o FROM OrderEntity o WHERE o.lawyerId IS NOT NULL")
	List<OrderEntity> findLawyerOrderEntity();
	
	@Query("SELECT o FROM OrderEntity o WHERE (o.lawyerId IS NOT NULL) AND "
			+ "CONCAT(o.userId.name, ' ', o.userId.email,' ',o.userId.contactNo,' ',o.orderNo,' ',o.lawyerId.name,' ',o.lawyerId.id,' ',o.expertise) LIKE %?1%")
	List<OrderEntity> findLawyerOrderEntityWithSearchValue(String search);
	
	@Query("select o from OrderEntity o  where o.lawyerId.id =:lawyerId and o.ratingApproved=true")
	List<OrderEntity> findByLawyerIddata(@Param("lawyerId") Long lawyerId);
	
	

}
