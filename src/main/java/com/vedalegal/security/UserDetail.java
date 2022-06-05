package com.vedalegal.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail implements  UserDetails{

	private static final long serialVersionUID = 1L;

	private Long id;
//	private String userId;
	private String email;
	private String password;
	private Boolean active;

	private Collection<? extends GrantedAuthority> authorities;

	@SuppressWarnings("unchecked")
	public static UserDetail create(UserDetailEntity entity ) {
		@SuppressWarnings("rawtypes")
		Set authorities=new HashSet<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_"+SecurityConstants.UserRole.USER_ROLE));
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//		return null;
		return new UserDetail(entity.getId(),entity.getEmail(), entity.getPassword(), entity.getActive(),authorities);
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}
	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		 return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		 return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	@Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetail that = (UserDetail) o;
        return Objects.equals(email, that.email);
	}


	@Override
	public int hashCode() {
	   return Objects.hash(email);
	}

}
