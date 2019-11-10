package com.hotent.sys.service.impl.permission;
import java.util.HashSet;
import java.util.Set;

import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.permission.AbstarctPermission;
import com.hotent.sys.util.ContextUtil;

public class UsersPermission extends AbstarctPermission  {

	@Override
	public String getTitle() {
		
		return "用户";
	}

	@Override
	public String getType() {
		return "user";
	}

	

	@Override
	public Set<String> getCurrentProfile() {
		IUser user=ContextUtil.getCurrentUser();
		Set<String> userSet=new HashSet<String>();
		userSet.add(user.getUserId());
		return userSet;
	}

	

}
