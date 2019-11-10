package com.hanthink.mon.dao.impl;



import org.springframework.stereotype.Repository;

import com.hanthink.mon.dao.MonPickPlanDao;
import com.hanthink.mon.model.MonPickPlanModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


@Repository
public class MonPickPlanDaoImpl extends MyBatisDaoImpl<String, MonPickPlanModel>
implements MonPickPlanDao{

	@Override
	public String getNamespace() {
		
		return MonPickPlanModel.class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<MonPickPlanModel> queryReceiptPage(MonPickPlanModel model, DefaultPage p) {
		return (PageList<MonPickPlanModel>) this.getList("queryReceiptPage", model, p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<MonPickPlanModel> queryReceiptDetailPage(MonPickPlanModel model, DefaultPage p) {
		return (PageList<MonPickPlanModel>) this.getList("queryReceiptDetailPage", model, p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<MonPickPlanModel> queryOutPage(MonPickPlanModel model, DefaultPage p) {
		return (PageList<MonPickPlanModel>) this.getList("queryOutPage", model, p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<MonPickPlanModel> queryOutDetailPage(MonPickPlanModel model, DefaultPage p) {
		return (PageList<MonPickPlanModel>) this.getList("queryOutDetailPage", model, p);
	}

	

}
