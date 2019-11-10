package com.hanthink.pup.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.pup.dao.PupProPlanDao;
import com.hanthink.pup.manager.PupProPlanManager;
import com.hanthink.pup.model.PupProPlanPageModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hanthink.pup.model.PupProPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

@Service("pupProPlanManager")
public class PupProPlanManagerImpl extends AbstractManagerImpl<String, PupProPlanModel>
					implements PupProPlanManager{
	
	@Resource
	PupProPlanDao planDao;
	
	@Override
	protected Dao<String, PupProPlanModel> getDao() {
		return this.planDao;
	}
	
	@Override
	public PageList<PupProPlanModel> queryProPlanForPage(PupProPlanPageModel plan,Page page) throws Exception {
		List<PupProPlanModel> list = new ArrayList<>();
		
		if(!PupUtil.isAllFieldNull(plan)) {
			if(plan.getWeek() != null) {
				int year = Integer.parseInt(PupUtil.getCurrentTime("yyyy"));
				plan.setAfoffTimeStart(PupUtil.getStartDayOfWeekNo(year, plan.getWeek()));
				plan.setAfoffTimeEnd(PupUtil.getEndDayOfWeekNo(year, plan.getWeek()));
			}
			list = planDao.queryProPlanForPage(plan, page);
		}
		
		return new PageList<>(list);
	}

	@Override
	public List<PupProPlanModel> queryPupPlanBykey(PupProPlanPageModel plan) throws Exception {
		return planDao.queryProPlanByKey(plan);
	}

	@Override
	public void deleteProPlanImportTempDataByUUID(String uuid) throws Exception {
		planDao.deleteProPlanTempDataByUUID(uuid);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importProPlanModel(MultipartFile file, String uuid, String ipAddr, IUser user)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		boolean result = true;
		String console = null;
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		//读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"sortId", "orderNo", "carType", "mark","mixSortId","singleSortId",
								"afoffDate","afoffTime"};
		List<PupProPlanModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<PupProPlanModel>) ExcelUtil.importExcelXLS(new PupProPlanModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<PupProPlanModel>) ExcelUtil.importExcelXLSX(new PupProPlanModel(), file.getInputStream(), columns, 1, 0);
			}else{
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			console = e.getMessage();
			throw new RuntimeException(console);
		}
		
		//数据导入初始化，格式初步检查
		for(PupProPlanModel planModel : importList){
			planModel.setId(UniqueIdUtil.getSuid());
			planModel.setUuid(uuid);
			planModel.setFactoryCode(user.getCurFactoryCode());
            planModel.setCreationUser(user.getAccount());
			planModel.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			planModel.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			PupProPlanModel.checkImportData(planModel);
			
			try {
				PupUtil.String2Date(planModel.getAfoffDate(), "yyyy-MM-dd");
			} catch (Exception e) {
				planModel.setAfoffDate(null);
				planModel.setAfoffTime(null);
			}
			if(StringUtil.isNotEmpty(planModel.getAfoffDate()) && StringUtil.isNotEmpty(planModel.getAfoffTime())) {
				if(planModel.getAfoffDate().length() > 10 && planModel.getAfoffTime().length() > 10) {
					planModel.setAfoffTime(planModel.getAfoffDate().substring(0, 10)+" "+planModel.getAfoffTime().substring(11));
				}else {
					planModel.setAfoffTime(planModel.getAfoffDate()+" "+planModel.getAfoffTime());
				}
			}
			if(StringUtil.isNotEmpty(planModel.getAfoffDate()) && StringUtil.isEmpty(planModel.getAfoffTime())) {
				planModel.setAfoffTime(planModel.getAfoffDate());
			}
		}
		//导入数据写入到临时表
		planDao.insertProPlanIntoTempData(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> checkMap = new HashMap<String, String>();
		checkMap.put("uuid", uuid);
		checkMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		checkMap.put("opeIp", ipAddr);
		checkMap.put("errorFlag", "");
		checkMap.put("errorMsg", "");
		planDao.checkProplanImportDataInformation(checkMap);
		result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(checkMap.get("errorMsg"));
		}
		/**
		 * 检查是否可以批量导入
		 */
		String flag = planDao.queryProPlanImportFlag(uuid);
		//临时导入情况返回
		resultMap.put("result", result);
		resultMap.put("console", console);
		resultMap.put("flag", flag);
		return resultMap;
	}

	@Override
	public PageList<PupProPlanModel> queryImportInformationForPage(Map<String, String> paramMap, Page page)
			throws Exception {
		List<PupProPlanModel> list = new ArrayList<>();
		if(!paramMap.isEmpty()) {
			list = planDao.queryImportInformationForPage(paramMap,page);
		}
		return new PageList<>(list); 
	}
	@Override
	public void insertTempDataToPlanTable(Map<String, String> paramMap) throws Exception {
		
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		planDao.insertTempDataToRegula(paramMap);
		planDao.updateImportStatus(paramMap);
		planDao.deleteImportedDataFromTemp(paramMap);
	}

	@Override
	public PageList<PupProPlanModel> getProPlan(PupProPlanPageModel model) throws Exception {
	    List<PupProPlanModel> list = new ArrayList<>();
		
		if(!PupUtil.isAllFieldNull(model)) {
			if(model.getWeek() != null) {
				int year = Integer.parseInt(PupUtil.getCurrentTime("yyyy"));
				model.setAfoffTimeStart(PupUtil.getStartDayOfWeekNo(year, model.getWeek()));
				model.setAfoffTimeEnd(PupUtil.getEndDayOfWeekNo(year, model.getWeek()));
			}
			DictVO sendFlag = planDao.querySendFlag();
			planDao.getProPlan(model);
			for (PupProPlanModel pupProPlanModel : list) {
				pupProPlanModel.setSendFlag(sendFlag.getValueName());
			}
		}
		return new PageList<>();
	}
}
