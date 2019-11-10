package com.hanthink.gps.util.logger;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.NDC;

import com.hanthink.gps.util.Constants;
import com.hanthink.gps.pub.vo.UserVO;

public class Log4jNDCFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest current = (HttpServletRequest)request;
	    HttpSession session = current.getSession(false);
	    UserVO target = null;
	    boolean push = false;
	    try
	    {
			if ((session != null)
					&& ((target = (UserVO) session.getAttribute(Constants.USER_KEY)) != null)) {
				// 取得画面名称
				String pageName = current.getRequestURI();
				Pattern p = Pattern.compile("(^/([^/]*)/([^-]*)(-.*\\.action))");
				Matcher m = p.matcher(pageName);
				if (m.find()) {
					pageName = UrlPageNameMap.getInstance().getPageName(
							m.group(3));
				}
				// 日志信息中追加用户ID和画面名称
				NDC.push(target.getUserId() + ": " + pageName + "");
				push = true;
			}
	      chain.doFilter(request, response);
	    }
	    catch (Exception localException)
	    {
	    	LogUtil.error("Log4j Error:" + localException.getMessage());
	    	LogUtil.error(localException);
	    }
	    finally
	    { 
	      if (push)
	        NDC.remove();
	    }
	}

	public void init(FilterConfig config) throws ServletException {
	}
	
}
