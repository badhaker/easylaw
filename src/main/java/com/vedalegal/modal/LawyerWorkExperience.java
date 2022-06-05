package com.vedalegal.modal;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerWorkExperience {

	private Long id;
	private String organization;
	private String designation;
	private LocalDate startTime;
	private LocalDate endTime; 
}
