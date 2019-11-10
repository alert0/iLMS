package com.hanthink.mon.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.mon.dao.MonProjDelayDao;
import com.hanthink.mon.model.MonProjDelayModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class MonProjDelayDaoImpl extends MyBatisDaoImpl<String, MonProjDelayModel>
						implements MonProjDelayDao{

	@Override
	public String getNamespace() {
		return MonProjDelayModel.class.getName();
	}

	@Override
	public List<MonProjDelayModel> queryProjDealyForPage(MonProjDelayModel model, Page page) throws Exception {
		return this.getByKey("queryProjDealyForPage", model, page);
	}

}
