package com.vedalegal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedalegal.modal.LawyerCourt;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.service.LawyerProfileService;

@RestController
@RequestMapping("/lawyers")
public class LawyerProfileController {

	@Autowired
	private LawyerProfileService lawyerProfileService;

	@DeleteMapping("deleteCourt/{id}")
    public  ResponseEntity<BaseApiResponse> deleteLaywerCourt(@PathVariable Long id){

    	String msg =  lawyerProfileService.deleteLawyerCourt(id);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
        return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
    }

	@DeleteMapping("deleteExperience/{id}")
    public  ResponseEntity<BaseApiResponse> deleteLaywerExperience(@PathVariable Long id){

    	String msg =  lawyerProfileService.deleteLawyerExperience(id);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
        return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
    }

	@DeleteMapping("deleteExpertise/{id}")
    public  ResponseEntity<BaseApiResponse> deleteLaywerExpertise(@PathVariable Long id){

    	String msg =  lawyerProfileService.deleteLawyerExpertise(id);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
        return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
    }
	
	@DeleteMapping("deleteLanguage/{id}")
    public  ResponseEntity<BaseApiResponse> deleteLaywerLanguage(@PathVariable Long id){

    	String msg =  lawyerProfileService.deleteLawyerLanguage(id);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
        return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
    }
	@DeleteMapping("deleteEducation/{id}")
    public  ResponseEntity<BaseApiResponse> deleteLaywerEducation(@PathVariable Long id){

    	String msg =  lawyerProfileService.deleteLawyerEducation(id);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
        return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
    }
	
	@PutMapping("/editCourt")
	 public  ResponseEntity<BaseApiResponse> editLawyerCourt(@RequestBody LawyerCourt edu){

    	String msg =  lawyerProfileService.editLawyerCourt(edu);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
        return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
    } 

/*	
	///apis to add data in profile tables
	@PostMapping("/addEducation")
	public  ResponseEntity<BaseApiResponse> addLawyerEducation(@RequestBody LawyerEducationAdd edu)
	{
		CommonSuccessResponse msg =  lawyerProfileService.addLawyerEducation(edu);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	} 
	@PostMapping("/addCourt")
	public  ResponseEntity<BaseApiResponse> addLawyerCourt(@RequestBody LawyerCourtAdd edu)
	{
		CommonSuccessResponse msg =  lawyerProfileService.addLawyerCourt(edu);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	} 
	@PostMapping("/addExpertise")
	public  ResponseEntity<BaseApiResponse> addLawyerExpertise(@RequestBody LawyerExpertiseAdd edu)
	{
		CommonSuccessResponse msg =  lawyerProfileService.addLawyerExpertise(edu);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	} 
	@PostMapping("/addExperience")
	public  ResponseEntity<BaseApiResponse> addLawyerExperience(@RequestBody LawyerExperienceAdd edu)
	{
		CommonSuccessResponse msg =  lawyerProfileService.addLawyerExperience(edu);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	} 
	@PostMapping("/addLanguage")
	public  ResponseEntity<BaseApiResponse> addLawyerLanguage(@RequestBody LawyerLanguageAdd edu)
	{
		CommonSuccessResponse msg =  lawyerProfileService.addLawyerLanguage(edu);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	} 
	
*/   
    
}
