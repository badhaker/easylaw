package com.vedalegal.modal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubService {

	private Long id;
	private String name;
	private Long categoryId;
	private String categoryName;
	private Long masterServiceId;
	private String mainImage;
	private List<QusAns> qusAns;
	private List<PlanDetails> planDetails;
	private List<SubServiceImageDetails> imageDescr;   
	private double taxRate;
	private boolean refundApplicable;
}
