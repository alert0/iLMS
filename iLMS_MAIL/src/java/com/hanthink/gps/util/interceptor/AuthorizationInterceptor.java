package com.hanthink.gps.util.interceptor;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.pub.vo.UserVO;

public class AuthorizationInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 5651794625022819641L;

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation ai) throws Exception {
		ActionContext ctx = ai.getInvocationContext();   
		HttpServletRequest httpRequest = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse httpResponse = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);   
		Map session = ctx.getSession();
		UserVO user = (UserVO) session.get(Constants.USER_KEY);
		// 用户未登录时
		if (user == null) {
			if (httpRequest.getHeader("x-requested-with") != null 
					&& httpRequest.getHeader("x-requested-with") 
					.equalsIgnoreCase("XMLHttpRequest")) { 
					httpResponse.setHeader("sessionstatus", "timeout");
					// ajax请求进入此
					httpResponse.getWriter().print("{sessionstatus:'timeout'}"); 
					return null;
		}
			return Action.LOGIN;
		} else {
			//Locale l = Locale.getDefault();
			// 设置为中文
			Locale l = new Locale("zh", "CN");
			ActionContext.getContext().setLocale(l);
			// TODO 检查用户权限
		}
		return ai.invoke();
	}
}
