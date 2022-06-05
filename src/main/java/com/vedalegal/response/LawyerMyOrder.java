package com.vedalegal.response;

import java.util.Date;

import com.vedalegal.enums.LawyerBookingType;
import com.vedalegal.enums.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LawyerMyOrder {

	private Long id;
	private String orderNo;
	private String userName;
	//private String userEmail;
	//private Long userContactNo;
	private Date orderDate;
	private String expertise;
	private LawyerBookingType mode;
	private String remarks;
	private OrderStatus status;


	
}
