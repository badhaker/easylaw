package com.vedalegal.repository;

import java.util.List;
import java.util.Optional;

import com.vedalegal.modal.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vedalegal.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<RoleEntity,Long>{

	Optional<RoleEntity> findByName(String string);

	@Query("Select r.permissions from RoleEntity r where r.name=?1")
	String getRolePermissions(String role);

	@Query("Select r from RoleEntity r where r.name=?1 and r.active=true")
	List getRole(String role);

	/*String saveRole(Role role);*/

}
  