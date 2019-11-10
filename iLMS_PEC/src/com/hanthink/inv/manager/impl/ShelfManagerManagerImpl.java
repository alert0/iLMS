package com.hanthink.inv.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.inv.dao.ShelfManagerDao;
import com.hanthink.inv.manager.ShelfManagerManager;
import com.hanthink.inv.model.ShelfManager;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("ShelfManager")
public class ShelfManagerManagerImpl extends AbstractManagerImpl<String, ShelfManager> implements ShelfManagerManager{

	@Resource
	private ShelfManagerDao shelfManagerDao;
	
	/**
	 * 货架数据分页查询
	 * @param model
	 * @param p
	 * @return
	 * 李兴辉
	 */
	@Override
	public PageList<ShelfManager> queryShelfManagerForPage(
			ShelfManager model, DefaultPage p) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return shelfManagerDao.querySelfManagerForPage(model,p);
	}

	
	@Override
	protected Dao<String, ShelfManager> getDao() {
		return shelfManagerDao;
	}

	/**
	 * 查询打印数据
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public List<ShelfManager> queryexportList(ShelfManager model)throws Exception {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			return shelfManagerDao.querySelfManagerExport(model);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	
	/**
	 * 货架数据查询导出
	 * @param model
	 * @return
	 * 李兴辉
	 */
	@Override
	public List<ShelfManager> queryPrintModel(ShelfManager model) {
		return shelfManagerDao.queryPrintModel(model);
	}
	
	/**
	 * 新增货架数据
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public boolean insertShelvesManager(ShelfManager model) {
		return shelfManagerDao.insertModel(model);
	}
	
	/**
	 * 新增时验证供应商和零件对应关系是否存在
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public boolean checkPartNoAndSupplierNoIsMaintain(ShelfManager model) {
		return shelfManagerDao.checkPartNoAndSupplierNoIsMaintain(model);
	}
	
	/**
	 * 修改货架数据
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public boolean updateShelvesManager(ShelfManager model) {
		return shelfManagerDao.updateShelvesManager(model);
	}

	/**
	 * 删除货架数据
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public boolean removeShelvesManager(ShelfManager model) {
		return shelfManagerDao.removeShelvesManager(model);
	}

	/**
	 * 分页查询导入的临时数据
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	@Override
	public PageList<ShelfManager> queryShelfManagerForPageTemp(ShelfManager model, DefaultPage p) {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		return shelfManagerDao.queryShelfManagerForPageTemp(model,p);
	}
	
	/**
	 * excel导入临时数据
	 * @param file
	 * @return
	 * Lxh
	 */
	@Override
	public Map<String, Object> importShelfManagerTemp(MultipartFile file) {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
//		if (!"货架标签信息管理导入模板".equals(fileName)) {
//			result = false;
//			rtnMap.put("result", result);
//			rtnMap.put("console", "导入模板错误，请校验");
//			return rtnMap;
//		}
		//读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"partNo", "partName", "partShortNo","supplierNo", "supplierName",
				"shelvesAddr","standardPack","safeStock","carType","stackLayers"};
		List<ShelfManager> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<ShelfManager>) ExcelUtil.importExcelXLS(new ShelfManager(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<ShelfManager>) ExcelUtil.importExcelXLSX(new ShelfManager(), file.getInputStream(), columns, 1, 0);
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
		//数据导入初始化，检查
		for(ShelfManager m : importList){
			ShelfManager.checkImportData(m);
			String checkFlag = m.getCheckFlag();
			if (ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS.equals(checkFlag)) {
				Boolean flag = shelfManagerDao.checkPartNoAndSupplierNoIsMaintain(m);
				if (!flag) {
					m.setCheckFlag(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
					m.setCheckResult("导入的数据零件与供应商关系未维护");
				}
			}

		}
		//导入数据写入到临时表
		boolean insertShelfManagerTemp = shelfManagerDao.insertShelfManagerTemp(importList);
		if (insertShelfManagerTemp) {
			result = true;
		}
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		return rtnMap;		
	}

	/**
	 * 确认提交
	 * 
	 * Lxh
	 */
	@Override
	public Map<String,Object> ImportData() {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("errorFlag", "");
		shelfManagerDao.ImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		//临时导入情况返回
		rtnMap.put("result", result);
		return rtnMap;
		
	}

	/**
	 * 查询是否存在导入但未提交的数据
	 * @param model
	 * @param page
	 * @return
	 * Lxh
	 */
	@Override
	public PageList<ShelfManager> queryPartMaintenanceTemp(ShelfManager model, DefaultPage page) {
		// TODO Auto-generated method stub
		return shelfManagerDao.queryPartMaintenanceTemp(model,page);
	}

	/**
	 * 导出临时数据
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public List<ShelfManager> exportTempData(ShelfManager model) {
		return shelfManagerDao.exportTempData(model);
	}

	/**
	 * 查询零件号
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public List<ShelfManager> queryPartNo(ShelfManager model) {
		return shelfManagerDao.queryPartNo(model);
	}

	/**
	 * 查询供应商编号
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public List<ShelfManager> querySupplierNoByPartNo(ShelfManager model) {
		return shelfManagerDao.querySupplierNoByPartNo(model);
	}


	/**
	 * 删除导入临时数据
	 */
	@Override
	public int removeShelvesManagerTemp() {
		
		return shelfManagerDao.removeShelvesManagerTemp();
	}

}
