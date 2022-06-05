package com.vedalegal.request;

import com.vedalegal.enums.OrderStatus;

//import com.vedalegal.enums.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Rating {
	private String remarks;
	private String userremark;
//	private Long averageRating;
	private Long lawyerId;
	private OrderStatus status;

	

}