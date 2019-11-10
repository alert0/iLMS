package com.hanthink.pup.manager.impl;

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
import com.hanthink.pup.dao.PupManualOrderDao;
import com.hanthink.pup.manager.PupManualOrderManager;
import com.hanthink.pup.model.PupManualOrderModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
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
 * 功能描述:手工调整订单业务层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月24日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("manualService")
public class PupManualOrderManagerImpl extends AbstractManagerImpl<String, PupManualOrderModel>
				implements PupManualOrderManager{
	
	@Resource 
	PupManualOrderDao manualOrderDao;
	
	@Override
	protected Dao<String, PupManualOrderModel> getDao(){
		return this.manualOrderDao;
	}
	/**
	 * 手工调整订单查询业务业务层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	@Override
	public PageList<PupManualOrderModel> queryManualOrderForPage(PupManualOrderModel model, Page page)
			throws Exception {
		
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<PupManualOrderModel> list = manualOrderDao.queryManualOrderForPage(model, page);
			
			
		return new PageList<>(list);
		
	}
	/**
	 * 手工调整订单删除数据业务层实现方法
	 * @param purchaseNo
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	@Override
	public void removeManualOders(String[] purchaseNos,String ipAddr) throws Exception {
		if (purchaseNos.length < 1) {
			throw new Exception("请选择需要删除的数据");
		}
		try {
			//记录删除日志
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("删除数据");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
			logVO.setTableName("MM_PUP_MANU_ORDER");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("PURCHASE_NO");
			pkColumnVO.setColumnValArr(purchaseNos);
			this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
			
			List<PupManualOrderModel> list = new ArrayList<PupManualOrderModel>();
			String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
			for (String purchaseNo : purchaseNos) {			
				PupManualOrderModel model = new PupManualOrderModel();
				model.setFactoryCode(factoryCode);
				model.setPurchaseNo(purchaseNo);
				list.add(model);
			}
			
			manualOrderDao.removeManualOders(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 手工调整订单页面数据导出业务实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月24日
	 */
	@Override
	public List<PupManualOrderModel> queryManualOederForExport(PupManualOrderModel model) throws Exception {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			
			List<PupManualOrderModel> list = new ArrayList<PupManualOrderModel>();
			list = manualOrderDao.queryManualOederForExport(model);
			for (PupManualOrderModel pupManualOrderModel : list) {
				if ("1".equals(pupManualOrderModel.getPickupFlag())) {
					pupManualOrderModel.setPickupFlag("取货");
				}else {
					pupManualOrderModel.setPickupFlag("不取货");
				}
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 根据UUID删除临时表数据业务层实现方法
	 * @param uuid
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@Override
	public void deleteTempManualOrderByUUID(String uuid) throws Exception {
		manualOrderDao.deleteTempManualOrderByUUID(uuid);
	}
	/**
	 * 将数据写入临时表业务层实现方法
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importManualOrderToTempTable(MultipartFile file, String uuid, String ipAddr)
			throws Exception {
		PupManualOrderModel importModel = new PupManualOrderModel();
		Map<String, Object> resultMap = new HashMap<>();
		boolean result = true;
		String console = null;
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		//读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"orderNo","purchaseNo","supFactory","supplierNo","carType","workday","pickDate","pickTime",
							"arriveDate","arriveTime","orderDate","routeCode","totalNo","mergeNo","todayNo",
							"pickupType","area","pickupFlag","orderDesc"};
		List<PupManualOrderModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())) {
				importList = (List<PupManualOrderModel>) ExcelUtil.importExcelXLS(importModel,file.getInputStream(),columns,1,0);
			}else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
				importList = (List<PupManualOrderModel>) ExcelUtil.importExcelXLSX(importModel,file.getInputStream(),columns,1,0);
			}else {
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
		try {
			//初步检查数据
			IUser user = ContextUtil.getCurrentUser();
			if(importList != null && importList.size() > 0) {
				for (PupManualOrderModel importData : importList) {
					if("取货".equals(importData.getPickupFlag())) {
						importData.setPickupFlag("1");
					}
					if("不取货".equals(importData.getPickupFlag())) {
						importData.setPickupFlag("0");
					}
					importData.setId(UniqueIdUtil.getSuid());
					importData.setUuid(uuid);
					importData.setCreationUser(user.getAccount());
					importData.setCreationTime(PupUtil.getCurrentTime("yyyy-MM-dd hh:mm:ss"));
					importData.setLastModifiedUser(user.getAccount());
					importData.setLastModifiedTime(PupUtil.getCurrentTime("yyyy-MM-dd hh:mm:ss"));
					importData.setFactoryCode(user.getCurFactoryCode());
					importData.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
					PupManualOrderModel.checkImportManualOrder(importData);
					if (StringUtil.isEmpty(importData.getArriveDate()) || StringUtil.isEmpty(importData.getArriveTime())) {
						importData.setArriveTime(null);
					}else {
						importData.setArriveTime(importData.getArriveDate()+" "+importData.getArriveTime());
					}
					
					if (StringUtil.isEmpty(importData.getPickDate()) || StringUtil.isEmpty(importData.getArriveTime())) {
						importData.setPickTime(null);
					}else {
						importData.setPickTime(importData.getPickDate()+" "+importData.getPickTime());
					}
				}
			}
			manualOrderDao.insertManualOrderToTempTable(importList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			console = "请按照模版导入数据";
			throw new RuntimeException(console);
		}
		try {
			//调用存储过程进行验证
			Map<String, String> checkMap = new HashMap<String,String>();
			checkMap.put("uuid", uuid);
			checkMap.put("userName", ContextUtil.getCurrentUser().getAccount());
			checkMap.put("opeIp", ipAddr);
			checkMap.put("errorFlag", "");
			checkMap.put("errorMsg", "");
			manualOrderDao.checkImportManualOrder(checkMap);
			result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
			if(!result && StringUtil.isEmpty(console)){
				console = String.valueOf(checkMap.get("errorMsg"));
			}
			/**
			 * 检查是否可以批量导入
			 */
			String flag = manualOrderDao.queryImportFlag(uuid);
			//临时导入情况返回
			resultMap.put("result", result);
			resultMap.put("console", console);
			resultMap.put("flag", flag);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	/**
	 * 查询手工调整订单导入数据业务层实现方法
	 * @param paramMap
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@Override
	public PageList<PupManualOrderModel> queryImportManualOrderForPage(Map<String, String> paramMap, Page page)
			throws Exception {
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		return (PageList<PupManualOrderModel>) manualOrderDao.queryImportManualOrderForPage(paramMap,page);
	}
	/**
	 * 将临时表数据写入正式业务表业务层实现方法
	 * @param paramMap
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@Override
	public void insertImportDataToFormalTable(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		
		Integer countImport = manualOrderDao.getCountForImport(paramMap);
		if (countImport > 0) {
			try {
				List<String> updateList = manualOrderDao.queryUpdateList(paramMap);
				if (updateList.size() > 0) {
					//记录日志
					TableOpeLogVO logVO = new TableOpeLogVO();
					logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
					logVO.setOpeIp(ipAddr); 
					logVO.setFromName("导入数据修改");
					logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
					logVO.setTableName("MM_PUP_MANU_ORDER");
					TableColumnVO pkColumnVO = new TableColumnVO();
					pkColumnVO.setColumnName("PURCHASE_NO");
					pkColumnVO.setColumnValArr(updateList.toArray(new String[updateList.size()]));
					this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
					
					manualOrderDao.updateManualOrderImportData(paramMap);
				}
				/**
				 * 将数据写入正式表
				 */
				manualOrderDao.insertTempDataToRegula(paramMap);
				
				/**
				 * 更新临时数据的导入状态
				 */
				manualOrderDao.updateManualOrderImportDataImpStatus(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("系统错误,请联系管理员");
			}
		}else {
			throw new Exception("没有可导入的正确数据");
		}
	}
	/**
	 * 将导入数据导出到Excel表格业务层实现方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@Override
	public List<PupManualOrderModel> queryManualOrderTempDataForExport(Map<String, String> paramMap) throws Exception {
		try {
			paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
			return manualOrderDao.queryManualOrderTempDataForExport(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
}
