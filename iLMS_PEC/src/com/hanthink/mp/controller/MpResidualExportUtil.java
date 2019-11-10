package com.hanthink.mp.controller;


import com.hanthink.mp.manager.MpResidualManager;
import com.hanthink.mp.model.MpResidualModel;
import com.hanthink.util.excel.ExcelExportUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public class MpResidualExportUtil extends ExcelExportUtil {
	
	private MpResidualManager mpResidualManager;
	private MpResidualModel mpResidualModel;
	
	public MpResidualExportUtil(MpResidualManager mpResidualManager, MpResidualModel model){
		this.mpResidualManager = mpResidualManager;
		this.mpResidualModel = model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<Object> queryDataFromDB(int pageNum) {
		DefaultPage page = new DefaultPage();
		page.setPage(pageNum);
		page.setLimit(pageSize);
		return (PageList)mpResidualManager.queryMpResidualForPage(mpResidualModel,page);
	}

	@Override
	public void showChange(Object vo) {
		

	}

}
