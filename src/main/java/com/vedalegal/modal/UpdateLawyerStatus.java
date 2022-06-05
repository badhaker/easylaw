package com.vedalegal.modal;

import com.vedalegal.enums.ApprovalStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateLawyerStatus {
	
	private Long id;
	private ApprovalStatus status;
	private String remarks;
	private Long rating;


}
