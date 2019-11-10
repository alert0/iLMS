package com.hotent.mini.web.json;

import java.util.Date;

/**
 * OAuth2中的RefreshToken对象
 * @author heyifan
 */
public class RefreshToken {
	private Date expiration;
	private String value;
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
