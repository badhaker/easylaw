package com.vedalegal.request;

import java.util.List;

import com.vedalegal.enums.Gender;
import com.vedalegal.modal.LawyerCourt;
import com.vedalegal.modal.LawyerEducation;
import com.vedalegal.modal.LawyerExpertise;
import com.vedalegal.modal.LawyerLanguage;
import com.vedalegal.modal.LawyerWorkExperience;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerProfile {

	private Long contactNo;
	private String email;
	private String name;
	private String city;
	private String state;
	private String pincode;
	private String remarks;
	private Long yearsOfExperience;
	private String description;
	private String expertiseList;
	private List<LawyerWorkExperience> experience;
	private List<LawyerExpertise> expertise;
	private List<LawyerCourt> courts;
	private List<LawyerLanguage> languages;
	private List<LawyerEducation> education;
	private String barCouncilNo;
	private String barCouncilName;
	private Gender gender;

}
