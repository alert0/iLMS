package com.hanthink.mp.controller;


import com.hanthink.mp.manager.MpAdjSupFeedbackManager;
import com.hanthink.mp.model.MpAdjSupFeedbackModel;
import com.hanthink.util.excel.ExcelExportUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public class MpAdjSupFeedbackExportUtil extends ExcelExportUtil {
	
	private MpAdjSupFeedbackManager mpAdjSupFeedbackManager;
	private MpAdjSupFeedbackModel mpAdjSupFeedbackModel;
	
	public MpAdjSupFeedbackExportUtil(MpAdjSupFeedbackManager mpAdjSupFeedbackManager, MpAdjSupFeedbackModel model){
		this.mpAdjSupFeedbackManager = mpAdjSupFeedbackManager;
		this.mpAdjSupFeedbackModel = model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<Object> queryDataFromDB(int pageNum) {
		DefaultPage page = new DefaultPage();
		page.setPage(pageNum);
		page.setLimit(pageSize);
		return (PageList)mpAdjSupFeedbackManager.queryMpAdjSupFeedbackForPage(mpAdjSupFeedbackModel, page);
	}

	@Override
	public void showChange(Object vo) {
		

	}

}
