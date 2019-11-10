package com.hanthink.demo.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.demo.controller.DemoImportUtil;
import com.hanthink.demo.dao.DemoDao;
import com.hanthink.demo.manager.DemoManager;
import com.hanthink.demo.model.DemoModel;
import com.hanthink.demo.model.DemoModelImport;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("demoManager")
public class DemoManagerImpl extends AbstractManagerImpl<String, DemoModel> implements DemoManager {
	
	@Resource
	private DemoDao demoDao;

	@Override
	protected Dao<String, DemoModel> getDao() {
		return demoDao;
	}
	
	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author ZUOSL	
	 * @DATE	2018年9月4日 上午10:54:52
	 */
	@Override
	public void updateAndLog(DemoModel demoModel, String ipAddr){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("DEMO_MODEL");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("PK_ID");
		pkColumnVO.setColumnVal(demoModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		demoDao.update(demoModel);
		
	}
	
	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author ZUOSL	
	 * @DATE	2018年9月4日 上午11:01:08
	 */
	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr){
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("DEMO_MODEL");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("PK_ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		super.removeByIds(aryIds);
	}

	/**
	 * 导入Excel数据Demo
	 * @param file
	 * @param uuid
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 上午10:27:10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importDemoModel(MultipartFile file, String uuid, String opeIp) {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		
		//读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"col1", "col2", "col3", "col4"};
		List<DemoModelImport> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<DemoModelImport>) ExcelUtil.importExcelXLS(new DemoModelImport(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<DemoModelImport>) ExcelUtil.importExcelXLSX(new DemoModelImport(), file.getInputStream(), columns, 1, 0);
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
		for(DemoModelImport m : importList){
			m.setPkId(UniqueIdUtil.getSuid());
			m.setUuid(uuid);
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
		}
		
		//导入数据写入到临时表
		demoDao.insertImportTempData(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", opeIp);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		demoDao.checkImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		
		//临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		return rtnMap;
	}
	
	/**
	 * 大数据量Excel导入Demo
	 * @param file
	 * @param uuid
	 * @param opeIp
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月4日 上午9:34:40
	 */
	@Override
	public Map<String, Object> importDemoModel2(MultipartFile file, String uuid, String opeIp) {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		
		//读取Excel数据
		DemoImportUtil excelutil = new DemoImportUtil(demoDao, uuid);
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"col1", "col2", "col3", "col4"};
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				excelutil.importExcel(ExcelUtil.EXCEL_XLS, new DemoModelImport(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				excelutil.importExcel(ExcelUtil.EXCEL_XLSX, new DemoModelImport(), file.getInputStream(), columns, 1, 0);
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
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", opeIp);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		demoDao.checkImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		
		
		//临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		return rtnMap;
	}

	/**
	 * 查询导入临时表数据
	 * @param paramMap
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 下午12:22:14
	 */
	@Override
	public PageList<DemoModelImport> queryImportTempData(Map<String, String> paramMap, Page page) {
		return demoDao.queryImportTempData(paramMap, page);
	}

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 下午12:22:28
	 */
	@Override
	public void insertImportData(Map<String, String> paramMap) {
		
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		demoDao.insertImportData(paramMap);
		
		//更新临时数据导入状态
		demoDao.updateImportDataImpStatus(paramMap);
	}

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 下午4:17:56
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		demoDao.deleteImportTempDataByUUID(uuid);
	}

	

}
