package com.vedalegal.response;

import java.util.List;

import com.vedalegal.enums.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LawyerListWebsite {

	private Long id;
	private String imgUrl;
	private String name;
	private String description;
	Long yearsOfExperience;
//	List<Expertise> expertise;
	private String expertise;
	private String city;
	private String state;
	private Gender gender;
	private Long rating;
	private boolean isSuspend;
	private String pincode;
	
}
