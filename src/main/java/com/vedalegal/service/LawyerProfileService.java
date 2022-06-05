package com.vedalegal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedalegal.entity.LawyerCourtEntity;
import com.vedalegal.entity.LawyerEducationEntity;
import com.vedalegal.entity.LawyerExpertiseEntity;
import com.vedalegal.entity.LawyerLanguageEntity;
import com.vedalegal.entity.LawyerWorkExperienceEntity;
import com.vedalegal.modal.LawyerCourt;
import com.vedalegal.repository.AreaOfExpertiseRepository;
import com.vedalegal.repository.CourtRepository;
import com.vedalegal.repository.EducationRepository;
import com.vedalegal.repository.ExperienceRepository;
import com.vedalegal.repository.ExpertiseRepository;
import com.vedalegal.repository.LanguageRepository;
import com.vedalegal.repository.UserRepository;
import com.vedalegal.request.LawyerCourtAdd;
import com.vedalegal.request.LawyerEducationAdd;
import com.vedalegal.request.LawyerExperienceAdd;
import com.vedalegal.request.LawyerExpertiseAdd;
import com.vedalegal.request.LawyerLanguageAdd;
import com.vedalegal.response.CommonSuccessResponse;

@Service
public class LawyerProfileService {

	@Autowired
	private CourtRepository courtRepo;
	@Autowired
	private EducationRepository educationRepo;
	@Autowired
	private ExperienceRepository experienceRepo;
	@Autowired
	private LanguageRepository languageRepo;
	@Autowired
	private ExpertiseRepository expertiseRepo;
	@Autowired
	private AreaOfExpertiseRepository allExpertiseRepo;
	
	@Autowired
	private UserRepository userRepo;

	public String deleteLawyerCourt(Long id) {

		courtRepo.deleteById(id);
		return "Lawyer court entry deleted successfully";
	}

	public String deleteLawyerExpertise(Long id) {

		expertiseRepo.deleteById(id);
		return "Lawyer expertise entry deleted successfully";
	}

	public String deleteLawyerExperience(Long id) {

		experienceRepo.deleteById(id);
		return "Lawyer experience entry deleted successfully";
	}

	public String deleteLawyerLanguage(Long id) {

		languageRepo.deleteById(id);
		return "Lawyer language entry deleted successfully";
	}

	public String deleteLawyerEducation(Long id) {

		educationRepo.deleteById(id);
		return "Lawyer education entry deleted successfully";
	}

	public CommonSuccessResponse addLawyerEducation(LawyerEducationAdd edu) {
		
		LawyerEducationEntity education=LawyerEducationEntity.builder()
		.course(edu.getCourse())
		.institution(edu.getInstitution())
		.startDate(edu.getStartDate())
		.endDate(edu.getEndDate())
		.lawyerId(userRepo.findById(edu.getLawyer_id()).get())
		.build();
		educationRepo.save(education);
		
		return new CommonSuccessResponse(true);
	}

	public CommonSuccessResponse addLawyerLanguage(LawyerLanguageAdd edu) {

		LawyerLanguageEntity lang=LawyerLanguageEntity.builder()
		.name(edu.getName())
		.lawyerId(userRepo.findById(edu.getLawyerId()).get())
		.build();
		languageRepo.save(lang);
		return new CommonSuccessResponse(true);
	}

	public CommonSuccessResponse addLawyerExperience(LawyerExperienceAdd edu) {
		LawyerWorkExperienceEntity work=LawyerWorkExperienceEntity.builder()
		.organization(edu.getOrganization())
		.designation(edu.getDesignation())
		.startTime(edu.getStartTime())
		.endTime(edu.getEndTime())
		.lawyerId(userRepo.findById(edu.getLawyerId()).get())
		.build();
		experienceRepo.save(work);
		return new CommonSuccessResponse(true);
	}

	public CommonSuccessResponse addLawyerExpertise(LawyerExpertiseAdd edu) {
		LawyerExpertiseEntity exp=LawyerExpertiseEntity.builder()
		.expertiseNo(allExpertiseRepo.findById(edu.getExpertiseId()).get())
		.callPrice(edu.getCallPrice())
		.meetingPrice(edu.getMeetingPrice())
		.lawyerId(userRepo.findById(edu.getLawyerId()).get())
		.build();
		
		expertiseRepo.save(exp);
		return new CommonSuccessResponse(true);
	}

	public CommonSuccessResponse addLawyerCourt(LawyerCourtAdd edu) {

		LawyerCourtEntity court=LawyerCourtEntity.builder().courtName(edu.getCourtName())
		.courtLocation(edu.getCourtLocation())
		.lawyerId(userRepo.findById(edu.getLawyerId()).get()).build();
		courtRepo.save(court);
		return new CommonSuccessResponse(true);
	}

	public String editLawyerCourt(LawyerCourt edu) {
		LawyerCourtEntity court=courtRepo.findById(edu.getId()).orElse(null);
		court.setCourtName(edu.getCourtName());
		court.setCourtLocation(edu.getCourtLocation());
		
		courtRepo.save(court);
		return "Court Entity edited successfully";
	}

}
