package com.hanthink.mp.controller;

import java.util.List;


import com.hanthink.mp.dao.MpTrialPlanDao;
import com.hanthink.mp.model.MpTrialPlanModelImport;
import com.hanthink.util.excel.ExcelImportUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.db.id.UniqueIdUtil;

public class MpTrialPlanImportUtil extends ExcelImportUtil {
	
	private MpTrialPlanDao mpTrialPlanDao;
	private String uuid;
	
	public MpTrialPlanImportUtil(MpTrialPlanDao MpTrialPlanDao, String uuid){
		this.mpTrialPlanDao = MpTrialPlanDao;
		this.uuid = uuid;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void insertDataToDB(List list) {
		
		//数据初始化与初步检查
		for(int i = 0; i < list.size(); i ++){
			/**
			 * 检查导入结果是一个公共的实体类
			 */
			MpTrialPlanModelImport m = (MpTrialPlanModelImport) list.get(i);
			m.setId(UniqueIdUtil.getSuid());
			m.setUuid(uuid);
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
		}
		
		mpTrialPlanDao.insertMpTrialPlanImportTempData(list);
	}

}
