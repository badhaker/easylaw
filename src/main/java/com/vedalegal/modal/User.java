package com.vedalegal.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

	//private Long id;
	private String name;
	private String email;
	private Long contactNo;
	private String password;
	private String idImage;
	private Role roleModal;
	private String description;

	// lawyer specific fields
	private Long yearsOfExperience;
	private String state;
	private String city;
	private String barCouncilNo;
	private String regCardFront;
	private String regCardBack;
	private String cancelCheque;
	private String approvalStatus;
	private String remarks;

	/*
	 * private List<LawyerWorkExperienceEntity> experience; private
	 * List<LawyerEducationEntity> education; private List<LawyerExpertiseEntity>
	 * expertise; private List<LawyerCourtEntity> courts; private
	 * List<LawyerLanguageEntity> languages;
	 */

}
