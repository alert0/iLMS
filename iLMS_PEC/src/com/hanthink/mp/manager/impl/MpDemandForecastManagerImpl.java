package com.hanthink.mp.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.mp.dao.MpDemandForecastDao;
import com.hanthink.mp.manager.MpDemandForecastManager;
import com.hanthink.mp.model.MpDemandForecastModel;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 需求预测数据
 * 
 * @author WY
 * 
 */
@Service
public class MpDemandForecastManagerImpl extends
		AbstractManagerImpl<String, MpDemandForecastModel> implements
		MpDemandForecastManager {

	@Resource
	private MpDemandForecastDao dao;

	@Override
	protected Dao<String, MpDemandForecastModel> getDao() {
		return dao;
	}

	@Override
	public Integer genMonthDemandForcast(MpDemandForecastModel model,
			IUser currentUser) {
		model.setCreationUser(currentUser.getAccount());
		model.setFactoryCode(currentUser.getCurFactoryCode());
		return dao.genMonthDemandForcast(model);
	}

	@Override
	public List<MpDemandForecastModel> getVersion(Map<String, String> map) {
		return dao.getVersion(map);
	}

	@Override
	public PageList<MpDemandForecastModel> queryDemandForeCastForPage(
			MpDemandForecastModel model, DefaultPage p) {
		return (PageList<MpDemandForecastModel>) dao
				.queryDemandForeCastForPage(model, p);
	}

	@Override
	public List<MpDemandForecastModel> queryDemandForeCastByKey(
			MpDemandForecastModel model) {
		return dao.queryDemandForeCastByKey(model);
	}

	@Override
	public void batchRemoveDemandForcast(String[] aryIds, String ipAddr) {
		// 日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_SW_DEMAND_FORECAST_SUPPORT");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);

		for (int i = 0; i < aryIds.length; i++) {
			if (aryIds[i] != null && !"".equals(aryIds[i])) {
				dao.batchRemoveDemandForcast(aryIds[i]);
			}
		}
	}

	@Override
	public void updateDemandForcast(
			MpDemandForecastModel mpDemandForecastModel, String ipAddr) {
		// 日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面修改");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SW_DEMAND_FORECAST_SUPPORT");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(mpDemandForecastModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);

		dao.updateDemandForcast(mpDemandForecastModel);
	}

	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		dao.deleteImportTempDataByUUID(uuid);
	}

	@Override
	public List<MpDemandForecastModel> getDefaultVersion(Map<String, String> map) {
		return dao.getDefaultVersion(map);
	}

	@Override
	public void clearDemandForecast(MpDemandForecastModel model,
			IUser currentUser) {
		dao.clearDemandForecast(model);
	}

	@Override
	public Map<String, Object> importMonthModel(MultipartFile file,
			String uuid, String ipAddr, IUser user) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";

		if (file == null || file.isEmpty()) {
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}

		// 读取Excel数据
		String fileExt = file.getOriginalFilename().substring(
				file.getOriginalFilename().indexOf("."));
		String[] columns = { "id", "partNo", "partShortNo", "orderQty",
				"partfId", "partfOrderQty", "planDelivery", "logisticsMode",
				"publishMonth", "objMonth", "startDate", "endDate",
				"modelCode", "version", "phase", "unloadPort", "supplierNo",
				"supFactory", "supplierName" };
		List<MpDemandForecastModel> importList = null;
		try {
			if (ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
				importList = (List<MpDemandForecastModel>) ExcelUtil
						.importExcelXLS(new MpDemandForecastModel(),
								file.getInputStream(), columns, 1, 0);
			} else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
				importList = (List<MpDemandForecastModel>) ExcelUtil
						.importExcelXLSX(new MpDemandForecastModel(),
								file.getInputStream(), columns, 1, 0);
			} else {
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 数据导入初始化，格式初步检查
		for (MpDemandForecastModel m : importList) {
			m.setUuid(uuid);
			m.setForeType("1");
			m.setAdvanceTime("");
			m.setFactoryCode(user.getCurFactoryCode());
			m.setCreationUser(user.getAccount());
			m.setCheckInfo("");
			m.setCheckResult("1");
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
		}

		// 导入数据写入到临时表
		dao.insertImportMonthTempData(importList);

		// 调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		dao.checkImportMonthData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if (!result && StringUtil.isEmpty(console)) {
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		String flag = dao.queryIsImportFlag(uuid);
		// 临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		rtnMap.put("flag", flag);
		return rtnMap;
	}

	@Override
	public Map<String, Object> importWeekModel(MultipartFile file,
			String uuid, String ipAddr, IUser user) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";

		if (file == null || file.isEmpty()) {
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}

		// 读取Excel数据
		String fileExt = file.getOriginalFilename().substring(
				file.getOriginalFilename().indexOf("."));
		String[] columns = { "id", "partNo", "partShortNo", "orderQty",
				"partfId", "partfOrderQty", "planDelivery", "logisticsMode",
				"publishWeek", "objWeek", "advanceTime","startDate", "endDate",
				"modelCode", "version", "phase", "unloadPort", "supplierNo",
				"supFactory", "supplierName" };
		List<MpDemandForecastModel> importList = null;
		try {
			if (ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
				importList = (List<MpDemandForecastModel>) ExcelUtil
						.importExcelXLS(new MpDemandForecastModel(),
								file.getInputStream(), columns, 1, 0);
			} else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
				importList = (List<MpDemandForecastModel>) ExcelUtil
						.importExcelXLSX(new MpDemandForecastModel(),
								file.getInputStream(), columns, 1, 0);
			} else {
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		String version = "";
//		for (MpDemandForecastModel mpDemandForecastModel : importList) {
//			if (StringUtil.isNotEmpty(mpDemandForecastModel.getVersion()) ) {
//				version = mpDemandForecastModel.getVersion();
//			}
//		}

		// 数据导入初始化，格式初步检查
		for (MpDemandForecastModel m : importList) {
//			m.setVersion(version);
			m.setUuid(uuid);
			m.setForeType("2");
			m.setFactoryCode(user.getCurFactoryCode());
			m.setCreationUser(user.getAccount());
			m.setCheckInfo("");
			m.setCheckResult("1");
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
		}

		// 导入数据写入到临时表
		dao.insertImportMonthTempData(importList);

		// 调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		dao.checkImportMonthData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if (!result && StringUtil.isEmpty(console)) {
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		String flag = dao.queryIsImportFlag(uuid);
		// 临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		rtnMap.put("flag", flag);
		return rtnMap;
	}
	
	@Override
	public PageList<MpDemandForecastModel> queryImportTempData(
			Map<String, String> paramMap, Page page) {
		return dao.queryImportTempData(paramMap, page);
	}

	@Override
	public List<MpDemandForecastModel> queryMonthTempDataForExport(
			Map<String, String> paramMap) {
		return dao.queryMonthTempDataForExport(paramMap);
	}

	@Override
	public void insertMonthImportData(Map<String, Object> paramMap,
			String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);

		try {
			List<MpDemandForecastModel> list = dao
					.queryForInsertMonthList(paramMap);
			if (list.size() < 1) {
				throw new Exception("没有正确数据可导入或已全部导入");
			}
			/**
			 * 拿出Id查询哪些数据要修改
			 */
			List<String> ids = dao
					.queryUpdateDataFromMonthImp(paramMap);
			if (ids.size() > 0) {
				/**
				 * 声明一个String数组，用于存放List
				 */
				String[] idp = ids.toArray(new String[ids.size()]);
				TableOpeLogVO logVO = new TableOpeLogVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(ipAddr);
				logVO.setFromName("导入更新");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
				logVO.setTableName("MM_SW_DEMAND_FORECAST_SUPPORT");
				TableColumnVO pkColumnVO = new TableColumnVO();
				pkColumnVO.setColumnName("ID");
				pkColumnVO.setColumnValArr(idp);
				this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);

				/**
				 * 导入修改的方法
				 */
				dao.updateMonthImportData(paramMap);

			}

			/**
			 * 导入新增的方法
			 */
			dao.insertMonthImportData(paramMap);

			// 更新临时数据导入状态
			dao.updateDemandForecastImportDataImpStatus(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误，请联系管理员");

		}
	}

	@Override
	public Integer releaseDemandForcast(MpDemandForecastModel model,
			IUser currentUser) {
		model.setCreationUser(currentUser.getAccount());
		model.setFactoryCode(currentUser.getCurFactoryCode());
		return dao.releaseDemandForcast(model);
	}

	@Override
	public Integer queryIsRelease(MpDemandForecastModel model) {
		return dao.queryIsRelease(model);
	}

	@Override
	public Integer queryIsReleaseById(List<String> aryIds) {
		return dao.queryIsReleaseById(aryIds);
	}

	@Override
	public Integer genDemandPartIf(MpDemandForecastModel model) {
		
		return dao.genDemandPartIf(model);
	}

	@Override
	public Map<String, Object> importWeekForecastModel(MultipartFile file, String uuid, String ipAddr, IUser user) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";

		if (file == null || file.isEmpty()) {
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}

		// 读取Excel数据
		String fileExt = file.getOriginalFilename().substring(
				file.getOriginalFilename().indexOf("."));
		String[] columns = { "id","partNo", "partShortNo", "orderQty",
				 "planDelivery", "logisticsMode",
				"publishWeek", "objWeek", "advanceTime","startDate", "endDate",
				"modelCode", "version", "phase", "unloadPort", "supplierNo",
				"supFactory", "supplierName" };
		List<MpDemandForecastModel> importList = null;
		try {
			if (ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
				importList = (List<MpDemandForecastModel>) ExcelUtil
						.importExcelXLS(new MpDemandForecastModel(),
								file.getInputStream(), columns, 1, 0);
			} else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
				importList = (List<MpDemandForecastModel>) ExcelUtil
						.importExcelXLSX(new MpDemandForecastModel(),
								file.getInputStream(), columns, 1, 0);
			} else {
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 数据导入初始化，格式初步检查
		for (MpDemandForecastModel m : importList) {
			m.setPlanDelivery(m.getPlanDelivery().substring(0, 10));
			m.setStartDate(m.getStartDate().substring(0,10));
			m.setEndDate(m.getEndDate().substring(0,10));
			m.setPartfId(m.getPartNo());
			m.setPartfOrderQty(m.getOrderQty());
			m.setUuid(uuid);
			m.setForeType("2");
			m.setFactoryCode(user.getCurFactoryCode());
			m.setCreationUser(user.getAccount());
			m.setCheckInfo("");
			m.setCheckResult("1");
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
		}

		// 导入数据写入到临时表
		dao.insertImportMonthTempData(importList);

		// 调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		dao.checkImportMonthDataFore(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if (!result && StringUtil.isEmpty(console)) {
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		String flag = dao.queryIsImportFlag(uuid);
		// 临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		rtnMap.put("flag", flag);
		return rtnMap;
	}

	@Override
	public void insertWeekForecastImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);

		try {
			List<MpDemandForecastModel> list = dao
					.queryForInsertMonthList(paramMap);
			if (list.size() < 1) {
				throw new Exception("没有正确数据可导入或已全部导入");
			}
			/**
			 * 拿出Id查询哪些数据要修改
			 */
			List<String> ids = dao
					.queryUpdateDataFromMonthImp(paramMap);
			if (ids.size() > 0) {
				/**
				 * 声明一个String数组，用于存放List
				 */
				String[] idp = ids.toArray(new String[ids.size()]);
				TableOpeLogVO logVO = new TableOpeLogVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(ipAddr);
				logVO.setFromName("导入更新");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
				logVO.setTableName("MM_SW_DEMAND_FORECAST");
				TableColumnVO pkColumnVO = new TableColumnVO();
				pkColumnVO.setColumnName("ID");
				pkColumnVO.setColumnValArr(idp);
				this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);

				/**
				 * 导入修改的方法
				 */
				dao.updateWeekForecastImportData(paramMap);

			}
			/**
			 * 修改生成的一级件周预测数据版本为导入第四周的版本
			 */
			dao.updateWeekForecastVersion(paramMap);
			/**
			 * 导入新增的方法
			 */
			dao.insertWeekForecastImportData(paramMap);

			// 更新临时数据导入状态
			dao.updateDemandForecastImportDataImpStatus(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误，请联系管理员");

		}
	}

	@Override
	public PageList<MpDemandForecastModel> queryDemandWeekForePage(MpDemandForecastModel model, DefaultPage p) {
		
		return dao.queryDemandWeekForePage(model,p);
	}

	@Override
	public List<MpDemandForecastModel> downloadMpDemandWeekForeCast(MpDemandForecastModel model) {
		
		return dao.downloadMpDemandWeekForeCast(model);
	}

	@Override
	public Integer effectDemand(MpDemandForecastModel model) {
		
		return dao.effectDemand(model);
	}

	@Override
	public Boolean isEffectVersion(MpDemandForecastModel model) {
		Integer count = dao.isEffectVersion(model);
		if (count != null && count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<MpDemandForecastModel> getForeVersion(Map<String, String> map) {
		
		return dao.getForeVersion(map);
	}

	@Override
	public boolean validateVersionExists(SwDemandForecastModel model) {
		Integer i = dao.validateVersionExists(model);
		if (i != null && i> 0) {
			return true;
		}
		return false;
	}

	@Override
	public void submitVersion(SwDemandForecastModel model) {
		dao.submitVersion(model);
	}

}
