package com.hotent.mini.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotent.base.core.util.AppUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.auth.IAuthService;
import com.hotent.sys.api.auth.IAuthUser;

/**
 * Servlet implementation class AuthValidServlet
 */
@WebServlet("/AuthValidServlet")
public class AuthValidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public AuthValidServlet() {
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("uid");
		response.setContentType("text/json;charset=utf-8");
		
		IAuthService service=AppUtil.getBean(IAuthService.class);
		IAuthUser authuser= service.getByUid(id);
		if(authuser==null){
			response.getWriter().print("{result:-1,msg:\"userNotFound\"}");
		}
		else{
			boolean timeOut=authuser.isTimeOut();
			if(timeOut){
				response.getWriter().print("{result:-2,msg:\"timeout\"}");
			}
			else{
				IUser user=authuser.getUser();
				service.setAuth(user);
				String json="{result:0,id:\""+user.getUserId()+"\",account:\""+user.getAccount()+"\",fullname:\""+user.getFullname()+"\"}";
				response.getWriter().print(json);
			}
		}
	}

}
