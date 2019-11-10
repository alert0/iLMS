package com.hanthink.mon.dao.impl;

import org.springframework.stereotype.Repository;

import com.hanthink.mon.dao.MonPickPlanDao;
import com.hanthink.mon.model.MonPickPlanModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class MonPickPlanDaoImpl extends MyBatisDaoImpl<String, MonPickPlanModel>
		implements MonPickPlanDao{

	@Override
	public String getNamespace() {
		return MonPickPlanModel.class.getName();
	}

}
