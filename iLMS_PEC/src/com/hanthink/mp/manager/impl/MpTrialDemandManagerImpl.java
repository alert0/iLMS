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
import com.hanthink.mp.model.MpTrialDemandModelImport;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.mp.dao.MpTrialDemandDao;
import com.hanthink.mp.manager.MpTrialDemandManager;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.ExcepOrderModelImport;
import com.hanthink.mp.model.MpTrialDemandModel;
import com.hanthink.util.excel.ExcelUtil;

/**
 * 基础包
 */
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：新车型需求计算 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpTrialDemandManager")
public class MpTrialDemandManagerImpl extends AbstractManagerImpl<String, MpTrialDemandModel> implements MpTrialDemandManager{
	@Resource
	MpTrialDemandDao mpTrialDemandDao;
	@Override
	protected Dao<String, MpTrialDemandModel> getDao() {
		return mpTrialDemandDao;
	}
	
	 @Override
	    public PageList<MpTrialDemandModel> queryMpTrialDemandForPage(MpTrialDemandModel model, DefaultPage p) {
	        return (PageList<MpTrialDemandModel>) mpTrialDemandDao.queryMpTrialDemandForPage(model, p);
	    }
	
	/**
	 * 更新数据并记录日志
	 * @param MpTrialDemandModel
	 * @param ipAddr
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:52
	 */
	@Override
	public void updateAndLog(MpTrialDemandModel MpTrialDemandModel, String ipAddr){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("新车型需求计算更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_MP_TRIAL_DEMAND");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(MpTrialDemandModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		mpTrialDemandDao.update(MpTrialDemandModel);
		
	}
	
	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author linzhuo	
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
		logVO.setTableName("MM_MP_TRIAL_DEMAND");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		super.removeByIds(aryIds);
	}

	/**
	 * 导入Excel数据MpTrialDemand
	 * @param file
	 * @param uuid
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 上午10:27:10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importMpTrialDemandModel(MultipartFile file, String uuid,String ipAddr, IUser user) throws Exception {
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
		String[] columns = { "supFactory", "partNo", "partShortNo", "supplierNo", "supplierName",
				"arriveTimeStr","orderNum","purchaseType","excOrderNum","totalOrderNum"};
		List<MpTrialDemandModelImport> importList = null;
		
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<MpTrialDemandModelImport>) ExcelUtil.importExcelXLS(new MpTrialDemandModelImport(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<MpTrialDemandModelImport>) ExcelUtil.importExcelXLSX(new MpTrialDemandModelImport(), file.getInputStream(), columns, 1, 0);
			}else{
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}
		
		//数据导入初始化，格式初步检查
		for(MpTrialDemandModelImport model : importList){
			/**
			 * 获取当前登录人作为临时表数据创建人
			 */
			model.setFactoryCode(user.getCurFactoryCode());
			model.setCreationUser(user.getAccount());
			model.setLastModifiedUser(user.getAccount());
			/**
			 * 校验字段
			 */
			model.setUuid(uuid);
            model.setCreationUser(user.getAccount());
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			model.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			MpTrialDemandModelImport.checkImportData(model);
		}
		
		//导入数据写入到临时表
		mpTrialDemandDao.insertMpTrialDemandImportTempData(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		mpTrialDemandDao.checkMpTrialDemandImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		/**
		 * 检查是否可以批量导入
		 */
		String flag = mpTrialDemandDao.queryMpTrialDemandIsImportFlag(uuid);
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
	public PageList<MpTrialDemandModelImport> queryMpTrialDemandImportTempData(Map<String, String> paramMap, Page page) {
		return mpTrialDemandDao.queryMpTrialDemandImportTempData(paramMap, page);
	}
	
	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 下午12:22:14
	 */
	@Override
	public List<MpTrialDemandModelImport> queryMpTrialDemandImportTempDataForExport(Map<String, String> paramMap) {
		return mpTrialDemandDao.queryMpTrialDemandImportTempDataForExport(paramMap);
	}

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月2日 下午12:22:28
	 */
	@Override
	public void insertMpTrialDemandImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
		
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		
			List<MpTrialDemandModelImport> list = mpTrialDemandDao.queryForInsertList(paramMap);
			if (list.size() < 1) {
				throw new Exception("没有正确数据可导入或已全部导入");
			}
		
		/**
		 * 拿出Id查询哪些数据要修改
		 */
		List<String> ids = mpTrialDemandDao.queryUpdateDataFromMpTrialDemandImp(paramMap);
		if(ids.size()>0) {
			/**
			 * 声明一个String数组，用于存放List
			 */
			String[] idp = new String[ids.size()];
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("导入更新");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_MP_TRIAL_DEMAND");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(ids.toArray(idp));
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			
			/**
			 * 导入修改的方法
			 */
			mpTrialDemandDao.updateMpTrialDemandImportData(paramMap);
		}
		/**
		 * 导入新增的方法
		 */
		mpTrialDemandDao.insertMpTrialDemandImportData(paramMap);
		
		//更新临时数据导入状态
		mpTrialDemandDao.updateMpTrialDemandImportDataImpStatus(paramMap);
	
	}

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public void deleteMpTrialDemandImportTempDataByUUID(String uuid) {
		mpTrialDemandDao.deleteMpTrialDemandImportTempDataByUUID(uuid);
	}

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpTrialDemandModel> queryMpTrialDemandByKey(MpTrialDemandModel model) {
		return mpTrialDemandDao.queryMpTrialDemandByKey(model);
	}

	/**
	 * 需求计算生成
	 */
	@Override
	public Integer generateMpTrialDemand(String curFactoryCode) {
		return mpTrialDemandDao.generateMpTrialDemand(curFactoryCode);	
	}

	/**
	 * 需求计算发布
	 */
	@Override
	public Integer releaseMpTrialDemand(String curFactoryCode) {
		return mpTrialDemandDao.releaseMpTrialDemand(curFactoryCode);
	}
	
	
}
