package com.vedalegal.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "lawyer_education")
public class LawyerEducationEntity extends BaseEntity {

	@Column(name = "institution")
	private String institution;

	@Column(name = "course")
	private String course;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	@Column(name = "start_date")
	private String startDate;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	@Column(name = "end_date")
	private String endDate;

	@ManyToOne
	@JoinColumn(name = "lawyer_id")
	private UserEntity lawyerId;

}
