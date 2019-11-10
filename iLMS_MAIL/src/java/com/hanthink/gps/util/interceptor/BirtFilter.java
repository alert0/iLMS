package com.hanthink.gps.util.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * <dd>概要：将原本的action打到birt的sevlert上面
 */
public class BirtFilter implements Filter {

	/* 容器,封装birt相关功能的uri和所对应的servlet名的键值对 */
	Map<String, String> map = new HashMap<String, String>();

	ServletContext context;

	public void destroy() {
		map = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest requestHttp = (HttpServletRequest) request;
		String requestURI = requestHttp.getRequestURI().toLowerCase();
		Set<String> keys = map.keySet();
		for(String key:keys){
			if(requestURI.contains(key)) {
				RequestDispatcher rd = this.context.getNamedDispatcher(map.get(key));
				rd.forward(requestHttp, response);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fc) throws ServletException {
		this.context = fc.getServletContext();
		map.put("preview", "EngineServlet");
	}

}
