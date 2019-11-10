package com.hotent.mobile.weixin.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.hotent.mobile.weixin.util.TokenUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * 微信使用到的常量。
 * 
 * @author ray
 *
 */
public class WeixinConsts {

	public final static String METHOD_GET = "GET";

	public final static String METHOD_POST = "POST";

	private final static String QIYE_URL = "https://qyapi.weixin.qq.com/cgi-bin";

	private final static String WX_URL = "https://api.weixin.qq.com/sns/oauth2";

	private final static String WX_UserInfoURL = "https://api.weixin.qq.com";

	/**
	 * 获取微信地址。
	 * 
	 * @param corpId
	 * @param secret
	 * @return
	 */
	public static String getWxToken() {
		String corpId = SysPropertyUtil.getByAlias("corpid");
		String secret = SysPropertyUtil.getByAlias("corpsecret");
		return QIYE_URL + "/gettoken?corpid=" + corpId + "&corpsecret=" + secret;
	}

	/**
	 * 获取应用的凭证
	 * 
	 * @return
	 */
	public static String getToKen() {
		String corpId = SysPropertyUtil.getByAlias("corpid");
		String secret = SysPropertyUtil.getByAlias("agentSecret");
		return QIYE_URL + "/gettoken?corpid=" + corpId + "&corpsecret=" + secret;
	}

	/**
	 * 获取微信验证地址。
	 * 
	 * @param appId
	 * @param redirectUrl
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getWxAuthorize(String redirectUrl) throws UnsupportedEncodingException {
		String corpId = SysPropertyUtil.getByAlias("corpid");
		String agentid = SysPropertyUtil.getByAlias("agentid");
		String redirect = URLEncoder.encode(redirectUrl, "utf-8");
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + corpId + "&redirect_uri=" + redirect
				+ "&agentid=" + agentid + "&response_type=code&scope=snsapi_base#wechat_redirect";
		return url;
	}
	
	
	/**
	 * 获取微信redirectUrl。
	 * 
	 * @param appId
	 * @param redirectUrl
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getWxRedirectUrl() {
		String cobackUrl="www.hotent.org:18086/x5eip/main/home";
		String RedirectUrl=null;
		try {
			RedirectUrl = URLEncoder.encode(cobackUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return RedirectUrl;
	}
	
	
	
	
	//
	/**
	 * 通过code appid secret 获取access_token
	 * 
	 * @param code appid secret
	 * @return
	 */

	public static String getWxAccessToken(String code, String appid, String secret) {

		String url = WX_URL + "/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code
				+ "&grant_type=authorization_code";
		return url;
	}

	/**
	 * 防止token超时refresh刷新 access_token
	 * 
	 * @param appid refreshToken
	 * @return
	 */

	public static String refreshToken(String appid, String refreshToken) {

		String url = WX_URL + "/refresh_token?appid=" + appid + "&grant_type=refresh_token" + "&refresh_token="
				+ refreshToken;
		return url;
	}

	/**
	 * 通过access_token  openid获取用户基本信息
	 * @param access_token openid
	 *            
	 * @return
	 */
	public static String getWxUserInfo(String access_token, String openid) {

		String url = WX_UserInfoURL + "/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
		return url;
	}

	public static String getQyWxUserInfo(String code) {
		String accessToken = TokenUtil.getAgentToken();
		String url = QIYE_URL + "/user/getuserinfo?access_token=" + accessToken + "&code=" + code;
		return url;
	}

	public static String getCreateUserUrl() {
		String url = QIYE_URL + "/user/create?access_token=" + TokenUtil.getWxToken();
		return url;
	}

	public static String getSendMsgUrl() {
		String url = QIYE_URL + "/message/send?access_token=" + TokenUtil.getAgentToken();
		return url;
	}

	public static String getDeleteUserUrl() {
		String url = QIYE_URL + "/user/delete?access_token=" + TokenUtil.getWxToken() + "&userid=";
		return url;
	}

	public static String getDeleteAllUserUrl() {
		String url = QIYE_URL + "/user/batchdelete?access_token=" + TokenUtil.getWxToken();
		return url;
	}

	public static String getUpdateUserUrl() {
		String url = QIYE_URL + "/user/update?access_token=" + TokenUtil.getWxToken();
		return url;
	}

	public static String getCreateOrgUrl() {
		String url = QIYE_URL + "/department/create?access_token=" + TokenUtil.getWxToken();
		return url;
	}

	public static String getUpdateOrgUrl() {
		String url = QIYE_URL + "/department/update?access_token=" + TokenUtil.getWxToken();
		return url;
	}

	public static String getDeleteOrgUrl() {
		String url = QIYE_URL + "/department/delete?access_token=" + TokenUtil.getWxToken() + "&id=";
		return url;
	}

	public static String getInviteUserUrl() {
		String url = QIYE_URL + "/invite/send?access_token=" + TokenUtil.getWxToken();
		return url;
	}

	/**
	 * 根据用户帐号获取企业微信用户信息地址
	 * 
	 * @return
	 */
	public static String getUserUrl() {
		String url = QIYE_URL + "/user/get?access_token=" + TokenUtil.getWxToken() + "&userid=";
		return url;
	}

	/**
	 * 获取部门成员
	 * 
	 * @return
	 */
	public static String getDepartmentUrl(String department_id) {
		String url = QIYE_URL + "/user/simplelist?access_token=" + TokenUtil.getWxToken() + "&department_id="
				+ department_id;
		return url;
	}

}
