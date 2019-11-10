package com.hanthink.sps.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.sps.dao.SpsConfigDetailDao;
import com.hanthink.sps.manager.SpsConfigDetailManager;
import com.hanthink.sps.model.SpsConfigDetailModel;
import com.hanthink.sps.model.SpsConfigModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:SPS配置明细维护业务层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年11月8日下午4:42:51
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("spsConfig")
public class SpsConfigDetailManagerImpl extends AbstractManagerImpl<String, SpsConfigDetailModel>
										implements SpsConfigDetailManager{
	
	@Resource
	private SpsConfigDetailDao configDao;
	
	@Override
	protected Dao<String, SpsConfigDetailModel> getDao() {
		return configDao;
	}

	@Override
	public PageList<SpsConfigDetailModel> queryConfigDetailsForPage(SpsConfigDetailModel model, Page page)
			throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return new PageList<SpsConfigDetailModel>(configDao.queryConfigDetailsForPage(model,page));
	}
	
	@Override
	public void createConfigDetails(SpsConfigDetailModel model) throws Exception {
		try {
			//页面数据非空验证
			if (StringUtil.isEmpty(model.getPartNo())) {
				throw new Exception("零件号不能为空");
			}
			if(StringUtil.isEmpty(model.getModelCode())) {
				throw new Exception("车型不能为空");
			}
			/*if (StringUtil.isEmpty(model.getStationCode())) {
				throw new Exception("工位不能为空");
			}*/
			if (StringUtil.isEmpty(model.getConfigCode())) {
				throw new Exception("配置项代码不能为空");
			}
			//判断零件号是否存在
			Object partNoIsExist = configDao.isExist(model.getPartNo());
			if (Integer.valueOf(partNoIsExist.toString()) <= 0) {
				throw new Exception("零件不存在");
			}
			//判断业务主键是否存在
			boolean mainKeyIsExist = configDao.isExist(model);
			if (mainKeyIsExist) {
				throw new Exception("主键重复");
			}
			
			IUser user = ContextUtil.getCurrentUser();
			model.setFactoryCode(user.getCurFactoryCode());
			model.setProductLine(user.getCurProductLine());
			model.setCreationUser(user.getAccount());
			String id = configDao.getConfigId(model);
			if (StringUtil.isEmpty(id)) {
				throw new Exception("配置项代码不存在");
			}
			model.setConfigId(id);
			
			configDao.saveAdd(model);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public void updateConfigDetails(SpsConfigDetailModel model,String ipAddr) throws Exception {
		//记录日志信息
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("配置项明细数据修改");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_SPS_CONFIG_DETAIL");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO); 
		configDao.updateConfigDetails(model);
	}
	@Override
	public void removeConfigDetailsByIds(String[] ids, String ipAddr) throws Exception {
		if (ids.length <= 0) {
			throw new Exception("请选择需要删除的数据");
		}
		//记录日志信息
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("配置项明细数据删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_SPS_CONFIG_DETAIL");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(ids);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		configDao.removeConfigDetailsByIds(ids);
	}

	@Override
	public List<SpsConfigDetailModel> exportQueryForExcel(SpsConfigDetailModel model)throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return configDao.exportQueryForExcel(model);
	}

	@Override
	public void deleteTempCongfigByUUID(String uuid) throws Exception {
		if (StringUtil.isNotEmpty(uuid)) {
			configDao.deleteTempCongfigByUUID(uuid);
		}else {
			throw new Exception("系统出错,请联系管理人员");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importConfigDetailModel(MultipartFile file, String uuid, String ipAddr)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = true;
		String console = null;
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		//读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"configCode","modelCode","stationCode","partNo","partMark","shelfNo"};
		List<SpsConfigDetailModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList =  (List<SpsConfigDetailModel>) ExcelUtil.importExcelXLS(new SpsConfigDetailModel(),file.getInputStream(), columns, 1, 0);
			}else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
				importList =(List<SpsConfigDetailModel>) ExcelUtil.importExcelXLSX(new SpsConfigDetailModel(),file.getInputStream(), columns, 1, 0);
			}else {
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
		IUser user = ContextUtil.getCurrentUser();
		//初始化数据检查
		for (SpsConfigDetailModel configModel : importList) {
			configModel.setUuid(uuid);
			configModel.setId(UniqueIdUtil.getSuid());
			configModel.setFactoryCode(user.getCurFactoryCode());
			configModel.setProductLine(user.getCurProductLine());
			configModel.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			configModel.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			SpsConfigDetailModel.checkImport(configModel);
		}
		configDao.insertConfigToTemp(importList);
		//调用存储过程校验数据
		Map<String, String> checkMap = new HashMap<String, String>();
		checkMap.put("uuid", uuid);
		checkMap.put("errorFlag", "");
		checkMap.put("errorMsg", "");
		configDao.ckeckImportConfig(checkMap);
		result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(checkMap.get("errorMsg"));
		}
		//临时导入情况返回
		resultMap.put("result", result);
		resultMap.put("console", console);
 		return resultMap;
	}
	
	@Override
	public PageList<SpsConfigDetailModel> queryImportForPage(String uuid, Page page) throws Exception {
		return new PageList<SpsConfigDetailModel>(configDao.queryImportForPage(uuid,page));
	}
	
	@Override
	public void insertTempToFormal(Map<String, Object> paramMap) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		paramMap.put("creationUser", ContextUtil.getCurrentUser().getAccount());
		//将临时数据表的数据写入正式表,check_result = 1
		configDao.insertTempToFormal(paramMap);
		//查询导入数据
		List<SpsConfigDetailModel> list_u = configDao.queryUpdateList(paramMap);
		for (SpsConfigDetailModel model_u : list_u) {
			model_u.setCreationUser(ContextUtil.getCurrentUser().getAccount());
			model_u.setLastModifiedIp(paramMap.get("ipAddr")+"");
			//将临时数据表的数据更新正式表,check_result = 2
			configDao.updateTempDataList(model_u);
		}
		//更新临时表的导入状态
		configDao.updateImportStatus(paramMap);
		
//		Integer countConfigImport = configDao.getCountConfigImport(paramMap);
//		if (countConfigImport > 0) {
//			
//		}else {
//			throw new Exception("存在校验结果不通过数据");
//		}
	}
	@Override
	public List<SpsConfigDetailModel> exportForImport(String uuid) throws Exception {
		return configDao.exportForImport(uuid);
	}

	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2018年12月30日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return configDao.queryIsExistsCheckResultFalse(uuid);
	}
}
