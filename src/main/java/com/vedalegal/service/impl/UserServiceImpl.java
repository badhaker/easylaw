package com.vedalegal.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.vedalegal.entity.*;
import com.vedalegal.enums.*;
import com.vedalegal.exception.OrderNotFoundException;
import com.vedalegal.modal.*;
import com.vedalegal.repository.*;
import com.vedalegal.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
/*import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;*/
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vedalegal.exception.AppException;
import com.vedalegal.exception.EnquiryNotFoundException;
import com.vedalegal.exception.LawyerOrUserNotFoundException;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.GetRemarkResponse;
import com.vedalegal.response.LawyerListWebsite;
import com.vedalegal.response.SocialMediaSigninResponse;
import com.vedalegal.service.CourtService;
import com.vedalegal.service.EducationService;
import com.vedalegal.service.EmailService;
import com.vedalegal.service.ExperienceService;
import com.vedalegal.service.ExpertiseService;
import com.vedalegal.service.FileUploadService;
import com.vedalegal.service.LanguageService;
import com.vedalegal.service.UserService;
import com.vedalegal.shared.dto.UserDto;
import com.vedalegal.util.AppUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private CourtRepository courtRepo;

	@Autowired
	private CourtService courtService;

	@Autowired
	private ExpertiseRepository expertiseRepo;

	@Autowired
	private ExpertiseService expertiseService;

	@Autowired
	private LanguageRepository languageRepo;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private ExperienceRepository experienceRepo;

	@Autowired
	private ExperienceService experienceService;

	@Autowired
	private EducationRepository educationRepo;

	@Autowired
	private EducationService educationService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private OrderRepository orderRepository;

	@Value("${AWS_BUCKET_NAME}")
	private String bucketName;

	@Autowired
	private FileUploadService fileService;

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Value("${RESET_PASSWORD_URL}")
	private String resetPasswordUrl;

	@Override
	public UserDto getUser(String email) {
		return null;
	}

	@Override
	public SocialMediaSigninResponse socialMediaSignin(SocialMediaSigninRequest request) {

		SocialMediaSigninResponse response = new SocialMediaSigninResponse();

		String email = request.getEmail();
		LoginModeEnum loginMode = request.getSingupMode();

		UserEntity user = userRepository.findByEmail(email).orElse(null);
		if (user != null) {

			if (user.getSingupMode().equals(LoginModeEnum.VEDALEGAL)) {
				throw new AppException(AppConstant.ErrorTypes.ACCOUNT_ALREADY_EXISTS,
						AppConstant.ErrorCodes.ACCOUNT_ALREADY_EXISTS_ERROR_CODE,
						AppConstant.ErrorMessage.ACCOUNT_ALREADY_EXISTS_SINGUP_MANUALLY);
			}
			if (request.getSingupMode().equals(user.getSingupMode())
					&& request.getToken().equals(user.getSocialMediaAccessToken())) {
				response.setSuccessfulLogin(true);
				return response;
			} else {
				throw new AppException(AppConstant.ErrorTypes.PASSWORD_DOES_NOT_MATCH,
						AppConstant.ErrorCodes.PASSWORD_DOES_NOT_MATCH_ERROR_CODE,
						AppConstant.ErrorMessage.PASSWORD_DOES_NOT_MATCH_MESSAGE);
			}
		} else {

			RoleEntity role = roleRepository.findByName(AppConstant.Commons.USER_ROLE)
					.orElseThrow(() -> new AppException(AppConstant.ErrorTypes.ROLE_NOT_EXISTS,
							AppConstant.ErrorCodes.ROLE_NOT_EXISTS_ERROR_CODE,
							AppConstant.ErrorMessage.ROLE_NOT_EXISTS_ERROR_MESSAGE));

			user = UserEntity.builder().email(email).socialMediaAccessToken(request.getToken()).singupMode(loginMode)
//					.contactNo(Long.parseLong(request.getMobileNumber()))
					.name(request.getName()).password(passwordEncoder.encode(" "))
					.approvalStatus(ApprovalStatus.Approved).role(role).build();
			if (request.getMobileNumber() != null && request.getMobileNumber() != "") {
				user.setContactNo(Long.parseLong(request.getMobileNumber()));
			}
			userRepository.save(user);
		}

		response.setSuccessfulLogin(true);
		return response;
	}
	@Override
	public List<LawyerList> getLawyerListByStatus(ApprovalStatus status, String search) {

		RoleEntity role = roleRepository.findByName(AppConstant.Commons.LAWYER_ROLE)
				.orElseThrow(() -> new AppException(AppConstant.ErrorTypes.ROLE_NOT_EXISTS,
						AppConstant.ErrorCodes.ROLE_NOT_EXISTS_ERROR_CODE,
						AppConstant.ErrorMessage.ROLE_NOT_EXISTS_ERROR_MESSAGE));
		System.out.println("Role " + role);
		List<UserEntity> userList = null;
		if (search == null || search.isEmpty()) {
			userList = userRepository.findByApprovalStatusAndRole(status, role);
		} else {
			userList = userRepository.findByApprovalStatusAndRoleAndSearchValue(status, role, search);
		}

		List<LawyerList> list = userList.stream().filter(u -> u.getBarCouncilNo() != null && u.getActive().equals(true))
				.map(u -> convertToLawyerResp(u)).collect(Collectors.toList());
		return list;
	}

	private LawyerList convertToLawyerResp(UserEntity user) {
		String location;
		if (user.getCity() != "" && user.getState() != "")
			location = user.getCity() + "," + user.getState();
		else if (user.getCity().equals(""))
			location = user.getState();
		else
			location = user.getCity();

		return LawyerList.builder().id(user.getId()).name(user.getName()).contactNo(user.getContactNo())
				.email(user.getEmail()).remarks(user.getRemarks()).location(location)
				.rating(user.getRating())
				.status(user.getApprovalStatus().toString()).isActive(user.getActive()).isSuspend(user.getIsSuspend())
				.build();
	}

	@Override
	public CommonSuccessResponse createCoustomer(CoustomerSingupRequest coustomerSingupRequest) {
		String email = coustomerSingupRequest.getEmail();
		Boolean check = userRepository.checkMobileExist(Long.parseLong(coustomerSingupRequest.getMobileNumber()));

		if (check) {
			throw new AppException(AppConstant.ErrorTypes.MOBILE_NO_ALREADY_EXISTS,
					AppConstant.ErrorCodes.MOBILE_NO_ALREADY_EXISTS_ERROR_CODE,
					AppConstant.ErrorMessage.MOBILE_NO_ALREADY_EXISTS_ERROR_MESSAGE);
		}
		UserEntity user = userRepository.checkEmailExist(email);

		if (user != null) {
				throw new AppException(AppConstant.ErrorTypes.ACCOUNT_ALREADY_EXISTS,
						AppConstant.ErrorCodes.ACCOUNT_ALREADY_EXISTS_ERROR_CODE,
						AppConstant.ErrorMessage.ACCOUNT_ALREADY_EXISTS_ERROR_MESSAGE);
		}
		
		RoleEntity role = roleRepository.findByName(AppConstant.Commons.USER_ROLE)
				.orElseThrow(() -> new AppException(AppConstant.ErrorTypes.ROLE_NOT_EXISTS,
						AppConstant.ErrorCodes.ROLE_NOT_EXISTS_ERROR_CODE,
						AppConstant.ErrorMessage.ROLE_NOT_EXISTS_ERROR_MESSAGE));

		 user = UserEntity.builder().email(email).password(passwordEncoder.encode(coustomerSingupRequest.getPassword()))
				.contactNo(Long.parseLong(coustomerSingupRequest.getMobileNumber()))
				.gender(Gender.MALE)
				.name(coustomerSingupRequest.getName()).singupMode(LoginModeEnum.VEDALEGAL)
				.approvalStatus(ApprovalStatus.Approved).role(role).isSuspend(false).build();

		userRepository.save(user);

		return new CommonSuccessResponse(true);
	}

	@Override
	public CommonSuccessResponse createLawyer(com.vedalegal.request.LawyerSingupRequest lawyerSingupRequest) {
		String email = lawyerSingupRequest.getEmail();

		Boolean check = userRepository.checkMobileExist(Long.parseLong(lawyerSingupRequest.getMobileNumber()));

		if (check) {
			throw new AppException(AppConstant.ErrorTypes.MOBILE_NO_ALREADY_EXISTS,
					AppConstant.ErrorCodes.MOBILE_NO_ALREADY_EXISTS_ERROR_CODE,
					AppConstant.ErrorMessage.MOBILE_NO_ALREADY_EXISTS_ERROR_MESSAGE);
		}

//		UserEntity user = userRepository.findByEmail(email).orElse(null);
		UserEntity user = userRepository.checkEmailExist(email);
		if (user != null) {
			throw new AppException(AppConstant.ErrorTypes.ACCOUNT_ALREADY_EXISTS,
					AppConstant.ErrorCodes.ACCOUNT_ALREADY_EXISTS_ERROR_CODE,
					AppConstant.ErrorMessage.ACCOUNT_ALREADY_EXISTS_ERROR_MESSAGE);
		}

		RoleEntity role = roleRepository.findByName(AppConstant.Commons.LAWYER_ROLE)
				.orElseThrow(() -> new AppException(AppConstant.ErrorTypes.ROLE_NOT_EXISTS,
						AppConstant.ErrorCodes.ROLE_NOT_EXISTS_ERROR_CODE,
						AppConstant.ErrorMessage.ROLE_NOT_EXISTS_ERROR_MESSAGE));

				 user = UserEntity.builder().email(email).password(passwordEncoder.encode(lawyerSingupRequest.getPassword()))
				.contactNo(Long.parseLong(lawyerSingupRequest.getMobileNumber())).name(lawyerSingupRequest.getName())
				.barCouncilNo(lawyerSingupRequest.getBarCouncilNumber())
				.gender(Gender.MALE)
				.barCouncilName(lawyerSingupRequest.getBarCouncilName()).approvalStatus(ApprovalStatus.InProcess)
				.singupMode(LoginModeEnum.VEDALEGAL).role(role).isSuspend(false).build();

		user = userRepository.save(user);
//		
//		Long id=user.getId();
//		String originalFileName=null;
//		String filePath=null;
//		originalFileName=cardFrontSide.getOriginalFilename();
//		filePath=getFileName(id, "CardFrontSide", originalFileName);
//		uploadFile(cardFrontSide, filePath);
//		user.setRegCardFront(filePath);
//		
//		originalFileName=cardBackSide.getOriginalFilename();
//		filePath=getFileName(id, "CardBackSide", originalFileName);
//		uploadFile(cardBackSide, filePath);
//		user.setRegCardBack(filePath);
//		
//		if (cancelledCheque!=null&&cancelledCheque.getSize()>0) {
//			originalFileName=cancelledCheque.getOriginalFilename();
//			filePath=getFileName(id, "cancelledCheque", originalFileName);
//			uploadFile(cancelledCheque, filePath);
//			user.setCancelCheque(filePath);
//		}
//	

//		userRepository.save(user);
		return new CommonSuccessResponse(true);
	}

	@Override
	public CommonSuccessResponse userLogIn(UserLogInRequest userLogInRequest) {
		String email = userLogInRequest.getEmail();

		UserEntity entity = userRepository.findByEmail(email)
				.orElseThrow(() -> new AppException(AppConstant.ErrorTypes.ENTITY_NOT_EXISTS_ERROR,
						AppConstant.ErrorCodes.ENTITY_NOT_EXISTS_ERROR_CODE,
						AppConstant.ErrorMessage.ENTITY_NOT_EXISTS_ERROR_MESSAGE));

		if (!LoginModeEnum.VEDALEGAL.equals(entity.getSingupMode())
				|| !passwordEncoder.matches(userLogInRequest.getPassword(), entity.getPassword())) {

			throw new AppException(AppConstant.ErrorTypes.INVALID_CREDENTIALS,
					AppConstant.ErrorCodes.INVALID_CREDENTIALS_ERROR_CODE,
					AppConstant.ErrorMessage.INVALID_CREDENTIALS_ERROR_MESSAGE);
		}
		if(entity.getApprovalStatus() != ApprovalStatus.Approved
				&& entity.getApprovalStatus() != ApprovalStatus.InProcess) {

			throw new AppException(AppConstant.ErrorTypes.ACCOUNT_NOT_APPROVED,
					AppConstant.ErrorCodes.ACCOUNT_NOT_APPROVED_CODE,
					AppConstant.ErrorMessage.ACCOUNT_NOT_APPROVED_MESSAGE);
			
		}
		
		if(entity.getIsSuspend().equals(true)) {
			throw new AppException(AppConstant.ErrorTypes.USER_SUPENEDED,
					AppConstant.ErrorCodes.USER_SUPENEDED_CODE,
					AppConstant.ErrorMessage.USER_SUPENEDED_MESSAGE);	
		}
		if(entity.getActive() == false) {
			throw new AppException(AppConstant.ErrorTypes.USER_DELETED,
					AppConstant.ErrorCodes.USER_DELETED_CODE,
					AppConstant.ErrorMessage.USER_DELETED_MESSAGE);	
		}
		// Validating user based on user role
		if (!userLogInRequest.getRoleType().equalsIgnoreCase(entity.getRole().getName())) {
			throw new AppException(AppConstant.ErrorTypes.INVALID_ROLE_ERROR,
					AppConstant.ErrorCodes.INVALID_ROLE_ERROR_CODE,
					AppConstant.ErrorMessage.INVALID_ROLE_ERROR_MESSAGE);
		}

		return new CommonSuccessResponse(true);
	}

	public LawyerDetailResponse getLawyerDetail(Long id) {
		UserEntity lawyer = userRepository.findById(id).orElse(new UserEntity());
		if (lawyer.getId() == null) {
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.LAWYER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.LAWYER_NOT_EXIST_CODE, AppConstant.ErrorMessage.LAWYER_NOT_EXIST_MESSAGE);
		}
		LawyerDetailResponse lawyerResp = convertToLawyerDetail(lawyer);
		return lawyerResp;

	}

	private LawyerDetailResponse convertToLawyerDetail(UserEntity lawyer) {
		String location;
		if (lawyer.getCity() != null && lawyer.getState() != null)
			location = lawyer.getCity() + "," + lawyer.getState();
		else if (lawyer.getCity() == null)
			location = lawyer.getState();
		else
			location = lawyer.getCity();

		return LawyerDetailResponse.builder().id(lawyer.getId()).name(lawyer.getName()).contactNo(lawyer.getContactNo())
				.email(lawyer.getEmail()).remarks(lawyer.getRemarks()).expertiseList(lawyer.getLawyerExpertise())
				.barCouncilNo(lawyer.getBarCouncilNo()).barCouncilName(lawyer.getBarCouncilName())
				.pincode(lawyer.getPincode()).location(location)
				.lawyerImage(fileService.generatePreSignedUrlForFileDownload(lawyer.getImgUrl()))
				.approvalStatus(lawyer.getApprovalStatus().toString())
				.cancelCheque(fileService.generatePreSignedUrlForFileDownload(lawyer.getCancelCheque()))
				.description(lawyer.getDescription()).yearsOfExperience(lawyer.getYearsOfExperience())
				.regCardFront(fileService.generatePreSignedUrlForFileDownload(lawyer.getRegCardFront()))
				.regCardBack(fileService.generatePreSignedUrlForFileDownload(lawyer.getRegCardBack()))
				.panCard(fileService.generatePreSignedUrlForFileDownload(lawyer.getPanCard()))
				.gender(lawyer.getGender().toString())
				.languages(lawyer.getLanguages().stream().filter(lan -> lan.getActive() != false)
						.map(l -> convertToModalLang(l)).collect(Collectors.toList()))
				.courts(lawyer.getCourts().stream().filter(lan -> lan.getActive() != false)
						.map(l -> convertToModalCourt(l)).collect(Collectors.toList()))
				.education(lawyer.getEducation().stream().filter(lan -> lan.getActive() != false)
						.map(l -> convertToModalEdu(l)).collect(Collectors.toList()))
				.experience(lawyer.getExperience().stream().filter(lan -> lan.getActive() != false)
						.map(l -> convertToModalWork(l)).collect(Collectors.toList()))
				.expertise(lawyer.getExpertise().stream().filter(lan -> lan.getActive() != false)
						.map(l -> convertToModalExp(l)).collect(Collectors.toList()))
				.build();
	}

	private LawyerExpertise convertToModalExp(LawyerExpertiseEntity entity) {
		return LawyerExpertise.builder()
				.callPrice(entity.getCallPrice()).meetingPrice(entity.getMeetingPrice()).build();
	}

	private LawyerWorkExperience convertToModalWork(LawyerWorkExperienceEntity entity) {

		return LawyerWorkExperience.builder().organization(entity.getOrganization()).id(entity.getId())
				.designation(entity.getDesignation()).startTime(entity.getStartTime()).endTime(entity.getEndTime())
				.build();
	}

	private LawyerEducation convertToModalEdu(LawyerEducationEntity entity) {
		return LawyerEducation.builder().id(entity.getId()).institution(entity.getInstitution())
				.course(entity.getCourse()).startDate(entity.getStartDate()).endDate(entity.getEndDate()).build();
	}

	private LawyerCourt convertToModalCourt(LawyerCourtEntity entity) {
		return LawyerCourt.builder().id(entity.getId()).courtName(entity.getCourtName())
				.courtLocation(entity.getCourtLocation()).build();
	}

	private LawyerLanguage convertToModalLang(LawyerLanguageEntity entity) {

		return LawyerLanguage.builder().id(entity.getId()).name(entity.getName()).build();
	}

	@Override
	public List<UserListResponse> getUserList(String search) {
		RoleEntity role = roleRepository.findByName(AppConstant.Commons.USER_ROLE).get();
		List<UserEntity> userList = null;
		if (search == null) {
			userList = userRepository.findAllByRole(role);
		} else {
			userList = userRepository.findAllByRoleAndSearchValue(role, search);
		}

		if (userList.size() <= 0) {
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.LAWYER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.LAWYER_NOT_EXIST_CODE, AppConstant.ErrorMessage.LAWYER_NOT_EXIST_MESSAGE);

		}
		return userList.stream().filter(t->t.getActive()==true).map(u -> convertToUserResp(u)).collect(Collectors.toList());

	}

	private UserListResponse convertToUserResp(UserEntity u) {

		return UserListResponse.builder().id(u.getId()).name(u.getName()).email(u.getEmail())
				.contactNo(u.getContactNo()).userIdURL(u.getIdImage()).isActive(u.getActive())
				.isSuspend(u.getIsSuspend()).build();
	}

	private void uploadFile(MultipartFile multipartFile, String filePath) throws IOException {
		InputStream streamToUpload = null;
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentType(multipartFile.getContentType());
		metaData.setContentLength(multipartFile.getSize());
		try {
			streamToUpload = multipartFile.getInputStream();
			amazonS3.putObject(new PutObjectRequest(bucketName, filePath, streamToUpload, metaData)
					.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (SdkClientException e) {
			throw new AppException(AppConstant.ErrorTypes.UPLOAD_FILE_EXCEPTION,
					AppConstant.ErrorCodes.UPLOAD_FILE_EXCEPTION_CODE, e.getMessage());

		} finally {
			streamToUpload.close();
		}
	}

	private String getFileName(Long userId, String fileName, String orignalFileName) {
		String extension = null;
		extension = orignalFileName.substring(orignalFileName.lastIndexOf("."));
		String filePath = "LawyerDocument/user_" + userId + "/" + fileName + extension;
		return filePath;
	}

	@Override
	public GetUserDetailsResponse getUserDetails(String email) {
		// UserEntity user=userRepository.findByEmail(email).orElse(new UserEntity());
		UserEntity user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.USER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.USER_NOT_EXIST_CODE, AppConstant.ErrorMessage.USER_NOT_EXIST_MESSAGE);
		}
		GetUserDetailsResponse UserResp = convertToUserDetail(user);
		return UserResp;

	}

	private GetUserDetailsResponse convertToUserDetail(UserEntity user) {
		return GetUserDetailsResponse.builder().id(user.getId()).name(user.getName()).contactNo(user.getContactNo())
				.email(user.getEmail()).role(user.getRole().getName()).permissions(user.getRole().getPermissions())
				.status(user.getApprovalStatus()).barCouncilNo(user.getBarCouncilNo())
				.imgURL(fileService.generatePreSignedUrlForFileDownload(user.getImgUrl())).build();
	}

	@Override
	// public String updateUserProfile(Long id , com.vedalegal.request.Profile
	// userProfile , MultipartFile document,MultipartFile image) throws IOException
	// {
	public String updateUserProfile(Long id, com.vedalegal.request.Profile userProfile, MultipartFile image)
			throws IOException {
		UserEntity entity = userRepository.findById(id)
				.orElseThrow(() -> new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.USER_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.USER_NOT_EXIST_CODE, AppConstant.ErrorMessage.USER_NOT_EXIST_MESSAGE));

		entity.setName(userProfile.getName());
		entity.setEmail(userProfile.getEmail());
