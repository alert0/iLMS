package com.hotent.mini.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.mini.web.security.SecurityUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

public class CachedSessionFilter implements Filter{
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		IUser currentUser = ContextUtil.getCurrentUser();
		// 未健全登录信息
		if(BeanUtils.isEmpty(currentUser)){
			//验证访问的请求所对应的sessionId是否在memcached中有缓存的记录
			String account = SecurityUtil.getSessionIdFromCache(req);
			if(StringUtil.isNotEmpty(account)){
				//健全登录信息
				SecurityUtil.login(req, account, "", true);
			}
		}
		//已健全登录信息
		else{
			//刷新memcached中的过期时间
			SecurityUtil.refreshSessionIdInCache(req);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
