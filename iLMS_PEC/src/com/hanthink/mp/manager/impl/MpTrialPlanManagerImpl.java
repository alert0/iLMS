package com.hanthink.mp.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.mp.model.MpTrialPlanModelImport;

/**
 * 根据表名实现的类
 */
import com.hanthink.mp.controller.MpTrialPlanImportUtil;
import com.hanthink.mp.dao.MpTrialPlanDao;
import com.hanthink.mp.manager.MpTrialPlanManager;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.ExcepOrderModelImport;
import com.hanthink.mp.model.MpResidualModelImport;
import com.hanthink.mp.model.MpTrialPlanModel;
import com.hanthink.mp.model.MpTrialPlanModelImport;
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
import com.hotent.sys.persistence.dao.SysTypeDao;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：新车型计划维护 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpTrialPlanManager")
public class MpTrialPlanManagerImpl extends AbstractManagerImpl<String, MpTrialPlanModel> implements MpTrialPlanManager{
	@Resource
	MpTrialPlanDao mpTrialPlanDao;
	@Resource
    SysTypeDao sysTypeDao;

	@Override
	protected Dao<String, MpTrialPlanModel> getDao() {
		return mpTrialPlanDao;
	}
	
	public SysTypeDao getSysTypeDao() {
		return sysTypeDao;
	}



	public void setSysTypeDao(SysTypeDao sysTypeDao) {
		this.sysTypeDao = sysTypeDao;
	}



	@Override
	    public PageList<MpTrialPlanModel> queryMpTrialPlanForPage(MpTrialPlanModel model, DefaultPage p) {
	        return (PageList<MpTrialPlanModel>) mpTrialPlanDao.queryMpTrialPlanForPage(model, p);
	    }
	
	/**
	 * 导入Excel数据Demo
	 * @param file
	 * @param uuid
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 上午10:27:10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importMpTrialPlanModel(MultipartFile file, String uuid,String ipAddr, IUser user) throws Exception{
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
		String[] columns = { "sortId","afoffDateStr","weonTimeStr","carType","proPhase","orderNo"};
		List<MpTrialPlanModelImport> importList = null;
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<MpTrialPlanModelImport>) ExcelUtil.importExcelXLS(new MpTrialPlanModelImport(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<MpTrialPlanModelImport>) ExcelUtil.importExcelXLSX(new MpTrialPlanModelImport(), file.getInputStream(), columns, 1, 0);
			}else{
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}
		
		/**
		 * 转化生产阶段
		 */
		List<DictVO> phaseList = sysTypeDao.queryPubDataDictByCodeType("PUB_PHASE");
		
		//数据导入初始化，格式初步检查
		for(MpTrialPlanModelImport model : importList){
			model.setId(UniqueIdUtil.getSuid());
			model.setUuid(uuid);
			model.setFactoryCode(user.getCurFactoryCode());
            model.setCreationUser(user.getAccount());
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			model.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			
			if (phaseList != null && phaseList.size() > 0) {
	            for (DictVO d : phaseList) {
	                if (d.getValueName() != null && d.getValueName().equals(model.getProPhase())) {
	                	model.setProPhase(d.getValueKey());
	                }
	            }
	        }
			
			MpTrialPlanModelImport.checkImportData(model);
		}
		
		//导入数据写入到临时表
		mpTrialPlanDao.insertMpTrialPlanImportTempData(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		mpTrialPlanDao.checkMpTrialPlanImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		/**
		 * 检查是否可以批量导入
		 */
		String flag = mpTrialPlanDao.queryMpTrialPlanIsImportFlag(uuid);
		//临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		rtnMap.put("flag", flag);
		return rtnMap;
	}
	
