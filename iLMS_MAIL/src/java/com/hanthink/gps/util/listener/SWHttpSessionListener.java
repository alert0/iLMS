package com.hanthink.gps.util.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hanthink.gps.pub.dao.UserDao;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.pub.vo.UserVO;

public class SWHttpSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
	}

	public void sessionDestroyed(HttpSessionEvent event) {
//		HttpSession session = event.getSession();
//		UserVO user = (UserVO) session.getAttribute(Constants.USER_KEY);
//		if(user==null) return;
//		// 更新用户状态
//		ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(event.getSession().getServletContext());
//		UserDao dao = (UserDao) appContext.getBean("userDao");
//		UserVO userInfo = new UserVO();
//		// 用户状态:离线
//		userInfo.setLoginStatus(Constants.LOGIN_STATUS_OFFLINE);
//		// 用户ID
//		userInfo.setUserId(user.getUserId());
//		// 用户身份:GAMC(只有GAMC才有状态)
//		if (Constants.USER_TYPE_SUPPLIER.equals(user.getUserType())) {
//			dao.updateExit(userInfo);
//		} 
	}
}
