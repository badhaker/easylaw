package com.vedalegal.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

/*import org.springframework.security.core.userdetails.UserDetailsService;*/
import com.vedalegal.request.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.vedalegal.enums.ApprovalStatus;
import com.vedalegal.modal.AddAssociate;
import com.vedalegal.modal.AssociateListResponse;
import com.vedalegal.modal.GetUserDetailsResponse;
import com.vedalegal.modal.LawyerDetailResponse;
import com.vedalegal.modal.LawyerList;
import com.vedalegal.modal.UpdateAssociate;
import com.vedalegal.modal.UpdateLawyerStatus;
import com.vedalegal.modal.UserListResponse;
import com.vedalegal.response.CommonSuccessResponse;
import com.vedalegal.response.GetRemarkResponse;
import com.vedalegal.response.LawyerListWebsite;
import com.vedalegal.response.SocialMediaSigninResponse;
import com.vedalegal.shared.dto.UserDto;

@Component
public interface UserService {
	
	//UserDto createdUser(UserDto user); 
	//LawyerDto createdLawyer(LawyerDto lawyer);
	
	UserDto getUser(String email);
	
	SocialMediaSigninResponse socialMediaSignin(SocialMediaSigninRequest masterService);
//	List<LawyerList> getLawyerListByStatus(ApprovalStatus status, String search);
	CommonSuccessResponse createCoustomer( CoustomerSingupRequest coustomerSingupRequest);
//	CommonSuccessResponse createLawyer(LawyerSingupRequest lawyerSingupRequest, MultipartFile cardFrontSide,
//			MultipartFile cardBackSide, MultipartFile cancelledCheque)throws IOException;
	CommonSuccessResponse userLogIn(@Valid UserLogInRequest userLogInRequest);
	
	LawyerDetailResponse getLawyerDetail(Long id);
	List<UserListResponse> getUserList(String search);
	GetUserDetailsResponse getUserDetails(String email);
//	List<LawyerListWebsite> getLawyersByLocationAndExpertise(String state, String city, Long expertise);
	//CommonSuccessResponse bookLawyer(BookLawyerRequest bookLawyerRequest, String email);
	
	CommonSuccessResponse addAssociate(AddAssociate addAssociate) throws AddressException, IOException, MessagingException;
	List<AssociateListResponse> getAssociate();
	String updateAssociate(Long id, UpdateAssociate updateAssociate);	
    String updateUserProfile(Long id, Profile profile,MultipartFile image)throws IOException;
//    String updateUserProfile(Long id, Profile profile) throws IOException;

//	String updateLawyerProfile(long id, LawyerProfile profile, MultipartFile lawyerImage) throws IOException, AddressException, MessagingException; 
//	CommonSuccessResponse updateLawyerStatus(UpdateLawyerStatus updateLawyerStatus) throws AddressException, IOException, MessagingException;
	CommonSuccessResponse updateLawyerStatus(UpdateLawyerStatus updateLawyerStatus)
			throws AddressException, IOException, MessagingException;
	CommonSuccessResponse sendResetPasswordMail(String email);

	CommonSuccessResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
	
	String updateLawyer(long id, LawyerAdminProfile lawyerAdminProfile) throws IOException, AddressException, MessagingException; 

	String deleteUserById(Long id);

	CommonSuccessResponse setLawyerAsSuspend(Long userId);

	CommonSuccessResponse createLawyer(LawyerSingupRequest lawyerSingupRequest);

	String updateLawyerProfile(long id, LawyerProfile profile, MultipartFile lawyerImage, MultipartFile cardFrontSide,
			MultipartFile cardBackSide, MultipartFile cancelledCheque, MultipartFile panCard)
			throws IOException, AddressException, MessagingException;
	String updateUserProfileAdmin(Long id, AdminUserProfile profile)throws IOException;
	
	List<LawyerListWebsite> getLawyersByLocationAndExpertise(String state, String city, String expertise, String name,
			Long lawyer_years_of_experience, String gender);

	String deleteAssociate(Long id);
	List<LawyerList> getLawyerListByStatus(ApprovalStatus status, String search);

	String deleteLawyerById(Long id);

	CommonSuccessResponse checkLawyerAndUser(String email, Long mobileNumber);
	
	String userRemark(String orderNo, Userremark userremark);
	
	String LawyerRemark(String orderNo, LawyerUserRemark lawyerUserRemark) throws MessagingException, IOException;
	GetRemarkResponse getUserRemark(Long id);
}   
    /*@Autowired
	private UserRepository userRepo;
    
	public User addUser(User user) {
		User user=userRepo.save(chanageToEntity);
		return null;
	}
	@Autowired
	private RoleService roleService;
    
	public User addUser(User modal) {
		return convertToModal(userRepo.save(convertToEntity(modal)));
	}
    
	public UserEntity convertToEntity(User modal) {
		return UserEntity.builder().name(modal.getName()).email(modal.getEmail()).contactNo(modal.getContactNo())
				.password(modal.getPassword()).role(roleService.findByIds(modal.getRoleModal().getId()))
				.idImage(modal.getIdImage()).build();
     
	}
     
	public User convertToModal(UserEntity entity) {
		return User.builder().name(entity.getName()).email(entity.getEmail()).contactNo(entity.getContactNo())
				.password(entity.getPassword()).roleModal(roleService.findByRoleId(entity.getRole().getId()))
				.idImage(entity.getIdImage()).build();
    
	}
    
}   
 */