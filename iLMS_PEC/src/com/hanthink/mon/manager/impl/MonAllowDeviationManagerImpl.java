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
import com.hanthink.mon.dao.MonAllowDeviationDao;
import com.hanthink.mon.manager.MonAllowDeviationManager;
import com.hanthink.mon.model.MonAllowDeviationModel;
import com.hanthink.mon.model.MonAllowDeviationModelImport;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: AllowDeviationManagerImpl
 * @Description: 允许误差查询
 * @author Midnight
 * @date 2018年11月03日
 */
@Service("allowDeviationManager")
public class MonAllowDeviationManagerImpl extends AbstractManagerImpl<String, MonAllowDeviationModel> implements MonAllowDeviationManager {

	@Resource
	private MonAllowDeviationDao allowDeviationDao;

	@Override
	protected Dao<String, MonAllowDeviationModel> getDao() {
		return allowDeviationDao;
	}

	/**
	 * @Description: 允许误差查询
	 * @param: model
	 * @param: page
	 * @param: @return
	 * @return: PageList<AllowDeviationModel>
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	public PageList<MonAllowDeviationModel> queryAllowDeviationPage(MonAllowDeviationModel model, DefaultPage page) {
		return allowDeviationDao.queryAllowDeviationPage(model, page);
	}

	/**
	 * 更新数据并记录日志
	 * @param model
	 * @param ipAddr
	 * @author Midnight
	 * @date 2018年11月03日
	 */
	public void updateAndLog(MonAllowDeviationModel model, String ipAddr){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("允许差异数据更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_MON_ALLOW_DEVIATION");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		allowDeviationDao.update(model);
		
	}
	
	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author Midnight
	 * @date 2018年11月03日
	 * @throws Exception 
	 */
	public void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception{
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("允许差异数据删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_MON_ALLOW_DEVIATION");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		//执行删除
		allowDeviationDao.deleteByIds(aryIds);
	}
	
	/**
	 * @Description: 允许误差导出查询
	 * @param: model
	 * @param: page
	 * @param: @return
	 * @return: PageList<AllowDeviationModel>
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	public List <MonAllowDeviationModel> queryAllowDeviationForExport(MonAllowDeviationModel model) {
		return allowDeviationDao.queryAllowDeviationForExport(model);
	}
	
	
	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	@Override
	public void deleteAllowDeviationImportTempDataByUUID(String uuid) {
		allowDeviationDao.deleteAllowDeviationImportTempDataByUUID(uuid);
	}

	/**
	 * 导入数据至临时表
	 * @param file 
	 * @param uuid
	 * @param ipAddr
	 * @param user
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importAllowDeviationTemp(MultipartFile file, String uuid, String ipAddr, IUser user) {
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
		String[] columns = {"routeCode", "errorDate"};
		List<MonAllowDeviationModelImport> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<MonAllowDeviationModelImport>) ExcelUtil.importExcelXLS(new MonAllowDeviationModelImport(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<MonAllowDeviationModelImport>) ExcelUtil.importExcelXLSX(new MonAllowDeviationModelImport(), file.getInputStream(), columns, 1, 0);
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
		for(MonAllowDeviationModelImport m : importList){
			m.setId(UniqueIdUtil.getSuid());
			m.setUuid(uuid);
			m.setFactoryCode(user.getCurFactoryCode());
            m.setCreationUser(user.getAccount());
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			MonAllowDeviationModelImport.checkImportData(m);
		}
		
		//导入数据写入到临时表
		allowDeviationDao.insertMonAllowDeviationImportTempData(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", ipAddr);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		//检查数据准确性
		allowDeviationDao.checkMonAllowDeviationImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}
		/**
		 * 检查是否可以批量导入
		 */
		String flag = allowDeviationDao.queryMonAllowDeviationImportFlag(uuid);
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
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	@Override
	public PageList<MonAllowDeviationModelImport> queryAllowDeviationImportTempData(Map<String, String> paramMap,
			Page page) {
		 PageList<MonAllowDeviationModelImport> temp=allowDeviationDao.queryMonAllowDeviationImportTempData(paramMap, page);
		 return temp;
	}

	/**
	 * 确定导入，将临时表数据写入到正式表
	 * @param paramMap
	 * @throws Exception 
	 * @author Midnight
	 * @date 2018年11月11日
	 */
	@Override
	public void insertAllowDeviationImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		 try {			
				List<MonAllowDeviationModelImport> list = allowDeviationDao.queryForInsertList(paramMap);
				if (list.size() < 1) {
					throw new Exception("没有正确数据可导入或已全部导入");
				}
		//查询需要修改的数据
		List<String> ids = allowDeviationDao.queryUpdateDataFromMonAllowDeviationImp(paramMap);
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
			logVO.setTableName("MM_MON_ALLOW_DEVIATION");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(ids.toArray(idp));
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
			
			//修改数据
			allowDeviationDao.updateMonAllowDeviationImportData(paramMap);
		    
		    }
		//新增数据
		allowDeviationDao.insertMonAllowDeviationImportData(paramMap);;
		
		//更新临时数据导入状态
		allowDeviationDao.updateMonAllowDeviationImportDataImpStatus(paramMap);
		
		 }catch (Exception e) {
				e.printStackTrace();
				throw new Exception("导入失败");
				
			}
	}

	@Override
	public List<MonAllowDeviationModelImport> queryAllowDeviationImportTempDataForExport(Map<String, String> paramMap) {
		return allowDeviationDao.queryAllowDeviationImportTempDataForExport(paramMap);
	}

	/**
	  * @Description: 判断集货路线是否存在 
	  * @param: @param model
	  * @param: @return    
	  * @return: List<MonAllowDeviationModel>   
	  * @author: dtp
	  * @date: 2018年11月27日
	  */
	@Override
	public List<MonAllowDeviationModel> queryIsExist(MonAllowDeviationModel model) {
		return allowDeviationDao.queryIsExist(model);
	}


}
