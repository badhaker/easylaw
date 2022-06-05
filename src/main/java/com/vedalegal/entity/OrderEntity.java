package com.vedalegal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vedalegal.enums.LawyerBookingType;
import com.vedalegal.enums.OrderStatus;
import com.vedalegal.enums.PlanType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_user")
public class OrderEntity extends BaseEntity{
    
	@Column(name="order_no",nullable = false ,updatable=false)
	private String orderNo;
	
    @ManyToOne
	@JoinColumn(name="user_id")
	private UserEntity userId;
    
	@Column(name="status",nullable = false, length = 50, columnDefinition="ENUM('NEW', 'INPROCESS', 'COMPLETED') default 'NEW'")
    @Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column(name="remarks", nullable = true, columnDefinition = "TEXT", updatable = true )
	private String remarks;
	
	@Column(name="transaction_id", nullable = true, updatable = true )
	private String transactionId;
	
	@Column(name="user_remarks", nullable = true, columnDefinition = "TEXT", updatable = true )
	private String userRemarks;
	
	@Column(name="lawyer_remarks", nullable = true, columnDefinition = "TEXT", updatable = true )
	private String lawyerRemarks;

	 
//for direct order
	@ManyToOne
	@JoinColumn(name="sub_service_id")
	private SubServiceEntity subServiceId;
	
	@Column(name="plan_type", length = 50, columnDefinition="ENUM ('gold','silver','bronze') default 'gold'" )
    @Enumerated(EnumType.STRING)
	private PlanType planType;
	
	@ManyToOne
	@JoinColumn(name="assigned_to_id")
	private UserEntity assignedTo;
	
//for third party lawyer order
	
	@Column(name="expertise", nullable = true, updatable = true )
	private String expertise;
	
	@ManyToOne
	@JoinColumn(name="lawyer_id")
	private UserEntity lawyerId; 
	
	@Column(name="booking_type",columnDefinition = "ENUM('InCall','InPerson') default 'InCall'" ,length=50 )
	@Enumerated(EnumType.STRING)
	private LawyerBookingType bookingType;
	
	@Column(name = "rating")
	private Long rating;
	
	@Column(name = "rating_approved",columnDefinition = "BOOLEAN")
	private Boolean ratingApproved = true;
	

}
