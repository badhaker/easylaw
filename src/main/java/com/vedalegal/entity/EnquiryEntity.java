package com.vedalegal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vedalegal.enums.EnquiryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enquiry")
public class EnquiryEntity extends BaseEntity {

	@Column(name="enquiry_no")
	private String enquiryNo;

	@Column(name="user_name",nullable = false, length = 100 , updatable = true)
	private String userName;
	
	@Column(name="user_email", length = 100, updatable = true)
	private String email;
	
	@Column(name="user_contact_no", length = 10 )
	private Long contactNo;
	
	@Column(name="query", nullable = false, columnDefinition = "TEXT" )
	private String query;

	
	@Column(name="status", length = 50, columnDefinition="ENUM('NEW', 'INPROGRESS', 'COMPLETED') default 'NEW'" )
	@Enumerated(EnumType.STRING)
	private EnquiryStatus status;
	
	@Column(name="remarks",nullable = true, columnDefinition = "TEXT", updatable = true )
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name="assigned_to_id")
	private UserEntity assignedTo;
	
	@ManyToOne
    @JoinColumn(name="sub_service_id")
	private SubServiceEntity subServiceNo;


}
