package com.hanthink.sps.manager.impl;

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
import com.hanthink.pub.manager.PubDataDictManager;
import com.hanthink.sps.dao.SpsConfigItemDao;
import com.hanthink.sps.manager.SpsConfigItemManager;
import com.hanthink.sps.model.SpsConfigDetailModel;
import com.hanthink.sps.model.SpsConfigItemModel;
import com.hanthink.sps.model.SpsMouldConfigModel;
import com.hanthink.sps.model.SpsMouldModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;


@Service("SpsConfigItemManager")
public class SpsConfigItemManagerImpl extends AbstractManagerImpl<String, SpsConfigItemModel> implements SpsConfigItemManager{
	
	@Resource
	private SpsConfigItemDao SpsConfigItemDao;
	@Resource
	private PubDataDictManager pubDataDictManager;
	
	
	@Override
	protected Dao<String, SpsConfigItemModel> getDao() {
		return SpsConfigItemDao;
	}


	/**
	 * 配置项代码导入分页查询
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@Override
	public PageList<SpsConfigItemModel> querySpsConfigItemPage(SpsConfigItemModel model, DefaultPage page) {
		return SpsConfigItemDao.querySpsConfigItemPage(model,page);
	}
	/**
	 * 配置项代码更新
	 * 
	 * @param model
	 * @return
	 */
	@Override
	public int updateConfigItem(SpsConfigItemModel model, String ipAddr) {
		//记录日志信息
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("配置项修改");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SPS_CONFIG");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		return SpsConfigItemDao.updateConfigItem(model);
		
	}
	/**
	 * 配置项代码批量删除
	 * @param arrayList
	 * @throws Exception 
	 */
	@Override
	public void deleteConfigItemByBatch(ArrayList<SpsConfigItemModel> arrayList) throws Exception {
		/*try {
			List<SpsConfigDetailModel> detailList = SpsConfigItemDao.querySpsConfigDetailList(arrayList);
			if(null != detailList && detailList.size() > 0) {
				throw new Exception("配置项代码[" + detailList.get(0).getConfigCode() + "]不可删除");
			}
			List<SpsMouldConfigModel> mouldConfigList = SpsConfigItemDao.querySpsMouldConfigList(arrayList); 
			if(null != mouldConfigList && mouldConfigList.size() > 0) {
				throw new Exception("配置项代码[" + mouldConfigList.get(0).getConfigCode() + "]不可删除");
			}
			SpsConfigItemDao.deleteConfigItemByBatch(arrayList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}*/
		
	}
	/**
	 * 配置项代码导出查询
	 * @param model
	 * @return
	 */
	@Override
	public List<SpsConfigItemModel> querySpsConfigItemList(SpsConfigItemModel model) {
		return SpsConfigItemDao.querySpsConfigItemList(model);
	}
	/**
	 * 判断配置项代码是否唯一
	 * @param model
	 * @return
	 */
	@Override
	public List<SpsConfigItemModel> queryConfigCode(SpsConfigItemModel model) {
		return SpsConfigItemDao.queryConfigCode(model);
	}
	/**
	 * 配置项代码新增或修改
	 * @param model
	 * @return
	 */
	@Override
	public int insertConfigItem(SpsConfigItemModel model) {
		return SpsConfigItemDao.insertConfigItem(model);
		
	}
	/**
	 * 配置项导入临时表
	 * @param file
	 * @param ipAddr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importSpsConfigItemTemp(MultipartFile file,String uuid ,String opeIp) {
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
		String[] columns = {"configCode", "configDesc", "configType", "configValue"};
		List<SpsConfigItemModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<SpsConfigItemModel>) ExcelUtil.importExcelXLS(new SpsConfigItemModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<SpsConfigItemModel>) ExcelUtil.importExcelXLSX(new SpsConfigItemModel(), file.getInputStream(), columns, 1, 0);
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
		Map<String, String> dictMap = pubDataDictManager.queryDataDictByCodeType("SPS_CONFIG_TYPE");
		//数据导入初始化，格式初步检查
		for(SpsConfigItemModel m : importList){
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			SpsConfigItemModel.checkImportData(m);
			IUser currentUser = ContextUtil.getCurrentUser();
			m.setCreationUser(currentUser.getAccount());
			m.setFactory(currentUser.getCurFactoryCode());
			m.setProductionLine(currentUser.getCurProductLine());
			m.setId(UniqueIdUtil.getSuid());
			m.setUuid(uuid);
			m.setConfigType(dictMap.get(m.getConfigType()));
		}
		//导入数据写入到临时表
		SpsConfigItemDao.insertSpsConfigItemTempData(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		SpsConfigItemDao.checkImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		
		//临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		return rtnMap;
		
	}
	
	
	@Override
	public Map<String,Object> spsConfigItemImportData() {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		SpsConfigItemDao.spsConfigItemImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));		
		//临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("message", String.valueOf(ckParamMap.get("errorMsg")));
		return rtnMap;
	}

	/**
	 * 导入临时数据查询
	 * @param model
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsConfigItemModel> querySpsConfigTemp(SpsConfigItemModel model, DefaultPage page) {
		return SpsConfigItemDao.querySpsConfigTemp(model,page);
	}
	
	/**
	 * 检验临时数据是否存在满足导入条件却未导入的
	 * @param model
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsConfigItemModel> querySpsConfigNotImport(SpsConfigItemModel model) {
		return SpsConfigItemDao.querySpsConfigNotImport(model);
	}
	/**
	 * 配置项代码临时数据查询
	 * @param model
	 * @return
	 */
	@Override
	public List<SpsConfigItemModel> querySpsConfigTemp(SpsConfigItemModel model) {
		return SpsConfigItemDao.querySpsConfigTempForExport(model);
	}


