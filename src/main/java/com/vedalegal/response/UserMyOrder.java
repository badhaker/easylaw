package com.vedalegal.response;

import java.util.Date;

import com.vedalegal.enums.OrderStatus;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UserMyOrder {

	private Long id;
	private String orderNo;
	private Date orderDate;
	private OrderStatus status;
	private String remarks;
	private String serviceName;
	private Long LawyerId;
	private String LawyerName;
}
