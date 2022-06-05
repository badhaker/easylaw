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
@Table(name="qus_ans")
public class FAQEntity extends BaseEntity{
	
	@Column(name="question", nullable = true, columnDefinition = "TEXT", updatable = true )
	private String question;
	
	@Column(name="answer",nullable = true, columnDefinition = "TEXT", updatable = true )
	private String answer;
	
	@ManyToOne
	@JoinColumn(name="sub_service_id")
	private SubServiceEntity subService;
	

}
