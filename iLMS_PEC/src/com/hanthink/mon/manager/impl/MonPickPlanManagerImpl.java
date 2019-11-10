package com.hanthink.mon.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonPickPlanDao;
import com.hanthink.mon.manager.MonPickPlanManager;
import com.hanthink.mon.model.MonPickPlanModel;
import com.hotent.base.db.api.Dao;

@Service("MonPickPlanManager")
public class MonPickPlanManagerImpl extends AbstractManagerImpl<String, MonPickPlanModel>
				implements MonPickPlanManager{

	@Resource
	private MonPickPlanDao dao;
	@Override
	protected Dao<String, MonPickPlanModel> getDao() {
		return dao;
	}

}
