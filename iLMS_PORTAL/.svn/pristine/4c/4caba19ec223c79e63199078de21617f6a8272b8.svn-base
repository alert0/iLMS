package com.hotent.mobile.weixin;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.EntityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import com.hotent.base.core.encrypt.EncryptUtil;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.HttpUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.mini.web.security.SecurityUtil;
import com.hotent.mobile.weixin.api.WeixinConsts;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.model.SysUser;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.api.auth.IAuthService;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

public class QYWXServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String redirect = SysPropertyUtil.getByAlias("httpbaseUrl");
		String code = req.getParameter("code");
		if (StringUtil.isNotEmpty(code)){
			String userUrl = WeixinConsts.getQyWxUserInfo(code);
			String json = HttpUtil.sendHttpsRequest(userUrl, "", WeixinConsts.METHOD_GET);
			JSONObject jsonObj = JSONObject.fromObject(json);
			String userId=jsonObj.getString("UserId");
			SecurityUtil.login(req, userId, "", true);
			SysUser user = (SysUser) ContextUtil.getCurrentUser();
			if(user.getHasSyncToWx() ==1){
				user.setHasSyncToWx(2);
				AppUtil.getBean(UserManager.class).update(user);
			}
			resp.sendRedirect(redirect);
		}
	}

	// }
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

}
