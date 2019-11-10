package com.hanthink.jisi.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jisi.dao.JisiPartGroupDao;
import com.hanthink.jisi.manager.JisiPartGroupManager;
import com.hanthink.jisi.model.JisiPartGroupModel;
import com.hanthink.pub.model.PubDataDictModel;
import com.hanthink.pup.model.PupProPlanModel;
import com.hanthink.sps.dao.SpsInsDao;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
@Service("JisiPartGroupManager")
public class JisiPartGroupManagerImpl extends AbstractManagerImpl<String, JisiPartGroupModel> implements JisiPartGroupManager {

	@Resource
	private JisiPartGroupDao dao;
	
	@Resource
	private SpsInsDao spsInsDao;
	
	@Override
	protected Dao<String, JisiPartGroupModel> getDao() {
		return dao;
	}
	
	/**
	 * 
	 * @Description: 厂内同步零件组维护分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月5日 下午9:24:07
	 */
	@Override
	public PageList<JisiPartGroupModel> queryJisiPartGroupForPage(JisiPartGroupModel model, DefaultPage p) {
		
		return dao.queryJisiPartGroupForPage(model,p);
	}

	/**
	 * 
	 * @Description: 厂内同步零件组维护，批量删除
	 * @param @param idArr
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午3:20:24
	 */
	@Override
	public void removeAndLogByIds(String[] idArr, String ipAddr) {
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("数据删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_JISI_PARTGROUP");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(idArr);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		super.removeByIds(idArr);
		
	}

	/**
	 * 
	 * @Description: 根据零件组代码判断是否已经存在
	 * @param @param partGroup
	 * @param @return   
	 * @return boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午4:15:25
	 */
	@Override
	public boolean isExistByCode(String partGroup) {
		List<JisiPartGroupModel> list = dao.getPartGroupByCode(partGroup);
		if (null != list && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Description: 修改厂内同步零件组维护
	 * @param @param model
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午4:23:08
	 */
	@Override
	public void updateAndLog(JisiPartGroupModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_JISI_PARTGROUP");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);

		dao.update(model);
	}

	/**
	 * 
	 * @Description: 查询需要导出的数据
	 * @param @param model
	 * @param @return   
	 * @return List<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午4:53:23
	 */
	@Override
	public List<JisiPartGroupModel> queryJisiPartGroupByKey(JisiPartGroupModel model) {
		
		return dao.queryJisiPartGroupByKey(model);
	}

	/**
	 * 
	 * @Description: 导入之前从临时表删除上次导入的数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午2:04:41
	 */
	@Override
	public void deleteJisiPartGroupImportTempDataByUUID(String uuid) {
		dao.deleteJisiPartGroupImportTempDataByUUID(uuid);
	}

	/**
	 * 
	 * @Description: 文件导入临时表,并返回校验信息
	 * @param @param file
	 * @param @param uuid
	 * @param @param ipAddr
	 * @param @param user
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午2:11:03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importJisiPartGroupModel(MultipartFile file, String uuid, String ipAddr, IUser user, String planCode) throws Exception {
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
		String[] columns = {"partGroupNo", "partGroupName", "printerName", "printLocation","isAutoPrintName","effStart",
								"effEnd"};
		List<JisiPartGroupModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<JisiPartGroupModel>) ExcelUtil.importExcelXLS(new JisiPartGroupModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<JisiPartGroupModel>) ExcelUtil.importExcelXLSX(new JisiPartGroupModel(), file.getInputStream(), columns, 1, 0);
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
		
		//校验打印机是否存在
		PubDataDictModel model_pr = new PubDataDictModel();
		model_pr.setCodeType("JISI");
		model_pr.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<PubDataDictModel> list_pr = spsInsDao.loadPrinterComboDataByType(model_pr);
		Map<String, String> map_pr = new HashMap<String, String>();
		for (PubDataDictModel pdm : list_pr) {
			map_pr.put(pdm.getCodeValueName(), pdm.getCodeValue());
		}
		
		//数据导入初始化，格式初步检查
		for(JisiPartGroupModel model : importList){
			model.setId(UniqueIdUtil.getSuid());
			model.setPlanCode(planCode);
			model.setUuid(uuid);
			model.setFactoryCode(user.getCurFactoryCode());
			model.setCreationUser(user.getAccount());
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			model.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			JisiPartGroupModel.checkImportData(model, map_pr);
//			
//			if(StringUtil.isNotEmpty(model.getAfoffDate()) && StringUtil.isEmpty(model.getAfoffTime())) {
//				model.setAfoffTime(model.getAfoffDate());
//			}else {
//				model.setAfoffTime(model.getAfoffDate()+" "+model.getAfoffTime());
//			}
			
		}
		//导入数据写入到临时表
		dao.insertJisiPartGroupIntoTempData(importList, planCode);
		dao.updateJisiPartGroupPrintId(importList);
		//调用存储过程等检查数据准确性
		Map<String, String> checkMap = new HashMap<String, String>();
		checkMap.put("uuid", uuid);
		checkMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		checkMap.put("opeIp", ipAddr);
		checkMap.put("errorFlag", "");
		checkMap.put("errorMsg", "");
		dao.checkJisiPartGroupImportDataInformation(checkMap);
		result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(checkMap.get("errorMsg"));
		}
		/**
		 * 检查是否可以批量导入
		 */
		String flag = dao.queryJisiPartGroupImportFlag(uuid);
		//临时导入情况返回
		resultMap.put("result", result);
		resultMap.put("console", console);
		resultMap.put("flag", flag);
		return resultMap;
	}

	/**
	 * 
	 * @Description: 分页查询导入临时表的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午4:39:36
	 */
	@Override
	public PageList<JisiPartGroupModel> queryImportInformationForPage(Map<String, String> paramMap, Page page) {
		
		return dao.queryImportInformationForPage(paramMap,page);
	}

	/**
	 * 
	 * @Description: 将临时表数据写入正式表
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:02:27
	 */
	@Override
	public void insertTempDataToPartGroupTable(Map<String, String> paramMap) throws Exception{
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		paramMap.put("creationUser",ContextUtil.getCurrentUser().getAccount());
		//查看是否有数据可以导入正式表
		Integer countImport = dao.getCountForImport(paramMap);
		if(countImport > 0) {
			/**
			 * 删除上一次的数据
			 * 根据业务需求，这里导入正式表时不需要删除正式表中数据
			 */
//			dao.deleteRegulaData(paramMap);
			/**
			 * 将数据写入正式表
			 */
			dao.insertTempDataToRegula(paramMap);
			/**
			 * 更新导入数据的导入状态
			 */
			dao.updateImportStatus(paramMap);
		}else {
			throw new Exception("没有可导入的正确数据");
		}
	}

	/**
	 * 
	 * @Description: 导出Excel在导入时所有数据
	 * @param @param uuid
	 * @param @return   
	 * @return List<PupProPlanModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:45:41
	 */
	@Override
	public List<JisiPartGroupModel> queryImportInformationForExport(String uuid) {
		
		return dao.queryImportInformationForExport(uuid);
	}

	/**
	 * 
	 * @Description: 通过工厂和信息点类型获取信息点
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月11日 下午3:41:58
	 */
	@Override
	public String getPlanCode(JisiPartGroupModel model) {
		
		return dao.getPlanCode(model);
	}

	/**
	 * @Description: 查询是否有校验结果不通过数据  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年1月27日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return dao.queryIsExistsCheckResultFalse(uuid);
	}

}
