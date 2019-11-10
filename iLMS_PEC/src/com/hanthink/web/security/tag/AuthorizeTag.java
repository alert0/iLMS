package com.hanthink.web.security.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.persistence.manager.SysResourceManager;
import com.hotent.mini.web.context.SubSystemUtil;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 * Description: 权限控制
 * Company: HanThink
 * Date: 2018年8月8日 下午6:11:00
 * </pre>
 * @author ZUOSL
 */
@Component
public class AuthorizeTag extends BodyTagSupport {
	private static final long serialVersionUID = -4398757500129444003L;
	
	SysResourceManager sysResourceManager = AppUtil.getBean(SysResourceManager.class);;
	
	/** 权限名称 */
	private String name;
	
	
	
	@Override
	public int doStartTag() {
		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		if(null != request){
			String systemId = SubSystemUtil.getSystemId(request);
			if(StringUtils.isNotEmpty(systemId)){
				if(sysResourceManager.hasAlias(systemId, ContextUtil.getCurrentUser(), this.name)){
					return EVAL_BODY_INCLUDE;
				}
			}
		}
		
		return SKIP_BODY;
	}
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
