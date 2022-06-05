package com.vedalegal.request;

import com.vedalegal.enums.ComplaintStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateComplaintRequest {
	
	private Long complaintId;
	private ComplaintStatusEnum status;
	private String remark;
}
