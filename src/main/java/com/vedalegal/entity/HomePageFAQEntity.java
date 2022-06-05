package com.vedalegal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="homepage_qus_ans")
public class HomePageFAQEntity extends BaseEntity {

	@Column(name="question", nullable = true, columnDefinition = "TEXT", updatable = true )
	private String question;
	
	@Column(name="answer",nullable = true, columnDefinition = "TEXT", updatable = true )
	private String answer;
	
}
