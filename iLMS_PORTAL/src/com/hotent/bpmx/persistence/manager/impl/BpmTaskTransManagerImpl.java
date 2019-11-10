package com.hotent.bpmx.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskTransDao;
import com.hotent.bpmx.persistence.model.BpmTaskTrans;
import com.hotent.bpmx.persistence.manager.BpmTaskTransManager;

@Service("bpmTaskTransManager")
public class BpmTaskTransManagerImpl extends AbstractManagerImpl<String, BpmTaskTrans> implements BpmTaskTransManager{
	@Resource
	BpmTaskTransDao bpmTaskTransDao;
	@Override
	protected Dao<String, BpmTaskTrans> getDao() {
		return bpmTaskTransDao;
	}
	@Override
	public BpmTaskTrans getByTaskId(String taskId) {
		
		return bpmTaskTransDao.getByTaskId(taskId);
	}
}
