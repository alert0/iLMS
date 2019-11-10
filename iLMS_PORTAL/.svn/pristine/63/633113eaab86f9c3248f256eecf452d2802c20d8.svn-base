package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysCategoryDao;
import com.hotent.sys.persistence.manager.SysCategoryManager;
import com.hotent.sys.persistence.model.SysCategory;

@Service("sysCategoryManager")
public class SysCategoryManagerImpl extends AbstractManagerImpl<String, SysCategory> implements SysCategoryManager{
	@Resource
	SysCategoryDao sysCategoryDao;
	@Override
	protected Dao<String, SysCategory> getDao() {
		return sysCategoryDao;
	}
	
	@Override
	public Boolean isKeyExist(String id, String groupKey) {
		return sysCategoryDao.isKeyExist(id,groupKey);
	}

	@Override
	public SysCategory getByTypeKey(String typeKey) {
		return sysCategoryDao.getByKey(typeKey);
	}
}
