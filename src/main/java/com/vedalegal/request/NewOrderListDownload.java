package com.vedalegal.request;

import java.util.List;

import com.vedalegal.enums.OrderStatus;
import com.vedalegal.response.OrderVedaListResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderListDownload {
	
	List<OrderVedaListResponse> OrderList;
}
