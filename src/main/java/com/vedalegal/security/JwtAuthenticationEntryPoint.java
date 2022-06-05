package com.vedalegal.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedalegal.exception.AppException;
import com.vedalegal.resource.AppConstant;
import com.vedalegal.response.BaseApiResponse;
import com.vedalegal.response.ResponseStatus;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		AppException appException = new AppException(AppConstant.ErrorTypes.INVALID_TOKEN_ERROR_TYPE,
				AppConstant.ErrorCodes.INVALID_TOKEN_ERROR_CODE,
				AppConstant.ErrorMessage.INVALID_TOKEN_ERROR_MESSAGE);
		//baseApiResponse.setResponseData(appException);
		ResponseStatus stattus=new ResponseStatus();
		stattus.setStatusCode(AppConstant.StatusCodes.FAILURE);
		baseApiResponse.setResponseStatus(stattus);
		baseApiResponse.setMessage(appException.getMessage());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		//		response.setStatus(200);
		ObjectMapper Obj = new ObjectMapper();
		String json = Obj.writeValueAsString(baseApiResponse);
		// String json=responseEntity.convertToJson();
		response.getWriter().write(json);
	}

}
