package com.hotent.org.api.impl.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.org.api.service.IParamService;
import com.hotent.org.persistence.manager.SysOrgParamsManager;
import com.hotent.org.persistence.manager.SysUserParamsManager;
import com.hotent.org.persistence.model.SysOrgParams;
import com.hotent.org.persistence.model.SysUserParams;


@Service(value="paramServiceImpl")
public class ParamServiceImpl  implements IParamService{
	
	@Resource
	private SysUserParamsManager sysUserParamsManager;
	
	@Resource 
	private SysOrgParamsManager sysOrgParamsManager;

	@Override
	public Object getParamsByKey(String userId, String key) {
		SysUserParams sysUserParams = sysUserParamsManager.getByUserIdAndAlias(userId, key);
		return sysUserParams!=null?sysUserParams.getValue():null;
	}

	@Override
	public Object getParamByGroup(String groupId,String key) {
		SysOrgParams sysOrgParams = sysOrgParamsManager.getByOrgIdAndAlias(groupId,key);
		return sysOrgParams!=null?sysOrgParams.getValue():null;
	}


}
