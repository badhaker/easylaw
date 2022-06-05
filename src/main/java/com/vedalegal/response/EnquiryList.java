package com.vedalegal.response;

import java.util.Date;

import com.vedalegal.enums.EnquiryStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnquiryList {
	
	private Long id;
	private String subServiceName;
	private String userName;
	private String assignedTo;
	private Date raisedOn;
	private EnquiryStatus status;
	private String remarks;
	private String email;
	private String query;
	private String enquiryNo;
	private Long contactNo;
	private boolean isactive;
}

