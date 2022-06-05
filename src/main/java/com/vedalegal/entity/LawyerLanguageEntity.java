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
@Table(name = "lawyer_language")
public class LawyerLanguageEntity extends BaseEntity{


	@Column(name="language",nullable = false , length= 100)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="lawyer_id")
	private UserEntity lawyerId;
	
	
	
}
