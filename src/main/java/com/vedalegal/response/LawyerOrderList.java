package com.vedalegal.response;

import com.vedalegal.enums.LawyerBookingType;
import com.vedalegal.enums.OrderStatus;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class LawyerOrderList {

	private Long id;
	private String orderNo;
	private String userName;
	private String userEmail;
	private String userContactNo;
	
	private String expertise;
	private Long lawyerId;
	private String lawyerName;
	private LawyerBookingType mode;
	private OrderStatus status;
	private String remarks;
	private Boolean isActive;
	private String userRemarks;
	private Long rating;


}
