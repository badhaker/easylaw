package com.vedalegal.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerList {

	private Long id;
	private String name;
	private String email;
	private Long contactNo;
	private String location;
	private String remarks;
	private String status;
	private Boolean isActive;
    private Boolean isSuspend;
	private Long rating;
}
