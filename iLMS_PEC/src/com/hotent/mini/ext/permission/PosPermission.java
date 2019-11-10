package com.hotent.mini.ext.permission;

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
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.sys.api.permission.AbstarctPermission;
import com.hotent.sys.util.ContextUtil;

public class PosPermission extends AbstarctPermission {
	
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
		List<IGroup> posList = defaultUserGroupService.getGroupsByGroupTypeUserId(getType(), user.getUserId());
		Set<String> set=new HashSet<String>();
		String currentGroupId = ContextUtil.getCurrentGroupId();
		if (StringUtil.isEmpty(currentGroupId)) return null;
		if (StringUtil.isNotEmpty(currentGroupId)) {
			for (IGroup position : posList) {
				OrgRel orgRel = (OrgRel) position;
				if (currentGroupId.equals(orgRel.getOrgId())) {
					set.add(position.getGroupId());
				}
			}
		}
		if (BeanUtils.isEmpty(set)) return null;
		return set;
	}
}
