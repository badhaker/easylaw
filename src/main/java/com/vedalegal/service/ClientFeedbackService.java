package com.vedalegal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import com.vedalegal.entity.ClientFeedbackEntity;
import com.vedalegal.entity.OrderEntity;
import com.vedalegal.exception.NoEntityInDatabaseException;
import com.vedalegal.exception.OrderNotFoundException;
import com.vedalegal.repository.ClientFeedbackRepository;
import com.vedalegal.repository.OrderRepository;
import com.vedalegal.repository.UserRepository;
import com.vedalegal.request.ClientFeedback;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.ClientFeedbackGet;
import com.vedalegal.response.CommonSuccessResponse;

@Service
public class ClientFeedbackService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	ClientFeedbackRepository clientFeedbackRepo;
	
	public CommonSuccessResponse addFeedback(ClientFeedback feedback) {
		if(feedback.getFeedbackId()!=null && feedback.getFeedbackId()!=0)
		{
			System.out.println("feedback Already exist");
			ClientFeedbackEntity entity=clientFeedbackRepo.findById(feedback.getFeedbackId()).get();
			entity.setRating(feedback.getStarRating());
			entity.setReview(feedback.getReview());
			clientFeedbackRepo.save(entity);
			return new CommonSuccessResponse(true);
		}
		
		OrderEntity order=orderRepo.findById(feedback.getOrderID()).get();
		if(order==null ||order.getActive()==false)
		{
			throw new OrderNotFoundException(AppConstant.ErrorTypes.ORDER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ORDER_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ORDER_NOT_EXIST_MESSAGE);
		}
		ClientFeedbackEntity entity=
				ClientFeedbackEntity.builder()
				.clientName(userRepo.findById(feedback.getClientId()).get().getName())
				.orderId(order)
				.lawyerId(order.getLawyerId().getId())
				.rating(feedback.getStarRating())
				.review(feedback.getReview())
				.build();
		clientFeedbackRepo.save(entity);
		return new CommonSuccessResponse(true);
	}

	public ClientFeedbackGet getFeedback(Long orderId) {

		OrderEntity order=orderRepo.findById(orderId).get();
		if(order==null ||order.getActive()==false)
		{
			throw new OrderNotFoundException(AppConstant.ErrorTypes.ORDER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ORDER_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ORDER_NOT_EXIST_MESSAGE);
		}
		ClientFeedbackEntity entity=clientFeedbackRepo.findByOrderId(orderRepo.findById(orderId).get());
		if(entity==null ||entity.getActive()==false)
		{
			throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);

		}
		return ClientFeedbackGet.builder().feedbackId(entity.getId())
		.review(entity.getReview())
		.starRating(entity.getRating())
		.clientName(entity.getClientName())
		.build();
	}

	public List<ClientFeedbackGet> getFeedbackForLawyer(Long lawyerId) {
	
	List<ClientFeedbackEntity> entity=clientFeedbackRepo.findAllByLawyerId(lawyerId);
	if(entity.size()<=0)
	{
		throw new NoEntityInDatabaseException(AppConstant.ErrorTypes.ENTITY_NOT_EXIST_ERROR,
				AppConstant.ErrorCodes.ENTITY_NOT_EXIST_CODE,
				AppConstant.ErrorMessage.ENTITY_NOT_EXIST_MESSAGE);
	}
	List<ClientFeedbackGet> list=entity.stream().map(rev-> convertToList(rev)).collect(Collectors.toList());
		return list;
	}

	private ClientFeedbackGet convertToList(ClientFeedbackEntity rev) {
		return ClientFeedbackGet.builder()
				.feedbackId(rev.getId())
				.review(rev.getReview())
				.starRating(rev.getRating())
				.clientName(rev.getClientName())
				.build();
	}

}
