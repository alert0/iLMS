package com.hanthink.mp.controller;


import com.hanthink.mp.manager.MpAdjOrderDiffManager;
import com.hanthink.mp.model.MpAdjOrderDiffModel;
import com.hanthink.util.excel.ExcelExportUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public class MpAdjOrderDiffExportUtil extends ExcelExportUtil {
	
	private MpAdjOrderDiffManager mpAdjOrderDiffManager;
	private MpAdjOrderDiffModel mpAdjOrderDiffModel;
	
	public MpAdjOrderDiffExportUtil(MpAdjOrderDiffManager mpAdjOrderDiffManager, MpAdjOrderDiffModel model){
		this.mpAdjOrderDiffManager = mpAdjOrderDiffManager;
		this.mpAdjOrderDiffModel = model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<Object> queryDataFromDB(int pageNum) {
		DefaultPage page = new DefaultPage();
		page.setPage(pageNum);
		page.setLimit(pageSize);
		return (PageList)mpAdjOrderDiffManager.queryMpAdjOrderDiffForPage(mpAdjOrderDiffModel, page);
	}

	@Override
	public void showChange(Object vo) {
		

	}

}
