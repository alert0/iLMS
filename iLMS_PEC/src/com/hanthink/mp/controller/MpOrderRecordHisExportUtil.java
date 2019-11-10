package com.hanthink.mp.controller;


import com.hanthink.mp.manager.MpOrderRecordHisManager;
import com.hanthink.mp.model.MpOrderRecordHisModel;
import com.hanthink.util.excel.ExcelExportUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public class MpOrderRecordHisExportUtil extends ExcelExportUtil {
	
	private MpOrderRecordHisManager mpOrderRecordHisManager;
	private MpOrderRecordHisModel mpOrderRecordHisModel;
	
	public MpOrderRecordHisExportUtil(MpOrderRecordHisManager mpOrderRecordHisManager, MpOrderRecordHisModel model){
		this.mpOrderRecordHisManager = mpOrderRecordHisManager;
		this.mpOrderRecordHisModel = model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<Object> queryDataFromDB(int pageNum) {
		DefaultPage page = new DefaultPage();
		page.setPage(pageNum);
		page.setLimit(pageSize);
		return (PageList)mpOrderRecordHisManager.queryMpOrderRecordHisForPage(mpOrderRecordHisModel, page);
	}

	@Override
	public void showChange(Object vo) {
		

	}

}
