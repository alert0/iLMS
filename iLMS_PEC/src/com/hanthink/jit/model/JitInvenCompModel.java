package com.hanthink.jit.model;
import java.util.Map;

import com.hanthink.util.excel.ExcelCheckUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.model.AbstractModel;
import com.mysql.jdbc.StringUtils;


 /**
  * <pre> 
  * 描述：MM_JIT_INVEN_COMP 实体对象
  * 作者:dtp
  * 日期:2018-11-03 21:10:35
  * </pre>
  */
public class JitInvenCompModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = -8557886860920654426L;
	
	private String[] tableColumnData;
	
	/**
	 * 批次
	 */
	private String curBatch;
	
	/**
	 * 最小批次
	 */
	private String minCurBatch;
	
	/**
	 * 最小批次
	 */
	private String maxCurBatch;
	
	/**
	 * 工厂代码
	 */
	protected String factoryCode;
	
	/**
	 * uuid
	 */
	protected String uuid;
	
	/**
	 * ID     
	 */
	protected String id; 
	
	/**
	 * 车间
	 */
	protected String workcenter;
	
	/**
	 * 车间描述
	 */
	protected String workcenterDesc;
	
	/**
	 * 推算状态
	 */
	protected String checkResultDesc;
	
	/**
	 * 信息点     
	 */
	protected String planCode; 
	
	/**
	 * 信息点描述
	 */
	protected String planCodeDesc;
	
	/**
	 * 零件号     
	 */
	protected String partNo; 
	
	/**
	 * 简号
	 */
	protected String partShortNo;
	
	/**
	 * 零件名称
	 */
	protected String partName;
	
	/**
	 * VIN     
	 */
	protected String vin; 
	
	/**
	 * 到货批次     
	 */
	protected String arrBatchNo; 
	
	/**
	 * ARR_PROCESS_NO     
	 */
	protected String arrProcessNo; 
	
	/**
	 * CURR_INVENTORY     
	 */
	protected String currInventory; 
	
	/**
	 * SAFETY_INVENTORY     
	 */
	protected String safetyInventory; 
	
	/**
	 * CHECK_PART_SHORT_NO     
	 */
	protected String checkPartShortNo; 
	
	/**
	 * CHECK_PART_NAME     
	 */
	protected String checkPartName; 
	
	/**
	 * CHECK_ARR_PRO_SEQNO     
	 */
	protected String checkArrProSeqno; 
	
	/**
	 * CHECK_CAL_VIN     
	 */
	protected String checkCalVin; 
	
	/**
	 * CHECK_CAL_KB_PRO_SEQNO     
	 */
	protected String checkCalKbProSeqno; 
	
	/**
	 * CHECK_PLAN_QTY     
	 */
	protected String checkPlanQty; 
	
	/**
	 * CHECK_ASSEMBLY_QTY     
	 */
	protected String checkAssemblyQty; 
	
	/**
	 * CHECK_PART_REMAIN     
	 */
	protected String checkPartRemain; 
	
	/**
	 * CHECK_INVENTORY_DIFFQTY     
	 */
	protected String checkInventoryDiffqty; 
	
	/**
	 * CHECK_SORT_NO     
	 */
	protected String checkSortNo; 
	
	/**
	 * CREATION_TIME     
	 */
	protected String creationTime; 
	
	/**
	 * IMP_UUID     
	 */
	protected String impUuid; 
	
	/**
	 * 0:有错误  1:正确     
	 */
	protected String checkResult; 
	
	/**
	 * CHECK_INFO     
	 */
	protected String checkInfo; 
	
	/**
	 * 0:未导入1:已导入     
	 */
	protected String importStatus; 
	
	/**
	 * 0:否:是     
	 */
	protected String continueCheck; 
	
	/**
	 * I:新增U:更新     
	 */
	protected String opeType; 
	
	/**
	 * 库存差异
	 */
	protected String diff;
	
	/**
	 * BUSI_ID     
	 */
	protected String busiId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanCodeDesc() {
		return planCodeDesc;
	}

	public void setPlanCodeDesc(String planCodeDesc) {
		this.planCodeDesc = planCodeDesc;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getArrBatchNo() {
		return arrBatchNo;
	}

	public void setArrBatchNo(String arrBatchNo) {
		this.arrBatchNo = arrBatchNo;
	}

	public String getArrProcessNo() {
		return arrProcessNo;
	}

	public void setArrProcessNo(String arrProcessNo) {
		this.arrProcessNo = arrProcessNo;
	}

	public String getCurrInventory() {
		return currInventory;
	}

	public void setCurrInventory(String currInventory) {
		this.currInventory = currInventory;
	}

	public String getSafetyInventory() {
		return safetyInventory;
	}

	public void setSafetyInventory(String safetyInventory) {
		this.safetyInventory = safetyInventory;
	}

	public String getCheckPartShortNo() {
		return checkPartShortNo;
	}

	public void setCheckPartShortNo(String checkPartShortNo) {
		this.checkPartShortNo = checkPartShortNo;
	}

	public String getCheckPartName() {
		return checkPartName;
	}

	public void setCheckPartName(String checkPartName) {
		this.checkPartName = checkPartName;
	}

	public String getCheckArrProSeqno() {
		return checkArrProSeqno;
	}

	public void setCheckArrProSeqno(String checkArrProSeqno) {
		this.checkArrProSeqno = checkArrProSeqno;
	}

	public String getCheckCalVin() {
		return checkCalVin;
	}

	public void setCheckCalVin(String checkCalVin) {
		this.checkCalVin = checkCalVin;
	}

	public String getCheckCalKbProSeqno() {
		return checkCalKbProSeqno;
	}

	public void setCheckCalKbProSeqno(String checkCalKbProSeqno) {
		this.checkCalKbProSeqno = checkCalKbProSeqno;
	}

	public String getCheckPlanQty() {
		return checkPlanQty;
	}

	public void setCheckPlanQty(String checkPlanQty) {
		this.checkPlanQty = checkPlanQty;
	}

	public String getCheckAssemblyQty() {
		return checkAssemblyQty;
	}

	public void setCheckAssemblyQty(String checkAssemblyQty) {
		this.checkAssemblyQty = checkAssemblyQty;
	}

	public String getCheckPartRemain() {
		return checkPartRemain;
	}

	public void setCheckPartRemain(String checkPartRemain) {
		this.checkPartRemain = checkPartRemain;
	}

	public String getCheckInventoryDiffqty() {
		return checkInventoryDiffqty;
	}

	public void setCheckInventoryDiffqty(String checkInventoryDiffqty) {
		this.checkInventoryDiffqty = checkInventoryDiffqty;
	}

	public String getCheckSortNo() {
		return checkSortNo;
	}

	public void setCheckSortNo(String checkSortNo) {
		this.checkSortNo = checkSortNo;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getImpUuid() {
		return impUuid;
	}

	public void setImpUuid(String impUuid) {
		this.impUuid = impUuid;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public String getImportStatus() {
		return importStatus;
	}

	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	public String getContinueCheck() {
		return continueCheck;
	}

	public void setContinueCheck(String continueCheck) {
		this.continueCheck = continueCheck;
	}

	public String getOpeType() {
		return opeType;
	}

	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}

	public String getBusiId() {
		return busiId;
	}

	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	

	public String getWorkcenterDesc() {
		return workcenterDesc;
	}

	public void setWorkcenterDesc(String workcenterDesc) {
		this.workcenterDesc = workcenterDesc;
	}

	public String getCheckResultDesc() {
		return checkResultDesc;
	}

	public void setCheckResultDesc(String checkResultDesc) {
		this.checkResultDesc = checkResultDesc;
	}
	
	public String getMinCurBatch() {
		return minCurBatch;
	}

	public void setMinCurBatch(String minCurBatch) {
		this.minCurBatch = minCurBatch;
	}

	public String getMaxCurBatch() {
		return maxCurBatch;
	}

	public void setMaxCurBatch(String maxCurBatch) {
		this.maxCurBatch = maxCurBatch;
	}

	public String getCurBatch() {
		return curBatch;
	}

	public void setCurBatch(String curBatch) {
		this.curBatch = curBatch;
	}
	
	public String[] getTableColumnData() {
		return tableColumnData;
	}

	public void setTableColumnData(String[] tableColumnData) {
		this.tableColumnData = tableColumnData;
	}

	/**
	 * 非空校验
	 */
	public static void checkImportData(JitInvenCompModel model, Map<String, String> dictMap) {
		StringBuffer checkInfo = new StringBuffer();
		if(StringUtils.isNullOrEmpty(model.getPlanCode())) {
			checkInfo.append("信息点不能为空;");
		}
		//提示信息点在系统不存在
		if(!StringUtils.isNullOrEmpty(model.getPlanCode())) {
			if(StringUtils.isNullOrEmpty(dictMap.get(model.getPlanCode()))) {
				checkInfo.append("信息点在系统不存在;");
				model.setPlanCode(null);
			}else {
				model.setPlanCode(dictMap.get(model.getPlanCode()));
			}
		}
		if(StringUtils.isNullOrEmpty(model.getPartNo())) {
			checkInfo.append("零件号不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getVin())) {
			checkInfo.append("盘点截止VIN不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getArrBatchNo())) {
			checkInfo.append("下一个到货批次不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getArrProcessNo())) {
			checkInfo.append("下一个到货进度不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getCurrInventory())) {
			checkInfo.append("盘点数量不能为空;");
		}
		if(StringUtils.isNullOrEmpty(model.getSafetyInventory())) {
			checkInfo.append("安全库存不能为空;");
		}
		if(ExcelCheckUtil.isNotNumeric(model.getArrBatchNo())) {
			checkInfo.append("下一个到货批次应为正整数;");
			model.setArrBatchNo(null);
		}
		if(ExcelCheckUtil.isNotNumeric(model.getArrProcessNo())) {
			checkInfo.append("下一个到货进度应为正整数;");
			model.setArrProcessNo(null);
		}
		if(ExcelCheckUtil.isNotNumeric(model.getCurrInventory())) {
			checkInfo.append("盘点数量应为正整数;");
			model.setCurrInventory(null);
		}
		if(ExcelCheckUtil.isNotNumeric(model.getSafetyInventory())) {
			checkInfo.append("安全库存应为正整数;");
			model.setSafetyInventory(null);
		}
		if(StringUtils.isNullOrEmpty(checkInfo.toString())){
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
		}else{
			model.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
			model.setCheckInfo(checkInfo.toString());
		}
	} 
	
	
}