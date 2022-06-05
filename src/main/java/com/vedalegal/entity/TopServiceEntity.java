package com.vedalegal.entity;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "services_at_top")
public class TopServiceEntity extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name="master_service_id")
	private MasterServiceEntity masterServiceId;
	

}
