package com.hotent.bpmx.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskCommuDao;
import com.hotent.bpmx.persistence.model.BpmTaskCommu;
import com.hotent.bpmx.persistence.manager.BpmTaskCommuManager;

@Service("bpmTaskCommuManager")
public class BpmTaskCommuManagerImpl extends AbstractManagerImpl<String, BpmTaskCommu> implements BpmTaskCommuManager{
	@Resource
	BpmTaskCommuDao bpmTaskCommuDao;
	@Override
	protected Dao<String, BpmTaskCommu> getDao() {
		return bpmTaskCommuDao;
	}
	
	
	@Override
	public BpmTaskCommu getByTaskId(String taskId) {
		BpmTaskCommu commu=bpmTaskCommuDao.getByTaskId(taskId);
		return commu;
	}
}
