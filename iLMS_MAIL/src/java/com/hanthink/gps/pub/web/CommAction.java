package com.hanthink.gps.pub.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hanthink.gps.pub.vo.ChkItemVO;
import com.hanthink.gps.pub.vo.CmbItemVO;
import com.hanthink.gps.pub.web.BaseActionSupport;
import com.hanthink.gps.pub.service.CommService;
import com.hanthink.gps.util.Constants;

public class CommAction extends BaseActionSupport {

	private static final long serialVersionUID = -6873532112901241297L;
	private CommService service;
	private String supplierNo;
	private String type;
	private boolean addBlank;
	private String param;
	private String temptime;
	private CmbItemVO cmbItemVO;
	private String modelName;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAddBlank() {
		return addBlank;
	}

	public void setAddBlank(boolean addBlank) {
		this.addBlank = addBlank;
	}
	
	public CmbItemVO getCmbItemVO() {
		return cmbItemVO;
	}
	public void setCmbItemVO(CmbItemVO cmbItemVO) {
		this.cmbItemVO = cmbItemVO;
	}

	
	public void queryPartGroupNoById(){
		CmbItemVO vo = service.queryPartGroupNoById(cmbItemVO.getCode());
		if(null != vo){
			writeJson(vo, true);
		}
	}
	
	public void querySpsPartById(){
		CmbItemVO vo = service.querySpsPartById(cmbItemVO.getCode());
		if(null != vo){
			writeJson(vo, true);
		}
	}
	
