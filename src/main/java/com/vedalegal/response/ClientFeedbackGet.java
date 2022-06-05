package com.vedalegal.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientFeedbackGet {
	
	private Long feedbackId;
	private String review;
	private double starRating;
	private String clientName;
}
