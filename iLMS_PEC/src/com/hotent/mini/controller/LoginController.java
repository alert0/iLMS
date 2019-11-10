package com.hotent.mini.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.CookieUitl;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.CommonDao;
import com.hotent.base.persistence.manager.CacheUtil;
import com.hotent.mini.web.context.SubSystemUtil;
import com.hotent.mini.web.security.HtSwitchUserFilter;
import com.hotent.mini.web.security.SecurityUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.api.auth.IAuthService;
import com.hotent.sys.api.auth.IAuthUser;
import com.hotent.sys.util.SysPropertyUtil;

@Controller
public class LoginController {
	@Resource
	AuthenticationManager authenticationManager;
	@Resource
	IUserService userService;
	//@Resource
	SessionAuthenticationStrategy sessionStrategy= new NullAuthenticatedSessionStrategy();
	@Resource
	IAuthService authService;
	@Resource
	CommonDao<Map<String, Object>> commDao;
	
	private  String defaultLogin="/login";
	

	@RequestMapping(value = "/login/valid", method = RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request, HttpServletResponse response) {
		
		String referer=request.getHeader("Referer");
		if(null != referer && referer.indexOf("mobile")>-1){
			CookieUitl.addCookie("loginAction", "mobile", true, request, response);
		}
		
		
		String account = RequestUtil.getString(request, "account");
		String password = RequestUtil.getString(request, "password");
		String factoryCode = RequestUtil.getString(request, "factoryCode");
		if(StringUtil.isEmpty(account)){
			return new LoginResult(false, null ,"账号不能为空.");
		}
		if(StringUtil.isEmpty(password)){
			return new LoginResult(false, null ,"密码不能为空.");
		}
		
		String systemId = SubSystemUtil.getSystemId(request);
		
		//删除切换用户标识
		CookieUitl.delCookie(HtSwitchUserFilter.SwitchAccount, request, response);
		try {
			
			User user = (User)userService.getUserByAccount(account);
			Authentication auth;
			auth =SecurityUtil.login(request, account, password, false);
			sessionStrategy.onAuthentication(auth, request, response);
			
			//登录工厂处理
			//是否允许不选工厂登录
			List<Map<String, Object>> userFactoryList = new ArrayList<Map<String, Object>>();
			if(!"1".equals(SysPropertyUtil.getByAlias("sys.allowEmptyFactoryLogin", "1"))){
				String queryFSql = " SELECT B.FACTORY_CODE, B.FACTORY_NAME, U.ACCOUNT_ USER_NAME FROM MM_PUB_FACTORY_USER A LEFT JOIN MM_PUB_FACTORY B ON B.FACTORY_CODE = A.FACTORY_CODE LEFT JOIN SYS_USER U ON U.ID_ = A.USER_ID ";
				QueryFilter filter = new DefaultQueryFilter();
				filter.addFilter("USER_NAME", account, QueryOP.EQUAL);
				userFactoryList = commDao.queryForList(queryFSql, filter, null);
				if(StringUtil.isNotEmpty(factoryCode)){
					boolean isok = false;
					for(Map<String, Object> map : userFactoryList){
						if(factoryCode.equals(String.valueOf(map.get("FACTORY_CODE")))){
							isok = true;
							break;
						}
					}
					if(!isok){
						return new LoginResult(false, null ,"用户工厂权限错误！");
					}
				}else{
					if(null != userFactoryList && 1 == userFactoryList.size()){
						factoryCode = String.valueOf(userFactoryList.get(0).get("FACTORY_CODE"));
					}else{ 
						if(null != userFactoryList && 1 < userFactoryList.size()){
							return new LoginResult(false, user.getAccount(), userFactoryList, "factory", "用户有多个工厂，请选择一个工厂登录");
						}else{
							return new LoginResult(false, null ,"对不起，您没有可用的工厂权限！");
						}
					}
				}
			}
			user.setCurFactoryCode(factoryCode);
			String uid = authService.setAuth(user);
//			if(authService.isCluster()){
				//共享session
//				SecurityUtil.addSessionId2Cache(request, account);
//			}
			//执行记住密码动作。
			SecurityUtil.writeRememberMeCookie( request,  response,  account,password);
			
			//清除资源缓存
			CacheUtil.cleanCache(CacheUtil.RESOURCE_USER_RES + systemId + user.getUserId());
			CacheUtil.cleanCache(CacheUtil.RESOURCE_REQ_USER_RES + systemId + user.getUserId());
			
			IAuthUser authUser = authService.getByUid(uid);
			
			//根据系统属性表配置的后台服务地址，将token信息保存到其它后台服务
			String serverUrlVal = SysPropertyUtil.getByAlias("sys.serverUrl", "");
			if(StringUtil.isNotEmpty(serverUrlVal)){
				String[] serverUrlArr = serverUrlVal.split(",");
				String localip = "";
				try {
					localip = InetAddress.getLocalHost().getHostAddress().toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(String serverUrl : serverUrlArr){
			        if(null != serverUrl && serverUrl.indexOf(localip) < 0){
			        	saveTokenToOtherServer(serverUrl, authUser, uid);
			        }
				}
			}
			
			return new LoginResult(true, uid, authUser.getTokenValue(), userFactoryList, "登陆成功");
		} catch (BadCredentialsException e) {
			return new LoginResult(false, null ,"账号或密码错误.");
		}
		catch (DisabledException e) {
			return new LoginResult(false, null ,"帐号被禁用.");
		}
		catch (LockedException e) {
			return new LoginResult(false, null ,"帐号被锁定.");
		}
		catch (AccountExpiredException e) {
			return new LoginResult(false, null ,"帐号过期.");
		}
		catch(Exception ex){
			return new LoginResult(false, null ,ex.getMessage());
		}
	}
	
	/**
	 * 服务端认证，记录token信息
	 * @param request
	 * @param response
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年4月12日 下午6:24:53
	 */
	@RequestMapping(value = "/login/validServer", method = RequestMethod.POST)
	@ResponseBody
	public Object loginServer(HttpServletRequest request, HttpServletResponse response) {
		
		String account = RequestUtil.getString(request, "account");
		String uid = RequestUtil.getString(request, "uid");
		String token = RequestUtil.getString(request, "tokenVal");
		if(StringUtil.isEmpty(account) || StringUtil.isEmpty(uid) || StringUtil.isEmpty(token)){
			return new LoginResult(false, null ,"参数获取不全.");
		}
		
		String systemId = SubSystemUtil.getSystemId(request);
		
		//删除切换用户标识
		CookieUitl.delCookie(HtSwitchUserFilter.SwitchAccount, request, response);
		try {
			
			IUser user = userService.getUserByAccount(account);
			Authentication auth = SecurityUtil.login(request, account, "", true);
			sessionStrategy.onAuthentication(auth, request, response);
			authService.setAuth(user, uid, token);
			if(authService.isCluster()){
				//共享session
				SecurityUtil.addSessionId2Cache(request, account);
			}
			
			//清除资源缓存
			CacheUtil.cleanCache(CacheUtil.RESOURCE_USER_RES + systemId + user.getUserId());
			CacheUtil.cleanCache(CacheUtil.RESOURCE_REQ_USER_RES + systemId + user.getUserId());
			
			IAuthUser authUser = authService.getByUid(uid);
			return new LoginResult(true, uid, authUser.getTokenValue(), "登陆成功");
		} catch (BadCredentialsException e) {
			return new LoginResult(false, null ,"账号或密码错误.");
		}
		catch (DisabledException e) {
			return new LoginResult(false, null ,"帐号被禁用.");
		}
		catch (LockedException e) {
			return new LoginResult(false, null ,"帐号被锁定.");
		}
		catch (AccountExpiredException e) {
			return new LoginResult(false, null ,"帐号过期.");
		}
		catch(Exception ex){
			ex.printStackTrace();
			return new LoginResult(false, null ,ex.getMessage());
		}
	}
	
	/**
	 * 保存认证信息到其它后台服务
	 * @param serverUrl
	 * @param authUser
	 * @param uid
	 * @author ZUOSL	
	 * @DATE	2019年4月13日 下午2:33:25
	 */
	private static String saveTokenToOtherServer(String serverUrl, IAuthUser authUser, String uid) {

		try {
			String reqUrl = serverUrl + "/login/validServer";
			StringBuffer param = new StringBuffer();
			param.append("account=");
			param.append(authUser.getUser().getAccount());
			param.append("&uid=");
			param.append(uid);
			param.append("&tokenVal=");
			param.append(authUser.getTokenValue());
			
			return post(reqUrl, param.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/loginRedirect")
	public void loginRedirect(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String loginAction=CookieUitl.getValueByName("loginAction", request);
		String redirectUrl="";
		Map<String,String> actionPageMap=(Map<String,String>)AppUtil.getBean("actionPageMap");
		if(StringUtil.isNotEmpty(loginAction) && actionPageMap.containsKey(loginAction)){
			//删除cookie
			CookieUitl.delCookie("loginAction", request, response);
			redirectUrl=actionPageMap.get(loginAction);
			response.sendRedirect(request.getContextPath() +redirectUrl);
			return ;
		}
		response.sendRedirect(request.getContextPath() +this.defaultLogin);
		
	}

	public class LoginResult {
		private final boolean success;
		
		private final String username;
		private final String message;
		private String token;
		private String errorCode;
		private List<Map<String,Object>> factoryList;

		public LoginResult(boolean success, String username,String message) {
			this.success = success;
			this.username = username;
			this.message = message;
		}
		
		public LoginResult(boolean success, String username, String token, String message) {
			this.success = success;
			this.username = username;
			this.token = token;
			this.message = message;
		}
		
		public LoginResult(boolean success, String username, String token, 
				List<Map<String,Object>> factoryList, String message) {
			this.success = success;
			this.username = username;
			this.token = token;
			this.factoryList = factoryList;
			this.message = message;
		}
		
		public LoginResult(boolean success, String username, 
				List<Map<String,Object>> factoryList, String errorCode, String message) {
			this.success = success;
			this.username = username;
			this.factoryList = factoryList;
			this.errorCode = errorCode;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getUsername() {
			return username;
		}
		
		public String getMessage() {
			return message;
		}
		public String getToken(){
			return this.token;
		}

		public List<Map<String, Object>> getFactoryList() {
			return factoryList;
		}

		public void setFactoryList(List<Map<String, Object>> factoryList) {
			this.factoryList = factoryList;
		}

		public String getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
		
	}
	
	public static String post(String URL, String parm) {

        HttpURLConnection conn = null;
        DataOutputStream dataOut = null;
        BufferedReader dataIn = null;

        try {
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Referer", "http://xxxx.xxx.com");
            conn.addRequestProperty("User-Agent", "");

            conn.connect();
            dataOut = new DataOutputStream(conn.getOutputStream());
            dataOut.writeBytes(parm != null ? parm : "");
            dataOut.flush();

            dataIn = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = dataIn.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataOut != null) {
                    dataOut.close();
                }
                if (dataIn != null) {
                    dataIn.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
	
}