package com.vedalegal.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lawyer_experience")
public class LawyerWorkExperienceEntity extends BaseEntity{

	@Column(name="organization",nullable = false )
	private String organization;
	
	@Column(name="designation",nullable = true )
	private String designation;
	
	@JsonFormat(pattern = "yyyy")
	@Column(name="start_time",nullable = true )
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDate startTime;
	
	@JsonFormat(pattern = "yyyy")
	@Column(name="end_time",nullable = true )
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDate endTime; 
	
	@ManyToOne
	@JoinColumn(name="lawyer_id")
	private UserEntity lawyerId;
	
	
}
