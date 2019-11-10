package com.hotent.mini.web.security;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.encrypt.EncryptUtil;
import com.hotent.base.core.spring.HtPropertyPlaceholderConfigurer;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.mini.controller.util.PlatformConsts;
import com.hotent.org.persistence.model.SysUser;

public class HtDecisionManager implements AccessDecisionManager{
	
	
		// 是否初始化
		private static boolean isInit = false;
		// 是否验证通过
		private static int isValid = -3;

		@Resource
		private HtPropertyPlaceholderConfigurer config;

		/**
		 * 验证是否有效。 0 有效 -1 没有产品key -2 产品key有问题 -3 有效期已过
		 * 
		 * @return
		 */
		private synchronized int validKey() {
			// 已初始化，直接返回验证结果
			if (isInit) {
				return isValid;
			}
			
			// 未初始化，设置当前为已经初始化
			isInit = true;
			// -1没有设置产品key
			String productKey = (String) config.getValue("productKey");

			if (StringUtil.isEmpty(productKey)) {
				isValid = -1;
				return -1;
			}
			
			try {
				productKey = Base64.getFromBase64(EncryptUtil.decrypt(productKey.trim()));
				if (isFirstEvaluate(productKey)) {
					isValid = 0;
					return 0;
				}
				String[] aryProductKey = productKey.split(",");
				//正版
				if (aryProductKey[0].equals("1")) {
					isValid = 0;
					return 0;
				} 
				//试用版
				Long startTime = Long.parseLong(aryProductKey[1]);
				Long stopTime = Long.parseLong(aryProductKey[2]);
				Long currentTime = System.currentTimeMillis();
				if (currentTime > startTime && currentTime < stopTime) {
					isValid = 0;
					return 0;
				}
				// 已过有效期
				else {
					isValid = -3;
					return -3;
				}
			}
			// key 有问题
			catch (Exception ex) {
				isValid = -2;
				return -2;
			}
		}
		
		public static void main(String[] args) throws Exception {
			String str= generateEvaluateKey();
			System.out.println(str);
		}

		/**
		 * 判断是否是叙作为评估版首次使用。如果是作为评估版首次使用，返回<code>true</code>，否则，返回<code>false</code>
		 * 
		 * @param productKey
		 * @return 作为评估版首次使用，返回<code>true</code>，否则，返回<code>false</code>。
		 */
		private  boolean isFirstEvaluate(String productKey) {
			if(!productKey.trim().equals("http://www.jee-soft.cn/")){
				return false;
			}
			String encryptKey = generateEvaluateKey();
			
			String path=FileUtil.getClassesPath() +"/conf/x5-base-db.properties".replace("/", File.separator);
			FileUtil.saveProperties(path, "productKey", encryptKey);
			
			return true;
		}

		/**
		 * 生成试用期的密钥
		 * @return 用密钥
		 */
		private static  String generateEvaluateKey() {
			Long startTime = System.currentTimeMillis();

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 60);
			Long endTime = calendar.getTimeInMillis();
			String key = "0," + startTime + "," + endTime;
			try {
				key = EncryptUtil.encrypt(Base64.getBase64(key));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return key;
		}

		private String getMessage(int i) {
			String msg = "";
			switch (i) {
			case -1:
				msg = "没有产品key,请联系<a href='http://www.jee-soft.cn' target='_blank'>宏天软件</a>购买正式版产品!";
				break;
			case -2:
				msg = "有效期已过,请联系<a href='http://www.jee-soft.cn' target='_blank'>宏天软件</a>购买正式版产品!";
			case -3:
				msg = "有效期已过,请联系<a href='http://www.jee-soft.cn' target='_blank'>宏天软件</a>购买正式版产品!";
			default:
				break;
			}
			return msg;
		}

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes.contains(PlatformConsts.ROLE_CONFIG_ANONYMOUS)) {
			return;
		}
		
		/**
		 * 验证码控制。
		 */
		int rtn = validKey();
		if (rtn != 0) {
			String msg = getMessage(rtn);
			throw new AccessDeniedException(msg);
		}   
		
	
		// 登陆访问
		if (authentication == null) {
			throw new AccessDeniedException("没有登录系统");
		}
		Object principal = authentication.getPrincipal();
		if (principal == null) {
			throw new AccessDeniedException("登录对象为空");
		}
		
		if (!(principal instanceof UserDetails)) {
			throw new AccessDeniedException("登录对象必须为SysUser实现类");
		}
		UserDetails user = (UserDetails) principal;
		// 获取当前用户的角色。
		Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) user.getAuthorities();
		// 超级访问
		if (roles.contains(PlatformConsts.ROLE_GRANT_SUPER)) {
			return;
		}
		//URL没有做授权
		if (configAttributes.contains(PlatformConsts.ROLE_CONFIG_NONE)) {
			throw new AccessDeniedException("URL没有做授权");
		}
		// 公开访问
		if (configAttributes.contains(PlatformConsts.ROLE_CONFIG_PUBLIC)) {
			return;
		}
		// 授权访问
		// 遍历当前用户的角色，如果当前页面对应的角色包含当前用户的某个角色则有权限访问。
		for (GrantedAuthority hadRole : roles) {
			if (configAttributes.contains(new SecurityConfig(hadRole.getAuthority()))) {
				return;
			}
		}
		throw new AccessDeniedException("对不起,你没有访问该页面的权限!");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
