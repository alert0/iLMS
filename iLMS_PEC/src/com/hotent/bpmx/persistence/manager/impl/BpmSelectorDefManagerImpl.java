package com.hotent.bpmx.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmSelectorDefDao;
import com.hotent.bpmx.persistence.manager.BpmSelectorDefManager;
import com.hotent.bpmx.persistence.model.BpmSelectorDef;

@Service("bpmSelectorDefManager")
public class BpmSelectorDefManagerImpl extends
		AbstractManagerImpl<String, BpmSelectorDef> implements
		BpmSelectorDefManager {
	@Resource
	BpmSelectorDefDao bpmSelectorDefDao;

	@Override
	protected Dao<String, BpmSelectorDef> getDao() {
		return bpmSelectorDefDao;
	}


	@Override
	public boolean isExistAlias(String alias, String id) {
		return bpmSelectorDefDao.isExistAlias(alias,id);
	}
	@Override
	public BpmSelectorDef getByAlias(String alias) {
		return bpmSelectorDefDao.getByAlias(alias);
	}
}
