package com.hotent.mini.web.json;

import java.util.Date;

/**
 * OAuth2中的AccessToken对象
 * @author heyifan
 */
public class AccessToken {
	private Date expiration;
	private Boolean expired;
	private Integer expiresIn;
	private RefreshToken refreshToken;
	private String[] scope;
	private String tokenType;
	private String value;
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public Boolean getExpired() {
		return expired;
	}
	public void setExpired(Boolean expired) {
		this.expired = expired;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public RefreshToken getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String[] getScope() {
		return scope;
	}
	public void setScope(String[] scope) {
		this.scope = scope;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
