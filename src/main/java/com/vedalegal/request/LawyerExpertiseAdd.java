package com.vedalegal.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerExpertiseAdd {

	private Long lawyerId;
	private Long expertiseId;
	private double callPrice;
	private double meetingPrice;
}
