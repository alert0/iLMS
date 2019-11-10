package com.hotent.mini.web.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

import com.hotent.sys.util.ContextUtil;

/**
 * 重写Spring Mvc Servlet，处理输入URL没有requestMapping处理的情况
 * <pre>
 *在地址栏输入URL地址时，如果没有找到对应的处理器，那么会通过这个noHandlerFound方法。
 *比如
 *  1.在地址栏中输入地址：
 * 	/user/add
 * 	那么这个地址会找到对应的jsp /user/add.jsp
 * 	这个jsp文件在 /WEB-INF/view 下。
 *  2.如果输入地址:
 *  /user/add;jsessionid=EAADEDC
 *  找到的地址如下:
 *  /user/add.jsp
 * </pre>
 * 
 */
public class SpringMvcServlet extends DispatcherServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestURI = request.getRequestURI();
		// 处理RequestURI
		String contextPath = request.getContextPath();
		int cxtIndex = requestURI.indexOf(contextPath);
		if (cxtIndex != -1) {
			requestURI = requestURI.substring(cxtIndex + contextPath.length());
		}
		
		if(requestURI.indexOf(";jsessionid")!=-1){
			int pos=requestURI.indexOf(";jsessionid");
			requestURI=requestURI.substring(0,pos);
		}
		
		String jspPath=requestURI + ".jsp";
		logger.debug("requestURI:" + request.getRequestURI() + " and forward to /WEB-INF/view" + jspPath);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/view" + jspPath);
		//将当前登陆用户添加到ModelAndView的上下文参数中
		request.setAttribute("currentUser", ContextUtil.getCurrentUser());
		requestDispatcher.forward(request, response);
	}
	
	
}
