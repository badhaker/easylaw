package com.vedalegal.modal;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerEducation {

	private Long id;
	private String institution;
	private String course;
	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private String startDate;
	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private String endDate;
}