//		entity.setContactNo(userProfile.getContactNo());
		/*
		 * String originalFileName=null; String filePath=null;
		 * originalFileName=document.getOriginalFilename(); filePath=getFileName(id,
		 * "document", originalFileName); uploadFile(document, filePath);
		 * entity.setIdImage(filePath);
		 */
		if (image != null && image.getSize() > 0) {
		String originalFileName = null;
		String filePath = null;
		originalFileName = image.getOriginalFilename();
		filePath = getFileName(id, "image", originalFileName);
		uploadFile(image, filePath);
		entity.setImgUrl(filePath);
		}
		
		if (userProfile.isBox() == true) {
			boolean isPasswordMatch = passwordEncoder.matches(userProfile.getOldpassword(), entity.getPassword());

			if (isPasswordMatch) {

				entity.setPassword(passwordEncoder.encode(userProfile.getPassword()));
			} else {
				throw new AppException(AppConstant.ErrorTypes.INVALID_CREDENTIALS,
						AppConstant.ErrorCodes.INVALID_CREDENTIALS_ERROR_CODE,
						AppConstant.ErrorMessage.INVALID_CREDENTIALS_ERROR_MESSAGE);
			}

		}
		userRepository.save(entity);
		return "Success";

	}

	@Override
	public CommonSuccessResponse addAssociate(AddAssociate addAssociate) throws AddressException, IOException, MessagingException {
		String email = addAssociate.getEmail();
//		UserEntity user = userRepository.findByEmail(email).orElse(null);
		UserEntity user = userRepository.checkEmailExist(email);
		if (user != null) {
			throw new AppException(AppConstant.ErrorTypes.ACCOUNT_ALREADY_EXISTS,
					AppConstant.ErrorCodes.ACCOUNT_ALREADY_EXISTS_ERROR_CODE,
					AppConstant.ErrorMessage.ACCOUNT_ALREADY_EXISTS_ERROR_MESSAGE);
		}
		Boolean check = userRepository.checkMobileExist(addAssociate.getContactNo());

		if (check) {
			throw new AppException(AppConstant.ErrorTypes.MOBILE_NO_ALREADY_EXISTS,
					AppConstant.ErrorCodes.MOBILE_NO_ALREADY_EXISTS_ERROR_CODE,
					AppConstant.ErrorMessage.MOBILE_NO_ALREADY_EXISTS_ERROR_MESSAGE);
		}
		UserEntity entity = new UserEntity();
		// System.out.println(addAssociate.getRoleId());
		RoleEntity role = roleRepository.findById(addAssociate.getRoleId()).orElse(null);
		// System.out.println(role);
		entity.setRole(role);
		entity.setName(addAssociate.getName());
		entity.setEmail(addAssociate.getEmail());
		entity.setContactNo(addAssociate.getContactNo());
		entity.setApprovalStatus(ApprovalStatus.Approved);
		entity.setSingupMode(LoginModeEnum.VEDALEGAL);
		entity.setPassword(passwordEncoder.encode(addAssociate.getPassword()));
		entity.setIsSuspend(false);
		String emailBody = "Dear " + entity.getName().split(" ")[0]
				+ ", \n \t  Your Associate account has been created by Easy Law team. \n\n Thanks and regards \n Easylaw  ";
		emailService.sendEmail(entity.getEmail(), "Easylaw: Associate Account creation.", emailBody);
		userRepository.save(entity);
		return new CommonSuccessResponse(true);

	}

	@Override
	public List<AssociateListResponse> getAssociate() {
		List<UserEntity> AList = (List<UserEntity>) userRepository.findAll();
		if (AList.size() <= 0) {
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.ASSOCIATE_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ASSOCIATE_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.ASSOCIATE_NOT_EXIST_MESSAGE);

		}

		AList = AList.stream()
				.filter(associate -> !(associate.getRole().getName().equals(AppConstant.Commons.USER_ROLE)
						|| associate.getRole().getName().equals(AppConstant.Commons.LAWYER_ROLE)
						|| associate.getRole().getName().equals(AppConstant.Commons.ADMIN_ROLE)))
				.collect(Collectors.toList());
		
		List<AssociateListResponse> useList = AList.stream().filter(t->t.getActive()!=false).map(u -> convertToU(u)).collect(Collectors.toList());
		return useList;
	}

	private AssociateListResponse convertToU(UserEntity u) {
		return AssociateListResponse.builder().name(u.getName()).assocoateId(u.getId()).contactNo(u.getContactNo())
				.email(u.getEmail()).roleId(u.getRole().getId()).role(u.getRole().getName())
				.build();

	}

	@Override
	public String updateAssociate(Long id, UpdateAssociate updateAssociate) {
		UserEntity entity = userRepository.findById(id).orElse(null);
		RoleEntity role = roleRepository.findById(updateAssociate.getRoleId()).orElse(null);
		entity.setRole(role);
		entity.setName(updateAssociate.getName());
		entity.setEmail(updateAssociate.getEmail());
		entity.setContactNo(updateAssociate.getContactNo());
		entity.setPassword(passwordEncoder.encode(updateAssociate.getPassword()));
		userRepository.save(entity);

		return "Associate Updated Sucessfully";

	}
	
	@Override
	public String deleteAssociate(Long id) {
		UserEntity entity = userRepository.findById(id).orElse(null);
		long number = (long) Math.floor(Math.random() * 900_000_000L) + 100_000_000L;

		String userEmail = entity.getEmail();
		userEmail = String.valueOf(number) + userEmail;

		entity.setContactNo(number);
		entity.setEmail(userEmail);
		entity.setActive(false);
		userRepository.save(entity);
		return "Associate Delete Sucessfully";
	}

	@Override
	public List<LawyerListWebsite> getLawyersByLocationAndExpertise(String state, String city, String expertise,
			String name, Long lawyer_years_of_experience, String gender) {

//		Gender genderEnum = Gender.valueOf(gender);
		List<UserEntity> list = userRepository.findAllByLocationAndExpertise(state, city, expertise, name,
				lawyer_years_of_experience, gender);
		List<LawyerListWebsite> lawyerList = list.stream()
				.filter(l -> l.getBarCouncilNo() != null && l.getRole().getName().equalsIgnoreCase("Lawyer"))
				.map(law -> converToLawyerListWebsite(law)).collect(Collectors.toList());

		System.out.println(list.size());
		System.out.println(lawyerList.size());

		if (lawyerList.size() <= 0) {
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.LAWYER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.LAWYER_NOT_EXIST_CODE, AppConstant.ErrorMessage.LAWYER_NOT_EXIST_MESSAGE);

		}
		return lawyerList;
	}

	private LawyerListWebsite converToLawyerListWebsite(UserEntity entity) {

		return LawyerListWebsite.builder().id(entity.getId()).name(entity.getName())
				.description(entity.getDescription())
				// .imgUrl(entity.getImgUrl())
				.imgUrl(fileService.generatePreSignedUrlForFileDownload(entity.getImgUrl()))
				.yearsOfExperience(entity.getYearsOfExperience()).city(entity.getCity()).state(entity.getState())
				.gender(entity.getGender())
				.rating(entity.getRating())
				.isSuspend(entity.getIsSuspend())
				.pincode(entity.getPincode())
				.expertise(entity.getLawyerExpertise())
				/*.expertise(entity.getExpertise().stream().filter(exp -> exp.getActive() != false)
						.map(exp -> expertiseService.convertToExpertiseModal(exp)).collect(Collectors.toList()))*/
				.build();
	}

	@Override
	public String updateLawyerProfile(long id, LawyerProfile profile, MultipartFile lawyerImage,
									  MultipartFile cardFrontSide, MultipartFile cardBackSide, MultipartFile cancelledCheque,
									  MultipartFile panCard) throws IOException, AddressException, MessagingException {

		UserEntity lawyer = userRepository.findById(id).orElse(null);
		if (lawyer == null) {
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.LAWYER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.LAWYER_NOT_EXIST_CODE, AppConstant.ErrorMessage.LAWYER_NOT_EXIST_MESSAGE);
		}
		lawyer.getEducation().stream().forEach(ed -> {
			ed.setActive(false);
			educationRepo.save(ed);
		});
		lawyer.getCourts().stream().forEach(ed -> {
			ed.setActive(false);
			courtRepo.save(ed);
		});
		lawyer.getExperience().stream().forEach(ed -> {
			ed.setActive(false);
			experienceRepo.save(ed);
		});
		lawyer.getExpertise().stream().forEach(ed -> {
			ed.setActive(false);
			expertiseRepo.save(ed);
		});
		lawyer.getLanguages().stream().forEach(ed -> {
			ed.setActive(false);
			languageRepo.save(ed);
		});
		lawyer.setEmail(profile.getEmail());
		lawyer.setGender(profile.getGender());
		lawyer.setCity(profile.getCity());
		lawyer.setState(profile.getState());
		lawyer.setPincode(profile.getPincode());
		lawyer.setDescription(profile.getDescription());
		lawyer.setLawyerExpertise(profile.getExpertiseList());
		if (lawyer.getApprovalStatus() == ApprovalStatus.InProcess || lawyer.getApprovalStatus() == ApprovalStatus.Approved) {
			lawyer.setApprovalStatus(ApprovalStatus.Pending);
		}
		lawyer.setBarCouncilNo(profile.getBarCouncilNo());
		lawyer.setBarCouncilName(profile.getBarCouncilName());
		lawyer.setYearsOfExperience(profile.getYearsOfExperience());
		profile.getCourts().stream().forEach(c -> courtRepo.save(courtService.convertToEntity(c, lawyer)));
		profile.getExpertise().stream().forEach(e -> expertiseRepo.save(expertiseService.convertToEntity(e, lawyer)));
		profile.getExperience().stream()
				.forEach(exp -> experienceRepo.save(experienceService.convertToEntity(exp, lawyer)));
		profile.getLanguages().stream()
				.forEach(lang -> languageRepo.save(languageService.convertToEntity(lang, lawyer)));
		profile.getEducation().stream()
				.forEach(edu -> educationRepo.save(educationService.convertToEntity(edu, lawyer)));

		String originalFileName = null;
		String filePath = null;

		if (lawyerImage != null && lawyerImage.getSize() > 0) {
			originalFileName = lawyerImage.getOriginalFilename();
			filePath = getFileName(id, "LawyerImage", originalFileName);
			uploadFile(lawyerImage, filePath);
			lawyer.setImgUrl(filePath);
		}

		if (cardFrontSide != null && cardFrontSide.getSize() > 0) {
			originalFileName = cardFrontSide.getOriginalFilename();
			filePath = getFileName(id, "CardFrontSide", originalFileName);
			uploadFile(cardFrontSide, filePath);
			lawyer.setRegCardFront(filePath);
		}

		if (cardBackSide != null && cardBackSide.getSize() > 0) {
			originalFileName = cardBackSide.getOriginalFilename();
			filePath = getFileName(id, "CardBackSide", originalFileName);
			uploadFile(cardBackSide, filePath);
			lawyer.setRegCardBack(filePath);
		}

		if (cancelledCheque != null && cancelledCheque.getSize() > 0) {
			originalFileName = cancelledCheque.getOriginalFilename();
			filePath = getFileName(id, "cancelledCheque", originalFileName);
			uploadFile(cancelledCheque, filePath);
			lawyer.setCancelCheque(filePath);
		}

		if (panCard != null && panCard.getSize() > 0) {
			originalFileName = panCard.getOriginalFilename();
			filePath = getFileName(id, "panCard", originalFileName);
			uploadFile(panCard, filePath);
			lawyer.setPanCard(filePath);
		}

		userRepository.save(lawyer);
		String emailBody = "Dear " + lawyer.getName().split(" ")[0]
				+ ", \n \t Thanks for updating your profile. Your profile has been freezed as of now and you will be able to login only after your profile gets approved. \n\n Thanks and regards \n Easylaw  ";
		emailService.sendEmail(lawyer.getEmail(), "Easylaw: profile updated Successfully.", emailBody);
		return "Lawyer profile updated successfully";
	}


	@Override
	public CommonSuccessResponse updateLawyerStatus(UpdateLawyerStatus updateLawyerStatus)
			throws AddressException, IOException, MessagingException {
		UserEntity entity = userRepository.findById(updateLawyerStatus.getId()).orElse(null);
		entity.setApprovalStatus(updateLawyerStatus.getStatus());
		entity.setRating(updateLawyerStatus.getRating());
		entity.setRemarks(updateLawyerStatus.getRemarks());

		String emailBody = "Dear " + entity.getName().split(" ")[0]
				+ ", \n \t Thanks for updating your profile. Your profile has been activate as of now and you will be able to login successfully \n\n Thanks and regards \n Easylaw  ";
		emailService.sendEmail(entity.getEmail(), "Easylaw: profile Activate.", emailBody);
		userRepository.save(entity);
		return new CommonSuccessResponse(true);

	}

	@Override
	public CommonSuccessResponse sendResetPasswordMail(String email) {
		UserEntity entity = userRepository.findByEmail(email)
				.orElseThrow(() -> new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.LAWYER_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.LAWYER_NOT_EXIST_CODE,
						AppConstant.ErrorMessage.LAWYER_NOT_EXIST_MESSAGE));
		String tocken = AppUtil.generateResetPasswordVerificationToken(email, TokenType.RESET_PASSWORD);
		VerificationTokenEntity verificationTokenEntity = verificationTokenRepository
				.findByUserEntityAndTokenType(entity, TokenType.RESET_PASSWORD);
		if (verificationTokenEntity == null) {
			verificationTokenEntity = new VerificationTokenEntity();
			verificationTokenEntity.setTokenType(TokenType.RESET_PASSWORD);
			verificationTokenEntity.setUserEntity(entity);
		}
		verificationTokenEntity.setConfirmationToken(tocken);

		verificationTokenRepository.save(verificationTokenEntity);

		String emailBody = "Reset Password Link:- " + resetPasswordUrl + tocken;
		try {
			emailService.sendEmail(email, "Easylaw: Reset Password", emailBody);
		} catch (IOException | MessagingException e) {
			e.printStackTrace();
		}
		return new CommonSuccessResponse(true);
	}

	@Override
	public CommonSuccessResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
		String verificationTocken = resetPasswordRequest.getVerificationTocken();
		String password = resetPasswordRequest.getPassword();

		VerificationTokenEntity verificationTokenEntity = verificationTokenRepository
				.findByConfirmationTokenAndTokenType(verificationTocken, TokenType.RESET_PASSWORD);

		if (verificationTokenEntity == null) {
			throw new AppException(AppConstant.ErrorTypes.ENTITY_NOT_EXISTS_ERROR,
					AppConstant.ErrorCodes.ENTITY_NOT_EXISTS_ERROR_CODE,
					AppConstant.ErrorMessage.ENTITY_NOT_EXISTS_ERROR_MESSAGE);
		}
		UserEntity entity = verificationTokenEntity.getUserEntity();
		entity.setPassword(passwordEncoder.encode(password));
		userRepository.save(entity);
		return new CommonSuccessResponse(true);
	}

	@Override
	public String deleteUserById(Long id) {
		UserEntity entity = userRepository.findById(id).orElse(null);
		if (entity == null || entity.getActive() == false) {
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.EXPERTISE_NAME_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.EXPERTISE_NAME_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.EXPERTISE_NAME_NOT_EXIST_MESSAGE);
		}
		long number = (long) Math.floor(Math.random() * 900_000_000L) + 100_000_000L;

		String userEmail = entity.getEmail();
		userEmail = String.valueOf(number) + userEmail;

		entity.setContactNo(number);
		entity.setEmail(userEmail);
		entity.setActive(false);
