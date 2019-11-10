package com.hotent.mini.controller;


import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.org.persistence.model.SysUser;
import com.hotent.sys.persistence.manager.LogErrManager;
import com.hotent.sys.persistence.model.LogErr;
import com.hotent.sys.util.ContextUtil;



/**
 * 异常
 * @author sfhq284
 *
 */
@Controller
public class ErrorController {
	@Resource
	private LogErrManager logErrManager;
	
	@RequestMapping("/error")
	public ModelAndView error(HttpServletRequest request,HttpServletResponse response)
			throws IOException {
		Object attribute = request.getAttribute("javax.servlet.error.exception");
		String error = "";
		if(attribute instanceof Throwable){
			Throwable ex = (Throwable)attribute;
			error = ExceptionUtils.getRootCauseMessage(ex);
		}
	    String errorurl = getErrorUrl(request);
	    String ip =RequestUtil.getIpAddr(request);	
		IUser sysUser= ContextUtil.getCurrentUser();
		String account = "未知用户";
		if(BeanUtils.isNotEmpty(sysUser)){
			account= sysUser.getAccount();
		}
		String id = UniqueIdUtil.getSuid();
		LogErr logErr =  new LogErr();
		logErr.setId(id);
		logErr.setAccount(account);
		logErr.setIp(ip);
		logErr.setContent(error);
		logErr.setUrl(StringUtils.substring(errorurl, 0, 1000));
		logErr.setCreateTime(new Date());
		
		logErrManager.create(logErr);
		
		return new ModelAndView("error").addObject("error",error);
	}
	
	private String getErrorUrl(HttpServletRequest request){
		
		String url=request.getAttribute("javax.servlet.error.request_uri").toString();
		StringBuffer urlThisPage = new StringBuffer();
		urlThisPage.append(url);
		Enumeration<?> e = request.getParameterNames();
		String para = "";
		String values = "";
		urlThisPage.append("?");
		while (e.hasMoreElements()) {
			para = (String) e.nextElement();
			values = request.getParameter(para);
			urlThisPage.append(para);
			urlThisPage.append("=");
			urlThisPage.append(values);
			urlThisPage.append("&");
		}
		return urlThisPage.substring(0, urlThisPage.length() - 1);
		
	}
}
