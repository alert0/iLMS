package com.hanthink.demo.controller;

import java.util.List;

import com.hanthink.demo.dao.DemoDao;
import com.hanthink.demo.model.DemoModelImport;
import com.hanthink.util.excel.ExcelImportUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.db.id.UniqueIdUtil;

public class DemoImportUtil extends ExcelImportUtil {
	
	private DemoDao demoDao;
	private String uuid;
	
	public DemoImportUtil(DemoDao demoDao, String uuid){
		this.demoDao = demoDao;
		this.uuid = uuid;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void insertDataToDB(List list) {
		
		//数据初始化与初步检查
		for(int i = 0; i < list.size(); i ++){
			DemoModelImport m = (DemoModelImport) list.get(i);
			m.setPkId(UniqueIdUtil.getSuid());
			m.setUuid(uuid);
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
		}
		
		demoDao.insertImportTempData(list);
	}

}
