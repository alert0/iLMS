package com.hanthink.mp.controller;


import com.hanthink.mp.manager.MpVehPlanManager;
import com.hanthink.mp.model.MpVehPlanModel;
import com.hanthink.util.excel.ExcelExportUtil;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public class MpVehPlanExportUtil extends ExcelExportUtil {
	
	private MpVehPlanManager mpVehPlanManager;
	private MpVehPlanModel mpVehPlanModel;
	
	public MpVehPlanExportUtil(MpVehPlanManager MpVehPlanManager, MpVehPlanModel model){
		this.mpVehPlanManager = MpVehPlanManager;
		this.mpVehPlanModel = model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<Object> queryDataFromDB(int pageNum) {
		DefaultPage page = new DefaultPage();
		page.setPage(pageNum);
		page.setLimit(pageSize);
		return (PageList)mpVehPlanManager.queryMpVehPlanForPage(mpVehPlanModel, page);
	}

	@Override
	public void showChange(Object vo) {
		

	}

}
