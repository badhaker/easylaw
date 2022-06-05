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
@Table(name="lawyer_expertise")

public class LawyerExpertiseEntity extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="lawyer_id")
	private UserEntity lawyerId;
	
	@ManyToOne
	@JoinColumn(name="expertise_id")
	private AllExpertiseEntity expertiseNo;
	
	@Column(name="call_price" )
	private double callPrice;
	
	@Column(name="meeting_price" )
	private double meetingPrice;
	
}
