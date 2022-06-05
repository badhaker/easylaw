package com.vedalegal.modal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LawyerOrderModel {
	private String orderNo;
	private String orderName;
	private String userName;
	private String mobileNo;
	private String userEmail;
	private String lawyerName;
	private String remark;
	private String status;
}
