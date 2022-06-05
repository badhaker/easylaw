package com.vedalegal.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vedalegal.resource.SecurityConstants;



public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			filterChain.doFilter(request, response);
		}else {
			String jwt = getJwtFromRequest(request);
			String header = request.getHeader(SecurityConstants.SecretKey.TOKEN_HEADER);

			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				String id=tokenProvider.extractUserId(jwt);
				String email=tokenProvider.extractUserEmail(jwt);
				String authToken = header.replace(SecurityConstants.SecretKey.TOKEN_PREFIX, "");
				UserDetails userDetails = userDetailService.loadUserByUsername(email);
				request.setAttribute("userId", id);
				UsernamePasswordAuthenticationToken authentication = tokenProvider.getAuthentication(authToken,
						SecurityContextHolder.getContext().getAuthentication(), userDetails);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // this line is
				SecurityContextHolder.getContext().setAuthentication(authentication);

				logger.info("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());

			}

			filterChain.doFilter(request, response);
		}

	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(SecurityConstants.SecretKey.TOKEN_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