	/**
	 * 临时表数据删除
	 */
	@Override
	public int removeSpsConfigItemTemp() {
		
		return SpsConfigItemDao.removeSpsConfigItemTemp();
	}

	/**
	 * @Description: SPS配置向导入临时表
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	@Override
	public void insertImportData(Map<String, String> paramMap) {
		SpsConfigItemDao.insertImportData(paramMap);
		//更新导入状态
		
		SpsConfigItemDao.updateImportStatus(paramMap);
	}

	/**
	 * @Description: 通过uuid删除临时表数据
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		SpsConfigItemDao.deleteImportTempDataByUUID(uuid);
	}

	/**
	 * @Description: 批量删除配置项 
	 * @param: @param arrayList
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @throws Exception 
	 * @date: 2018年12月27日
	 */
	@Override
	public void deleteConfigItemByBatch(ArrayList<SpsConfigItemModel> arrayList, String ipAddr, 
				String[] idArr) throws Exception {
		/*List<SpsConfigDetailModel> detailList = SpsConfigItemDao.querySpsConfigDetailList(arrayList);
		if(null != detailList && detailList.size() > 0) {
			throw new Exception("配置项代码[" + detailList.get(0).getConfigCode() + "]不可删除");
		}*/
		//记录日志信息
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("配置项删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_SPS_CONFIG_DETAIL");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(idArr);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		SpsConfigItemDao.deleteConfigItemByBatch(arrayList);
		
	}

	/**
	 * @Description: 删除前判断配置项是否维护明细  
	 * @param: @param arrayList
	 * @param: @return    
	 * @return: List<SpsConfigDetailModel>   
	 * @author: dtp
	 * @date: 2018年12月28日
	 */
	@Override
	public List<SpsConfigDetailModel> querySpsConfigDetailList(ArrayList<SpsConfigItemModel> arrayList) {
		return SpsConfigItemDao.querySpsConfigDetailList(arrayList);
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
		return SpsConfigItemDao.queryIsExistsCheckResultFalse(uuid);
	}

}
