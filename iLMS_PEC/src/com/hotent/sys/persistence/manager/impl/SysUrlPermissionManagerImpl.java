package com.hotent.sys.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysUrlPermissionDao;
import com.hotent.sys.persistence.manager.SysUrlPermissionManager;
import com.hotent.sys.persistence.model.SysUrlPermission;
 

@Service("sysUrlPermissionManager")
public class SysUrlPermissionManagerImpl extends AbstractManagerImpl<String, SysUrlPermission> implements SysUrlPermissionManager{
	@Resource
	SysUrlPermissionDao sysUrlPermissionDao;
	@Override
	protected Dao<String, SysUrlPermission> getDao() {
		return sysUrlPermissionDao;
	}
	@Override
	public List<SysUrlPermission> getAllByEnable(Short enable) {
		List<SysUrlPermission> sysUrlPermissions = sysUrlPermissionDao.getAllByEnable(enable);
		return sysUrlPermissions;
	}
}
