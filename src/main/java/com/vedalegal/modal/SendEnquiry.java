package com.vedalegal.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendEnquiry {

	private String name;
	private String email;
	private Long contactNo;
	public Long serviceId;
	private String enquiry;

}
