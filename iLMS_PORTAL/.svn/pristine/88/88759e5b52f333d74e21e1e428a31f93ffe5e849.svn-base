package com.hotent.sys.service.impl.permission;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.sys.api.permission.AbstarctPermission;
import com.hotent.sys.util.ContextUtil;

public class GroupPermission extends AbstarctPermission {
	
	@Resource
	IUserGroupService defaultUserGroupService;
	@Override
	public String getTitle() {
		
		return GroupTypeConstant.ORG.name();
	}

	@Override
	public String getType() {
		return GroupTypeConstant.ORG.key();
	}

	

	@Override
	public Set<String> getCurrentProfile() {
		IUser user=ContextUtil.getCurrentUser();
		Map<String, List<IGroup>> groups = defaultUserGroupService.getAllGroupByUserId(user.getUserId());
		if(BeanUtils.isEmpty(groups)) return null;
		Collection<List<IGroup>> list=groups.values();
		Set<String> set=new HashSet<String>();
		for(List<IGroup> listGroup : list){
			List<IGroup> temps=listGroup;
			for(IGroup temp:temps){
				set.add(temp.getGroupId());
			}
		}
		return set;
	}
	


}
