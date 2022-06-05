package com.vedalegal.modal;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LawyerDetailResponse {

	private Long id;
	private String name;
	private String email;
	private Long contactNo;
	private String barCouncilNo;
	private String barCouncilName;
	private String location;
	private Long yearsOfExperience;
	private String description;
	private String remarks;
	private String regCardFront;
	private String regCardBack;
	private String cancelCheque;
	private String panCard;
	private String approvalStatus;
	private String expertiseList;
	private String lawyerImage;
	private String pincode;
	private String gender;

	private List<LawyerWorkExperience> experience;
	private List<LawyerExpertise> expertise;
	private List<LawyerCourt> courts;
	private List<LawyerLanguage> languages;
	private List<LawyerEducation> education;

}
