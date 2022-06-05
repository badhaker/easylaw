package com.vedalegal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {


	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Column(name = "created", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date created;

	

	@Column(name = "updated")
	@UpdateTimestamp
	private Date updated ;
	
	@Column(name = "created_by", updatable = false)
	@CreatedBy
	private String createdBy;
	
	@Column(name = "updated_by", updatable = true)
	@LastModifiedBy
	private String updatedBy;
	
	@Column(name = "is_active",columnDefinition = "BOOLEAN")
	private Boolean active = true;

	public Long getId() {
		return id;
	}

	public Long setId(Long id) {
		return this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return "BaseEntity [id=" + id + ", created=" + created + ", updated=" + updated + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", active=" + active + "]";
	}
	
}
