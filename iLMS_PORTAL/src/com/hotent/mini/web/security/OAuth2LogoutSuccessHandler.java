package com.hotent.mini.web.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.hotent.mini.web.service.OAuth2Service;

/**
 * 
 * @author heyifan
 */
public class OAuth2LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler{
	private String logoutSuccessUrl = "/";
	
	public void setLogoutSuccessUrl(String logoutSuccessUrl) {
		this.logoutSuccessUrl = logoutSuccessUrl;
	}

	@Resource
	OAuth2Service oauth2Service;
	
	@Override  
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)   
			throws IOException, ServletException {  
		try {
			oauth2Service.logout();
			response.sendRedirect(this.logoutSuccessUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
}
