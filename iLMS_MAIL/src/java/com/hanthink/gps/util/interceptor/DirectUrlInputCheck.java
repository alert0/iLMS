package com.hanthink.gps.util.interceptor;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <dd>概要：防止直接输入URL的检查filter */
public class DirectUrlInputCheck implements Filter  {


	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest requestHttp = (HttpServletRequest) request;  
		HttpServletResponse responseHttp = (HttpServletResponse) response;
        String requestURI = requestHttp.getRequestURI().toLowerCase();
        
        // 判断是否是登陆   
        boolean isLogin = requestURI.indexOf("login") >= 0 || requestHttp.getHeader("referer") != null; 
        if (!isLogin) {   
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.action");   
//            requestDispatcher.forward(request, response);   
        	responseHttp.sendRedirect(requestHttp.getContextPath()+"/login.jsp");
        }else{   
            chain.doFilter(request, response);   
        } 
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}

