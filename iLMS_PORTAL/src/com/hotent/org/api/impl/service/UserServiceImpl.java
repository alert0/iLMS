package com.hotent.org.api.impl.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.dao.UserDao;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.UserManager;
 
 

@SuppressWarnings("unchecked")
@Service(value="userServiceImpl")
public class UserServiceImpl implements IUserService{
	@Resource
	UserManager userManager;
	@Resource
	OrgManager orgManager;
	@Resource
	UserDao userDao;
	@Override
	public IUser getUserById(String userId)
	{
		return userManager.get(userId);
	}
	
	@Override
	public IUser getUserByAccount(String account)
	{
		return userManager.getByAccount(account);
	}
	
	@Override
	public List<IUser> getUserListByGroup(String groupType, String groupId)
	{
		//此处可以根据不同的groupType去调用真实的实现：如角色下的人，组织下的人
		if(groupType.equals(GroupTypeConstant.ORG.key())){
		   return (List)userManager.getUserListByOrgId(groupId);
		}
		if(groupType.equals(GroupTypeConstant.ROLE.key())){
		   return (List)userManager.getUserListByRoleId(groupId);
		}
		if(groupType.equals(GroupTypeConstant.POSITION.key())){
		   return (List)userManager.getListByRelId(groupId);
		}
		if(groupType.equals(GroupTypeConstant.JOB.key())){
		   return (List)userManager.getListByJobId(groupId);
		}
		return null;
	}

	@Override
	public IUser getLoginUser(String account)
	{
		return userManager.getLoginUser(account);
	}

	@Override
	public List<IUser> getUserByIds(String[] userIds)
	{
		 return (List)userDao.getUserByIds(userIds);
	}

	@Override
	public Integer querySupplierAccountStatus(String account) {
		return userDao.querySupplierAccountStatus(account);
	}	
	
}