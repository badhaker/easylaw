package com.vedalegal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedalegal.entity.UserEntity;
import com.vedalegal.entity.VerificationTokenEntity;
import com.vedalegal.enums.TokenType;

@Repository
public interface VerificationTokenRepository  extends JpaRepository<VerificationTokenEntity, Long>{

	VerificationTokenEntity findByUserEntityAndTokenType(UserEntity entity, TokenType resetPassword);

	VerificationTokenEntity findByConfirmationTokenAndTokenType(String verificationTocken, TokenType resetPassword);

}
