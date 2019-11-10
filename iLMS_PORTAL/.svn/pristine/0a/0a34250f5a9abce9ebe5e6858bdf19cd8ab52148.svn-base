package com.hanthink.pup.util;

import com.hanthink.pup.manager.PupProPlanManager;
import com.hanthink.pup.model.PupProPlanModel;
import com.hanthink.pup.model.PupProPlanPageModel;
import com.hanthink.util.excel.ExcelExportUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
* <pre> 
* 描述：生产计划导入工具类
* 构建组：x5-bpmx-platform
* 日期:2018-09-14
* 版权：汉思信息技术有限公司
* </pre>
*/
public class PupProPlanExportUtil extends ExcelExportUtil{
	
	private PupProPlanManager planManager;
	private PupProPlanPageModel planModel;
	
	public PupProPlanExportUtil(PupProPlanManager manager,PupProPlanPageModel model) {
		this.planManager = manager;
		this.planModel = model;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageList<Object> queryDataFromDB(int pageNo) {
		DefaultPage page = new DefaultPage();
		page.setPage(pageNo);
		page.setLimit(pageSize);
		PageList<PupProPlanModel> list = null;
		try {
			list = planManager.queryProPlanForPage(planModel, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PageList(list);
	}

	@Override
	public void showChange(Object vo) {
		
	}

}
