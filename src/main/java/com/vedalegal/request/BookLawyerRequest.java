package com.vedalegal.request;

import com.vedalegal.enums.LawyerBookingType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookLawyerRequest {
	
	private Long userId;
	private Long lawyerId;
	private String transactionId;
	private LawyerBookingType bookingType;
	private String expertise;
	//private Long orderDate;
	
}
