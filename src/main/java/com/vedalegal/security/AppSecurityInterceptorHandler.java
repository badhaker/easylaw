
package com.vedalegal.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//import com.innovationm.importal.tasktracker.constants.AppConstants;
//import com.innovationm.importal.tasktracker.constants.SecurityConstants;
//import com.innovationm.importal.tasktracker.exception.AppException;


//@Component
//@Order(1)
public class AppSecurityInterceptorHandler extends HandlerInterceptorAdapter {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info(" the requested method is    "+	request.getMethod()+"   the provided url is   "+request.getRequestURI());

		//if("OPTIONS".equalsIgnoreCase(request.getMethod()))
		//{ 
			
			 /* response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			  
			  response.setHeader("Access-Control-Allow-Headers",
			  "authorization,Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Access-Control-Allow-Origin,"
			  +
			  "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Content-disposition, Authorization,"
			  + "AppAuthenticationToken, timestamp, employeeId");
			  response.setHeader("Access-Control-Expose-Headers",
			  "Content-disposition, Authorization");
			  response.addHeader("Access-Control-Max-Age", "3600"); */
		
			response.addHeader("Access-Control-Allow-Methods", "*");
			  
			  response.setHeader("Access-Control-Allow-Headers","*");
			  
			  response.setHeader("Access-Control-Expose-Headers",
			  "Content-disposition, Authorization");
			  response.addHeader("Access-Control-Max-Age", "3600"); 
	//	} 
		return true; 
	}
}
		