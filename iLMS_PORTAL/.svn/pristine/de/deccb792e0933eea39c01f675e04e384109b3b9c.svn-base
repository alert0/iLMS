package com.hotent.mini.web.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.mini.web.json.AccessToken;
import com.hotent.mini.web.json.CheckTokenResult;
import com.hotent.mini.web.security.SecurityUtil;
import com.hotent.mini.web.service.OAuth2Service;

public class OAuth2Filter implements Filter{
	@Resource
	private OAuth2Service oauth2Service;
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String code = req.getParameter("code");
		if(StringUtil.isNotEmpty(code)){
			AccessToken token;
			try {
				token = oauth2Service.getTokenByCode(code);
				CheckTokenResult checkToken = oauth2Service.checkToken(token);
				String account = checkToken.getUser_name();
				// 踢出之前的会话信息
				SecurityUtil.kickoutUser(account);
				SecurityUtil.login(req, account, "", true);
			}
			catch(BadCredentialsException ex){
				res.sendError(503, "账户信息未同步");
				return;
			}
			catch (Exception e) {
				res.sendError(501, "系统异常");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
