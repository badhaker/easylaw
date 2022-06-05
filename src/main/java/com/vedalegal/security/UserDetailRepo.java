package com.vedalegal.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepo extends JpaRepository<UserDetailEntity, Long>{
Optional<UserDetailEntity> findByEmail(String email);

}
