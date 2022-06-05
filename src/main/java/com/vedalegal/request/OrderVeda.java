package com.vedalegal.request;

import com.vedalegal.enums.PlanType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderVeda {

	
	private Long userId;
	private String transactionId;
	private Long subServiceId;
	private PlanType planType;
	private Boolean isActive;
	
}
