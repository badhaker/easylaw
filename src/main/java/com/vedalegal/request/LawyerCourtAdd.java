package com.vedalegal.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerCourtAdd {

	private Long lawyerId;
	private String courtName;
	private String courtLocation;
}
