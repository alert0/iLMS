package com.hotent.mini.web.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.persistence.manager.ResRoleManager;
import com.hotent.mini.controller.util.PlatformConsts;
import com.hotent.mini.web.context.SubSystemUtil;

/**
 * 根据当前的URL获取他上面分配的角色列表。
 * @author ray
 *
 */
public class HtSecurityMetadataSource implements FilterInvocationSecurityMetadataSource ,BeanPostProcessor{

	@Resource(name="anonymousUrls")
	RegMatchers matchers;
	@Resource
	ResRoleManager resRoleManager;
	
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		Collection<ConfigAttribute> configAttribute =new HashSet<ConfigAttribute>();
		
		FilterInvocation filterInvocation=((FilterInvocation)object);
    	HttpServletRequest request=filterInvocation.getRequest();
    	
    	String url = request.getRequestURI();
    	url=removeCtx(url,request.getContextPath());
    	
    	if(url.startsWith("/WEB-INF")) return configAttribute;
    	
    	if(matchers.isContainUrl(url) ){
    		configAttribute.add(PlatformConsts.ROLE_CONFIG_ANONYMOUS);
    		return configAttribute;
    	}
    	
    	String systemId=SubSystemUtil.getSystemId(request);
    	
    	Map<String,Set<String>> urlRoleMap=resRoleManager.getUrlRoleBySystem(systemId);
    	//根据当前的URL获取资源对应的角色。
    	if(urlRoleMap.containsKey(url)){
    		Set<String> urlSet=urlRoleMap.get(url);
    		for(String role:urlSet){
    			configAttribute.add(new SecurityConfig(role));
    		}
    	}
    	else{
    		configAttribute.add(PlatformConsts.ROLE_CONFIG_PUBLIC);
    	}
    	//如果数据库中定义了URL但是没有任何角色授权，则添加一个ROLE_CONFIG_NONE 角色，
    	//在 HtDecisionManager 判断这个角色如果存在，则抛出没有授权信息额异常。
    	if(configAttribute.size()==0){
    		configAttribute.add(PlatformConsts.ROLE_CONFIG_NONE);
    	}
		return configAttribute;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
        return true;
    }
    	
	
	/**
	 * 获取当前URL
	 * @param url
	 * @param ctxPath
	 * @return
	 */
	private static String removeCtx(String url,String ctxPath){
		url=url.trim();
		if(StringUtil.isEmpty(ctxPath)) return url;
		if(StringUtil.isEmpty(url)) return "";
		if(url.startsWith(ctxPath)){
			url=url.replaceFirst(ctxPath, "");
		}
		return url;
	}
	
	
	/**
	 * 保证service的注入。
	 * 获取系统资源。
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

}