	/**
	 * 大数据量Excel导入Demo
	 * @param file
	 * @param uuid
	 * @param opeIp
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月4日 上午9:34:40
	 */
	@Override
	public Map<String, Object> importMpTrialPlanModel2(MultipartFile file, String uuid, String opeIp) {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		
		//读取Excel数据
		MpTrialPlanImportUtil excelutil = new MpTrialPlanImportUtil(mpTrialPlanDao, uuid);
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"sortId", "afoffDateStr", "carType", "proPhase","orderNo"};
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				excelutil.importExcel(ExcelUtil.EXCEL_XLS, new MpTrialPlanModelImport(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				excelutil.importExcel(ExcelUtil.EXCEL_XLSX, new MpTrialPlanModelImport(), file.getInputStream(), columns, 1, 0);
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
		mpTrialPlanDao.checkMpTrialPlanImportData(ckParamMap);
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
	 * @author linzhuo	
	 * @DATE	2018年9月2日 下午12:22:14
	 */
	@Override
	public PageList<MpTrialPlanModelImport> queryMpTrialPlanImportTempData(Map<String, String> paramMap, Page page) {
		return mpTrialPlanDao.queryMpTrialPlanImportTempData(paramMap, page);
	}
	
	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 下午12:22:14
	 */
	@Override
	public List<MpTrialPlanModelImport> queryMpTrialPlanImportTempDataForExport(Map<String, String> paramMap) {
		return mpTrialPlanDao.queryMpTrialPlanImportTempDataForExport(paramMap);
	}

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月2日 下午12:22:28
	 */
	@Override
	public void insertMpTrialPlanImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
		
		/**
		 * 查询出主表中最大的sortId
		 */
		Integer sortIdMax = mpTrialPlanDao.selectMaxSortId();
		if(sortIdMax==null) {
			sortIdMax = 0;
		}
		paramMap.put("sortIdMax", sortIdMax);
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		 try {			
				List<MpTrialPlanModelImport> list = mpTrialPlanDao.queryForInsertList(paramMap);
				if (list.size() < 1) {
					throw new Exception("没有正确数据可导入或已全部导入");
				}
		/**
		 * 拿出Id查询哪些数据是未订购的
		 */
		List<String> ids =   mpTrialPlanDao.querySortIdAndLogByCalStatus();
		if(ids.size()>0) {
			/**
			 * 声明一个String数组，用于存放List
			 */
			String[] idp = new String[ids.size()];
			ids.toArray(idp);
			/**
			 * 删除未订购的方法
			 */
			mpTrialPlanDao.removeAndLogByCalStatus(idp, ipAddr);
		}
		/**
		 * 导入新增的方法
		 */
		mpTrialPlanDao.insertMpTrialPlanImportData(paramMap);
		
		/**
		 * 更新临时数据导入状态
		 */
		mpTrialPlanDao.updateMpTrialPlanImportDataImpStatus(paramMap);
		
		 }catch (Exception e) {
				e.printStackTrace();
				throw new Exception("导入失败");
				
			}
	}

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public void deleteMpTrialPlanImportTempDataByUUID(String uuid) {
		mpTrialPlanDao.deleteMpTrialPlanImportTempDataByUUID(uuid);
	}

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpTrialPlanModel> queryMpTrialPlanByKey(MpTrialPlanModel model) {
		return mpTrialPlanDao.queryMpTrialPlanByKey(model);
	}

	/**
	 * 查询未订购数据的SortId用于记录日志
	 * @param 
	 * @return
	 */
	@Override
	public List<String> querySortIdAndLogByCalStatus() {
		return mpTrialPlanDao.querySortIdAndLogByCalStatus();
	}

	/**
	 * 直接删除未订购数据
	 * @param model
	 * @return
	 */
	@Override
	public void removeAndLogByCalStatus(List<String> listIds,String ipAddr) {
		//日志记录
				TableOpeLogVO logVO = new TableOpeLogVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(ipAddr); 
				logVO.setFromName("界面更新");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
				logVO.setTableName("MM_MP_TRIAL_PLAN");
				TableColumnVO pkColumnVO = new TableColumnVO();
				pkColumnVO.setColumnName("SORT_ID");
				/**
				 * 集合转数组
				 */
				String[] aryIds = new String[listIds.size()];
				listIds.toArray(aryIds);
				pkColumnVO.setColumnValArr(aryIds);
				this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
				mpTrialPlanDao.removeAndLogByCalStatus(aryIds, ipAddr);
		
	}

	/**
	 * 获取新车型计划
	 */
	@Override
	public Integer getMpTrialPlan(String curFactoryCode) {
		return mpTrialPlanDao.getMpTrialPlan(curFactoryCode);
	}
	
	
}
