package com.hanthink.inv.manager.impl;

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
import com.hanthink.inv.dao.InvStockDao;
import com.hanthink.inv.manager.InvStockManager;
import com.hanthink.inv.model.InvStockModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("stock")
public class InvStockManagerImpl extends AbstractManagerImpl<String, InvStockModel>
								implements InvStockManager{
	
	@Resource
	private InvStockDao stockDao;
	@Override
	protected Dao<String, InvStockModel> getDao() {
		return stockDao;
	}
	/**
	 * 分页数据查询业务实现方法
	 * @param model
	 * @param page
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@Override
	public PageList<InvStockModel> queryStockForPage(InvStockModel model, Page page) throws Exception {

		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<InvStockModel> list = stockDao.queryStockForPage(model,page);
			
		return new PageList<InvStockModel>(list);
	}
	/**
	 * 加载单条数据详情业务实现方法
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@Override
	public InvStockModel queryStockById(String id) throws Exception {
		if(StringUtil.isEmpty(id)) {
			throw new Exception("未选择数据");
		}
		return stockDao.queryStockById(id);
	}
	/**
	 * 修改安全库存数业务实现方法
	 * @param model
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@Override
	public void updateForSafeStockNum(InvStockModel model,String ipAddr) throws Exception {
		if(StringUtil.isEmpty(model.getId())) {
			throw new Exception("获取参数失败,请联系管理人员");
		}
		String reg = "^[1-9]\\d*|0$";
		if(!model.getSafeStock().matches(reg)) {
			throw new Exception("最小库存数据获取失败");
		}
		if(!model.getMaxStock().matches(reg)) {
			throw new Exception("最大库存数据获取失败");
		}
		//获取零件收容数
		Integer standPackage = stockDao.queryStandPackageForPart(model);
		if(standPackage == null) {
			standPackage = 1;
		}
		Integer minStock = Integer.parseInt(model.getSafeStock()) * standPackage;
		Integer maxStock = Integer.parseInt(model.getMaxStock()) * standPackage;
		model.setSafeStock(minStock.toString());
		model.setMaxStock(maxStock.toString());
		//记录更新日志
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr);
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_INV_STOCK");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(model.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		model.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		//更新数据
		stockDao.updateForSafeStockNum(model);
	}
	/**
	 * 库存管理查询数据导出业务实现方法
	 * @param model
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@Override
	public List<InvStockModel> queryExportDataForExcel(InvStockModel model) throws Exception {		
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<InvStockModel> list = stockDao.queryExportDataForExcel(model);
		
		return list;
	}
	/**
	 *  批量修改库存数据
	 * @param list
	 * @param maxStock
	 * @param minStock
	 * @param ipAddr 
	 * @throws Exception
	 * @author zmj
	 * @date 2019年2月18日
	 */
	@Override
	public void batchUpdateStock(List<InvStockModel> list, String maxStock, String minStock,String ipAddr) throws Exception {
		if (StringUtil.isEmpty(maxStock) || StringUtil.isEmpty(minStock) || list.size() < 1) {
			throw new Exception("系统错误,请联系管理员");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("maxStock", maxStock);
		paramMap.put("minStock", minStock);
		paramMap.put("list", list);
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		try {
			String[] ids = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ids[i] = list.get(i).getId();
			}
			//设置日志记录条件
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr);
			logVO.setFromName("批量修改");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_INV_STOCK");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(ids);
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
			//修改数据
			stockDao.batchUpdateStock(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importStock(MultipartFile file, String uuid, String ipAddr) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		boolean result = true;
		String console = null;
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
//		String[] columns = {"wareCode", "partNo", "minStock", "maxStock","stock","adjRemark"};
		String[] columns = { "partNo", "fromDepotNo", "toDepotNo", "stock", "minStock", "maxStock","adjRemark"};
		
		List<InvStockModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<InvStockModel>) ExcelUtil.importExcelXLS(new InvStockModel(), file.getInputStream(), columns, 1, 0);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<InvStockModel>) ExcelUtil.importExcelXLSX(new InvStockModel(), file.getInputStream(), columns, 1, 0);
			}else{
				result = false;
				console = "上传文件不是excel类型！";
				throw new RuntimeException(console);
			}
			int i = 1;
			for (InvStockModel invStockModel : importList) {
				invStockModel.setUuid(uuid);
				invStockModel.setId(UniqueIdUtil.getSuid());
				invStockModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
				invStockModel.setCreationUser(ContextUtil.getCurrentUser().getAccount());
				invStockModel.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS); 
				invStockModel.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
				invStockModel.setInvOutNo("RES"+PupUtil.getCurrentTime("yyyymmddHHmmss")+"_"+i);
				i++;
				InvStockModel.checkImportModle(invStockModel);
			}
			try {
				stockDao.insertToTempStock(importList);				
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
				console = "请按照模版导入数据";
				throw new RuntimeException(console);
			}
			
			Map<String, String> checkMap = new HashMap<String, String>();
			checkMap.put("uuid", uuid);
			checkMap.put("userName", ContextUtil.getCurrentUser().getAccount());
			checkMap.put("opeIp", ipAddr);
			checkMap.put("errorFlag", "");
			checkMap.put("errorMsg", "");
			stockDao.checkImportDataInformation(checkMap);
			result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
			if(!result && StringUtil.isEmpty(console)){
				console = String.valueOf(checkMap.get("errorMsg"));
			}
			
			//临时导入情况返回
			resultMap.put("result", result);
			resultMap.put("console", console);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			console = e.getMessage();
			throw new RuntimeException(console);
		}
	}
	@Override
	public void delTempDataByUUID(String uuid) throws Exception {
		stockDao.delTempDataByUUID(uuid);
	}
	@Override
	public PageList<InvStockModel> queryImportTemp(Map<String, String> paramMap, Page page) throws Exception {
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		List<InvStockModel> list = stockDao.queryImportTemp(paramMap, page);
		return new PageList<InvStockModel>(list);
	}
	@Override
	public void insertTempDataTpFromal(Map<String, String> paramMap) throws Exception {
		
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		paramMap.put("creationUser", ContextUtil.getCurrentUser().getAccount());
		
		boolean flag = stockDao.queryStockImportFlag(paramMap.get("uuid"));
		if (flag) {
			throw new Exception("请保证所有数据的正确性");
		}
		//计算盘赢、盘亏数据
//		List<InvStockModel> calList = stockDao.calDiffFlag(paramMap);
//		List<InvStockModel> pwList = new ArrayList<InvStockModel>();
//		List<InvStockModel> plList = new ArrayList<InvStockModel>();
//		String insNoWin = "PWINE"+PupUtil.getCurrentTime("yyyyMMddHHmmss");
//		String insNoLose = "PLOSE"+PupUtil.getCurrentTime("yyyyMMddHHmmss");
//		for (InvStockModel invStockModel : calList) {
//			if (String.valueOf(0).equals(invStockModel.getDiffFlag())) {
//				invStockModel.setInsNo(insNoLose);
//				pwList.add(invStockModel);
//			}
//			if (String.valueOf(1).equals(invStockModel.getDiffFlag())) {
//				invStockModel.setInsNo(insNoWin);
//				plList.add(invStockModel);
//			}
//		}
//		//将数据写入盘点表
//		if (pwList.size() > 0) {
//			paramMap.put("insNo", insNoWin);
//			stockDao.insertToStockTakeHeader(paramMap);
//			stockDao.insertToStockTakeBody(pwList);
//		}
//		if (plList.size() > 0) {
//			paramMap.put("insNo", insNoLose);
//			stockDao.insertToStockTakeHeader(paramMap);
//			stockDao.insertToStockTakeBody(plList);
//		}
		//将数据写入库存表
//		stockDao.insertTempDataToFormal(paramMap);
		//将数据写入INV_OUT
		stockDao.insertToInvOut(paramMap);
		stockDao.insertToInvOutDetail(paramMap);
		
		stockDao.updateTempImportStatus(paramMap);
	}
	@Override
	public List<InvStockModel> exportImportData(Map<String, String> paramMap) throws Exception {
		return stockDao.exportImportData(paramMap);
	}
}
