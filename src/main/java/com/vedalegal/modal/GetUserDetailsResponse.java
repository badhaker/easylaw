package com.vedalegal.modal;

import com.vedalegal.enums.ApprovalStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserDetailsResponse {

	private Long id;
	private String name;
	private String email;
	private Long contactNo;
	private String role;
	private String permissions;
	private String barCouncilNo;
    private ApprovalStatus status;
    private String imgURL;
}
