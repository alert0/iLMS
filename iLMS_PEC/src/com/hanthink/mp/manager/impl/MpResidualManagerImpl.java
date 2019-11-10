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
import com.hanthink.mp.model.MpResidualModelImport;

/**
 * 根据表名实现的类
 */
import com.hanthink.mp.controller.MpResidualImportUtil;
import com.hanthink.mp.dao.MpResidualDao;
import com.hanthink.mp.manager.MpResidualManager;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.ExcepOrderModelImport;
import com.hanthink.mp.model.MpResidualModel;
import com.hanthink.mp.model.MpResidualModelImport;
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
 * 描述：剩余量主数据 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("mpResidualManager")
public class MpResidualManagerImpl extends AbstractManagerImpl<String, MpResidualModel> implements MpResidualManager{
	@Resource
	MpResidualDao mpResidualDao;
	@Override
	protected Dao<String, MpResidualModel> getDao() {
		return mpResidualDao;
	}
	
	/**
	 * 分页查询
	 */
	 @Override
	    public PageList<MpResidualModel> queryMpResidualForPage(MpResidualModel model, DefaultPage p) {
	        return (PageList<MpResidualModel>) mpResidualDao.queryMpResidualForPage(model, p);
	    }
	
	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:52
	 */
	@Override
	public void updateAndLog(MpResidualModel mpResidualModel, String ipAddr){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_MP_RESIDUAL");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(mpResidualModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		mpResidualDao.update(mpResidualModel);
		
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
		logVO.setFromName("零件剩余量主数据删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_MP_RESIDUAL");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		//执行删除
		mpResidualDao.deleteByIds(aryIds);
	}

	/**
	 * 导入Excel数据MpResidualModel
	 * @param file
	 * @param uuid
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 上午10:27:10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importMpResidualModel(MultipartFile file, String uuid,String ipAddr, IUser user) throws Exception{
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
		List<MpResidualModelImport> importList = null;
		
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<MpResidualModelImport>) ExcelUtil.importExcelXLS(new MpResidualModelImport(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<MpResidualModelImport>) ExcelUtil.importExcelXLSX(new MpResidualModelImport(), file.getInputStream(), columns, 1, 0);
			}else{
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}
		
		
		//数据导入初始化，格式初步检查
		for(MpResidualModelImport m : importList){
			m.setId(UniqueIdUtil.getSuid());
			m.setUuid(uuid);
			m.setFactoryCode(user.getCurFactoryCode());
            m.setCreationUser(user.getAccount());
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			MpResidualModelImport.checkImportData(m);
		}
		
		//导入数据写入到临时表
		mpResidualDao.insertMpResidualImportTempData(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		mpResidualDao.checkMpResidualImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		/**
		 * 检查是否可以批量导入
		 */
		String flag = mpResidualDao.queryMpResidualIsImportFlag(uuid);
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
	public Map<String, Object> importMpResidualModel2(MultipartFile file, String uuid, String opeIp) throws Exception {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		
		//读取Excel数据
		MpResidualImportUtil excelutil = new MpResidualImportUtil(mpResidualDao, uuid);
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"partNo", "supplierNo", "supFactory", "unloadPort","manuResidual"};
		
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				excelutil.importExcel(ExcelUtil.EXCEL_XLS, new MpResidualModelImport(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				excelutil.importExcel(ExcelUtil.EXCEL_XLSX, new MpResidualModelImport(), file.getInputStream(), columns, 1, 0);
			}else{
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", opeIp);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		mpResidualDao.checkMpResidualImportData(ckParamMap);
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
	public PageList<MpResidualModelImport> queryMpResidualImportTempData(Map<String, String> paramMap, Page page) {
		return mpResidualDao.queryMpResidualImportTempData(paramMap, page);
	}
	
	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月2日 下午12:22:14
	 */
	@Override
	public List<MpResidualModelImport> queryMpResidualImportTempDataForExport(Map<String, String> paramMap) {
		return mpResidualDao.queryMpResidualImportTempDataForExport(paramMap);
	}

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月2日 下午12:22:28
	 */
	@Override
	public void insertMpResidualImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
		
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		 try {			
				List<MpResidualModelImport> list = mpResidualDao.queryForInsertList(paramMap);
				if (list.size() < 1) {
					throw new Exception("没有正确数据可导入或已全部导入");
				}
		/**
		 * 拿出Id查询哪些数据要修改
		 */
		List<String> ids = mpResidualDao.queryUpdateDataFromMpResidualImp(paramMap);
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
			mpResidualDao.updateMpResidualImportData(paramMap);
		    
		    }

		/**
		 * 导入新增的方法
		 */
		mpResidualDao.insertMpResidualImportData(paramMap);
		
		//更新临时数据导入状态
		mpResidualDao.updateMpResidualImportDataImpStatus(paramMap);
		
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
	public void deleteMpResidualImportTempDataByUUID(String uuid) {
		mpResidualDao.deleteMpResidualImportTempDataByUUID(uuid);
	}

	/**
	 * 获取计算队列下拉框
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List getUnloadPort() {
		return mpResidualDao.getUnloadPort();
	}

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpResidualModel> queryMpResidualByKey(MpResidualModel model) {
		return mpResidualDao.queryMpResidualByKey(model);
	}

	/**
	 * 判断主键冲突
	 */
	@Override
	public Integer selectPrimaryKey(MpResidualModel mpResidualModel) {
		return mpResidualDao.selectPrimaryKey(mpResidualModel);
	}

	/**
	 * 校验计算队列是否存在
	 */
	@Override
	public Integer selectUnloadPort(MpResidualModel mpResidualModel) {
		return mpResidualDao.selectUnloadPort(mpResidualModel);
	}
	
}
