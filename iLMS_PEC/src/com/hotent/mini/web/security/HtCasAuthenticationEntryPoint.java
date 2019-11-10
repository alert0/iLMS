package com.hotent.mini.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.util.StringUtils;

/**
 * 自定义CAS登录入口
 * <pre>
 * 在登录的cas地址中添加service作为回调参数，实现完成登录以后跳转到之前访问的页面
 * </pre>
 * @author heyifan
 */
public class HtCasAuthenticationEntryPoint extends CasAuthenticationEntryPoint{
	private String serviceUrlBak = null;
	
	@SuppressWarnings("static-access")
	@Override
	protected String createServiceUrl(final HttpServletRequest request, final HttpServletResponse response) {
		if(serviceUrlBak==null){
			serviceUrlBak=getServiceProperties().getService();
		}
		if(serviceUrlBak!=null){
			String queryString=request.getQueryString();
			StringBuffer requestURL = request.getRequestURL();
			String requestURI = requestURL.toString();
			String serviceUrl="";
			if(!requestURI.equals("/") && requestURI.length()>0){
				serviceUrl ="?" + getServiceProperties().DEFAULT_CAS_SERVICE_PARAMETER;
				serviceUrl += "=" + requestURI;
				if(!StringUtils.isEmpty(queryString)){
					serviceUrl+="?"+queryString;
				}
			}
			getServiceProperties().setService(serviceUrlBak+serviceUrl);
		}
		return super.createServiceUrl(request, response);
	}
}
