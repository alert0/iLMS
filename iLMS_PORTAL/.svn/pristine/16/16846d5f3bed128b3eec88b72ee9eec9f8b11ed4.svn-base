package com.hotent.mini.ext.permission;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.sys.api.permission.AbstarctPermission;
import com.hotent.sys.util.ContextUtil;

public class RolePermission  extends AbstarctPermission {

	@Resource
	IUserGroupService defaultUserGroupService;
	
	@Override
	public String getTitle() {
		return GroupTypeConstant.ROLE.label();
	}
	@Override
	public String getType() {
		return GroupTypeConstant.ROLE.key();
	}
	
	@Override
	public Set<String> getCurrentProfile() {
		IUser user=ContextUtil.getCurrentUser();
		List<IGroup> roles = defaultUserGroupService.getGroupsByGroupTypeUserId(getType(), user.getUserId());
		if (BeanUtils.isEmpty(roles)) return null;
		Set<String> set=new HashSet<String>();
		for (IGroup role : roles) {
			set.add(role.getGroupId());
		}
		return set;
	}
}
