package com.hanthink.pup.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pup.dao.PupProPlanDao;
import com.hanthink.pup.manager.PupProPlanManager;
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
	public PageList<PupProPlanModel> queryProPlanForPage(PupProPlanModel plan,
														Page page) throws Exception {
		
		plan.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		//默认根据时间控件的时间段进行查询，如果周次有值则将周次算为具体时间进行查询
		if(StringUtil.isNotEmpty(plan.getWeek())) {
			int year = Integer.parseInt(PupUtil.getCurrentTime("yyyy"));
			plan.setAfoffTimeStart(PupUtil.getStartDayOfWeekNo(year, Integer.parseInt(plan.getWeek())));
			plan.setAfoffTimeEnd(PupUtil.getEndDayOfWeekNo(year, Integer.parseInt(plan.getWeek())));
		}else {
			if(StringUtil.isEmpty(plan.getAfoffTimeStart())) {
				if (StringUtil.isEmpty(plan.getAfoffTimeEnd())) {
					return null;
				}
			}
			if (StringUtil.isNotEmpty(plan.getAfoffTimeEnd())) {
				plan.setAfoffTimeEnd(PupUtil.getTargetDate(plan.getAfoffTimeEnd(), "yyyy-MM-dd", 1));
			}
		}
			
		return new PageList<PupProPlanModel>(planDao.queryProPlanForPage(plan, page));
	}

	@Override
	public List<PupProPlanModel> queryPupPlanBykey(PupProPlanModel plan) throws Exception {
		try {
			plan.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			//默认根据时间控件的时间段进行查询，如果周次有值则将周次算为具体时间进行查询
			if(StringUtil.isNotEmpty(plan.getWeek())) {
				int year = Integer.parseInt(PupUtil.getCurrentTime("yyyy"));
				plan.setAfoffTimeStart(PupUtil.getStartDayOfWeekNo(year, Integer.parseInt(plan.getWeek())));
				plan.setAfoffTimeEnd(PupUtil.getEndDayOfWeekNo(year, Integer.parseInt(plan.getWeek())));
			}else {
				if(StringUtil.isEmpty(plan.getAfoffTimeStart())) {
					if (StringUtil.isEmpty(plan.getAfoffTimeEnd())) {
						return null;
					}
				}
				if (StringUtil.isNotEmpty(plan.getAfoffTimeEnd())) {
					plan.setAfoffTimeEnd(PupUtil.getTargetDate(plan.getAfoffTimeEnd(), "yyyy-MM-dd", 1));
				}
			}
			return planDao.queryProPlanByKey(plan);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
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
		try {
			//数据导入初始化，格式初步检查
			for(PupProPlanModel planModel : importList){
				planModel.setId(UniqueIdUtil.getSuid());
				planModel.setUuid(uuid);
				planModel.setFactoryCode(user.getCurFactoryCode());
	            planModel.setCreationUser(user.getAccount());
				planModel.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
				planModel.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
				PupProPlanModel.checkImportData(planModel);
				if (planModel.getAfoffDate() == null || planModel.getAfoffTime() == null) {
					planModel.setAfoffTime(null);
				}else {
					planModel.setAfoffTime(planModel.getAfoffDate()+" "+planModel.getAfoffTime());
				}
			}
			//导入数据写入到临时表
			planDao.insertProPlanIntoTempData(importList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			console = "请按照模版导入数据";
			throw new RuntimeException(console);
		}
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}

	@Override
	public PageList<PupProPlanModel> queryImportInformationForPage(Map<String, String> paramMap, Page page)
			throws Exception {
		List<PupProPlanModel> list = new ArrayList<>();
		if(!paramMap.isEmpty()) {
			paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
			list = planDao.queryImportInformationForPage(paramMap,page);
		}
		return new PageList<>(list); 
	}
	
	@Override
	public void insertTempDataToPlanTable(Map<String, String> paramMap) throws Exception {
		
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		//查看是否有数据可以导入正式表
		Integer countImport = planDao.getCountForImport(paramMap);
		if(countImport > 0) {
			try {
				//记录删除日志条件
				TableOpeLogVO logVO = new TableOpeLogVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(paramMap.get("ipAddr")); 
				logVO.setFromName("导入数据删除");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
				logVO.setTableName("MM_PUP_PRO_PLAN");
				TableColumnVO pkColumnVO = new TableColumnVO();
				pkColumnVO.setColumnName("FACTORY_CODE");
				pkColumnVO.setColumnVal(paramMap.get("factoryCode"));
				//记录日志
				this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
				/**
				 * 删除上一次的数据
				 */
				planDao.deleteRegulaData(paramMap);
				/**
				 * 将数据写入正式表
				 */
				planDao.insertTempDataToRegula(paramMap);
				/**
				 * 更新导入数据的导入状态
				 */
				planDao.updateImportStatus(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("系统错误,请联系管理员");
			}	
		}else {
			throw new Exception("没有可导入的正确数据");
		}
	}
	
	@Override
	public List<PupProPlanModel> queryImportInformationForExport(String uuid) throws Exception {
		return planDao.queryImportInformationForExport(uuid);
	}
	
	@Override
	public void getProPlan(PupProPlanModel model) throws Exception {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		if (StringUtil.isEmpty(model.getWeek())) {
			if (StringUtil.isEmpty(model.getAfoffTimeStart()) && StringUtil.isEmpty(model.getAfoffTimeEnd())) {
				throw new Exception("请输入计划下线日期或者输入周次后从新获取计划");
			}
		}
		
		paramMap.put("week", model.getWeek());
		paramMap.put("opeId", model.getOpeId());
		paramMap.put("afoffTimeStart", model.getAfoffTimeStart());
		paramMap.put("afoffTimeEnd", model.getAfoffTimeEnd());
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		try {
			planDao.getProPlan(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取取货计划数据失败");
		}
	}
	/**
	 * 查询时间输入框默认值
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月13日
	 */
	@Override
	public PupProPlanModel querySearchTimes() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		return planDao.querySearchTimes(factoryCode);
	}
}
