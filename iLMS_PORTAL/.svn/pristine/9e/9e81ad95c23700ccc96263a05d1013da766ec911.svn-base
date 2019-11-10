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
import com.hanthink.pup.model.PupManualOrderPageModel;
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
	public PageList<PupManualOrderModel> queryManualOrderForPage(PupManualOrderPageModel model, Page page)
			throws Exception {
		List<PupManualOrderModel> list = new ArrayList<>();
		
		if (!PupUtil.isAllFieldNull(model)) {
			
			list = manualOrderDao.queryManualOrderForPage(model, page);
			
			for (PupManualOrderModel orderModel : list) {
				if(orderModel.getPickupFlag().equals("0")) {
					orderModel.setPickupFlag("不取货");
				}else if(orderModel.getPickupFlag().equals("1")){
					orderModel.setPickupFlag("取货");
				}else {
					throw new Exception("取货标识不可用");
				}
			}
		}
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
	public void removeManualOders(String[] purchaseNo) throws Exception {
		if (purchaseNo.length < 1) {
			throw new Exception("请选择需要删除的数据");
		}
		manualOrderDao.removeManualOders(purchaseNo);
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
	public List<PupManualOrderModel> queryManualOederForExport(PupManualOrderPageModel model) throws Exception {
		return manualOrderDao.queryManualOederForExport(model);
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
		String[] columns = {"pickupFlag","area","purchaseNo","orderDesc","supFactory","pickupType","carType",
				"orderNo","workday","pickDate","pickTime","arriveDate","arriveTime","orderDate",
				"routeCode","totalNo","todayNo","mergeNo"};
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
		
		//初步检查数据
		IUser user = ContextUtil.getCurrentUser();
		if(importList != null && importList.size() > 0) {
			for (PupManualOrderModel importData : importList) {
				if(StringUtil.isNotEmpty(importData.getPickDate())&&StringUtil.isNotEmpty(importData.getPickTime())) {
					if(importData.getPickTime().length() > 8) {
						importData.setPickDate(importData.getPickDate().substring(0,10)+" "+importData.getPickTime().substring(11));
					}else {
						importData.setPickDate(importData.getPickDate().substring(0,10)+" "+importData.getPickTime());
					}
				}
				if(StringUtil.isNotEmpty(importData.getArriveDate())&&StringUtil.isNotEmpty(importData.getArriveTime())) {
					if(importData.getArriveTime().length() > 8) {
						importData.setArriveDate(importData.getArriveDate().substring(0, 10)+" "+importData.getArriveTime().substring(11));
					}else {
						importData.setArriveDate(importData.getArriveDate().substring(0, 10)+" "+importData.getArriveTime());
					}					
				}
				if("取货".equals(importData.getPickupFlag())) {
					importData.setPickupFlag("0");
				}
				if("不取货".equals(importData.getPickupFlag())) {
					importData.setPickupFlag("1");
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
			}
			manualOrderDao.insertManualOrderToTempTable(importList);
		}
		
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
		/**
		 * 拿出Id查询哪些数据要修改
		 */
		List<String> ids = manualOrderDao.queryUpdateDataFromTemp(paramMap);
		if(ids.size() > 0) {
			/**
			 * 声明一个String数组，用于存放List
			 */
			String[] idp = new String[ids.size()];
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("导入更新");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_PUP_MANUAL_ORDER_UPDATE");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnValArr(ids.toArray(idp));
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			/**
			 * 导入修改的数据
			 */
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
		return manualOrderDao.queryManualOrderTempDataForExport(paramMap);
	}
}
