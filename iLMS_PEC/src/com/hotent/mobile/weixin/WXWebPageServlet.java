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

public class WXWebPageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String redirect = SysPropertyUtil.getByAlias("httpbaseUrl");
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		// if (sysUser==null) {
		String code = req.getParameter("code");
		if (StringUtil.isNotEmpty(code)){
			// 根据code获取access_token
			String appid = SysPropertyUtil.getByAlias("AppID");
			String secret = SysPropertyUtil.getByAlias("AppSecret");
			String userUrl = WeixinConsts.getWxAccessToken(code, appid, secret);
			String json = HttpUtil.sendHttpsRequest(userUrl, "", WeixinConsts.METHOD_GET);
			JSONObject jsonObj = JSONObject.fromObject(json);
			// 防止access_token超时先refresh token
			String refresh_token = jsonObj.getString("refresh_token");
			String refreshTokenUrl = WeixinConsts.refreshToken(appid, refresh_token);
			json = HttpUtil.sendHttpsRequest(refreshTokenUrl, "", WeixinConsts.METHOD_GET);
			jsonObj = JSONObject.fromObject(json);
			String access_token = jsonObj.getString("access_token");
			String openid = jsonObj.getString("openid");
			// 根据access_token和openid获取用户所有基础信息
			String UserInfoURL = WeixinConsts.getWxUserInfo(access_token, openid);
			json = HttpUtil.sendHttpsRequest(UserInfoURL, "", WeixinConsts.METHOD_GET);
			jsonObj = JSONObject.fromObject(json);
			String name = jsonObj.getString("nickname");
			String sex = jsonObj.getString("sex");
			String country = jsonObj.getString("country");
			String province = jsonObj.getString("province");
			String city = jsonObj.getString("city");
			// 开发者拥有多个移动应用、网站应用和公众帐号，可通过获取用户基本信息中的unionid来区分用户的唯一性
			// String unionid = jsonObj.getString("unionid");
			User user = (User) AppUtil.getBean(IUserService.class).getUserByAccount(openid);
			if (BeanUtils.isEmpty(user)) {
				User saveUser = new User();
				saveUser.setId(UniqueIdUtil.getSuid());
				saveUser.setStatus(1);
				saveUser.setAddress(country + "-" + province + "-" + city);
				saveUser.setOpenId(openid);
				saveUser.setSex(sex);
				saveUser.setFullname(name);
				saveUser.setAccount("account"+String.valueOf(new Random().nextInt(999999999)+1000000000));
				saveUser.setPassword(EncryptUtil.encryptSha256("123456"));
				AppUtil.getBean(UserManager.class).create(saveUser);
				User saveUser1=AppUtil.getBean(UserManager.class).get(saveUser.getId());
				if (BeanUtils.isNotEmpty(saveUser1)){
					Authentication auth;
					auth = SecurityUtil.login(req, saveUser.getOpenId(), "", true);
					AppUtil.getBean(SessionAuthenticationStrategy.class).onAuthentication(auth, req, resp);
				}
				
				if (AppUtil.getBean(IAuthService.class).isCluster()) {
					// 共享session
					SecurityUtil.addSessionId2Cache(req, openid);
				}
				resp.sendRedirect(redirect);
			} else {

				Authentication auth;
				auth = SecurityUtil.login(req, user.getOpenId(), "", true);
				AppUtil.getBean(SessionAuthenticationStrategy.class).onAuthentication(auth, req, resp);
				if (AppUtil.getBean(IAuthService.class).isCluster()) {
					// 共享session
					SecurityUtil.addSessionId2Cache(req, openid);
				}
				resp.sendRedirect(redirect);
			}

		}

	}

	// }
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

}
