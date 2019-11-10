package com.hotent.mini.web.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.hotent.mini.web.service.OAuth2Service;

/**
 * 
 * @author heyifan
 */
public class OAuth2AuthenticationLoginEntry implements AuthenticationEntryPoint{
	@Resource
	private OAuth2Service oauth2Service;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		String requestURL = request.getRequestURL().toString();
        response.sendRedirect(oauth2Service.constructServiceUrl(requestURL));
	}
}
