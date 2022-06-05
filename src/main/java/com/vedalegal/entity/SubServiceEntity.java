package com.vedalegal.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "sub_service_details")
public class SubServiceEntity extends BaseEntity{

	@Column(name="name" )
	private String name;
	
	@Column(name="image_path" )
	private String mainImage;

	@ManyToOne
	@JoinColumn(name="category_id")
	private CategoryEntity category;
	
	@OneToMany(mappedBy = "subService")
	private List<ImageEntity> images;
	
	@Column(name = "is_refund_applicable",columnDefinition = "BOOLEAN")
	private boolean refundApplicable;

	@OneToMany(mappedBy = "subService")
	private List<PlanDetailsEntity> planDetails;
 
	@OneToMany(mappedBy = "subService")
	private List<FAQEntity> questionAnswers;

}
