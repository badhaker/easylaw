package com.vedalegal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vedalegal.enums.PlanType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="sub_service_plan")
public class PlanDetailsEntity extends BaseEntity{

	@Column(name="benefits",columnDefinition = "TEXT", updatable = true )
	private String benefits;
	
	@Column(name="plan_type", length = 50, columnDefinition="ENUM ('gold','silver','bronze') default 'gold'" )
    @Enumerated(EnumType.STRING)	
	private PlanType planType;
	
	@Column(name="price", updatable = true )
	private double price;
	
	@ManyToOne
	@JoinColumn(name="sub_service_id")
	private SubServiceEntity subService;

	
}
