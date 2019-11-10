package com.hotent.sys.service.impl.permission;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.sys.api.permission.IPermission;
import com.hotent.sys.util.ContextUtil;

public class PosPermission extends GroupPermission implements IPermission {

	@Resource
	IUserGroupService defaultUserGroupService;
	
	@Override
	public String getTitle() {
		return GroupTypeConstant.POSITION.label();
	}
	@Override
	public String getType() {
		return "pos";
	}
	
	@Override
	public Set<String> getCurrentProfile() {
		IUser user=ContextUtil.getCurrentUser();
		List<IGroup> posList = defaultUserGroupService.getGroupsByGroupTypeUserId(GroupTypeConstant.POSITION.key(), user.getUserId());
		Set<String> set=new HashSet<String>();
		String currentGroupId = ContextUtil.getCurrentGroupId();
		if (StringUtil.isEmpty(currentGroupId)) return null;
		if (StringUtil.isNotEmpty(currentGroupId) && BeanUtils.isNotEmpty(posList) ) {
			for (IGroup position : posList) {
				// 岗位的父ID为组织ID
				if (currentGroupId.equals(position.getParentId())) {
					set.add(position.getGroupId());
				}
			}
		}
		if (BeanUtils.isEmpty(set)) return null;
		return set;
	}
}
