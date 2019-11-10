package com.hotent.mini.web.json;


/**
 * 认证结果实体
 * @author heyifan
 */
public class CheckTokenResult {
	private String user_name;
	private String[] scope;
	private String[] authorities;
	private String client_id;
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String[] getScope() {
		return scope;
	}
	public void setScope(String[] scope) {
		this.scope = scope;
	}
	public String[] getAuthorities() {
		return authorities;
	}
	public void setAuthorities(String[] authorities) {
		this.authorities = authorities;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	
}
