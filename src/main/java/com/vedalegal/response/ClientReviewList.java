package com.vedalegal.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientReviewList {

	private Long reviewId;
	private String review;
	private String imageUrl;
	private String clientName;
    private Boolean isActive;
}
