package com.hanthink.sw.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sw.dao.SwVentureReceiveDao;
import com.hanthink.sw.manager.SwVentureReceiveManager;
import com.hanthink.sw.model.SwVentureReceiveModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;

@Service("SwVentureReceive")
public class SwVentureReceiveManagerImpl extends AbstractManagerImpl<SwVentureReceiveModel, String>
					implements SwVentureReceiveManager{
	
	@Resource
	private SwVentureReceiveDao receiveDao;
	
	@Override
	protected Dao<SwVentureReceiveModel, String> getDao() {
		return receiveDao;
	}
	/**
	 * 合资车收货数据界面查询
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2019年8月21日
	 */
	@Override
	public PageList<SwVentureReceiveModel> querySwVentureReceiveForPage(SwVentureReceiveModel model, Page page) {
		List<SwVentureReceiveModel> list = receiveDao.querySwVentureReceiveForPage(model, page);
		return new PageList<SwVentureReceiveModel>(list);
	}
	/**
	 * 合资车收货数据界面导出
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月21日
	 */
	@Override
	public List<SwVentureReceiveModel> exportForQueryPage(SwVentureReceiveModel model) throws Exception {
		return receiveDao.exportForQueryPage(model);
	}
	/**
	 * 合资车收货数据导入删除上一次导入数据
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月21日
	 */
	@Override
	public void deleteLastTimeImportExcel(String uuid) throws Exception {
		receiveDao.deleteLastTimeImportExcel(uuid);
	}
	/**
	 * 合资车收货数据导入
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 * @author zmj
	 * @date 2019年8月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importReceiveExcel(MultipartFile file, String uuid, String ipAddr, IUser user)
			throws Exception {
		String factoryCode = user.getCurFactoryCode();
		Map<String, Object> resultMap = new HashMap<>();
		boolean result = true;
		String console = null;
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			resultMap.put("result", result);
			resultMap.put("console", console);
			return resultMap;
		}
		
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"ordPlace", "ordOrderNo", "ordOrderRowNo", "partNo", "recQty", "recTime"};
		List<SwVentureReceiveModel> importList = null;
		
		if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
			importList = (List<SwVentureReceiveModel>) ExcelUtil.importExcelXLS(new SwVentureReceiveModel(), file.getInputStream(), columns, 1, 0);
		}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
			importList = (List<SwVentureReceiveModel>) ExcelUtil.importExcelXLSX(new SwVentureReceiveModel(), file.getInputStream(), columns, 1, 0);
		}else{
			result = false;
			console = "上传文件不是excel类型！";
			resultMap.put("result", result);
			resultMap.put("console", console);
			return resultMap;
		}
		
		for (SwVentureReceiveModel swVentureReceiveModel : importList) {
			swVentureReceiveModel.setFactoryCode(factoryCode);
			swVentureReceiveModel.setImpUUID(uuid);
			swVentureReceiveModel.setCreationUser(user.getAccount());
			swVentureReceiveModel.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			swVentureReceiveModel.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
		}
		//将数据写入临时表
		receiveDao.importReceiveExcel(importList);
		Map<String, Object> checkMap = new HashMap<String, Object>();
		checkMap.put("uuid", uuid);
		checkMap.put("factoryCode", factoryCode);
		checkMap.put("result", null);
		checkMap.put("console", null);
		if (true == result) {
			//校验本次导入的数据
			resultMap = receiveDao.checkImportReceiveExcel(checkMap);
		}
		
		return resultMap;
	}
	@Override
	public PageList<SwVentureReceiveModel> queryImportForPage(String uuid, Page page) {
		List<SwVentureReceiveModel> list = receiveDao.queryImportForPage(uuid, page);
		return new PageList<SwVentureReceiveModel>(list);
	}
	/**
	 * 确认导入数据
	 * @param paramMap
	 * @author zmj
	 * @date 2019年8月22日
	 */
	@Override
	public void isImportForRecNum(Map<String, Object> paramMap) {
		receiveDao.isImportForRecNum(paramMap);
	}
	/**
	 * 检查导入数据是否全部校验通过
	 * @param uuid
	 * @return
	 * @author zmj
	 * @date 2019年8月22日
	 */
	@Override
	public Boolean checkImportCount(String uuid) {
		return receiveDao.checkImportCount(uuid);
	}
	/**
	 * 导出导入数据
	 * @param uuid
	 * @return
	 * @author zmj
	 * @date 2019年8月22日
	 */
	@Override
	public List<SwVentureReceiveModel> queryRecImportForExport(String uuid) {
		return receiveDao.queryRecImportForExport(uuid);
	}
	/**
	 * 收货数据审核
	 * @author zmj
	 * @param paramMap 
	 * @date 2019年8月22日
	 */
	@Override
	public void checkRecForUploadAll(Map<String, Object> paramMap) {
		receiveDao.checkRecForUploadAll(paramMap);
	}

}
