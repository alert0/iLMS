package com.hanthink.sw.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.sw.dao.SwMaterialOrderDao;
import com.hanthink.sw.manager.SwMaterialOrderManager;
import com.hanthink.sw.model.SwMaterialOrderModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

@Service("SwMaterialOrderManager")
public class SwMaterialOrderManagerImpl  extends AbstractManagerImpl<String, SwMaterialOrderModel> implements SwMaterialOrderManager{

	@Resource
	private SwMaterialOrderDao swMaterialOrderDao;
	
	
	@Override
	protected Dao<String, SwMaterialOrderModel> getDao() {
		return swMaterialOrderDao;
	}
	/**
	 * 订单分页查询
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	@Override
	public PageList<SwMaterialOrderModel> queryMaterialOrderPage(SwMaterialOrderModel model, DefaultPage p) {
		return swMaterialOrderDao.queryMaterialOrderPage(model,p);
	}
	
	

	
	/**
	 * 资材订单导出
	 * @param model
	 * @param p
	 * @return
	 * Lxh
	 */
	@Override
	public List<SwMaterialOrderModel> downloadMaterialOrder(SwMaterialOrderModel model) {
		
		return swMaterialOrderDao.downloadMaterialOrder(model);
	}
	
	
	/**
	 * 交货反馈修改
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public Boolean updateMaterialOrderPage(SwMaterialOrderModel model) {
		
		return swMaterialOrderDao.updateMaterialOrderPage(model);
	}
	
	/**
	 * 标签打印
	 * @param orderModel
	 * @return
	 * Lxh
	 */
	@Override
	public List<SwMaterialOrderModel> queryDemondOrderPrintLabelList(SwMaterialOrderModel orderModel) {

		return swMaterialOrderDao.queryDemondOrderPrintLabelList(orderModel);
	}
	
	
	/**
	 * 交货反馈临时数据新增
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@Override
	public Boolean insertMaterialOrderReturnTmp(SwMaterialOrderModel model) {

		return swMaterialOrderDao.insertMaterialOrderReturnTmp(model);
	}
	
	/**
	 * 交货反馈临时数据查询
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 * Lxh
	 */
	@Override
	public PageList<SwMaterialOrderModel> queryMaterialOrderReturnTmp(SwMaterialOrderModel model, DefaultPage p) {
		
		return swMaterialOrderDao.queryMaterialOrderReturnTmp(model,p);
	}
	
	/**
     * 判断反馈数量汇总是否满足订单总数
     * @param model
     * @return
     * Lxh
     */
	@Override
	public Boolean checkMeetDelivery(SwMaterialOrderModel model) {
		
		return swMaterialOrderDao.checkMeetDelivery(model);
	}
	
	/**
	 * 获取反馈条数
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public int getMaterialOrderReturnTmpCount(SwMaterialOrderModel model) {
		
		return swMaterialOrderDao.getMaterialOrderReturnTmpCount(model);
	}
	
	/**
	 * 更新反馈状态
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public Boolean updateReturnStatus(SwMaterialOrderModel model) {
		
		return swMaterialOrderDao.updateReturnStatus(model);
	}
	
	/**
	 * 反馈临时数据提交到业务
	 * @param model
	 * @return
	 * Lxh
	 */
	@Override
	public Boolean MaterialOrderReturnTmpToOrder(SwMaterialOrderModel model) {
		
		return swMaterialOrderDao.MaterialOrderReturnTmpToOrder(model);
	}
	
