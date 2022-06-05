package com.vedalegal.response;

import java.util.Date;

import com.vedalegal.enums.ComplaintStatusEnum;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ComplaintList {
	 
		private Long id;
		private String orderNo;
		private String complaintNo;
		private String complaintDescription;
		private String userName;
		private Long UserContact;
		private String userEmail;
		private String serviceName;
		private ComplaintStatusEnum status;
		private String remarks;
		private Date dateOfComplaint;
		
	
}
