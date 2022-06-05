package com.vedalegal.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TermsAndPolicyGet {

	private Long id;
	private String termsAndPolicy;
	private String lastUpdated;
}
