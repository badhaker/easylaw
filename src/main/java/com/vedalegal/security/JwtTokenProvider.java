package com.vedalegal.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.vedalegal.resource.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	public String generateToken(Authentication authentication) {

		UserDetail userDetail=(UserDetail) authentication.getPrincipal();
		String authorities=userDetail.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));


		return Jwts.builder().setSubject(userDetail.getEmail())
				.setIssuedAt(new Date())
				.claim("AUTHORITIES_KEY", authorities)
				.claim("id", userDetail.getId())
				//.setExpiration(expireDate) we don,t have any expire time so we don,t need this
				//				.signWith(SignatureAlgorithm.HS512,SecurityConstants.SecretKey.SECRET )
				.signWith(SignatureAlgorithm.HS512,SecurityConstants.SecretKey.SECRET)
				.compact();
	}

	public  UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth, final UserDetails userDetails){

		final JwtParser jwtParser = Jwts.parser().setSigningKey(SecurityConstants.SecretKey.SECRET);

        final Claims claimsJws = jwtParser.parseClaimsJws(token).getBody();

        String user = claimsJws.getSubject();
        final Collection<SimpleGrantedAuthority> authorities =
                Arrays.stream(claimsJws.get("AUTHORITIES_KEY").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
  
        return new UsernamePasswordAuthenticationToken(user, "", authorities);
	}

	public boolean validateToken(String authToken) {

		String id=extractUserId(authToken);
		return true;

	}


	public String extractUserId(String token){
		return extractClaim(token,Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	private Claims extractAllClaims(String token){
		return Jwts.parser().setSigningKey(SecurityConstants.SecretKey.SECRET).parseClaimsJws(token).getBody();
	}
	
	public String extractUserEmail(String token) {
		final JwtParser jwtParser = Jwts.parser().setSigningKey(SecurityConstants.SecretKey.SECRET);
        final Claims claimsJws = jwtParser.parseClaimsJws(token).getBody();
        return claimsJws.getSubject();
	}
}
