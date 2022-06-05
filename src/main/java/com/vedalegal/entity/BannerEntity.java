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
@Table(name = "banner")
public class BannerEntity extends BaseEntity{
	
	@Column(name ="banner_url")
	private String bannerUrl;	
	
	@Column(name = "service_id")
	private Long serviceId;	
	
	@Column(name="hyperlink")
	private String hyperLink;

}
