package com.hanthink.mon.manager.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonPickPlanDao;
import com.hanthink.mon.manager.MonPickPlanManager;
import com.hanthink.mon.model.MonPickPlanModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Service("MonReceiptManager")
public class MonPickPlanManagerImpl extends AbstractManagerImpl<String, MonPickPlanModel>
implements MonPickPlanManager{

	@Resource
	private MonPickPlanDao dao;
	@Override
	protected Dao<String, MonPickPlanModel> getDao() {
		
		return dao;
	}
	
	@Override
	public PageList<MonPickPlanModel> queryReceiptPage(MonPickPlanModel model, DefaultPage p) {
		return dao.queryReceiptPage(model,p);
	}
	@Override
	public PageList<MonPickPlanModel> queryReceiptDetailPage(MonPickPlanModel model, DefaultPage p) {
		return dao.queryReceiptDetailPage(model,p);
	}

	@Override
	public PageList<MonPickPlanModel> queryOutPage(MonPickPlanModel model, DefaultPage p) {
		return dao.queryOutPage(model,p);
	}

	@Override
	public PageList<MonPickPlanModel> queryOutDetailPage(MonPickPlanModel model, DefaultPage p) {
		return dao.queryOutDetailPage(model,p);
	}
	
	

	

	


}
