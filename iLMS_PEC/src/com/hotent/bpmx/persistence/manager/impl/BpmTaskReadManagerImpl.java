package com.hotent.bpmx.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskReadDao;
import com.hotent.bpmx.persistence.model.BpmTaskRead;
import com.hotent.bpmx.persistence.manager.BpmTaskReadManager;

@Service("bpmTaskReadManager")
public class BpmTaskReadManagerImpl extends AbstractManagerImpl<String, BpmTaskRead> implements BpmTaskReadManager{
	@Resource
	BpmTaskReadDao bpmTaskReadDao;
	@Override
	protected Dao<String, BpmTaskRead> getDao() {
		return bpmTaskReadDao;
	}
	@Override
	public void delByInstList(List<String> instList) {
		bpmTaskReadDao.delByInstList(instList);
	}
}
