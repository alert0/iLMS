package com.hotent.mini.web.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.hotent.base.persistence.manager.SysResourceManager;
import com.hotent.base.persistence.model.SysResource;
import com.hotent.mini.web.context.SubSystemUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.auth.IAuthService;
import com.hotent.sys.api.auth.IAuthUser;
import com.hotent.sys.util.ContextUtil;

/**
 * springsecurity 安全过滤器。
 * 文件app-security.xml。
 * &lt;bean id="permissionFilter" class="com.hotent.core.web.filter.PermissionFilter">
 *		&lt;property name="authenticationManager" ref="authenticationManager" />
 *		&lt;property name="accessDecisionManager" ref="accessDecisionManager" />
 *		&lt;property name="securityMetadataSource" ref="securityMetadataSource" />
 *	&lt;/bean>
 * @author ray
 *
 */
public class PermissionFilter extends AbstractSecurityInterceptor implements Filter {

	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	
	@Resource
	IAuthService authService;
	@Resource
	SysResourceManager sysResourceManager;

	@Override
	public void destroy() {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;

		//添加token信息认证
		ContextUtil.clearCurrentUser();
		String token = getRequestToken(httpRequest);
		if(null != token){
			try {
				String[] decodeToken = SecurityUtil.decodeLoginToken(token);
				String uid = SecurityUtil.getUidByLoginToken(decodeToken);
				String userName = SecurityUtil.getUserNameByLoginToken(decodeToken);
//					Long expiryTime = SecurityUtil.getExpiryTimeByLoginToken(decodeToken);
				IAuthUser authuser = authService.getByUid(uid);
				if(null != authuser && token.equals(authuser.getTokenValue()) 
						&& null != userName && !authuser.isTimeOut()){ //&& System.currentTimeMillis() <= expiryTime.longValue()
					SecurityUtil.login(httpRequest, userName, "", true);
					authService.resetUserTimeout(uid);
					ContextUtil.setCurrentUser(authuser.getUser());
				}else{
					httpResponse.sendError(503, "认证失败或已过期，请重新登录！");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				httpResponse.sendError(503, e.toString());
				return;
			}
		}
		
		//检查操作请求权限
		IUser  user = ContextUtil.getCurrentUser();
		String systemId = SubSystemUtil.getSystemId(httpRequest);
		if(null != user && null != systemId){
			String reqAction = httpRequest.getServletPath(); //"/org/role/listJson"
			if(!sysResourceManager.hasReqAction(systemId, user, reqAction)){
				String resType = sysResourceManager.getReqActionResType(systemId, reqAction);
				if(SysResource.SYS_RES_TYPE_REQAJAX.equals(resType)){
					httpResponse.sendError(620, "您没有权限执行该操作！");
					return;
				}else if(SysResource.SYS_RES_TYPE_REQFILE.equals(resType)){
					downloadError(httpRequest, httpResponse, "您没有权限执行该操作！");
					return;
				}
			}
		}
		
		
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		boolean canInvokeNext =true;
		if(canInvokeNext){
			invoke(fi);
		}
	}

	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	public void invoke(FilterInvocation fi) throws IOException,ServletException {
		super.setRejectPublicInvocations(false);
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(
			FilterInvocationSecurityMetadataSource newSource) {
		this.securityMetadataSource = newSource;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//此处可能会拿到 xml配置里的 <http></http> 头
	}
	
	/**
	 * 获取Token信息
	 * @param httpRequest
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月19日 下午9:16:26
	 */
	private String getRequestToken(HttpServletRequest httpRequest){
        String token = httpRequest.getHeader("token");
        if(null == token || "".equals(token)){
            try {
            	token = httpRequest.getParameter("token");
            	if(null != token){
            		token = URLDecoder.decode(token,"utf-8");
            	}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        }
        return token;
    }
	
	private void downloadError(HttpServletRequest request, HttpServletResponse response, String errMsg) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			StringBuffer sbf = new StringBuffer();
			sbf.append("<!DOCTYPE html>");
			sbf.append("<head><meta charset=\"UTF-8\"><title>下载失败</title></head>");
			sbf.append("<body><script>");
			sbf.append("var tmpvue = new window.parent.Vue(); tmpvue.$message({message:\"");
			sbf.append(null == errMsg ? "文件下载失败啦!" : errMsg);
			sbf.append("\",type:\"error\"});");
			sbf.append("</script></body>");
			sbf.append("</html>");
			writer.print(sbf.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}