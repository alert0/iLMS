package com.hanthink.sps.manager.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pub.manager.PubDataDictManager;
import com.hanthink.sps.dao.SpsMouldDao;
import com.hanthink.sps.manager.SpsMouldManager;
import com.hanthink.sps.model.SpsConfigModel;
import com.hanthink.sps.model.SpsMouldConfigModel;
import com.hanthink.sps.model.SpsMouldModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: SpsMouldManagerImpl
 * @Description: SPS票据模板管理
 * @author dtp
 * @date 2018年11月21日
 */
@Service("SpsMouldManager")
public class SpsMouldManagerImpl extends AbstractManagerImpl<String, SpsMouldModel> implements SpsMouldManager{

	@Resource
	private SpsMouldDao spsMouldDao;
	
	@Resource
	private PubDataDictManager pubDataDictManager;
	
	@Override
	protected Dao<String, SpsMouldModel> getDao() {
		return spsMouldDao;
	}

	/**
	 * @Description: 模板列表查询  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsMouldModel>   
	 * @author: dtp
	 * @date: 2018年11月21日
	 */
	@Override
	public PageList<SpsMouldModel> querySpsMouldPage(SpsMouldModel model, DefaultPage page) {
		return spsMouldDao.querySpsMouldPage(model, page);
	}

	/**
	 * @Description: 修改模板列表
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月21日
	 */
	@Override
	public void updateSpsMould(SpsMouldModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("SPS票据模板管理模板更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SPS_MOULD");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		spsMouldDao.updateSpsMould(model);
	}

	/**
	 * @Description: 配置列表查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月21日
	 */
	@Override
	public PageList<SpsMouldConfigModel> querySpsMouldConfigPage(SpsMouldConfigModel model, DefaultPage page) {
		return spsMouldDao.querySpsMouldConfigPage(model, page);
	}

	/**
	 * @Description: 配置列表导出excel  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月22日
	 */
	@Override
	public List<SpsMouldConfigModel> querySpsMouldConfigList(SpsMouldConfigModel model) {
		return spsMouldDao.querySpsMouldConfigList(model);
	}

	/**
	 * @Description: 修改配置列表信息 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月22日
	 */
	@Override
	public void updateSpsMouldConfig(SpsMouldConfigModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("SPS票据模板管理配置更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SPS_MOULD_CONFIG");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		spsMouldDao.updateSpsMouldConfig(model);
	}

	/**
	 * @Description: SPS票据模板配置导入  
	 * @param: @param file
	 * @param: @param uuid
	 * @param: @param ipAddr
	 * @param: @param mouldId SPS票据模板id
	 * @param: @return    
	 * @return: Map<String,Object>   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importSpsMould(MultipartFile file, String uuid, String opeIp, String mouldId) {
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
		String[] columns = {"mouldPlace", "configCode", "configShow"};
		List<SpsMouldConfigModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<SpsMouldConfigModel>) ExcelUtil.importExcelXLS(new SpsMouldConfigModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<SpsMouldConfigModel>) ExcelUtil.importExcelXLSX(new SpsMouldConfigModel(), file.getInputStream(), columns, 1, 0);
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
		Map<String, String> dictMap = pubDataDictManager.queryDataDictByCodeType("SPS_CONFIG_SHOW");
		//sps配置项配置项代码与id转换
		Map<String, String> configMap = new HashMap<>();
		SpsConfigModel model = new SpsConfigModel();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		model.setProductionLine(ContextUtil.getCurrentUser().getCurProductLine());
		List<SpsConfigModel> list = spsMouldDao.querySpsConfigList(model);
		for (SpsConfigModel spsConfigModel : list) {
			configMap.put(spsConfigModel.getConfigCode(), spsConfigModel.getConfigCode());
		}
		//数据导入初始化，格式初步检查
		for (SpsMouldConfigModel m : importList) {
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			SpsMouldConfigModel.checkImportData(m, configMap, dictMap);
			//m.setId(UniqueIdUtil.getSuid());
			m.setMouldId(mouldId);
			m.setUuid(uuid);
			//m.setConfigShow(dictMap.get(m.getConfigShow()));
			//m.setConfigId(configMap.get(m.getConfigId()));
		}
		//导入数据写入到临时表
		spsMouldDao.insertSpsMouldConfigTempData(importList);
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", opeIp);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		spsMouldDao.checkImportData(ckParamMap);
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
	 * @Description: SPS配置列表临时表导入到正式表  
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@Override
	public void insertImportData(Map<String, String> paramMap) {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		spsMouldDao.insertImportData(paramMap);
		
		//更新临时数据导入状态
		spsMouldDao.updateImportDataImpStatus(paramMap);
	}

	/**
	 * @Description: 查询临时表数据
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@Override
	public PageList<SpsMouldConfigModel> queryImportTempPage(SpsMouldConfigModel model, DefaultPage page) {
		return spsMouldDao.queryImportTempPage(model, page);
	}

	/**
	 * @Description: 导出临时表数据 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@Override
	public List<SpsMouldConfigModel> queryImportTempList(SpsMouldConfigModel model) {
		return spsMouldDao.queryImportTempList(model);
	}

	/**
	 * @Description: 根据UUID删除临时表数据 
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		spsMouldDao.deleteImportTempDataByUUID(uuid);
	}

	/**
	 * @Description: 上传图片后保存fileId
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月22日
	 */
	@Override
	public void updateSpsMouldConfigFileId(SpsMouldConfigModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("SPS上传图片");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SPS_MOULD_CONFIG");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		spsMouldDao.updateSpsMouldConfigFileId(model);
	}

	/**
	 * @Description: 加载SPS配置项列表下拉框
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsConfigModel>   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	@Override
	public List<SpsConfigModel> querySpsConfigData(SpsConfigModel model) {
		return spsMouldDao.querySpsConfigList(model);
	}

	/**
	 * @Description: 查询校验结果是否包含不通过  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2018年12月30日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return spsMouldDao.queryIsExistsCheckResultFalse(uuid);
	}

	/**
	 * @Description: 新增票据模板  
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年1月12日
	 */
	@Override
	public void insertSpsMould(SpsMouldModel model, String ipAddr) {
		/*String mouldName = pubDataDictManager.queryDataDictCodeValueName("SPS_PR_MOULD", model.getMouldCode());
		model.setMouldName(mouldName);*/
		spsMouldDao.insertSpsMould(model);
	}

	/**
	 * @Description: 配置列表删除
	 * @param: @param models
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年1月21日
	 */
	@Override
	public void deleteSpsMouldConfig(SpsMouldConfigModel[] models, String ipAddr) {
		for (int i = 0; i < models.length; i++) {
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("SPS模板配置列表删除");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
			logVO.setTableName("MM_SPS_MOULD_CONFIG");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnVal(models[i].getId());
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			
			spsMouldDao.deleteSpsMouldConfig(models[i]);
		}
	}

	/**
	 * @Description: 新增票据模板配置列表
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月28日
	 */
	@Override
	public void insertSpsMouldConfig(SpsMouldConfigModel model, String ipAddr) {
		spsMouldDao.insertSpsMouldConfig(model);
	}

	/**
	 * @Description:  查询业务主键是否冲突 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2019年3月23日
	 */
	@Override
	public List<SpsMouldConfigModel> queryIsExists(SpsMouldConfigModel model) {
		return spsMouldDao.queryIsExists(model);
	}
	
}
