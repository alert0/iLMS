package com.hotent.sys.api.auth;

import com.hotent.org.api.model.IUser;

public interface IAuthService {
	
	/**
	 * 登录后设置缓存。
	 * @param uid
	 * @param user
	 */
	String setAuth(IUser user);
	
	/**
	 * 根据ID获取缓存。
	 * @param uid
	 * @return
	 */
	IAuthUser getByUid(String uid);
	
	/**
	 * 获取appId 获取客户端验证的URL。
	 * @param appId
	 * @return
	 */
	String getClientUrlByAppId(String appId);
	
	/**
	 * 根据APPID获取返回的URL。
	 * @param appId
	 * @return
	 */
	String getReturnUrlByAppId(String appId);

	/**
	 * 是否集群运行环境
	 * @return
	 */
	Boolean isCluster();
	
	/**
	 * 根据UID和token直接保存认证信息，服务端分布式部署使用
	 * @param user
	 * @param uid
	 * @param token
	 * @return
	 * @DATE	2019年4月13日 下午2:13:23
	 */
	String setAuth(IUser user, String uid, String token);
}
