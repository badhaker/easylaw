package com.vedalegal.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.vedalegal.enums.ApprovalStatus;
import com.vedalegal.enums.Gender;
import com.vedalegal.enums.LoginModeEnum;

import com.vedalegal.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity{

	
	private static final long serialVersionUID = 1L;
	
	@Column(name="name",nullable = false, length =   100)
	private String name;
	
	@Column(name="email",nullable = false, length = 100, unique = true)
	private String email;
	
	@Column(name="contact_no",nullable = true, length = 10)
	private Long contactNo;
	
	@Column(name = "rating")
	private Long rating;
	
	@Column(name = "rating_approved",columnDefinition = "BOOLEAN")
	private Boolean ratingApproved = true;
	
	@Column(name ="password",nullable = false, columnDefinition = "TEXT")
	private String password;
	
	@Column(name = "average_Rating")
	private Long averageRating;
	
	@Column(name = "email_verification")
	private String emailVerification;
	
	@Column(name="singup_mode",columnDefinition = "ENUM('GOOGLE','FACEBOOK','VEDALEGAL') default 'VEDALEGAL'")
	@Enumerated(EnumType.STRING)
	private LoginModeEnum singupMode;
	
	@Column(name="social_media_access_token")
	private String socialMediaAccessToken;
	
	@Column(name = "is_email_verified" ,columnDefinition = "BOOLEAN")
	private Boolean emailVerificationStatus = false;
	
	@Column(name = "user_or_lawyer_image_path")
	private String imgUrl;
	
	@Column(name = "user_id_path")
	private String idImage;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private RoleEntity role;
	
	@Column(name = "description" ,columnDefinition = "TEXT")
	private String description;
	// lawyer specific fields
	
	@Column(name = "lawyer_years_of_experience")
	private Long yearsOfExperience;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "pincode")
	private String pincode;
	
	
	@Column(name="bar_council_no",length = 100)
	private String barCouncilNo;
	
	@Column(name="bar_council_name")
	private String barCouncilName;
	
	@Column(name = "regcard_front_path")	
	private String regCardFront;
	
	@Column(name = "regcard_back_path")	
	private String regCardBack;
	
	@Column(name = "cancelled_check_path")	
	private String cancelCheque;
	
	@Column(name = "pancard_path")	
	private String panCard;
	
	@Column(name = "is_suspend")	
	private Boolean isSuspend;
	
	@Column(name ="expertise",nullable = true, columnDefinition = "TEXT")
	private String lawyerExpertise;
	

	@Column(name="approval_status",columnDefinition = "ENUM('Pending','Approved','InProcess') default 'InProcess'")
	@Enumerated(EnumType.STRING)
	private ApprovalStatus approvalStatus;
	
	@Column(name="remarks", columnDefinition="TEXT")
	private String remarks;
	
	@Column(name="User_remarks", columnDefinition="TEXT")
	private String Userremarks;
	
	@Column(name="User_rating", columnDefinition="TEXT")
	private Long User_Rating;
	
	@Column(name="gender",columnDefinition = "ENUM('MALE','FEMALE') default 'MALE'")
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@OneToMany( mappedBy = "lawyerId")
	private List<LawyerWorkExperienceEntity> experience;

	@OneToMany( mappedBy = "lawyerId")
	private List<LawyerEducationEntity> education;

	@OneToMany( mappedBy = "lawyerId")
	private List<LawyerExpertiseEntity> expertise;

	@OneToMany( mappedBy = "lawyerId")
	private List<LawyerCourtEntity> courts;

	@OneToMany( mappedBy = "lawyerId") 
	private List<LawyerLanguageEntity> languages;

	@Column(name="order_status")
	private OrderStatus orderStatus;

}