	/**
	 * 交货反馈临时数据修改
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@Override
	public Boolean updateMaterialOrderReturnTmp(SwMaterialOrderModel model) {
		
		return swMaterialOrderDao.updateMaterialOrderReturnTmp(model);
	}
	
	/**
	 * 交货反馈临时数据删除
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@Override
	public void deleteMaterialOrderReturnTmp(SwMaterialOrderModel model) {
		swMaterialOrderDao.deleteMaterialOrderReturnTmp(model);
		
	}
	
	/**
	 * 根据uuid删除临时表数据
	 * @param uuid
	 * @throws Exception
	 *
	 */
	@Override
	public void deleteTempMaterialOrderByUUID(String uuid) throws Exception{
		if (StringUtil.isNotEmpty(uuid)) {
			swMaterialOrderDao.deleteTempCongfigByUUID(uuid);
		}else {
			throw new Exception("系统出错,请联系管理人员");
		}
		
	}
	/**
	 * 将Excel数据写入临时数据表
	 * @param file
	 * @param uuid
	 * @param ipAddr
	 * @return
	 * @throws Exception
	 * 
	 */
	@Override
	public Map<String, Object> importMaterialOrderModel(MultipartFile file, String uuid, String ipAddr) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = true;
		String console = null;
		
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		//读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
//		订单号	采购单行号	物料编号	资材名称	规格	订购数量	单位	订购日期	订购人	联系方式	供应商代码	
//		供应商名称	仓库地址	库存区分	费用中心代码	成本中心代码	订单打印时间	反馈状态	反馈时间	计划交货日期	交货数量	反馈备注
		String[] columns = {"purchaseNo","purchaseRowNo","partNo","partName","standPackage",
				"orderQty","orderUnit","orderDate",
				"recUser","recTel","supplierNo","supplierName","recAdress",
				"invType","costCode","costCenter","printTime","returnStatus",
				"returnTime","planTime","planNum","returnMsg"};
		List<SwMaterialOrderModel> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList =  (List<SwMaterialOrderModel>) ExcelUtil.importExcelXLS(new SwMaterialOrderModel(),file.getInputStream(), columns, 1, 0);
			}else if (ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())) {
				importList =(List<SwMaterialOrderModel>) ExcelUtil.importExcelXLSX(new SwMaterialOrderModel(),file.getInputStream(), columns, 1, 0);
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
		IUser user = ContextUtil.getCurrentUser();
		//初始化数据检查
		for (SwMaterialOrderModel configModel : importList) {
			configModel.setUuid(uuid);
			configModel.setId(UniqueIdUtil.getSuid());
			configModel.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			configModel.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
			SwMaterialOrderModel.checkImport(configModel);
		}
		swMaterialOrderDao.insertConfigToTemp(importList);
		//调用存储过程校验数据
		Map<String, String> checkMap = new HashMap<String, String>();
		checkMap.put("uuid", uuid);
		checkMap.put("errorFlag", "");
		checkMap.put("errorMsg", "");
		swMaterialOrderDao.ckeckImportConfig(checkMap);
		result = "0".equals(String.valueOf(checkMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(checkMap.get("errorMsg"));
		}
		//临时导入情况返回
		resultMap.put("result", result);
		resultMap.put("console", console);
 		return resultMap;
	}
	/**
	 * 查询分页导入数据
	 * @param uuid
	 * @param page
	 * @return
	 * @throws Exception
	 * 
	 */
	@Override
	public PageList<SwMaterialOrderModel> queryImportForPage(String uuid, Page page) {
		return new PageList<SwMaterialOrderModel>(swMaterialOrderDao.queryImportForPage(uuid,page));
	}
	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * 
	 * 
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return swMaterialOrderDao.queryIsExistsCheckResultFalse(uuid);
	}
	/**
	 * 将临时数据表信息写入正式表
	 * @param paramMap
	 * @throws Exception
	 * 
	 */
	@Override
	public void insertTempToFormal(Map<String, Object> paramMap) {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);

			//将临时数据表的数据写入正式表
			swMaterialOrderDao.insertTempToFormal(paramMap);
			//更新临时表的导入状态
			swMaterialOrderDao.updateImportStatus(paramMap);
		
		
	}
	/**
	 * 导入数据详情Excel导出
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * 
	 */
	@Override
	public List<SwMaterialOrderModel> exportForImport(String uuid) {
		
		return swMaterialOrderDao.exportForImport(uuid);
	}
	
	/**
	 * 修改打印状态
	 */
	@Override
	public void updatePrintStatus(String ipAddr, SwMaterialOrderModel model) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("修改打印状态");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_SW_FEEDBACK_ZC");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("PURCHASE_NO");
		pkColumnVO.setColumnVal(model.getPurchaseNo()+model.getPurchaseRowNo());
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		swMaterialOrderDao.updatePrintStatus(model);
		
	}
	
	
	
	

	

}
