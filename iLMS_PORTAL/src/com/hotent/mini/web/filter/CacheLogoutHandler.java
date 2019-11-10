package com.hotent.mini.web.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.hotent.mini.web.security.SecurityUtil;

/**
 * 
 * @author heyifan
 */
public class CacheLogoutHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler{
	@Override
	public void onLogoutSuccess(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Authentication authentication) 
					throws IOException, ServletException {
		//清除共享的session数据
		SecurityUtil.removeSessionIdFromCache(request);
		super.onLogoutSuccess(request, response, authentication);
	}
}