package com.vedalegal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "all_expertise")
public class AllExpertiseEntity extends BaseEntity{
	
	@Column(name = "expertise_name", nullable = false, updatable = true, unique = true)
	private String areaOfExpertiseName;


}
