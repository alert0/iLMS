package com.hotent.mini.web.service;

import com.hotent.mini.web.json.AccessToken;
import com.hotent.mini.web.json.CheckTokenResult;

/**
 * OAuth2服务类
 * @author heyifan
 */
public interface OAuth2Service {
	/**
	 * 构建authorize地址
	 * @return
	 */
	String constructServiceUrl(String url);
	
	/**
	 * 通过code获取token
	 * @param code
	 * @return
	 */
	AccessToken getTokenByCode(String code) throws Exception;
	
	/**
	 * 验证token
	 * @param token
	 * @return
	 */
	CheckTokenResult checkToken(AccessToken token) throws Exception;
	
	/**
	 * 退出
	 * @return
	 * @throws Exception
	 */
	String logout() throws Exception;
}
