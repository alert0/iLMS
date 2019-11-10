package com.hotent.sys.service.impl.permission;

import java.util.HashSet;
import java.util.Set;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.sys.api.permission.IPermission;
import com.hotent.sys.util.ContextUtil;

public class OrgPermission  extends GroupPermission  implements IPermission {

	@Override
	public String getTitle() {
		
		return GroupTypeConstant.ORG.label();
	}


	@Override
	public String getType() {
		return GroupTypeConstant.ORG.key();
	}
	
	@Override
	public Set<String> getCurrentProfile() {
		// 只取登录用户的当前部门ID
		String currentGroupId = ContextUtil.getCurrentGroupId();
		if (StringUtil.isEmpty(currentGroupId)) return null;
		Set<String> userSet=new HashSet<String>();
		userSet.add(currentGroupId);
		return userSet;
	}

}
