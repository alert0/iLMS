package com.hotent.bpmx.core.helper.identity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.hotent.bpmx.api.helper.identity.BpmIdentityConverter;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.model.IdentityType;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.org.api.service.IUserService;

public class DefaultBpmIdentityConverter implements BpmIdentityConverter{
	

	@Resource
	IUserService userServiceImpl;
	@Resource
	IUserGroupService userGroupService;

	@Override
	public BpmIdentity convertUser(IUser user) {
		if(user == null) return null;
		
		DefaultBpmIdentity bpmIdentity = new DefaultBpmIdentity();
		bpmIdentity.setId(user.getUserId());
		bpmIdentity.setName(user.getFullname());
		bpmIdentity.setType(BpmIdentity.TYPE_USER);
		return bpmIdentity;		
	}


	@Override
	public List<BpmIdentity> convertUserList(List<IUser> userList) {
		List<BpmIdentity> bpmIdentities = new ArrayList<BpmIdentity>();

		for(IUser user:userList){
			if(user==null) continue;
			BpmIdentity bpmIdentity = convertUser(user); 
			bpmIdentities.add(bpmIdentity);
		}
		return bpmIdentities;
	}

	@Override
	public BpmIdentity convertGroup(IGroup group) {
		if(group == null) return null;
		
		BpmIdentity bpmIdentity = new DefaultBpmIdentity();
		bpmIdentity.setId(group.getGroupId());
		bpmIdentity.setName(group.getName());
		bpmIdentity.setType(BpmIdentity.TYPE_GROUP);
		bpmIdentity.setGroupType(group.getGroupType());
		return bpmIdentity;
	}

	@Override
	public List<BpmIdentity> convertGroupList(List<IGroup> groupList) {
		List<BpmIdentity> bpmIdentities = new ArrayList<BpmIdentity>();
		for(IGroup group:groupList){
			if(group ==null) continue;
			BpmIdentity bpmIdentity = convertGroup(group);
			bpmIdentities.add(bpmIdentity);
		}
		return bpmIdentities;
	}


	@Override
	public BpmIdentity convert(String type, String id) {
		BpmIdentity bpmIdentity = new DefaultBpmIdentity();
		bpmIdentity.setId(id);
		bpmIdentity.setType(type);
		return bpmIdentity;
	}


	@Override
	public BpmIdentity convertValue(String type, String id) {
		BpmIdentity bpmIdentity = new DefaultBpmIdentity();
		if(BpmIdentity.TYPE_USER.equalsIgnoreCase(type)){
			IUser user = userServiceImpl.getUserById(id);
			bpmIdentity.setType(type);
			if(user != null)
				bpmIdentity.setName(user.getFullname());
		}else{
			IGroup group= userGroupService.getById(type, id);
			bpmIdentity.setType(IdentityType.GROUP);
			bpmIdentity.setGroupType(group.getGroupType());
			bpmIdentity.setName(group.getName());
		}
		bpmIdentity.setId(id);
		return bpmIdentity;
	}
}
