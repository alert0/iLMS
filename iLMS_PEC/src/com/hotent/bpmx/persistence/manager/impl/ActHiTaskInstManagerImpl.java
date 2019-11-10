package com.hotent.bpmx.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.ActHiTaskInstDao;
import com.hotent.bpmx.persistence.model.ActHiTaskInst;
import com.hotent.bpmx.persistence.manager.ActHiTaskInstManager;

@Service("actHiTaskInstManager")
public class ActHiTaskInstManagerImpl extends AbstractManagerImpl<String, ActHiTaskInst> implements ActHiTaskInstManager{
	@Resource
	ActHiTaskInstDao actHiTaskInstDao;
	@Override
	protected Dao<String, ActHiTaskInst> getDao() {
		return actHiTaskInstDao;
	}
}
