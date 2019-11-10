package com.hotent.mini.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 编码过滤器， 设置当前系统的编码。
 * 
 * @author hotent
 * 
 */
public class EncodingFilter implements Filter
{

	private String encoding = "UTF-8";
	private String contentType = "text/html;charset=UTF-8";
	private final List<String> allowedOrigins = Arrays.asList("http://localhost:8080");

	@Override
	public void destroy()
	{

	}
	
	private void handlerCache(HttpServletRequest request, HttpServletResponse response){
		String requestURI = request.getRequestURI();
		// 静态资源无需进行缓存确认
		if(requestURI.matches("^.*?(css|js|map|jpg|png|woff2|gif)$")){
			response.setHeader("Cache-Control", "max-age=86400");
	        response.setHeader("Pragma", "max-age=86400");
		}
		else{
			response.setHeader("Cache-Control", "no-cache");
	        response.setHeader("Pragma", "no-cache");
		}
		response.setDateHeader("Expires", -1);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse httpresponse,
			FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response=(HttpServletResponse)httpresponse;
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		response.setContentType(contentType);
		// 处理缓存配置
		handlerCache(request, response);
		
		//OPTIONS请求跨域配置
		String origin = request.getHeader("Origin");
//		response.setHeader("Access-Control-Allow-Origin","*"); 
		response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
        response.setHeader("Vary", "Origin");
//		response.setHeader("Access-Control-Allow-Origin","*"); 
//        response.setHeader("Access-Control-Allow-Headers","x-requested-with, accept, origin, content-type"); 
        response.setHeader("Access-Control-Allow-Headers","x-requested-with, accept, content-type"); 
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		response.setHeader("Access-Control-Allow-Methods","POST,GET,PUT,DELETE,OPTIONS"); 
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException
	{
		String _encoding = config.getInitParameter("encoding");
		String _contentType = config.getInitParameter("contentType");
		// String ext=config.getInitParameter("ext");
		if (_encoding != null)
			encoding = _encoding;
		if (_contentType != null)
			contentType = _contentType;

	}

}
