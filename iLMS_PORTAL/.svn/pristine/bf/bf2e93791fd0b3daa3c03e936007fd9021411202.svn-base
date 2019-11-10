package com.hotent.org.api.impl.service;

import javax.annotation.Resource;

import com.hotent.org.api.service.IOrgService;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.org.api.service.IUserService;

public class DefaultOrgService implements IOrgService {
	
	@Resource
	IUserGroupService userGroupService;
	
	@Resource
	IUserService userService;

	@Override
	public IUserGroupService getUserGroupService() {
		return userGroupService;
	}

	@Override
	public IUserService getUserService() {
		return userService;
	}

}
