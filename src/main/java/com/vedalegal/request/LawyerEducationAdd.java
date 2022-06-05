package com.vedalegal.request;

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
public class LawyerEducationAdd {
	
	private Long lawyer_id;
	private String institution;
	private String course;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private String startDate;
	
//	@JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "yyyy")
	private String endDate;

}