//		entity.setContactNo((long) 001111);
		userRepository.save(entity);
		return "User Delete Successfully";
	}
	

	@Override
	public String deleteLawyerById(Long id) {
		UserEntity entity = userRepository.findById(id).orElse(null);
		if (entity == null || entity.getActive() == false) {
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.EXPERTISE_NAME_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.EXPERTISE_NAME_NOT_EXIST_CODE,
					AppConstant.ErrorMessage.EXPERTISE_NAME_NOT_EXIST_MESSAGE);
		}
		long number = (long) Math.floor(Math.random() * 900_000_000L) + 100_000_000L;

		String userEmail = entity.getEmail();
		userEmail = String.valueOf(number) + userEmail;

		entity.setContactNo(number);
		entity.setEmail(userEmail);
		entity.setActive(false);
//		entity.setContactNo((long) 001111);
		userRepository.save(entity);
		return "Lawyer Delete Successfully";
	}

	@Override
	public String updateLawyer(long id, LawyerAdminProfile profile)
			throws IOException, AddressException, MessagingException {
		UserEntity lawyer = userRepository.findById(id).orElse(null);
		if (lawyer == null) {
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.LAWYER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.LAWYER_NOT_EXIST_CODE, AppConstant.ErrorMessage.LAWYER_NOT_EXIST_MESSAGE);
		}
		if (!profile.getName().equals("") && !profile.getName().equals(null))
			lawyer.setName(profile.getName());
		if (!profile.getContactNo().equals("") && !profile.getContactNo().equals(null))
			lawyer.setContactNo(profile.getContactNo());
		if (!profile.getEmail().equals("") && !profile.getEmail().equals(null))
			lawyer.setEmail(profile.getEmail());
		if (!profile.getCity().equals("") && !profile.getCity().equals(null))
			lawyer.setCity(profile.getCity());
		if (!profile.getState().equals("") && !profile.getState().equals(null))
			lawyer.setState(profile.getState());
		if (!profile.getRating().equals("") && !profile.getRating().equals(null))
			lawyer.setRating(profile.getRating());
		lawyer.setRemarks(profile.getRemarks());
		userRepository.save(lawyer);
		return "Lawyer profile updated successfully";
	}
	
	@Override
	public CommonSuccessResponse setLawyerAsSuspend(Long userId) {
		UserEntity lawyer = userRepository.findById(userId).orElse(null);
		if (lawyer == null) {
			throw new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.LAWYER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.LAWYER_NOT_EXIST_CODE, AppConstant.ErrorMessage.LAWYER_NOT_EXIST_MESSAGE);
		}
		if (lawyer.getIsSuspend()) {
			lawyer.setIsSuspend(false);
		} else {
			lawyer.setIsSuspend(true);
		}
		userRepository.save(lawyer);
		return new CommonSuccessResponse(true);
	}

	public String updateUserProfileAdmin(Long id, AdminUserProfile userProfile) throws IOException {
		UserEntity entity = userRepository.findById(id)
				.orElseThrow(() -> new LawyerOrUserNotFoundException(AppConstant.ErrorTypes.USER_NOT_EXIST_ERROR,
						AppConstant.ErrorCodes.USER_NOT_EXIST_CODE, AppConstant.ErrorMessage.USER_NOT_EXIST_MESSAGE));

		entity.setName(userProfile.getName());
		entity.setEmail(userProfile.getEmail());
		entity.setContactNo(userProfile.getContactNo());
		userRepository.save(entity);
		return " User update Successfully by admin";

	}
	
	@Override
	public CommonSuccessResponse checkLawyerAndUser(String email,Long mobileNumber) {
		if(email==null)
		{
			UserEntity user = userRepository.checkMobileExists(mobileNumber);
			if (user != null) {
				throw new AppException(AppConstant.ErrorTypes.USER_EXIST_IN_DATABASE,
						AppConstant.ErrorCodes.USER_EXIST_IN_DATABASE_ERROR_CODE,
						AppConstant.ErrorMessage.USER_EXIST_IN_DATABASE_ERROR_MESSAGE);
			}
		}
		else if(mobileNumber==null)
		{
			UserEntity user = userRepository.findByEmail(email).orElse(null);
			if (user != null) {
				throw new AppException(AppConstant.ErrorTypes.USER_EXIST_IN_DATABASE,
						AppConstant.ErrorCodes.USER_EXIST_IN_DATABASE_ERROR_CODE,
						AppConstant.ErrorMessage.USER_EXIST_IN_DATABASE_ERROR_MESSAGE);
			}
		}
		else {
//			UserEntity user = userRepository.checkIfExists(email, mobileNumber);
			UserEntity user = userRepository.findByEmail(email).orElse(null);

			if (user != null) {
				throw new AppException(AppConstant.ErrorTypes.USER_EXIST_IN_DATABASE,
						AppConstant.ErrorCodes.USER_EXIST_IN_DATABASE_ERROR_CODE,
						AppConstant.ErrorMessage.USER_EXIST_IN_DATABASE_ERROR_MESSAGE);
			}

			UserEntity user1 = userRepository.checkMobileExists(mobileNumber);
			if (user1 != null) {
				throw new AppException(AppConstant.ErrorTypes.USER_EXIST_IN_DATABASE,
						AppConstant.ErrorCodes.USER_EXIST_IN_DATABASE_ERROR_CODE,
						AppConstant.ErrorMessage.USER_EXIST_IN_DATABASE_ERROR_MESSAGE);
			}
		}
		
		return new CommonSuccessResponse(true);
	}
	
	@Override
	public String userRemark(String orderNo, Userremark userremark) {

		Long count = orderRepository.getOrderCount(orderNo);

		if(count<1)
		{
			throw new OrderNotFoundException(AppConstant.ErrorTypes.ORDER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ORDER_NOT_EXIST_CODE,"Order Does Not Exist");
		}

		OrderEntity orderEntity = orderRepository.findByOrderNo(orderNo);
		UserEntity entity = orderEntity.getUserId();

		entity.setUser_Rating(userremark.getUserrating());
		entity.setUserremarks(userremark.getReview());
		userRepository.save(entity);
		return "User Review update";
	}
	
	@Override
	public String LawyerRemark(String orderNo, LawyerUserRemark lawyerUserRemark) throws MessagingException, IOException {

		Long count = orderRepository.getOrderCount(orderNo);

		if(count<1)
		{
			throw new OrderNotFoundException(AppConstant.ErrorTypes.ORDER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.ORDER_NOT_EXIST_CODE,"Order Does Not Exist");
		}

		OrderEntity orderEntity = orderRepository.findByOrderNo(orderNo);
		UserEntity entity = orderEntity.getUserId();

		if(lawyerUserRemark.getOrderStatus().equals(OrderStatus.INPROCESS))
		{
			emailService.sendEmail(orderEntity.getUserId().getEmail(),"Easylaw- Order Status Changed.",
					"Dear "+orderEntity.getUserId().getName().split(" ")[0]+", \n \n \t Your Order No. "+orderEntity.getOrderNo()+"" +
							" has been picked by lawyer "+orderEntity.getLawyerId().getName().split(" ")[0]+". The Lawyer will connect with " +
							"you soon. \n" +
							"Hope you like our service. Have a good day.");

			emailService.sendEmail(orderEntity.getLawyerId().getEmail(),"Easylaw- Order Status Changed.",
					"Dear "+orderEntity.getLawyerId().getName().split(" ")[0]+", \n \n \t Order No. "+orderEntity.getOrderNo()+"" +
							" logged by "+orderEntity.getUserId().getName().split(" ")[0]+" has been picked by you. Please connect with the client accordingly.\n" +
							"Have a good day.");
		}

		if(lawyerUserRemark.getOrderStatus().equals(OrderStatus.COMPLETED))
		{
			emailService.sendEmail(orderEntity.getUserId().getEmail(),"Easylaw- Order Status Changed.",
					"Dear "+orderEntity.getUserId().getName().split(" ")[0]+", \n \n \t We hope you are doing well. Your Order for Order No. "+orderEntity.getOrderNo()+"" +
							" which was assigned to "+orderEntity.getLawyerId().getName().split(" ")[0]+" has been completed. \n" +
							"Please login to website and provide your valuable feedback and rating to the lawyer. \n" +
							"Hope you like our service. Have a good day.");

			emailService.sendEmail(orderEntity.getLawyerId().getEmail(),"Easylaw- Order Status Changed.",
					"Dear "+orderEntity.getLawyerId().getName().split(" ")[0]+", \n \n \t Order No. "+orderEntity.getOrderNo()+"" +
							" logged by "+orderEntity.getUserId().getName().split(" ")[0]+" has been completed. Please drop your feedback.\n" +
							"Have a good day.");
		}


		entity.setOrderStatus(lawyerUserRemark.getOrderStatus());
		entity.setUserremarks(lawyerUserRemark.getReview());
		userRepository.save(entity);
		return "Lawyer Review update";

	}

	@Override
	public GetRemarkResponse getUserRemark(Long id) {
		UserEntity entity = userRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new EnquiryNotFoundException(AppConstant.ErrorTypes.USER_NOT_EXIST_ERROR,
					AppConstant.ErrorCodes.USER_NOT_EXIST_CODE, AppConstant.ErrorMessage.USER_NOT_EXIST_MESSAGE);

		}
		
		GetRemarkResponse up = convertToEnquiryDetails(entity);
		return up;
	}

private GetRemarkResponse convertToEnquiryDetails(UserEntity entity) {

	return GetRemarkResponse.builder().Rating(entity.getUser_Rating()).Review(entity.getUserremarks()).build();
}


}
