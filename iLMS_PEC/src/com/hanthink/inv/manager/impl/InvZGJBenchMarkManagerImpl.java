package com.hanthink.inv.manager.impl;

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
import com.hanthink.inv.dao.InvZGJBenchMarkDao;
import com.hanthink.inv.manager.InvZGJBenchMarkManager;
import com.hanthink.inv.model.InvZGJBenchMarkModel;
import com.hanthink.mp.model.MpDemandForecastModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 支给件W-1周库存数据
 * @author WY
 *
 */
@Service("invZGJBenchMarkManager")
public class InvZGJBenchMarkManagerImpl extends AbstractManagerImpl<String, InvZGJBenchMarkModel> implements 
		InvZGJBenchMarkManager{

	@Resource
	private InvZGJBenchMarkDao dao; 
	
	@Override
	protected Dao<String, InvZGJBenchMarkModel> getDao() {
		return dao;
	}

	@Override
	public PageList<InvZGJBenchMarkModel> queryBenchMarkForPage(
			InvZGJBenchMarkModel model, Page page) {
		return dao.queryBenchMarkForPage(model, page);
	}

	@Override
	public List<InvZGJBenchMarkModel> queryExportDataForExcel(
			InvZGJBenchMarkModel model) {
		return dao.queryExportDataForExcel(model);
	}

	@Override
	public void updateObj(InvZGJBenchMarkModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_INV_BENCHMARK");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		dao.updateObj(model);
	}

	@Override
	public Integer addObj(InvZGJBenchMarkModel model) {
		//查询这个零件是否支给件件,以及该支给件和仓库的对应关系是否正确
		Integer i = dao.queryIsSupportPart(model);
		if(i <= 0){
			return 0;
		}
		Integer j = dao.queryIsExistsBenchMark(model);
		if(j > 0){
			return 2;
		}
		dao.addObj(model);
		return 1;
	}

	@Override
	public void delBatchObj(List<InvZGJBenchMarkModel> list, String ipAddr,
			IUser currentUser) {

        // 循环取货处理
        if (list != null && list.size() > 0) {
            List<String> idList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                // 将所有ID存到List
                idList.add(list.get(i).getId());
            }
            // 将list id转为数组
            String[] ids = new String[idList.size()];
            // 记录删除循环取货零件日志
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("批量删除");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
            logVO.setTableName("MM_INV_BENCHMARK");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(idList.toArray(ids));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);

            //删除W-1周库存基准数据
            dao.delBatchObj(idList.toArray(ids));
        }

	}

	@Override
	public Integer generateBenchMark(InvZGJBenchMarkModel model) {
		return dao.generateBenchMark(model);
	}

	
	/***导入******************************************************/
	/**
	 * 
	 * @Description: 根据UUID删除上次数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午2:39:48
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		dao.deleteImportTempDataByUUID(uuid);
	}

	/**
	 * 
	 * @Description: 导入临时表
	 * @param @param file
	 * @param @param uuid
	 * @param @param ipAddr
	 * @param @param user
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午2:40:11
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> imporModel(MultipartFile file, String uuid, String ipAddr, IUser user) {
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
		String[] columns = { "wareCode", "partNo", "stock"};
		List<InvZGJBenchMarkModel> importList = null;
		try {
			if (ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
				importList = (List<InvZGJBenchMarkModel>) ExcelUtil
						.importExcelXLS(new InvZGJBenchMarkModel(),
								file.getInputStream(), columns, 1, 0);
			} else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
				importList = (List<InvZGJBenchMarkModel>) ExcelUtil
						.importExcelXLSX(new InvZGJBenchMarkModel(),
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
		for (InvZGJBenchMarkModel m : importList) {
			m.setUuid(uuid);
			m.setFactoryCode(user.getCurFactoryCode());
			m.setCreationUser(user.getAccount());
			m.setCheckInfo("");
			m.setCheckResult("1");
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
		}

		// 导入数据写入到临时表
		dao.insertImportTempData(importList);

		// 调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		dao.checkImportData(ckParamMap);
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

	/**
	 * 
	 * @Description: 分页查询导入临时表的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午2:47:56
	 */
	@Override
	public PageList<InvZGJBenchMarkModel> queryImportTempData(Map<String, String> paramMap, Page page) {
		
		return dao.queryImportTempData(paramMap,page);
	}

	/**
	 * 
	 * @Description: 导出  在导入时的数据
	 * @param @param paramMap
	 * @param @return   
	 * @return List<InvZGJBenchMarkModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午2:48:52
	 */
	@Override
	public List<InvZGJBenchMarkModel> queryTempDataForExport(Map<String, String> paramMap) {
		
		return dao.queryTempDataForExport(paramMap);
	}

	/**
	 * 
	 * @Description: 临时表写入正式表
	 * @param @param paramMap
	 * @param @param ipAddr   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月17日 下午2:49:21
	 */
	@Override
	public void insertImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);

		try {
			List<InvZGJBenchMarkModel> list = dao
					.queryForInsertList(paramMap);
			if (list.size() < 1) {
				throw new Exception("没有正确数据可导入或已全部导入");
			}
			/**
			 * 拿出修改标识查询哪些数据要修改
			 */
			List<String> ids = dao
					.queryUpdateDataFromImp(paramMap);
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
				logVO.setTableName("MM_INV_BENCHMARK");
				TableColumnVO pkColumnVO = new TableColumnVO();
				pkColumnVO.setColumnName("ID");
				pkColumnVO.setColumnValArr(idp);
				this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);

				/**
				 * 导入修改的方法
				 */
				dao.updateImportData(paramMap);
			}

			/**
			 * 导入新增的方法
			 */
			dao.insertImportData(paramMap);

			// 更新临时数据导入状态
			dao.updateImportDataImpStatus(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误，请联系管理员");

		}
	
	}

	@Override
	public void dealStock(InvZGJBenchMarkModel model) {
		//处理时，如果未处理的数据已存在则将原来已处理状态的数据的基准库存更新
		dao.dealStockUpdateExists(model);
		
		//将未处理且在已处理（原来的数据）数据中不存在的更新标识为已处理 
		dao.dealStockNotUpdateExists(model);
		
		//上面两步执行完成后，将处理标识为未处理的删除（这里剩下的数据则为原来存在的数据，更新基准库存后该数据多余出来）
		dao.dealStockDeleteExists(model);
	}

	
	/***支给件推算周维护开始******************************************************************************************/
	@Override
	public PageList<InvZGJBenchMarkModel> queryWeekCalForPage(InvZGJBenchMarkModel model, Page page) {
		
		return dao.queryWeekCalForPage(model,page);
	}

	@Override
	public List<InvZGJBenchMarkModel> queryExportWeekCalForExcel(InvZGJBenchMarkModel model) {
		
		return dao.queryExportWeekCalForExcel(model);
	}

	@Override
	public void updateWeekCalObj(InvZGJBenchMarkModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_INV_WEEK_CAL");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		dao.updateWeekCalObj(model,ipAddr);
	}

	@Override
	public Integer addWeekCalObj(InvZGJBenchMarkModel model) {
		//查询这个零件是否支给件件,以及该支给件和仓库的对应关系是否正确
		Integer i = dao.queryIsSupportPart(model);
		if(i <= 0){
			return 0;
		}
		Integer j = dao.queryIsExistsBenchMark(model);
		if(j > 0){
			return 2;
		}
		dao.addWeekCalObj(model);
		return 1;
	}

	@Override
	public void delBatchWeekCalObj(List<InvZGJBenchMarkModel> list, String ipAddr, IUser currentUser) {
		 
        if (list != null && list.size() > 0) {
            List<String> idList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                // 将所有ID存到List
                idList.add(list.get(i).getId());
            }
            // 将list id转为数组
            String[] ids = new String[idList.size()];
            // 记录删除支给件推算周日志
            TableOpeLogVO logVO = new TableOpeLogVO();
            logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
            logVO.setOpeIp(ipAddr);
            logVO.setFromName("批量删除");
            logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
            logVO.setTableName("MM_INV_WEEK_CAL");
            TableColumnVO pkColumnVO = new TableColumnVO();
            pkColumnVO.setColumnName("ID");
            pkColumnVO.setColumnValArr(idList.toArray(ids));
            this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);

            //删除支给件推算周数据
            dao.delBatchWeekCalObj(idList.toArray(ids));
	}
	}

	@Override
	public List<InvZGJBenchMarkModel> selectUnloadWare(Map<String, String> map) {
		
			return dao.selectUnloadWare(map);
	}

	@Override
	public PageList<InvZGJBenchMarkModel> handleListPartNo(InvZGJBenchMarkModel model, DefaultPage p) {
		
		return dao.handleListPartNo(model,p);
	}

	
	/****支给件推算周导入******************************************************/
	@Override
	public void deleteImportTempDataWeekCalByUUID(String uuid) {
		dao.deleteImportTempDataWeekCalByUUID(uuid);
	}

	@Override
	public Map<String, Object> imporModelWeekCal(MultipartFile file, String uuid, String ipAddr, IUser user) {
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
		String[] columns = { "wareCode", "partNo", "calWeek"};
		List<InvZGJBenchMarkModel> importList = null;
		try {
			if (ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
				importList = (List<InvZGJBenchMarkModel>) ExcelUtil
						.importExcelXLS(new InvZGJBenchMarkModel(),
								file.getInputStream(), columns, 1, 0);
			} else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
				importList = (List<InvZGJBenchMarkModel>) ExcelUtil
						.importExcelXLSX(new InvZGJBenchMarkModel(),
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
		for (InvZGJBenchMarkModel m : importList) {
			m.setUuid(uuid);
			m.setFactoryCode(user.getCurFactoryCode());
			m.setCreationUser(user.getAccount());
			m.setCheckInfo("");
			m.setCheckResult("1");
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
		}

		// 导入数据写入到临时表
		dao.insertImportTempDataWeekCal(importList);

		// 调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		dao.checkImportDataWeekCal(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if (!result && StringUtil.isEmpty(console)) {
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		String flag = dao.queryIsImportFlagWeekCal(uuid);
		// 临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		rtnMap.put("flag", flag);
		return rtnMap;
	}

	@Override
	public PageList<InvZGJBenchMarkModel> queryImportTempDataWeekCal(Map<String, String> paramMap, Page page) {
		
		return dao.queryImportTempDataWeekCal(paramMap,page);
	}

	@Override
	public List<InvZGJBenchMarkModel> queryTempDataForExportWeekCal(Map<String, String> paramMap) {
		
		return dao.queryTempDataForExportWeekCal(paramMap);
	}

	@Override
	public void insertImportDataWeekCal(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);

		try {
			List<InvZGJBenchMarkModel> list = dao
					.queryForInsertListWeekCal(paramMap);
			if (list.size() < 1) {
				throw new Exception("没有正确数据可导入或已全部导入");
			}
//			/**
//			 * 拿出修改标识查询哪些数据要修改
//			 */
//			List<String> ids = dao
//					.queryUpdateDataFromImpWeekCal(paramMap);
//			if (ids.size() > 0) {
//				/**
//				 * 声明一个String数组，用于存放List
//				 */
//				String[] idp = ids.toArray(new String[ids.size()]);
//				TableOpeLogVO logVO = new TableOpeLogVO();
//				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
//				logVO.setOpeIp(ipAddr);
//				logVO.setFromName("导入更新");
//				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
//				logVO.setTableName("MM_INV_BENCHMARK");
//				TableColumnVO pkColumnVO = new TableColumnVO();
//				pkColumnVO.setColumnName("ID");
//				pkColumnVO.setColumnValArr(idp);
//				this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);

				/**
				 * 导入修改的方法
				 */
				dao.updateImportDataWeekCal(paramMap);
//			}

			/**
			 * 导入新增的方法
			 */
			dao.insertImportDataWeekCal(paramMap);

			// 更新临时数据导入状态
			dao.updateImportDataImpStatusWeekCal(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误，请联系管理员");

		}
	}

	/**支给件缺件查询开始**********************************/
	@Override
	public PageList<InvZGJBenchMarkModel> queryWeekCalForPageDifference(InvZGJBenchMarkModel model, Page page) {
		
		return dao.queryWeekCalForPageDifference(model,page);
	}

	@Override
	public List<InvZGJBenchMarkModel> exportForExcelDifference(InvZGJBenchMarkModel model) {
		
		return dao.exportForExcelDifference(model);
	}

	@Override
	public Integer getZGJDifference(InvZGJBenchMarkModel model) {
		
		return dao.getZGJDifference(model);
	}
}
