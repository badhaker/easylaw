package com.vedalegal.response;

import com.vedalegal.enums.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailAdmin {
	
	private Long id;
	private String orderNo;
	private String userName;
	private String userEmail;
	private String userContactNo;
	private String subServiceName;
	private String assignedTo;
	private OrderStatus status;
	private String remarks;
	private String transactionId;
	private Long assignedToId;
}
