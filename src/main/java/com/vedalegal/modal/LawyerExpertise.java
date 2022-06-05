package com.vedalegal.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerExpertise {
	
//	private Long id;
//	private String expertise;
	private double callPrice;
	private double meetingPrice;
}
