package com.vedalegal.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientFeedback {

	private Long feedbackId;
	private Long clientId;
	private double starRating;
	private String review;
	private Long orderID;
	
}
