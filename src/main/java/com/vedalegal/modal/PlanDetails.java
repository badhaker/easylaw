package com.vedalegal.modal;

import com.vedalegal.enums.PlanType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanDetails {

	private Long planId;
	private PlanType planType;
	private String benefits;
	private double price;
}
 