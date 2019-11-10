package com.hanthink.mp.controller;


import com.hanthink.mp.manager.MpSupplierSortManager;
import com.hanthink.mp.model.MpSupplierSortModel;
import com.hanthink.util.excel.ExcelExportUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public class MpSupplierSortExportUtil extends ExcelExportUtil {
	
	private MpSupplierSortManager mpSupplierSortManager;
	private MpSupplierSortModel mpSupplierSortModel;
	
	public MpSupplierSortExportUtil(MpSupplierSortManager MpSupplierSortManager, MpSupplierSortModel model){
		this.mpSupplierSortManager = MpSupplierSortManager;
		this.mpSupplierSortModel = model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<Object> queryDataFromDB(int pageNum) {
		DefaultPage page = new DefaultPage();
		page.setPage(pageNum);
		page.setLimit(pageSize);
		return (PageList)mpSupplierSortManager.queryMpSupplierSortForPage(mpSupplierSortModel, page);
	}

	@Override
	public void showChange(Object vo) {
		

	}

}
