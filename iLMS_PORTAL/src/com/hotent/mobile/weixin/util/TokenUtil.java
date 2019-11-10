package com.hotent.mobile.weixin.util;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.base.core.util.HttpUtil;
import com.hotent.mobile.weixin.api.WeixinConsts;
import com.hotent.mobile.weixin.model.TokenModel;


/**
 * 获取token工具类。
 * @author ray
 *
 */
public class TokenUtil {
	
	protected static Logger log = LoggerFactory.getLogger(TokenUtil.class);
	
	private static TokenModel model=new TokenModel();
	
	/**
	 * 获取企业token。
	 * <pre>
	 * 1.如果没有初始化则直接从数据库中获取。
	 * 2.如果已经初始化。
	 * 	1.如果已经过期则重新获取。
	 * 	2.从缓存中获取。
	 * </pre>
	 * @return
	 */
	public static synchronized String getWxToken() {
		//没有初始化直接获取。
		if(!model.isInit()){
			String token=getToken();
			return token;
		}
		else{
			//如果token要过期则重新获取。
			if(model.isExpire(model.getLastUpdTime(),model.getExprieIn())){
				String token=getToken();
				return token;
			}
			else{
				//从缓存中获取。
				return model.getToken();
			}
		}
	}
	
	/**
	 * 获取应用token。
	 * <pre>
	 * 1.如果没有初始化则直接从数据库中获取。
	 * 2.如果已经初始化。
	 * 	1.如果已经过期则重新获取。
	 * 	2.从缓存中获取。
	 * </pre>
	 * @return
	 */
	public static synchronized String getAgentToken() {
		//没有初始化直接获取。
		if(!model.isAgentInit()){
			String token=getToKen();
			return token;
		}
		else{
			//如果token要过期则重新获取。
			if(model.isExpire(model.getAgentTokenlastUpdTime(),model.getAgentexprieIn())){
				String token=getToKen();
				return token;
			}
			else{
				//从缓存中获取。
				return model.getAgentToken();
			}
		}
	}
	
	
	/**
	 * 获取token。
	 * @return
	 */
	public static String getToken() {
		String url=WeixinConsts.getWxToken();
		String rtn=HttpUtil.sendHttpsRequest(url, "", WeixinConsts.METHOD_GET);
		JSONObject jsonObj = JSONObject.fromObject(rtn);
		System.out.println(rtn);
		//取到了
		if((Integer)jsonObj.get("errcode")==0){
			String token=jsonObj.getString("access_token");
			int expireIn=jsonObj.getInt("expires_in");
			model.setCorpToken(token, expireIn);
			return token;
		}
		//获取失败
		else{
			String errMsg=jsonObj.getString("errmsg");
			log.error(errMsg);
			return "-1";
		}
	}

	/**
	 * 获取应用的凭证access_token
	 * @return
	 */
	public static  String getToKen() {
		String url = WeixinConsts.getToKen();
		String rtn=HttpUtil.sendHttpsRequest(url, "", WeixinConsts.METHOD_GET);
		JSONObject jsonObj = JSONObject.fromObject(rtn);
		//取到了
		if((Integer)jsonObj.get("errcode")==0){
			String token=jsonObj.getString("access_token");
			int expireIn=jsonObj.getInt("expires_in");
			model.setAgentToken(token, expireIn);
			return token;
		}
		//获取失败
		else{
			String errMsg=jsonObj.getString("errmsg");
			log.error(errMsg);
			return "-1";
		}
	}
	
}
