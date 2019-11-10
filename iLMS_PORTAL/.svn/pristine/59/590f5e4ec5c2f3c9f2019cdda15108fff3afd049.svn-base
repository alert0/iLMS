package com.hanthink.sw.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

@Controller
@RequestMapping("/sw/userType")
public class UserTypeController {

	/**
	 * 
	 * @Description: 获取当前登录用户的类型，如果是供应商用户则设置供应商代码框不可输入
	 * @param @param request
	 * @param @param response
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月6日 下午12:14:47
	 */
	@RequestMapping("getUserType")
	public @ResponseBody IUser getUserType(HttpServletRequest request, HttpServletResponse response) {
		IUser user = ContextUtil.getCurrentUser();
//		String userType =  user.getUserType();
		return user;
	}
}
