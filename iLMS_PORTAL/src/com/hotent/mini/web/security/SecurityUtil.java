package com.hotent.mini.web.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.encrypt.EncryptUtil;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.CookieUitl;
import com.hotent.base.core.web.RequestUtil;

/**
 * springsecurity 工具类。
 * @author ray
 *
 */
public class SecurityUtil {
	
	private static String rememberPrivateKey = "rememberPrivateKey";
	private static String sessionIdKey = "JSESSIONID";
	private static Integer sessionOutTime = 30 * 60;
	
	private static SessionRegistry sessionRegistry;
	
	/**
	 * 按照 spring 提供的规则 SPRING_SECURITY_REMEMBER_ME_COOKIE。
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @param enPassword
	 */
	public static void writeRememberMeCookie(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		String rememberMe = RequestUtil.getString(request, "rememberMe", "0") ;
		if (!"1".equals(rememberMe)) return;
		
		String enPassword=EncryptUtil.encryptSha256(password);
		
		long tokenValiditySeconds = 1209600; // 14 days
		long tokenExpiryTime = System.currentTimeMillis() + (tokenValiditySeconds * 1000);
		String signatureValue = makeTokenSignature(tokenExpiryTime,username,enPassword);
		String tokenValue = username + ":" + tokenExpiryTime + ":" + signatureValue;
		String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue.getBytes()));
		Cookie cookie = new Cookie(TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, tokenValueBase64);
		cookie.setMaxAge(60 * 60 * 24 * 365 ); // 5 years
		cookie.setPath(org.springframework.util.StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
		response.addCookie(cookie);
	}
	
	/**
	 * 添加 SessionId到内存数据库中
	 * @param request
	 * @param account
	 */
	@SuppressWarnings("unchecked")
	public static void addSessionId2Cache(HttpServletRequest request, String account){
		String sessionId = CookieUitl.getValueByName(sessionIdKey, request);
		ICache<String> icache = AppUtil.getBean(ICache.class);
		icache.add(sessionId,account,sessionOutTime);
	}
	
	/**
	 * 刷新sessionId
	 * @param request
	 */
	public static void refreshSessionIdInCache(HttpServletRequest request){
		String account = getSessionIdFromCache(request);
		if(StringUtil.isNotEmpty(account)){
			addSessionId2Cache(request, account);
		}
	}
	
	/**
	 * 获取sessionId
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public static String getSessionIdFromCache(HttpServletRequest request){
		String sessionId = CookieUitl.getValueByName(sessionIdKey, request);
		String account = "";
		if(sessionId==null) return account;
		ICache<String> icache = AppUtil.getBean(ICache.class);
		Object byKey = icache.getByKey(sessionId);
		if(BeanUtils.isNotEmpty(byKey)){
			account = byKey.toString();
		}
		return account;
	}
	
	/**
	 * 删除 SessionId
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public static void removeSessionIdFromCache(HttpServletRequest request){
		String sessionId = CookieUitl.getValueByName(sessionIdKey, request);
		ICache<String> icache = AppUtil.getBean(ICache.class);
		icache.delByKey(sessionId);
	}
	
	/**
	 * 登录系统。
	 * @param request
	 * @param userName		用户名
	 * @param pwd			密码
	 * @param isIgnorePwd	是否忽略密码。
	 * @return
	 */
	public static Authentication login(HttpServletRequest request,String userName,String pwd,boolean isIgnorePwd){
		AuthenticationManager authenticationManager =(AuthenticationManager) AppUtil.getBean("authenticationManager");
		CustomPwdEncoder.setIngore(isIgnorePwd);
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, pwd);
		authRequest.setDetails(new WebAuthenticationDetails(request));
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication auth = authenticationManager.authenticate(authRequest);
		securityContext.setAuthentication(auth);
		return auth;
	}
	
	/**
	 * 踢出用户
	 * @param account 账号
	 */
	public static void kickoutUser(String account){
		if(StringUtil.isEmpty(account)) return;
		if(sessionRegistry==null){
			sessionRegistry = AppUtil.getBean(SessionRegistry.class);
		}
		List<Object> objects = sessionRegistry.getAllPrincipals();
        for (Object o : objects) {
        	User user = (User) o;
            if (account.equals(user.getUsername())) {
                List<SessionInformation> sis = sessionRegistry.getAllSessions(o, false);
                if (sis != null) {
                    for (SessionInformation si : sis) {
                        si.expireNow();
                    }
                }
            }
        }
	}

	/**
	 * 将过期时间:用户名：密码 和 rememberPrivateKey 进行MD5签名。
	 * @param tokenExpiryTime
	 * @param username
	 * @param password
	 * @return
	 */
	private static String makeTokenSignature(long tokenExpiryTime, String username, String password) {
        String data = username + ":" + tokenExpiryTime + ":" + password + ":" + rememberPrivateKey;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(Hex.encode(digest.digest(data.getBytes())));
	}
	
	/**
	 * 生成登录Token信息
	 * @param uid 登录UID
	 * @param username 登录账号 用户名
	 * @param enPassword 用户密码
	 * @param tokenExpiryTime 失效时间
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月19日 下午8:37:20
	 */
	public static String generateLoginToken(String uid, String username, String enPassword, long tokenExpiryTime){
		String signatureValue = makeTokenSignature(tokenExpiryTime,username,enPassword);
		String tokenValue = uid + ":" + username + ":" + tokenExpiryTime + ":" + signatureValue;
		return new String(Base64.encodeBase64(tokenValue.getBytes()));
	}
	
	/**
	 * 解码登录Token
	 * @param token
	 * @return
	 * @author ZUOSL	
	 * @throws Exception 
	 * @DATE	2018年9月19日 下午8:44:24
	 */
	public static String[] decodeLoginToken(String token) throws Exception{
		if(null == token || "".equals(token)){
			return null;
		}
		String[] arr = new String(Base64.decodeBase64(token)).split(":");
		if(4 != arr.length){
			throw new Exception("token格式错误！");
		}
		return arr;
	}
	
	/**
	 * 根据解析的Token信息获取登录UID
	 * @param deocdeToken
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月19日 下午8:53:58
	 */
	public static String getUidByLoginToken(String[] decodeToken){
		return getTokenInfo(decodeToken, 0);
	}
	
	/**
	 * 根据解析的Token信息获取登录账号 用户名
	 * @param decodeToken
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月19日 下午8:59:24
	 */
	public static String getUserNameByLoginToken(String[] decodeToken){
		return getTokenInfo(decodeToken, 1);
	}
	
	/**
	 * 根据解析的Token信息获取token失效时间
	 * @param decodeToken
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月19日 下午9:01:33
	 */
	public static Long getExpiryTimeByLoginToken(String[] decodeToken){
		return Long.valueOf(getTokenInfo(decodeToken, 2));
	}
	
	private static String getTokenInfo(String[] decodeToken, int index){
		if(null != decodeToken && index < decodeToken.length){
			return decodeToken[index];
		}
		return null;
	}
	

}
