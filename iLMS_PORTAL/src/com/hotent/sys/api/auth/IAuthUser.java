package com.hotent.sys.api.auth;

import com.hotent.org.api.model.IUser;

public interface IAuthUser {
	
	void resetTimeout();
	
	boolean isTimeOut();
	
	IUser getUser();
	
	String getTokenValue();

}
