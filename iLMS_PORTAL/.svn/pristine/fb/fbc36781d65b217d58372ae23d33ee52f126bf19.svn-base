package com.hotent.mini.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.hotent.base.core.web.RequestContext;
import com.hotent.base.db.datasource.DbContextHolder;
import com.hotent.bpmx.activiti.cache.ActivitiDefCache;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.mini.web.tag.AnchorTag;
import com.hotent.sys.util.ContextUtil;
/**
 * 
 * <pre> 
 * 描述：用于拦截请求以获取HttpSevletRequest对象，以供后续其他类使用,如可获取当前用户请求的IP等信息
 *       用于相同线程间共享Request对象
 * 构建组：x5-bpmx-platform
 * 作者：csx
 * 邮箱: chensx@jee-soft.cn
 * 日期:2014-1-4-下午4:01:41
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class RequestThreadFilter implements Filter{
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		try{
			cleanThreadLocal();
			RequestContext.setHttpServletRequest((HttpServletRequest) request);
			chain.doFilter(request, response);
		}finally{
			cleanThreadLocal();
		}
	}
	
	private void cleanThreadLocal(){
		RequestContext.clearHttpReqResponse();
//		ContextUtil.clearAll();  // 
		AnchorTag.cleanFuncRights(); //清理权限标签的 缓存
		ContextThreadUtil.cleanAll();
		DbContextHolder.setDefaultDataSource();
		
//		ActivitiDefCache.clearLocal(); //
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void destroy() {
		
	}
}
