package com.vedalegal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="lawyer_court")
public class LawyerCourtEntity extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="lawyer_id")
	private UserEntity lawyerId;
	
	@Column(name="court",nullable = false )
	private String courtName;
	
	@Column(name="court_location",nullable = false )
	private String courtLocation;
	
}
