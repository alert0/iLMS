package com.hanthink.mp.controller;


import com.hanthink.mp.manager.MpTrialPlanManager;
import com.hanthink.mp.model.MpTrialPlanModel;
import com.hanthink.util.excel.ExcelExportUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public class MpTrialPlanExportUtil extends ExcelExportUtil {
	
	private MpTrialPlanManager MpTrialPlanManager;
	private MpTrialPlanModel MpTrialPlanModel;
	
	public MpTrialPlanExportUtil(MpTrialPlanManager MpTrialPlanManager, MpTrialPlanModel model){
		this.MpTrialPlanManager = MpTrialPlanManager;
		this.MpTrialPlanModel = model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<Object> queryDataFromDB(int pageNum) {
		DefaultPage page = new DefaultPage();
		page.setPage(pageNum);
		page.setLimit(pageSize);
		return (PageList)MpTrialPlanManager.queryMpTrialPlanForPage(MpTrialPlanModel,page);
	}

	@Override
	public void showChange(Object vo) {
		

	}

}
