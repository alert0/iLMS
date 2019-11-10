package com.hanthink.mp.controller;


import com.hanthink.mp.manager.MpPartSortManager;
import com.hanthink.mp.model.MpPartSortModel;
import com.hanthink.util.excel.ExcelExportUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public class MpPartSortExportUtil extends ExcelExportUtil {
	
	private MpPartSortManager MpPartSortManager;
	private MpPartSortModel MpPartSortModel;
	
	public MpPartSortExportUtil(MpPartSortManager MpPartSortManager, MpPartSortModel model){
		this.MpPartSortManager = MpPartSortManager;
		this.MpPartSortModel = model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<Object> queryDataFromDB(int pageNum) {
		DefaultPage page = new DefaultPage();
		page.setPage(pageNum);
		page.setLimit(pageSize);
		return (PageList)MpPartSortManager.queryMpPartSortForPage(MpPartSortModel, page);
	}

	@Override
	public void showChange(Object vo) {
		

	}

}
