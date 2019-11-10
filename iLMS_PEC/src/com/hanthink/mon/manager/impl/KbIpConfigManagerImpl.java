package com.hanthink.mon.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.mon.manager.KbIpConfigManager;
import com.hanthink.mon.model.KbIpConfigModel;
import com.hanthink.pub.dao.PubDataDictDao;
import com.hanthink.mon.dao.KbIpConfigDao;
/**
 * 根据表名实现的类
 */
import com.hanthink.util.excel.ExcelUtil;
/**
 * 基础包
 */
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：看板IP配置 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("KbIpConfigManager")
public class KbIpConfigManagerImpl extends AbstractManagerImpl<String, KbIpConfigModel> implements KbIpConfigManager{
	@Resource
	private PubDataDictDao dataDictDao;
	
	@Resource
	KbIpConfigDao kbIpConfigDao;
	@Override
	protected Dao<String, KbIpConfigModel> getDao() {
		return kbIpConfigDao;
	}
	
	/**
	 * 分页查询
	 */
	 @Override
	    public PageList<KbIpConfigModel> queryKbIpConfigForPage(KbIpConfigModel model, Page page) {

		 	List<KbIpConfigModel> list = kbIpConfigDao.queryKbIpConfigForPage(model, page);
		 	
	        return new PageList<KbIpConfigModel>(list);
	    }
	 
