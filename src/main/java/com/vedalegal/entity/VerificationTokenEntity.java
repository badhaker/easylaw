package com.vedalegal.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vedalegal.enums.TokenType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Builder
@Table(name = "verification")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerificationTokenEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
	@Column(name = "token_type")
    private TokenType tokenType;
	
    
	@Column(name = "confirmation_token", nullable = false, updatable = true)
    private String confirmationToken;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
	private UserEntity userEntity;
	    
    
}
