package com.hanthink.inv.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.inv.dao.InvPartLocationDao;
import com.hanthink.inv.manager.InvPartLocationManager;
import com.hanthink.inv.model.InvPartLocationModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.mysql.jdbc.StringUtils;

/**
 * @ClassName: InvPartLocationManagerImpl
 * @Description: 零件属地维护
 * @author dtp
 * @date 2019年2月16日
 */
@Service("invPartLocationManager")
public class InvPartLocationManagerImpl extends AbstractManagerImpl<String, InvPartLocationModel>
	implements InvPartLocationManager{

	@Resource
	private InvPartLocationDao invPartLocationDao;
	
	@Override
	protected Dao<String, InvPartLocationModel> getDao() {
		return invPartLocationDao;
	}

	/**
	 * @Description: 分页查询零件属地  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@Override
	public PageList<InvPartLocationModel> queryInvPartLocationPage(InvPartLocationModel model, DefaultPage page) {
		return invPartLocationDao.queryInvPartLocationPage(model, page);
	}

	/**
	 * @Description:  查询零件属地 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@Override
	public List<InvPartLocationModel> queryInvPartLocationList(InvPartLocationModel model) {
		return invPartLocationDao.queryInvPartLocationList(model);
	}

	/**
	 * @Description:  零件数据维护查询货架标签打印信息 
	 * @param: @param model
	 * @param: @return    
	 * @return: InvPartLocationModel   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@Override
	public InvPartLocationModel queryInvShelfPrintInfo(InvPartLocationModel model) {
		return invPartLocationDao.queryInvShelfPrintInfo(model);
	}

	/**
	 * @Description: 根据uuid删除导入临时数据   
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		invPartLocationDao.deleteImportTempDataByUUID(uuid);
	}

	/**
	 * @Description:  excel批量导入 
	 * @param: @param file
	 * @param: @param uuid
	 * @param: @param ipAddr
	 * @param: @return    
	 * @return: Map<String,Object>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importInvPartLocation(MultipartFile file, String uuid, String ipAddr) {
		List<InvPartLocationModel> importList = null;
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
		String[] columns = {"oldUnloadPort","oldSupplierNo", "oldPartNo", "oldReparePerson",
				"oldCarpool","oldStorage", "oldDistriPerson", "oldLocation", "oldStationCode",
				"oldShelfNo", "oldLocationNum",	 "oldDeptNo","newUnloadPort", "newSupplierNo", 
				"newPartNo", "newReparePerson","newCarpool",
				"newStorage", "newDistriPerson", "newLocation","newStationCode", "newShelfNo",
				"newLocationNum","newDeptNo","operationType","modelCode","effStart","effEnd"};	
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<InvPartLocationModel>) ExcelUtil.importExcelXLS(new InvPartLocationModel(), file.getInputStream(), columns, 2, 1);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<InvPartLocationModel>) ExcelUtil.importExcelXLSX(new InvPartLocationModel(), file.getInputStream(), columns, 2, 1);
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
		List<InvPartLocationModel> warCodeList = null;
		List<InvPartLocationModel> newImportList = new ArrayList<InvPartLocationModel>();
		for (InvPartLocationModel m : importList) {
			m.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			//操作人信息
			m.setCreationUser(ContextUtil.getCurrentUser().getAccount());
			m.setLastModifiedIp(ipAddr);
			//校验必填项 ,数据类型 
			InvPartLocationModel.checkImportData(m);
			//根据卸货口获取仓库代(新增or移动)
			if("新增".equals(m.getOperationType()) || "移动".equals(m.getOperationType())) {
				warCodeList = invPartLocationDao.queryWareCodeByNewUnloadPort(m);
			}else if("废止".equals(m.getOperationType())) {
				warCodeList = invPartLocationDao.queryWareCodeByOldUnloadPort(m);
			}
			if(null != warCodeList && warCodeList.size() > 0) {
				if(warCodeList.size() == 1) {
					m.setWareCode(warCodeList.get(0).getWareCode());
					m.setWorkCenter(warCodeList.get(0).getWorkCenter());
				}else {
					m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
					m.setCheckInfo("卸货口在系统不唯一;");
				}
			}else {
				m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
				m.setCheckInfo("卸货口在系统不存在;");
			}
			//拆分车型
			if(!StringUtils.isNullOrEmpty(m.getModelCode())) {
				String[] modelCodeArr = m.getModelCode().split(",");
				for (String modelCode : modelCodeArr) {
					InvPartLocationModel mm = new InvPartLocationModel();
					mm = (InvPartLocationModel) m.clone();
					mm.setModelCode(modelCode);
					mm.setUuid(uuid);
					newImportList.add(mm);
				}
			}
		}
		//导入数据到临时表
		invPartLocationDao.insertInvPartLocationTempData(newImportList);
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		invPartLocationDao.checkImportData(ckParamMap);
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
	 * @Description: 查询临时表数据  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public PageList<InvPartLocationModel> queryImportTempPage(InvPartLocationModel model, DefaultPage page) {
		return invPartLocationDao.queryImportTempPage(model, page);
	}

	/**
	 * @Description: 导出临时表数据 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public List<InvPartLocationModel> queryImportTempList(InvPartLocationModel model) {
		return invPartLocationDao.queryImportTempList(model);
	}

	/**
	 * @Description: 查询导入校验结果
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return invPartLocationDao.queryIsExistsCheckResultFalse(uuid);
	}

	/**
	 * @Description: 确定导入，将临时表数据写入到正式业务表
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public void insertImportData(Map<String, String> paramMap) {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		//临时表写入正式表(新增)
		invPartLocationDao.insertImportDataAdd(paramMap);
		//临时表写入正式表
		//invPartLocationDao.updateImportData(paramMap);
		//临时表写入正式表--先查询出移动数据,然后更新
		List<InvPartLocationModel> ydList = invPartLocationDao.queryYDList(paramMap);
		for (InvPartLocationModel model : ydList) {
			invPartLocationDao.updateImportDataYD(model);
		}
		//临时表写入正式表--先查询出废止数据,然后更新
		List<InvPartLocationModel> fzList = invPartLocationDao.queryFZList(paramMap);
		for (InvPartLocationModel model : fzList) {
			invPartLocationDao.updateImportDataFZ(model);
		}
		//更新临时数据导入状态
		invPartLocationDao.updateImportDataImpStatus(paramMap);
	}

	/**
	 * @Description: 零件属地维护日志查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月25日
	 */
	@Override
	public PageList<InvPartLocationModel> queryInvPartLocationLogPage(InvPartLocationModel model, DefaultPage page) {
		return invPartLocationDao.queryInvPartLocationLogPage(model, page);
	}

	/**
	 * @Description: 属地维护日志导入  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月25日
	 */
	@Override
	public List<InvPartLocationModel> queryInvPartLocationLogList(InvPartLocationModel model) {
		return invPartLocationDao.queryInvPartLocationLogList(model);
	}

}
