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
import com.hanthink.inv.dao.InvStockTakDao;
import com.hanthink.inv.manager.InvStockTakManager;
import com.hanthink.inv.model.InvStockTakModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:盘点信息管理业务层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月12日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("stockTak")
public class InvStockTakManagerImpl extends AbstractManagerImpl<String, InvStockTakModel>
							implements InvStockTakManager{
	
	@Resource
	private InvStockTakDao stockTakDao;
	
	@Override
	protected Dao<String, InvStockTakModel> getDao() {
		return stockTakDao;
	}
	
	/**
	 * 分页数据查询业务层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public PageList<InvStockTakModel> queryStockTakForPage(InvStockTakModel model, Page page)throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<InvStockTakModel> list = stockTakDao.queryStockTakForPage(model,page);
		
		return new PageList<InvStockTakModel>(list);
	}
	/**
	 * Excel数据导出查询业务实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public List<InvStockTakModel> queryDataForExcelExport(InvStockTakModel model) throws Exception {

		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());

 		return stockTakDao.queryDataForExcelExport(model);
	}
	/**
	 * 根据UUID删除临时表数据
	 * @param importUuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public void deleteTempStockTakByUUID(String importUuid) throws Exception {
		stockTakDao.deleteTempStockTakByUUID(importUuid);
	}
	/**
	 * 将数据导入临时数据表
	 * @param file
	 * @param importUuid
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importStockTakToTemp(MultipartFile file, String importUuid, String ipAddr)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		//读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"partNo", "partShortNo", "wareCode", "takeStock"};
		List<InvStockTakModel> importList = null;
		
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<InvStockTakModel>) ExcelUtil.importExcelXLS(new InvStockTakModel(), file.getInputStream(), columns, 1, 1);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<InvStockTakModel>) ExcelUtil.importExcelXLSX(new InvStockTakModel(), file.getInputStream(), columns, 1, 1);
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
		IUser user = ContextUtil.getCurrentUser();
		//数据导入初始化，格式初步检查
		for (InvStockTakModel invStockTakModel : importList) {	
			invStockTakModel.setImportUuid(importUuid);
			invStockTakModel.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			invStockTakModel.setCreationUser(user.getAccount());
			invStockTakModel.setFactoryCode(user.getCurFactoryCode());
			invStockTakModel.setCreationTime(PupUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			invStockTakModel.checkImportModel(invStockTakModel);
		}
		
		//导入数据写入到临时表
		stockTakDao.importStockTakToTemp(importList);
		
		/** 调用存储过程校验数据 */
		Map<String, String> checkMap = new HashMap<String, String>();
		checkMap.put("uuid", importUuid);
		checkMap.put("userName", ContextUtil.getCurrentUser().getAccount());
		checkMap.put("opeIp", ipAddr);
		checkMap.put("errorFlag", "");
		checkMap.put("errorMsg", "");
		stockTakDao.checkImportData(checkMap);
		result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(checkMap.get("errorMsg"));
		}
		checkMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
		//临时导入情况返回
		resultMap.put("result", result);
		resultMap.put("console", console);
		return resultMap;
	}
	/**
	 * Excel数据导入查询业务实现
	 * @param uuid
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public PageList<InvStockTakModel> queryImportTempForPage(String uuid,Page page) throws Exception {
		
		List<InvStockTakModel> list = stockTakDao.queryImportTempForPage(uuid,page);
		
		return new PageList<InvStockTakModel>(list);
	}
	/**
	 * 确定将临时表数据写入正式表实现方法
	 * @param paramMap
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月12日
	 */
	@Override
	public void isImport(Map<String, String> paramMap) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		
		try {
			//计算库存差异
			stockTakDao.calDiffStock(paramMap);
			//判断数据是否全部正确
			Boolean correctOrNot = stockTakDao.getCorrectOrNot(paramMap);
			if (!correctOrNot) {
				throw new Exception("请保证所有数据的准确性");
			}
			//查询校验无误的数据信息
			List<InvStockTakModel> list = stockTakDao.queryForInsertList(paramMap);
			
			paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
			paramMap.put("creationUser", ContextUtil.getCurrentUser().getAccount());
			//生成单号 盘赢-insNoWin 盘亏-insNoLose
			String insNoWin = "PWINE"+PupUtil.getCurrentTime("yyyyMMddHHmmss");
			String insNoLose = "PLOSE"+PupUtil.getCurrentTime("yyyyMMddHHmmss");
			//临时变量,计算盘赢、盘亏的数量
			int winNum = 0;
			int loseNum = 0;
			//盘赢数据list
			List<InvStockTakModel> pwList = new ArrayList<InvStockTakModel>();
			//盘数数据list
			List<InvStockTakModel> plList = new ArrayList<InvStockTakModel>();
			for (InvStockTakModel invStockTakModel : list) {
				if (Integer.parseInt(invStockTakModel.getDiffStock()) < 0) {
					invStockTakModel.setInsNo(insNoLose);
					plList.add(invStockTakModel);
					loseNum++;
				}
				if (Integer.parseInt(invStockTakModel.getDiffStock()) >= 0) {
					invStockTakModel.setInsNo(insNoWin);
					pwList.add(invStockTakModel);
					winNum++;
				}
			}
			//如果盘赢数据计数大于0，则将盘点信息写入头表
			if (winNum > 0) {
				paramMap.put("insNoWin", insNoWin);
				stockTakDao.insertPWHeader(paramMap);
			}
			//如果盘亏数据计数大于0，则将盘点信息写入头表
			if (loseNum > 0) {
				paramMap.put("insNoLose", insNoLose);
				stockTakDao.insertPLHeader(paramMap);
			}
			//将数据信息导入至从表
			stockTakDao.insertStockTakToFormal(list);
			//记录修改日志
			List<InvStockTakModel> idsList = stockTakDao.queryUpdateIds(paramMap);
			String[] ids = new String[idsList.size()];
			//设置记录日志条件
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(paramMap.get("ipAddr")); 
			logVO.setFromName("修改盘点库存");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_INV_STOCK");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(ids);
			//记录日志
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
			
			//修改库存数据
			stockTakDao.updateLocalStock(paramMap);
			//修改已导入数据的导入状态
			stockTakDao.updateImportStatus(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 库存管理查询数据导出业务实现
	 * @param model
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月10日
	 */
	@Override
	public PageList<InvStockTakModel> queryImportTempForExport(String uuid,Page page) throws Exception {
		List<InvStockTakModel> list = stockTakDao.queryImportTempForPage(uuid,page);
		Integer i = 1;
		for (InvStockTakModel invStockTakModel : list) {
			invStockTakModel.setNO(i.toString());
			i++;
		}
		return new PageList<InvStockTakModel>(list);
	}
}