	@Override
	public List<KbIpConfigModel> queryKbList() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		return kbIpConfigDao.queryKbList(factoryCode);
	}
	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 上午10:54:52
	 */
	@Override
	public void updateAndLog(KbIpConfigModel KbIpConfigModel, String ipAddr) throws Exception{
		
		if (StringUtil.isEmpty(KbIpConfigModel.getDistriPerson()) && StringUtil.isEmpty(KbIpConfigModel.getLimitDelay())
			&& StringUtil.isEmpty(KbIpConfigModel.getCombIp()) && StringUtil.isEmpty(KbIpConfigModel.getRunProcessNo())
			&& StringUtil.isEmpty(KbIpConfigModel.getStation()) && StringUtil.isEmpty(KbIpConfigModel.getShiftSchedule())) {
			throw new Exception("系统错误,请联系管理员");
		}
		if (StringUtil.isNotEmpty(KbIpConfigModel.getCombIp())) {
			boolean combIpIsInused = kbIpConfigDao.combIpIsInusedJudge(KbIpConfigModel);
			if (combIpIsInused) {
				throw new Exception("请核对该IP地址是否被占用");
			}
		}
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		logVO.setTableName("MM_KB_IP_CONFIG");
		pkColumnVO.setColumnVal(KbIpConfigModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		StringBuilder sql = new StringBuilder();
		int count = 0;
		sql.append("UPDATE MM_KB_IP_CONFIG MKI SET ");
		if (StringUtil.isNotEmpty(KbIpConfigModel.getDistriPerson())) {
			count += 1;
			sql.append("MKI.DISTRI_PERSON = '").append(KbIpConfigModel.getDistriPerson()).append("'");
		}
		if (StringUtil.isNotEmpty(KbIpConfigModel.getLimitDelay())) {
			if (count == 0) {
				count += 1;
				sql.append("MKI.LIMIT_DELAY = '").append(KbIpConfigModel.getLimitDelay()).append("'");				
			}else {
				count += 1;
				sql.append(", MKI.LIMIT_DELAY = '").append(KbIpConfigModel.getLimitDelay()).append("'");
			}
		}
		if (StringUtil.isNotEmpty(KbIpConfigModel.getRunProcessNo())) {
			if (count == 0) {
				count += 1;
				sql.append("MKI.RUN_PROCESS_NO = '").append(KbIpConfigModel.getRunProcessNo()).append("'");				
			}else {
				count += 1;
				sql.append(", MKI.RUN_PROCESS_NO = '").append(KbIpConfigModel.getRunProcessNo()).append("'");				
			}
		}
		if (StringUtil.isNotEmpty(KbIpConfigModel.getCombIp())) {
			if (count == 0) {
				count += 1;
				sql.append("MKI.MBPS_COMB = '").append(KbIpConfigModel.getCombIp()).append("'");
			}else {
				count += 1;
				sql.append(", MKI.MBPS_COMB = '").append(KbIpConfigModel.getCombIp()).append("'");
			}
		}
		if (StringUtil.isNotEmpty(KbIpConfigModel.getShiftSchedule())) {
			if (count == 0) {
				count += 1;
				sql.append("MKI.SHIT_SCHEDULE = '").append(KbIpConfigModel.getShiftSchedule()).append("'");				
			}else {
				count += 1;
				sql.append(", MKI.SHIT_SCHEDULE = '").append(KbIpConfigModel.getShiftSchedule()).append("'");				
			}
		}
		if (StringUtil.isNotEmpty(KbIpConfigModel.getStation())) {
			if (count == 0) {
				count += 1;
				sql.append("MKI.STATION_CODE = '").append(KbIpConfigModel.getStation()).append("'");
			}else {
				count += 1;
				sql.append(", MKI.STATION_CODE = '").append(KbIpConfigModel.getStation()).append("'");
			}
		}
		sql.append(", MKI.LAST_MODIFIED_USER = '").append(KbIpConfigModel.getLastModifiedUser()).append("'");
		sql.append(", MKI.LAST_MODIFIED_IP = '").append(KbIpConfigModel.getLastModifiedIp()).append("'");
		sql.append(", MKI.LAST_MODIFIED_TIME = SYSDATE");
		sql.append(" WHERE MKI.ID = '").append(KbIpConfigModel.getId()).append("'");
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("updateSQL", sql.toString());
		try {
			kbIpConfigDao.updateKbConfigData(paramMap);				
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月4日 上午11:01:08
	 */
	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception{
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("看板IP配置删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_KB_IP_CONFIG");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		//执行删除
		kbIpConfigDao.deleteByIds(aryIds);
	}

	/**
	 * 导入Excel数据KbIpConfigModel
	 * @param file
	 * @param uuid
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 上午10:27:10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importKbIpConfigModel(MultipartFile file, String uuid,String ipAddr, IUser user) throws Exception{
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
		String[] columns = {"partNo", "supplierNo", "supFactory", "unloadPort","manuResidual"};
		List<KbIpConfigModel> importList = null;
		
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<KbIpConfigModel>) ExcelUtil.importExcelXLS(new KbIpConfigModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<KbIpConfigModel>) ExcelUtil.importExcelXLSX(new KbIpConfigModel(), file.getInputStream(), columns, 1, 0);
			}else{
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}
		
		
		//数据导入初始化，格式初步检查
		for(KbIpConfigModel m : importList){
			m.setId(UniqueIdUtil.getSuid());
//			m.setUuid(uuid);
//			m.setFactoryCode(user.getCurFactoryCode());
//            m.setCreationUser(user.getAccount());
//			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
//			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
//			KbIpConfigModel.checkImportData(m);
		}
		
		//导入数据写入到临时表
		kbIpConfigDao.insertKbIpConfigImportTempData(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		kbIpConfigDao.checkKbIpConfigImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		/**
		 * 检查是否可以批量导入
		 */
		String flag = kbIpConfigDao.queryKbIpConfigIsImportFlag(uuid);
		//临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		rtnMap.put("flag", flag);
		return rtnMap;
	}

	/**
	 * 查询导入临时表数据
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 下午12:22:14
	 */
	@Override
	public PageList<KbIpConfigModel> queryKbIpConfigImportTempData(Map<String, String> paramMap, Page page) {
		return kbIpConfigDao.queryKbIpConfigImportTempData(paramMap, page);
	}
	
	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 下午12:22:14
	 */
	@Override
	public List<KbIpConfigModel> queryKbIpConfigImportTempDataForExport(Map<String, String> paramMap) {
		return kbIpConfigDao.queryKbIpConfigImportTempDataForExport(paramMap);
	}

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月2日 下午12:22:28
	 */
	@Override
	public void insertKbIpConfigImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
		
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		 try {			
				List<KbIpConfigModel> list = kbIpConfigDao.queryForInsertList(paramMap);
				if (list.size() < 1) {
					throw new Exception("没有正确数据可导入或已全部导入");
				}
		/**
		 * 拿出Id查询哪些数据要修改
		 */
		List<String> ids = kbIpConfigDao.queryUpdateDataFromKbIpConfigImp(paramMap);
		if(ids.size()>0) {
			/**
			 * 声明一个String数组，用于存放List
			 */
			String[] idp = ids.toArray(new String[ids.size()]);
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("导入更新");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_MP_RESIDUAL");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(idp);
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
			
			/**
			 * 导入修改的方法
			 */
			kbIpConfigDao.updateKbIpConfigImportData(paramMap);
		    
		    }

		/**
		 * 导入新增的方法
		 */
		kbIpConfigDao.insertKbIpConfigImportData(paramMap);
		
		//更新临时数据导入状态
		kbIpConfigDao.updateKbIpConfigImportDataImpStatus(paramMap);
		
		 }catch (Exception e) {
				e.printStackTrace();
				throw new Exception("系统错误，请联系管理员");
				
			}
	}

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public void deleteKbIpConfigImportTempDataByUUID(String uuid) {
		kbIpConfigDao.deleteKbIpConfigImportTempDataByUUID(uuid);
	}
	
	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<KbIpConfigModel> queryKbIpConfigByKey(KbIpConfigModel model) {
		return kbIpConfigDao.queryKbIpConfigByKey(model);
	}

	/**
	 * 判断主键冲突（主表）
	 */
	@Override
	public Integer selectPrimaryKey(KbIpConfigModel KbIpConfigModel) {
		return kbIpConfigDao.selectPrimaryKey(KbIpConfigModel);
	}

	/**
	 * 判断主键冲突（明细）
	 */
	@Override
	public Integer selectPrimaryKeyDetail(KbIpConfigModel kbIpConfigModel) {
		return kbIpConfigDao.selectPrimaryKeyDetail(kbIpConfigModel);
	}
	
	/**
	 * 新增明细
	 */
	@Override
	public void createDetail(KbIpConfigModel kbIpConfigModel) {
		kbIpConfigDao.createDetail(kbIpConfigModel);
	}

	@Override
	public Integer getCurrBussId() throws Exception {
		return kbIpConfigDao.getCurrBussId();
	}

	@Override
	public PageList<KbIpConfigModel> queryKbTypeForPage(KbIpConfigModel model, Page page) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<KbIpConfigModel> list = kbIpConfigDao.queryKbTypeForPage(model,page);
		return new PageList<KbIpConfigModel>(list);
	}

	@Override
	public void saveKbType(KbIpConfigModel kbIpConfigModel, String ipAddr) throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		kbIpConfigModel.setFactoryCode(factoryCode);
		String userAccount = ContextUtil.getCurrentUser().getAccount();
		kbIpConfigModel.setLastModifiedIp(ipAddr);
		kbIpConfigModel.setLastModifiedUser(userAccount);
		
		String kbId = kbIpConfigModel.getKbCode();
		kbIpConfigModel.setKbId(kbId);
		
		if (StringUtil.isEmpty(kbIpConfigModel.getId())) {
			kbIpConfigModel.setCreationUser(userAccount);
			boolean isExist = kbIpConfigDao.queryForMaxSortNum(kbIpConfigModel);
			if (isExist) {
				throw new Exception("该IP地址已被占用");
			}
			String nextIdNum = kbIpConfigDao.getNextIdNum("SEQ_MM_KB_IP_CONFIG");
			kbIpConfigModel.setId(nextIdNum);
			try {
				kbIpConfigDao.saveBasicKbInfo(kbIpConfigModel);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("系统错误,请联系管理员");
			}
		}else {
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(userAccount);
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("看板IP配置修改");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_KB_IP_CONFIG");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnVal(kbIpConfigModel.getId());
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			
			try {
				kbIpConfigDao.updateKbType(kbIpConfigModel);	
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("系统错误,请联系管理员");
			}
		}
	}
}
