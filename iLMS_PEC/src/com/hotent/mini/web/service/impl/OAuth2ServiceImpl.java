package com.hotent.mini.web.service.impl;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.Assert;

import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.mini.web.json.AccessToken;
import com.hotent.mini.web.json.CheckTokenResult;
import com.hotent.mini.web.service.OAuth2Service;

/**
 * OAuth2服务实现类
 * @author heyifan
 */
public class OAuth2ServiceImpl implements OAuth2Service,InitializingBean{
	private String oauth2BaseUrl = ""; 		/*内网地址*/
	private String oauth2Internet = "";		/*外网地址*/
	private String clientId = "";
	private String clientSecret = "";
	private String redirectUri = "";
	//是否演示模式
	private boolean isDemoMode = false;
	
	public void setIsDemoMode(boolean isDemoMode){
		this.isDemoMode = isDemoMode;
	}
	
	/**
	 * 是否演示模式
	 * @return
	 */
	public boolean isDemoMode(){
		return this.isDemoMode;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(this.oauth2BaseUrl, "loginUrl cannot be empty.");
		Assert.hasLength(this.oauth2Internet, "oauth2Internet cannot be empty.");
		Assert.hasLength(this.clientId, "clientId cannot be empty.");
		Assert.hasLength(this.clientSecret, "clientSecret cannot be empty.");
	}
	
	public void setOauth2BaseUrl(String oauth2BaseUrl) {  
        this.oauth2BaseUrl = oauth2BaseUrl;  
    }
	
	public void setOauth2Internet(String oauth2Internet) {
		this.oauth2Internet = oauth2Internet;
	}

	public void setClientId(String clientId) {  
        this.clientId = clientId;  
    }
	
	public void setClientSecret(String clientSecret) {  
        this.clientSecret = clientSecret;  
    }
	
	@Override
	public String constructServiceUrl(String url) {
		this.redirectUri = url;
		String authorizeUrl = this.oauth2Internet + "/oauth/authorize?response_type=code&client_id=" + 
							  this.clientId + "&client_secret=" + this.clientSecret;
		authorizeUrl += "&redirect_uri=" + url;
		return authorizeUrl;
	}

	@Override
	public AccessToken getTokenByCode(String code) throws Exception {
		String url = this.oauth2BaseUrl + "/oauth/token?grant_type=authorization_code&client_id=" + 
					 this.clientId + "&client_secret=" + this.clientSecret + "&code=" + code;
		if(StringUtil.isEmpty(this.redirectUri)){
			throw new RuntimeException("redirectUri cannot be empty.");
		}
		url += "&redirect_uri=" + this.redirectUri;
		HttpPost httpPost = new HttpPost(url);
		String basicStr = this.clientId + ":" + this.clientSecret;
		httpPost.addHeader("Authorization", "Basic " + new String(Base64.encode(basicStr.getBytes())));
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(httpPost);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {
			String result= EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			AccessToken token = GsonUtil.toBean(result, AccessToken.class);
			return token;
		}
		else{
			throw new RuntimeException("Fail to get AccessToken with code.");
		}
	}

	@Override
	public CheckTokenResult checkToken(AccessToken token) throws Exception {
		String url = this.oauth2BaseUrl + "/oauth/check_token?token=" + token.getValue();
		HttpPost httpPost = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(httpPost);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {
			String result= EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			CheckTokenResult checkTokenResult = GsonUtil.toBean(result, CheckTokenResult.class);
			return checkTokenResult;
		}
		else{
			throw new RuntimeException("Fail to Check token.");
		}
	}

	@Override
	public String logout() throws Exception {
		String url = this.oauth2BaseUrl + "/logout";
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {
			String result= EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			return result;
		}
		return null;
	}
}
