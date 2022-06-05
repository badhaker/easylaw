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
@Table(name="lawyer_faq")
public class LawyerFAQEntity extends BaseEntity{
	@Column(name="question", nullable = true, columnDefinition = "TEXT", updatable = true )
	private String question;
	
	@Column(name="answer",nullable = true, columnDefinition = "TEXT", updatable = true )
	private String answer;
	
	@ManyToOne
	@JoinColumn(name="expertise_id") 
	private AllExpertiseEntity expertise;

}
