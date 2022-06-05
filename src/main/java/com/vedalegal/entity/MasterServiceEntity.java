package com.vedalegal.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "master_service")
public class MasterServiceEntity extends BaseEntity{

	@Column(name="name",nullable = false,updatable = true )
    private String name;
    
	@Column(name="description",nullable = true, columnDefinition = "TEXT", updatable = true )
    private String description;
	
	@Column(name="image_path",nullable = true )
    private String image;
	
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "serviceEntity")
    private List<CategoryEntity> categories;
    
    
    
}
