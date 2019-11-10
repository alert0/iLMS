package com.hotent.mini.web.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * @author heyifan
 */
public class SwaggerAuthFilter implements Filter{

	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		
		String authorization = req.getHeader("Authorization");
		if(StringUtil.isNotEmpty(authorization)){
			IUser user = ContextUtil.getCurrentUser();
			if (user==null && authorization.matches("^Basic\\s{1}[\\w|=]*?$")){
				String[] splits = authorization.split(" ");
				String accountPwd64 = splits[1];
				String accountPwd = Base64.getFromBase64(accountPwd64);
				String[] arys = accountPwd.split(":");
				if(BeanUtils.isNotEmpty(arys) && arys.length==2){
					// 解析传过来的token，获得用户账号
					SecurityUtil.login(req, arys[0], arys[1], false);
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
