package com.vedalegal.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vedalegal.entity.RoleEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserDetailEntity {
	
	@Column(name = "id", nullable = false, updatable = false,insertable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name="email",nullable = false, length = 100)
	private String email;
	
	@Column(name ="password",nullable = false, columnDefinition = "TEXT")
	private String password;
	
	@Column(name = "is_active",columnDefinition = "BOOLEAN")
	private Boolean active;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private RoleEntity role;
}
