package com.vedalegal.security;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailService userDetailsService;

	public WebSecurityConfig(UserDetailService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("security.........");
		http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/users/socialMediaSignin").permitAll()
				.antMatchers(HttpMethod.POST, "/users/customerSignUp").permitAll()
				.antMatchers(HttpMethod.POST, "/users/LawyerSignUp").permitAll()
				.antMatchers(HttpMethod.POST, "/users/Login").permitAll()
				.antMatchers(HttpMethod.GET, "/subService/get/**").permitAll()
				.antMatchers(HttpMethod.GET, "/masterService/getDetails").permitAll()
				.antMatchers(HttpMethod.GET, "/OTP/getSmsOTP/**").permitAll()
				.antMatchers(HttpMethod.GET, "/OTP/validateOTPSms").permitAll()
				.antMatchers(HttpMethod.POST, "/enquiry/sendEnquiry").permitAll()
				.antMatchers(HttpMethod.POST, "/enquiry/sendLoginEnquiry").permitAll()
				.antMatchers(HttpMethod.DELETE, "/enquiry/deleteEnquiry/**").permitAll()
				.antMatchers(HttpMethod.POST, "/schedule/**").permitAll()
				.antMatchers(HttpMethod.GET, "/users/lawyerListWebsite/**").permitAll()
				.antMatchers(HttpMethod.GET, "/users/lawyerDetail/**").permitAll()
				.antMatchers(HttpMethod.GET, "/policy/get").permitAll()
				.antMatchers(HttpMethod.GET, "/area_of_expertise/getExpertiseService").permitAll()
				.antMatchers(HttpMethod.GET, "/review/getList").permitAll()
				.antMatchers(HttpMethod.GET, "/review/getclientspeaklist").permitAll()
				.antMatchers(HttpMethod.POST, "/review/add").permitAll()
				.antMatchers(HttpMethod.PUT, "/review/update").permitAll()
				.antMatchers(HttpMethod.PUT, "/review/reviewsuspend").permitAll()
				.antMatchers(HttpMethod.DELETE, "/review/delete/**").permitAll()
				.antMatchers(HttpMethod.GET, "/topServices/getList").permitAll()
				.antMatchers(HttpMethod.GET, "/news/getHeadlines").permitAll()
				.antMatchers(HttpMethod.POST, "/news/addHeadlines").permitAll()
				.antMatchers(HttpMethod.PUT, "/news/updateHeadlines").permitAll()
				.antMatchers(HttpMethod.DELETE, "/news/deleteHeadlines").permitAll()
				.antMatchers(HttpMethod.GET, "/termAndPolicy/get").permitAll()
				.antMatchers(HttpMethod.GET, "/homepage/FAQs/get").permitAll()
				.antMatchers(HttpMethod.POST, "/homepage/FAQs/add").permitAll()
				.antMatchers(HttpMethod.PUT, "/homepage/FAQs/edit").permitAll()
				.antMatchers(HttpMethod.DELETE, "/homepage/FAQs/deletereview").permitAll()
				.antMatchers(HttpMethod.GET, "/adminDashboard").permitAll()
				.antMatchers(HttpMethod.GET, "/subService/search").permitAll()
				.antMatchers(HttpMethod.POST, "/users/resetPassword").permitAll()
				.antMatchers(HttpMethod.GET, "/users/sendResetPasswordMail").permitAll()
				.antMatchers(HttpMethod.PUT, "/users/updateUser/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/users/updateLawyer").permitAll()
				.antMatchers(HttpMethod.POST, "/voicecall").permitAll()
				.antMatchers(HttpMethod.GET, "/verify/room/**").permitAll()
				.antMatchers(HttpMethod.GET, "/downloadRecordingUri").permitAll()
				.antMatchers(HttpMethod.POST, "/callbackUriTwilio").permitAll()
				.antMatchers(HttpMethod.GET, "/roomDetails").permitAll()
				.antMatchers(HttpMethod.PUT, "/users/updateLawyerProfile/**").permitAll()
				.antMatchers(HttpMethod.GET, "/users/lawyerList/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/users/suspendLawyer/**").permitAll()
				.antMatchers(HttpMethod.GET, "/faq/getLawyerFAQList").permitAll()
				.antMatchers(HttpMethod.POST, "/faq/add").permitAll()
				.antMatchers(HttpMethod.DELETE, "/faq/deleteLawyerFAQ").permitAll()
				.antMatchers(HttpMethod.PUT, "/faq/editLawyerFAQList").permitAll()
				.antMatchers(HttpMethod.PUT, "/faq/editUserFAQList").permitAll()
				.antMatchers(HttpMethod.POST, "/banners/addBanners").permitAll()
				.antMatchers(HttpMethod.PUT, "/banners/updateBanners").permitAll()
				.antMatchers(HttpMethod.DELETE, "/banners/deleteBanners").permitAll()
				.antMatchers(HttpMethod.GET, "/banners/getList").permitAll()
				.antMatchers(HttpMethod.GET, "/banners/getListWeb").permitAll()
				.antMatchers(HttpMethod.POST, "/subService/uploadSubServiceImage").permitAll()
				.antMatchers(HttpMethod.DELETE, "/subService/deleteSubServiceImage/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/subService/updateSubServiceImage/**").permitAll()
				.antMatchers(HttpMethod.GET, "/enquiry/getEnqueryList").permitAll()
				.antMatchers(HttpMethod.POST, "/order/addEasyLawOrder").permitAll()
				.antMatchers(HttpMethod.GET, "/order/getEasyLawOrderList").permitAll()
				.antMatchers(HttpMethod.DELETE, "/order/deleteLawyerOrder/**").permitAll()
				.antMatchers(HttpMethod.GET, "/order/getLawyerOrderList").permitAll()
				.antMatchers(HttpMethod.GET, "/users/update/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/order/assignAssociate/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/order/updateLawyerOrder/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/users/updateLawyerStatus").permitAll()
				.antMatchers(HttpMethod.PUT, "/order/ratingAverage").permitAll()
				.antMatchers(HttpMethod.PUT, "/topServices/update").permitAll()
				.antMatchers(HttpMethod.PUT, "/complaint/updateComplaint").permitAll()
				.antMatchers(HttpMethod.DELETE, "/complaint/deleteComplaint/**").permitAll()
				.antMatchers(HttpMethod.GET, "/complaint/getList").permitAll()
				.antMatchers(HttpMethod.GET, "/enquiry/getEnquiryDetails/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/termAndPolicy/update/**").permitAll()
				.antMatchers(HttpMethod.GET, "/enquiry/getList").permitAll()
				.antMatchers(HttpMethod.POST, "/topServices/add").permitAll()
				.antMatchers(HttpMethod.DELETE, "/users/deleteAssociate/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/users/updateAssociate/**").permitAll()
				.antMatchers(HttpMethod.POST, "/users/addAssociate").permitAll()
				.antMatchers(HttpMethod.PUT, "/users/update/**").permitAll()
				.antMatchers(HttpMethod.DELETE, "/users/deleteUser/**").permitAll()
				.antMatchers(HttpMethod.GET, "/roles/permissions/**").permitAll()
				.antMatchers(HttpMethod.DELETE, "/roles/delete/**").permitAll()
				.antMatchers(HttpMethod.GET, "/users/getAssociate").permitAll()
				.antMatchers(HttpMethod.GET, "/roles/permissions/**").permitAll()
				.antMatchers(HttpMethod.GET, "/roles").permitAll()
				.antMatchers(HttpMethod.POST, "/roles/addRole").permitAll()
				.antMatchers(HttpMethod.POST, "/roles/addRole**").permitAll()
				.antMatchers(HttpMethod.POST, "/subService/add").permitAll()
				.antMatchers(HttpMethod.PUT, "/roles/updatePermission/**").permitAll()
				.antMatchers(HttpMethod.POST, "/roles/addRole").permitAll()
				.antMatchers(HttpMethod.DELETE, "/users/deleteLawyer/**").permitAll()
				.antMatchers(HttpMethod.GET, "/subService").permitAll()
				.antMatchers(HttpMethod.GET, "/users/checking").permitAll()
				.antMatchers(HttpMethod.POST, "/complaint/add").permitAll()
				.antMatchers(HttpMethod.GET, "/subService/searchSubservice").permitAll()
				.antMatchers(HttpMethod.GET, "/users/getAssosiateList").permitAll()
				.antMatchers(HttpMethod.POST, "/enquiry/sendContactEnquiry").permitAll()
				.antMatchers(HttpMethod.POST, "/users/userRemark").permitAll()
				.antMatchers(HttpMethod.POST, "/users/lawyerOrderRemark").permitAll()
				.antMatchers(HttpMethod.GET, "/users/getUserRemark/**").permitAll()
				.antMatchers(HttpMethod.GET, "/order/user/myOrders/**").permitAll()
				
				
				
				.anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
				"/swagger-ui.html", "/webjars/**");
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
//         config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setMaxAge(Duration.ofMinutes(60L));
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
