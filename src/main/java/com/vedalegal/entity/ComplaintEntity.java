package com.vedalegal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vedalegal.enums.ComplaintStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "complaint")
public class ComplaintEntity extends BaseEntity{

	@Column(name ="complaint_no", nullable = false)
	private String complaintNo;
	
	@Column(name ="service_name", nullable = true)
	private String serviceName;
	
	@Column(name ="description", nullable = true, columnDefinition = "TEXT")
	private String complaintDescription;
	
	@Column(name ="status", nullable = true, columnDefinition = "ENUM('NEW', 'COMPLETED', 'INPROGRESS') default 'NEW'")
	@Enumerated(EnumType.STRING)
	private ComplaintStatusEnum status;
	
	@Column(name="remarks", columnDefinition = "TEXT")
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private OrderEntity orderId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserEntity userId;
}
