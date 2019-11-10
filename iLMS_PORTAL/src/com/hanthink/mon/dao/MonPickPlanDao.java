package com.hanthink.mon.dao;


import com.hanthink.mon.model.MonPickPlanModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface MonPickPlanDao extends Dao<String, MonPickPlanModel>{

	PageList<MonPickPlanModel> queryReceiptPage(MonPickPlanModel model, DefaultPage p);

	PageList<MonPickPlanModel> queryReceiptDetailPage(MonPickPlanModel model, DefaultPage p);

	PageList<MonPickPlanModel> queryOutPage(MonPickPlanModel model, DefaultPage p);

	PageList<MonPickPlanModel> queryOutDetailPage(MonPickPlanModel model, DefaultPage p);


}
