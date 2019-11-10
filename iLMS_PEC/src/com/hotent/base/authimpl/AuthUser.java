package com.hotent.base.authimpl;

import java.io.Serializable;

import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.auth.IAuthUser;

public class AuthUser implements IAuthUser, Serializable{
	
	private static final long serialVersionUID = -5914772888541026111L;

	private Long createTime=0L;
	
	private IUser user = null;
	
	private static int OUT_TIME = 7200; //60; //s 用超时时长，超过该时长不操作则token失效 7200
	
	private String tokenValue;
	
	public AuthUser(){}
	
	public AuthUser(IUser user){
		this.createTime=System.currentTimeMillis();
		this.user=user;
	}

	public AuthUser(IUser user, String tokenValue) {
		this.createTime = System.currentTimeMillis();
		this.user = user;
		this.tokenValue = tokenValue;
	}

	@Override
	public boolean isTimeOut() {
		Long time= System.currentTimeMillis()-createTime;
		if(time < OUT_TIME * 1000){  //time/1000<3600*24
			return false;
		}
		return true;
	}

	@Override
	public IUser getUser() {
		return user;
	}



	@Override
	public void resetTimeout() {
		createTime=System.currentTimeMillis();
	}

	@Override
	public String getTokenValue() {
		return this.tokenValue;
	}

}
