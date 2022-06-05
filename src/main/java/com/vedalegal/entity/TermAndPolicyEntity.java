package com.vedalegal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "terms_and_policy")
public class TermAndPolicyEntity extends BaseEntity{

	@Column(name ="terms_policy", columnDefinition = "TEXT")
	private String termAndPolicy;
}
