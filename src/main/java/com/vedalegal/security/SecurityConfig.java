package com.vedalegal.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class SecurityConfig implements WebMvcConfigurer {

	@Autowired
	private AppSecurityInterceptorHandler applicationSecurityInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(applicationSecurityInterceptor).excludePathPatterns("/v2/api-docs", "/configuration/ui",
				"/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");
	}

}
