package com.vedalegal.response;

import java.util.List;

import com.vedalegal.modal.LawyerOrderModel;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LawyerOrdersResponse {

	List<LawyerOrderModel> lawyerOrders;
	
}
