package com.hanthink.demo.controller;

import com.hanthink.demo.manager.DemoManager;
import com.hanthink.util.excel.ExcelExportUtil;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public class DemoExportUtil extends ExcelExportUtil {
	
	private DemoManager demoManager;
	private QueryFilter queryFilter;
	
	public DemoExportUtil(DemoManager demoManager, QueryFilter queryFilter){
		this.demoManager = demoManager;
		this.queryFilter = queryFilter;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<Object> queryDataFromDB(int pageNum) {
		DefaultPage page = new DefaultPage();
		page.setPage(pageNum);
		page.setLimit(pageSize);
		queryFilter.setPage(page);
		return (PageList)demoManager.query(queryFilter);
	}

	@Override
	public void showChange(Object vo) {
		

	}

}
