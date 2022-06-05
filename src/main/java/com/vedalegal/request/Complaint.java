package com.vedalegal.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Complaint {
 
	private String orderNo;
	private String complaintDescription;
	private Long userId;
	private String serviceName;
}
