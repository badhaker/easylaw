package com.vedalegal.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vedalegal.entity.ClientFeedbackEntity;
import com.vedalegal.entity.OrderEntity;

public interface ClientFeedbackRepository extends JpaRepository<ClientFeedbackEntity, Long>{

	ClientFeedbackEntity findByOrderId(OrderEntity orderEntity);

	
	//List <ClientFeedbackEntity> findByLawyerId(Long lawyerId);


	@Query(value="SELECT * FROM client_rating_and_review c WHERE  c.lawyer_id LIKE ?1 AND c.is_active!=false ", 
			nativeQuery = true)
	List<ClientFeedbackEntity> findAllByLawyerId(Long lawyerId);

}
