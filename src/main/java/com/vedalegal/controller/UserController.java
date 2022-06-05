package com.vedalegal.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.vedalegal.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.enums.ApprovalStatus;
import com.vedalegal.modal.AddAssociate;
import com.vedalegal.modal.AssociateListResponse;
import com.vedalegal.modal.GetEnquiryDetailsResponse;
import com.vedalegal.modal.GetUserDetailsResponse;
import com.vedalegal.modal.LawyerDetailResponse;
import com.vedalegal.modal.LawyerList;
import com.vedalegal.modal.UpdateAssociate;
import com.vedalegal.modal.UpdateLawyerStatus;
import com.vedalegal.modal.UserListResponse;
import com.vedalegal.resource.SecurityConstants;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.GetRemarkResponse;
import com.vedalegal.response.LawyerListWebsite;
import com.vedalegal.response.ResponseBuilder;
import com.vedalegal.response.SocialMediaSigninResponse;
import com.vedalegal.security.JwtTokenProvider;
import com.vedalegal.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/customerSignUp")
	public ResponseEntity<BaseApiResponse> createCoustomer(
			@Valid @RequestBody CoustomerSingupRequest coustomerSingupRequest, HttpServletResponse response,
			HttpServletRequest request) throws Exception {

		CommonSuccessResponse commonResponse = userService.createCoustomer(coustomerSingupRequest);
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				coustomerSingupRequest.getEmail(), coustomerSingupRequest.getPassword()));
		String jwt = jwtTokenProvider.generateToken(authentication);
		System.out.println("  signupResponse  " + commonResponse);
		response.setHeader("Access-Control-Allow-Headers",
				"Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
						+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		response.setHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader(SecurityConstants.SecretKey.TOKEN_HEADER, SecurityConstants.SecretKey.TOKEN_PREFIX + jwt);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.CREATED);

	}

	@PostMapping(value = "/LawyerSignUp")
	public ResponseEntity<BaseApiResponse> createLawyer(@Valid @RequestBody LawyerSingupRequest SignUpRequest,
			HttpServletResponse response, HttpServletRequest request) throws Exception {

//		@RequestPart(value= "cardFrontSide",required = true) MultipartFile cardFrontSide,
//		@RequestPart(value= "cardBackSide",required = true) MultipartFile cardBackSide,
//		@RequestPart(value= "cancelledCheque",required = false) MultipartFile cancelledCheque

//		LawyerSingupRequest lawyerSingupRequest=new ObjectMapper().readValue(jsonRequest,LawyerSingupRequest.class);
		CommonSuccessResponse commonResponse = userService.createLawyer(SignUpRequest);
//		CommonSuccessResponse commonResponse=userService.createLawyer(lawyerSingupRequest,cardFrontSide,cardBackSide,cancelledCheque);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(SignUpRequest.getEmail(), SignUpRequest.getPassword()));
		String jwt = jwtTokenProvider.generateToken(authentication);
		System.out.println("  signupResponse  " + commonResponse);
		response.setHeader("Access-Control-Allow-Headers",
				"Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
						+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		response.setHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader(SecurityConstants.SecretKey.TOKEN_HEADER, SecurityConstants.SecretKey.TOKEN_PREFIX + jwt);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.CREATED);
	}

	@PostMapping("/Login")
	public ResponseEntity<BaseApiResponse> userLogIn(@Valid @RequestBody UserLogInRequest userLogInRequest,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		CommonSuccessResponse commonResponse = userService.userLogIn(userLogInRequest);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userLogInRequest.getEmail(), userLogInRequest.getPassword()));
		String jwt = jwtTokenProvider.generateToken(authentication);
		System.out.println("  signupResponse  " + commonResponse);
		response.setHeader("Access-Control-Allow-Headers",
				"Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
						+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		response.setHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader(SecurityConstants.SecretKey.TOKEN_HEADER, SecurityConstants.SecretKey.TOKEN_PREFIX + jwt);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PostMapping("/socialMediaSignin")
	public ResponseEntity<BaseApiResponse> createMasterService(
			@Valid @RequestBody SocialMediaSigninRequest masterService, HttpServletResponse response,
			HttpServletRequest request) {
		SocialMediaSigninResponse service = userService.socialMediaSignin(masterService);
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(masterService.getEmail(), " "));
		String jwt = jwtTokenProvider.generateToken(authentication);
		System.out.println("  signupResponse  " + service);
		response.setHeader("Access-Control-Allow-Headers",
				"Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
						+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		response.setHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader(SecurityConstants.SecretKey.TOKEN_HEADER, SecurityConstants.SecretKey.TOKEN_PREFIX + jwt);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(service);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.CREATED);
	}

	@GetMapping("/lawyerList")
	// public ResponseEntity<BaseApiResponse> getLawyerList(@RequestParam String
	// status) {
	public ResponseEntity<BaseApiResponse> getLawyerList(@RequestParam ApprovalStatus status,
			@RequestParam(required = false) String search) {
		System.out.println();
		List<LawyerList> lawyerList = userService.getLawyerListByStatus(status, search);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(lawyerList);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/lawyerDetail/{id}")
	public ResponseEntity<BaseApiResponse> getLawyerDetail(@PathVariable Long id) {
		LawyerDetailResponse subService = userService.getLawyerDetail(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(subService);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/usersList")
	public ResponseEntity<BaseApiResponse> getUserList(@RequestParam(required = false) String search) {
		List<UserListResponse> userList = userService.getUserList(search);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(userList);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/userDetails")
	public ResponseEntity<BaseApiResponse> getUserDetails(
			@RequestHeader(value = SecurityConstants.SecretKey.TOKEN_HEADER) String token) {
		String email = jwtTokenProvider.extractUserEmail(token.substring(7));
		GetUserDetailsResponse user = userService.getUserDetails(email);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(user);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

	}

	/*
	 * @PostMapping("/bookLawyer") public ResponseEntity<BaseApiResponse>
	 * bookLawyer(@RequestBody BookLawyerRequest bookLawyerRequest,
	 * 
	 * @RequestHeader(value = SecurityConstants.SecretKey.TOKEN_HEADER) String
	 * token){ String email=jwtTokenProvider.extractUserEmail(token.substring(7));
	 * CommonSuccessResponse
	 * response=userService.bookLawyer(bookLawyerRequest,email); BaseApiResponse
	 * baseApiResponse = ResponseBuilder.getSuccessResponse(response); return new
	 * ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	 * 
	 * }
	 */

	@GetMapping("/lawyerListWebsite")
	public ResponseEntity<BaseApiResponse> getLawyersByLocationAndExpertise(
			@RequestParam(required = false) String state, @RequestParam(required = false) String city,
			@RequestParam(required = false) String expertise, @RequestParam(required = false) String name,
			@RequestParam(required = false) Long lawyer_years_of_experience,
			@RequestParam(required = false) String gender) {
		List<LawyerListWebsite> lawyerList = userService.getLawyersByLocationAndExpertise(state, city, expertise, name,
				lawyer_years_of_experience, gender);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(lawyerList);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	
	public ResponseEntity<BaseApiResponse> updateUserProfile(@PathVariable Long id, @RequestPart String jsonRequest,
			@RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
		Profile profile = new ObjectMapper().readValue(jsonRequest, Profile.class);
		String msg = userService.updateUserProfile(id, profile, image);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PostMapping("/addAssociate")
	public ResponseEntity<BaseApiResponse> addAssociate(@RequestBody AddAssociate addAssociate)
			throws AddressException, IOException, MessagingException {
		CommonSuccessResponse msg = userService.addAssociate(addAssociate);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/getAssociate")
	public ResponseEntity<BaseApiResponse> getAssociate() {
		List<AssociateListResponse> msg = userService.getAssociate();
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PutMapping("/updateAssociate/{id}")
	public ResponseEntity<BaseApiResponse> updateAssociate(@PathVariable Long id,
			@RequestBody UpdateAssociate updateAssociate) {

		String msg = userService.updateAssociate(id, updateAssociate);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/deleteAssociate/{id}")
	public ResponseEntity<BaseApiResponse> deleteAssociate(@PathVariable Long id) {

		String msg = userService.deleteAssociate(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PutMapping("/updateLawyerProfile/{id}")
	public ResponseEntity<BaseApiResponse> updateLawyerProfile(@PathVariable("id") long id,
			@RequestPart String jsonRequest,
			@RequestPart(value = "lawyerImage", required = false) MultipartFile lawyerImage,
			@RequestPart(value = "cardFrontSide", required = false) MultipartFile cardFrontSide,
			@RequestPart(value = "cardBackSide", required = false) MultipartFile cardBackSide,
			@RequestPart(value = "cancelledCheque", required = false) MultipartFile cancelledCheque,
			@RequestPart(value = "panCard", required = false) MultipartFile panCard)
			throws IOException, AddressException, MessagingException {

		System.out.println("Inside constructor method");
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		LawyerProfile profile = mapper.readValue(jsonRequest, LawyerProfile.class);
//		  String response = userService.updateLawyerProfile(id, profile, lawyerImage );
		String response = userService.updateLawyerProfile(id, profile, lawyerImage, cardFrontSide, cardBackSide,
				cancelledCheque, panCard);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PutMapping("/updateLawyerStatus")
	public ResponseEntity<BaseApiResponse> updateLawyerStatus(@RequestBody UpdateLawyerStatus updateLawyerStatus)
			throws IOException, AddressException, MessagingException {

		CommonSuccessResponse response = userService.updateLawyerStatus(updateLawyerStatus);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@GetMapping("/sendResetPasswordMail")
	public ResponseEntity<BaseApiResponse> sendResetPasswordMail(@RequestParam String email) {
		CommonSuccessResponse response = userService.sendResetPasswordMail(email);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<BaseApiResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		CommonSuccessResponse response = userService.resetPassword(resetPasswordRequest);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/deleteUser")
	public ResponseEntity<BaseApiResponse> deleteUserById(@RequestParam Long id) {
		String msg = userService.deleteUserById(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/deleteLawyer")
	public ResponseEntity<BaseApiResponse> deleteLawyerById(@RequestParam Long id) {
		String msg = userService.deleteLawyerById(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PutMapping("/updateUser/{id}")
	public ResponseEntity<BaseApiResponse> updateUser(@PathVariable Long id, @RequestBody AdminUserProfile profile)
			throws IOException {
//		AdminUserProfile profile = new ObjectMapper().readValue(jsonRequest, AdminUserProfile.class);
		String msg = userService.updateUserProfileAdmin(id, profile);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PutMapping("/updateLawyer")
	public ResponseEntity<BaseApiResponse> updateLawyer(@RequestParam long id,
			@RequestBody LawyerAdminProfile lawyerAdminProfile)
			throws IOException, AddressException, MessagingException {
//		  ObjectMapper mapper = new ObjectMapper();
//		  mapper.registerModule(new JavaTimeModule());

//		  LawyerAdminProfile profile=mapper.readValue(jsonRequest,LawyerAdminProfile.class);
		String response = userService.updateLawyer(id, lawyerAdminProfile);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

	@PutMapping("/suspendLawyer/{userId}")
	public ResponseEntity<BaseApiResponse> suspendLawyer(@PathVariable("userId") Long userId) {

		CommonSuccessResponse entity = userService.setLawyerAsSuspend(userId);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(entity);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/checking")
	public ResponseEntity<BaseApiResponse> getLawyerList(@RequestParam (required = false) String email,
			@RequestParam (required = false) Long mobileNumber) {
		CommonSuccessResponse entity = userService.checkLawyerAndUser(email, mobileNumber);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(entity);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@PostMapping("/userRemark")
	public ResponseEntity<BaseApiResponse> userRemark(@RequestParam String orderNo, @RequestBody Userremark userremark) {
		String response = userService.userRemark(orderNo, userremark);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	@PostMapping("/lawyerOrderRemark")
	public ResponseEntity<BaseApiResponse> LawyerRemark(@RequestParam String orderNo, @RequestBody LawyerUserRemark lawyerUserRemark) throws MessagingException, IOException {
		String response = userService.LawyerRemark(orderNo, lawyerUserRemark);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getUserRemark/{id}")
	 public ResponseEntity<BaseApiResponse> getUserRemark(@PathVariable Long id){
		GetRemarkResponse msg =  userService.getUserRemark(id);
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(msg);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
}
