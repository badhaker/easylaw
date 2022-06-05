package com.vedalegal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client_rating_and_review")
public class ClientFeedbackEntity extends BaseEntity{

	@Column(name ="review", columnDefinition = "TEXT") 
	private String review;
	
	@Column(name ="client_name")
	private String clientName;
	
	@Column(name ="rating")
	private double rating;
	
	@OneToOne
	@JoinColumn(name="order_id")
	private OrderEntity orderId;
	
	@Column(name ="lawyer_id")
	private Long lawyerId; 
}
