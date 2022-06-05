package com.vedalegal.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LawyerFAQGetList {

	private Long id;
	private String question;
	private String answer;
	//private String expertise;
}
