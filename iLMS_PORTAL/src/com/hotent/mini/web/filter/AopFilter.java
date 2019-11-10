package com.hotent.mini.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.web.RequestContext;
import com.hotent.org.api.model.IUser;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.util.ContextUtil;

 

/**
 * <pre>
 * 在web.xml配置了请求的拦截器
 * 
 * 作用:
 * 1.将request和response 放到上下文请求中。
 * 2.将当前用户信息放到 request 请求中。
 * 3.清空系统线程变量。
 * </pre>
 * 
 * @author csx
 * 
 */
public class AopFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try{
			RequestContext.setHttpServletRequest((HttpServletRequest) request);
			RequestContext.setHttpServletResponse((HttpServletResponse) response);
			IUser user=ContextUtil.getCurrentUser();
			if(BeanUtils.isNotEmpty(user)){
				request.setAttribute("currentUser", user);
			}
			chain.doFilter(request, response);
		}
		finally{
			ContextUtil.clearAll();
		}
	
	}

	@Override
	public void destroy() {
	}

}
