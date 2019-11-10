package com.hanthink.mp.controller;


import com.hanthink.mp.manager.MpAdjPlanManager;
import com.hanthink.mp.model.MpAdjPlanModel;
import com.hanthink.util.excel.ExcelExportUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public class MpAdjPlanExportUtil extends ExcelExportUtil {
	
	private MpAdjPlanManager mpAdjPlanManager;
	private MpAdjPlanModel mpAdjPlanModel;
	
	public MpAdjPlanExportUtil(MpAdjPlanManager MpAdjPlanManager, MpAdjPlanModel model){
		this.mpAdjPlanManager = MpAdjPlanManager;
		this.mpAdjPlanModel = model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<Object> queryDataFromDB(int pageNum) {
		DefaultPage page = new DefaultPage();
		page.setPage(pageNum);
		page.setLimit(pageSize);
		return (PageList)mpAdjPlanManager.queryMpAdjPlanForPage(mpAdjPlanModel, page);
	}

	@Override
	public void showChange(Object vo) {
		

	}

}
