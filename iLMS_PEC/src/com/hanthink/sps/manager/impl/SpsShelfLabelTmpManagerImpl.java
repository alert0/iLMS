package com.hanthink.sps.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sps.dao.SpsShelfLabelTmpDao;
import com.hanthink.sps.manager.SpsShelfLabelTmpManager;
import com.hanthink.sps.model.SpsShelfLabelTmpModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: SpsShelfLabelTmpManagerImpl
 * @Description: 货架标签打印
 * @author dtp
 * @date 2018年10月31日
 */
@Service("SpsShelfLabelTmpManager")
public class SpsShelfLabelTmpManagerImpl extends AbstractManagerImpl<String, SpsShelfLabelTmpModel> 
	implements SpsShelfLabelTmpManager{
	
	@Resource
	private SpsShelfLabelTmpDao spsShelfLabelTmpDao;
	
	@Override
	protected Dao<String, SpsShelfLabelTmpModel> getDao() {
		return spsShelfLabelTmpDao;
	}

	/**
	 * @Description: 获取导入excel数据   
	 * @param: @param file
	 * @param: @param uuid
	 * @param: @param ipAddr
	 * @param: @return    
	 * @return: Map<String,Object>   
	 * @author: dtp
	 * @date: 2018年11月1日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importSpsShelfLabelTmpModel(MultipartFile file, String uuid, String opeIp) {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"shelfNoView", "mark", "partShortNo", "partName", "partNo", "modelCode","shelfNo"};
		List<SpsShelfLabelTmpModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<SpsShelfLabelTmpModel>) ExcelUtil.importExcelXLS(new SpsShelfLabelTmpModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<SpsShelfLabelTmpModel>) ExcelUtil.importExcelXLSX(new SpsShelfLabelTmpModel(), file.getInputStream(), columns, 1, 0);
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
		for(SpsShelfLabelTmpModel m : importList){
			m.setId(UniqueIdUtil.getSuid());
			m.setImpUuid(uuid);
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
		}
		//导入数据写入到临时表
		spsShelfLabelTmpDao.insertSpsShelfLabelTmp(importList);
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("uuid", uuid);
		ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		ckParamMap.put("opeIp", opeIp);
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		spsShelfLabelTmpDao.checkImportData(ckParamMap);
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
	 * @Description: 查询导入数据 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsShelfLabelTmpModel>   
	 * @author: dtp
	 * @date: 2018年11月1日
	 */
	@Override
	public PageList<SpsShelfLabelTmpModel> querySpsShelfLabelPage(SpsShelfLabelTmpModel model, DefaultPage page) {
		return spsShelfLabelTmpDao.querySpsShelfLabelPage(model, page);
	}

	/**
	 * @Description: 获取货架标签打印信息  
	 * @param: @param model
	 * @param: @return    
	 * @return: SpsShelfLabelTmpModel   
	 * @author: dtp
	 * @date: 2018年11月8日
	 */
	@Override
	public List<SpsShelfLabelTmpModel> querySpsShelfLabelList(String[] idArr) {
		return spsShelfLabelTmpDao.querySpsShelfLabelList(idArr);
	}

	/**
	 * @Description: 根据uuid删除导入临时表数据
	 * @param: @param uuid
	 * @param: @return    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月8日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		spsShelfLabelTmpDao.deleteImportTempDataByUUID(uuid);
	}

	/**
	 * @Description: 货架标签打印导入信息导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsShelfLabelTmpModel>   
	 * @author: dtp
	 * @date: 2018年12月26日
	 */
	@Override
	public List<SpsShelfLabelTmpModel> querySpsShelfLabelList(SpsShelfLabelTmpModel model) {
		return spsShelfLabelTmpDao.querySpsShelfLabelList(model);
	}
	
	
}
