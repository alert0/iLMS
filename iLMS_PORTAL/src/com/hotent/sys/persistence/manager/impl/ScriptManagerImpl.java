package com.hotent.sys.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.ScriptDao;
import com.hotent.sys.persistence.manager.ScriptManager;
import com.hotent.sys.persistence.model.Script;
 

@Service("scriptManager")
public class ScriptManagerImpl extends AbstractManagerImpl<String, Script> implements ScriptManager{
	@Resource
	private ScriptDao scriptDao;

	@Override
	protected Dao<String, Script> getDao() {
		return scriptDao;
	}
	
	@Override
	public List<String> getDistinctCategory() {
		return scriptDao.getDistinctCategory();
	}

	@Override
	public Integer isNameExists(String name) {
		return scriptDao.isNameExists(name);
	}

}
