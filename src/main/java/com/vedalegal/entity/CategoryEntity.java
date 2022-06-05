package com.vedalegal.entity;

import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "category")
public class CategoryEntity extends BaseEntity{

	@Column(name = "name", updatable = true)
    private String name;
   
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="master_service_id")
    private MasterServiceEntity serviceEntity;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    private List<SubServiceEntity> subServiceList;

}
