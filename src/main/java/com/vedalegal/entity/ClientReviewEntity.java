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
@Table(name = "client_review")
public class ClientReviewEntity extends BaseEntity{

	@Column(name ="img_url") 
	private String imageUrl;	
	
	@Column(name ="review", columnDefinition = "TEXT") 
	private String review;
	
	@Column(name ="client_name")
	private String clientName;

}
