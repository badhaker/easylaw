package com.vedalegal.modal;

import java.util.Date;

import com.vedalegal.enums.EnquiryStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetEnquiryDetailsResponse {

	public Long id;
	public String servicename;
	private String Username;
	private Long contactNo;
	private String email;
	private String enquiry;
	private Date Date;
	private EnquiryStatus status;
	private String remark;
	private String AssignedTo;
	private Long assigndToId;
	private Long serviceToId;
}
