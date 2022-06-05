package com.vedalegal.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerAdminProfile {
	
	private Long contactNo;
	private String email;
	private String name;
	private String city;
	private String state;
	private String pincode;
	private String remarks;
	private Long rating;


}
