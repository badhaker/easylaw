package com.vedalegal.request;

import com.vedalegal.enums.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest{
	
	private OrderStatus orderStatus;
	private String remark;

}

