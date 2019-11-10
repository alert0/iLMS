package com.hanthink.mon.manager;


import com.hanthink.mon.model.MonPickPlanModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface MonPickPlanManager extends Manager<String, MonPickPlanModel>{

	PageList<MonPickPlanModel> queryReceiptPage(MonPickPlanModel model, DefaultPage p);


	PageList<MonPickPlanModel> queryReceiptDetailPage(MonPickPlanModel model, DefaultPage p);


	PageList<MonPickPlanModel> queryOutPage(MonPickPlanModel model, DefaultPage p);


	PageList<MonPickPlanModel> queryOutDetailPage(MonPickPlanModel model, DefaultPage p);

}
