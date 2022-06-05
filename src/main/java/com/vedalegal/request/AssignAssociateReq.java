package com.vedalegal.request;

import com.vedalegal.enums.EnquiryStatus;
import com.vedalegal.enums.OrderStatus;
import com.vedalegal.enums.PlanType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignAssociateReq {
	
	private Long associateId;
	private EnquiryStatus enquiryStatus;
	private String remark;

}
