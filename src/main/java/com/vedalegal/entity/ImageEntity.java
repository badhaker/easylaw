package com.vedalegal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vedalegal.constants.ImageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_service_image")
public class ImageEntity extends BaseEntity{

//	@Column(name="image_type", length = 50, columnDefinition="ENUM('Main', 'Description', 'Component', 'Tax', 'Gst', 'Gst_Return', 'Document', 'Return_Application', 'Why_Easy_Law', 'Recent_Updates') default 'Main'" )
//    @Enumerated(EnumType.STRING)
	@Column(name="image_types", columnDefinition = "TEXT", updatable = true )
    private String type;
	
	@Column(name="image_path" )
    private String imageURI;
	
	@Column(name="description", columnDefinition = "TEXT", updatable = true )
    private String imgDescription;
	  
    @ManyToOne
	@JoinColumn(name="sub_service_id")
    private SubServiceEntity subService;
}
