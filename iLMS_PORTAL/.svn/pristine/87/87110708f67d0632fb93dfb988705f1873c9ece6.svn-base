package com.hotent.sys.persistence.manager.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.ResourcesDao;
import com.hotent.sys.persistence.dao.RoleResourceDao;
import com.hotent.sys.persistence.manager.RoleResourceManager;
import com.hotent.sys.persistence.model.Resources;
import com.hotent.sys.persistence.model.RoleResource;
 

@Service("roleResourceManager")
public class RoleResourceManagerImpl extends AbstractManagerImpl<String, RoleResource> implements RoleResourceManager{
	@Resource
	RoleResourceDao roleResourceDao;
	@Resource
	ResourcesDao resourcesDao;
	@Override
	protected Dao<String, RoleResource> getDao() {
		return roleResourceDao;
	}
	
	@Override
	public List<Resources> getRoleResTreeChecked(String roleId, String systemId) {
		List<Resources> resourcesList = resourcesDao.getBySystemId(systemId);
		List<RoleResource> roleResourcesList = roleResourceDao.getByRoleSystem(roleId,systemId);
		
		Set<String> set = new HashSet<String>();
		for (RoleResource rolRes : roleResourcesList) {
			set.add(rolRes.getResId());
		}
		
		for(Resources res : resourcesList){
			if(set.contains(res.getId())){
				res.setChecked(Resources.IS_CHECKED_Y);
			}else{
				res.setChecked(Resources.IS_CHECKED_N);
			}
		}
		
		return resourcesList;
	}

	@Override
	public void updateRoleRes(String[] resIds, String roleId, String systemId) {
		roleResourceDao.deleteByRoleSystem(roleId,systemId);
		if(resIds != null && resIds.length > 0)
		for(String resId : resIds){
			RoleResource roleRes = new RoleResource(roleId, resId, systemId);
			roleResourceDao.create(roleRes);
		}
	}
}