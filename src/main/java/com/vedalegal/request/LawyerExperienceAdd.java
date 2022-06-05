package com.vedalegal.request;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerExperienceAdd {

	private Long lawyerId;
	private String organization;
	private String designation;
	private LocalDate startTime;
	private LocalDate endTime; 
}