	public void querySupplierNameById(){
		CmbItemVO vo = service.querySupplierNameById(cmbItemVO.getCode());
		if(null != vo){
			writeJson(vo, true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void cmb(){
		if("supplierNoGamc".equals(type) || "mouldSup".equals(type)|| "infoSup".equals(type)){
			supplierNo = userInfo.getUserId();
		}
		List<CmbItemVO> cmbItems = (List<CmbItemVO>)service.queryCmbItems(type,param,supplierNo);
		if(addBlank){
		 cmbItems.add(0, new CmbItemVO("",""));
		}
		writeJson(cmbItems);
	}
	
	@SuppressWarnings("unchecked")
	public void cmbMpUnload(){
//		List<CmbItemVO> cmbItems=null;
		String factory= null;
		if("unloadIdSpecialNo".equals(type)){
			  factory = userInfo.getCurLoginFactory();
		}
		 List<CmbItemVO> cmbItems = (List<CmbItemVO>)service.queryCmbItemsByFactory(type,param,factory);

		if(addBlank){
				cmbItems.add(0, new CmbItemVO("",""));
		}
		writeJson(cmbItems);
	}
	

	public void cmbByFactory(){

		String factory = userInfo.getCurLoginFactory();
		
		param =param + "." +userInfo.getUserName();
		
		if(Constants.USER_TYPE_SUPPLIER.equals(userInfo.getUserType())){
			factory = userInfo.getUserType();
		}
		List<CmbItemVO> cmbItems = (List<CmbItemVO>)service.queryCmbItemsByFactory(type,param,factory);
		if(addBlank){
		 cmbItems.add(0, new CmbItemVO("",""));
		}
		writeJson(cmbItems);
	}
	
	
	public void chk(){
		List<ChkItemVO> chkItems =new ArrayList<ChkItemVO>();
		//(List<CmbItemVO>)service.queryCmbItems(type,param);
		chkItems.add(new ChkItemVO("aa","aa","1"));
		chkItems.add(new ChkItemVO("bb","bb","1"));
		chkItems.add(new ChkItemVO("cc","cc","1"));
		chkItems.add(new ChkItemVO("dd","dd","1"));
		writeJson(chkItems);
	}
	
	
	
	public CommService getService() {
		return service;
	}

	public void setService(CommService service) {
		this.service = service;
	}
	
	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setTemptime(String temptime) {
		this.temptime = temptime;
	}

	public String getTemptime() {
		return temptime;
	}
	
	
	/**
	 * 导出excel模板
	 * @author zuosl  2015-11-2
	 */
	public void exportExcelModel(){
		if(StringUtils.isEmpty(modelName)){
			return;
		}
		
		String exportFileName = null;
		String resource = null;
		
		if("VMI_STOCK_CHECK_UPDATE_MODEL".equals(modelName)){
			//外协仓库存盘点更新批量导入模板
			exportFileName = "VMI库存盘点更新";
			resource = "VMI_STOCK_CHECK_UPDATE_MODEL.xls";
		}else if("MP_AB_SUPPLIER_MANAGE_MODEL".equals(modelName)){
			//AB点供应商管理批量导入模板
			exportFileName = "订购主数据AB点供应商导入模板";
			resource = "MP_AB_SUPPLIER_MANAGE_MODEL.xls";
		}else if("MP_RESIDUAL_MODEL".equals(modelName)){
			//剩余量主数据导入模板
			exportFileName = "剩余量主数据导入模板";
			resource = "MP_RESIDUAL_MODEL.xls";
		}else if("MP_PART_ORDER_MODEL".equals(modelName)){
			//零件订购主数据导入模板
			exportFileName = "零件订购主数据导入模板";
			resource = "MP_PART_ORDER_MODEL.xls";
		}else if("MP_SUPPLIER_ORDER_MODEL".equals(modelName)){
			//供应商订购主数据导入模板
			exportFileName = "供应商订购主数据导入模板";
			resource = "MP_SUPPLIER_ORDER_MODEL.xls";
		}else if("MP_PARTS_SUPPLIER_MODEL".equals(modelName)){
			//AB点供应商导入模版
			exportFileName = "AB点供应商导入模版";
			resource = "MP_PARTS_SUPPLIER_MODEL.xls";
		}else if("M_SYN_ITEM_GROUP_LINE_IMPORT_MODEL".equals(modelName)){
			//同步零件明细导入模板
			exportFileName = "同步零件明细导入模板";
			resource = "M_SYN_ITEM_GROUP_LINE_IMPORT_MODEL.xls";
		}else if("MP_SORT_PLAN_VEHICLE".equals(modelName)){
			//整车订购计划模版
			exportFileName = "整车订购计划模版";
			resource = "MP_SORT_PLAN_VEHICLE.xls";
		}else if("MP_CAR_TYPE_CONFIG_MODEL".equals(modelName)){
			//车型配置导入模板
			exportFileName = "车型配置导入模板";
			resource = "MP_CAR_TYPE_CONFIG_MODEL.xls";
		}else if("MP_VMI_PO_ADD_MODEL".equals(modelName)){
			//订购订单管理VMI手工订单模版
			exportFileName = "VMI手工订单模版";
			resource = "MP_VMI_PO_ADD_MODEL.xls";
		}else if("MP_VMI_PO_UPDATE_DATE_MODEL".equals(modelName)){
			//订购订单管理VMI订单计划日期更新模版
			exportFileName = "VMI订单计划日期更新模版";
			resource = "MP_VMI_PO_UPDATE_DATE_MODEL.xls";
		}else if("VMI_PART_INFO_MODEL".equals(modelName)){
			//外协仓零件基础信息导入模板
			exportFileName = "外协仓零件基础信息导入模板";
			resource = "VMI_PART_INFO_MODEL.xls";
		}else if("SW_DEMAND_FORECAST_IMPORT_MODEL".equals(modelName)){
			//需求预测导入模板
			exportFileName = "需求预测导入模板";
			resource = "SW_DEMAND_FORECAST_IMPORT_MODEL.xlsx";
		}else if("SW_SUPPLY_FORECAST_MODEL".equals(modelName)){
			//供货预测导入模板
			exportFileName = "供货预测导入模板";
			resource = "SW_SUPPLY_FORECAST_MODEL.xlsx";
		}else if("SW_PICKUP_PLAN_IMPORT_MODEL".equals(modelName)){
			//取货计划导入模板
			exportFileName = "取货计划导入模板";
			resource = "SW_PICKUP_PLAN_IMPORT_MODEL.xlsx";
		}else if("LOC_PART_MODEL".equals(modelName)){
			//零件单落点信息数据导入模板
			exportFileName = "零件单落点信息数据导入模板";
			resource = "LOC_PART_MODEL.xls";
		}else if("LOC_PART_MTO_MODEL".equals(modelName)){
			//零件多落点信息数据导入模板
			exportFileName = "零件多落点信息数据导入模板";
			resource = "LOC_PART_MTO_MODEL.xls";
		}else if("LOC_NUM_MODEL".equals(modelName)){
			//落点车体流动数维护信息数据导入模板
			exportFileName = "落点车体流动数维护信息数据导入模板";
			resource = "LOC_NUM_MODEL.xls";
		}else if("SPS_PART_IMPORT_MODEL".equals(modelName)){
			//SPS零件信息数据导入模板
			exportFileName = "sps零件信息导入模板";
			resource = "SPS_PART_IMPORT_MODEL.xls";
		}else if("JIT_PART_REMAIN_IMPORT_MODEL".equals(modelName)){
			//零件余量数据导入模板
			exportFileName = "零件余量数据导入模板";
			resource = "JIT_PART_REMAIN_IMPORT_MODEL.xls";
		}else if("JIT_PART_MODEL".equals(modelName)){
			//配送零件基本信息导入模版
			exportFileName = "配送零件基本信息导入模版";
			resource = "JIT_PART_MODEL.xls";
		}else if("SW_MILITARY_VEHICLE_IMPORT_MODEL".equals(modelName)){
			//军车订单信息数据导入模板
			exportFileName = "军车订单导入模板";
			resource = "SW_MILITARY_VEHICLE_IMPORT_MODEL.xls";
		}else if("INV_INVENTORY_MODEL".equals(modelName)){
			//初始库存信息数据导入模板
			exportFileName = "初始库存导入模板";
			resource = "INV_INVENTORY_MODEL.xls";
		}else if("JIT_ENG_PLAN_IMPORT_MODEL".equals(modelName)){
			//发动机生产计划维护导入模板
			exportFileName = "发动机生产计划维护导入模板";
			resource = "JIT_ENG_PLAN_IMPORT_MODEL.xls";		
		}else if("INV_PARTCHANGE_MODEL".equals(modelName)){
			//设变新旧零件维护导入模板
			exportFileName = "设变新旧零件维护导入模板";
			resource = "INV_PARTCHANGE_MODEL.xls";		
		}else if("JIS_MM_JISI_PART_MODEL".equals(modelName)){
			//厂内同步零件导入模板
			exportFileName = "厂内同步零件导入模板";
			resource = "JIS_MM_JISI_PART_MODEL.xls";		
		}else if("MM_JIT_ROUTE_CARPOOL_MODEL".equals(modelName)){
			//拉动配送路线拼车导入模板
			exportFileName = "拉动配送路线拼车导入模板";
			resource = "MM_JIT_ROUTE_CARPOOL_MODEL.xls";		
		}else if("BASIC_DATA_PART_MODEL".equals(modelName)){
			//物料主数据维护导入模板
			exportFileName = "物料主数据维护导入模板";
			resource = "BASIC_DATA_PART_MODEL.xls";		
		}else if("MON_SUP_DEVIATION_HOUR_MODEL".equals(modelName)){
			//供应商差异时间维护导入模板
			exportFileName = "供应商差异时间维护导入模板";
			resource = "MON_SUP_DEVIATION_HOUR_MODEL.xlsx";		
		}else if("JIT_CARPOOL_PRINTER_MODEL".equals(modelName)){
			//拉动拼车打印机关系维护模板
			exportFileName = "拉动拼车打印机关系维护模板";
			resource = "JIT_CARPOOL_PRINTER_MODEL.xlsx";		
		}
		
    	response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			if(resource.endsWith(".xlsx")){
				response.setHeader("Content-disposition",
						"attachment; filename=" + new String(exportFileName.getBytes("GBK"), "ISO_8859_1") + ".xlsx");
			}else{
				response.setHeader("Content-disposition",
						"attachment; filename=" + new String(exportFileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
			}
			
			InputStream is = null;
			try {
				is = this.getClass().getResourceAsStream("/resource/" + resource);
				OutputStream out =	response.getOutputStream();;
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if (null != is) {
					is.close();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	
	
}
