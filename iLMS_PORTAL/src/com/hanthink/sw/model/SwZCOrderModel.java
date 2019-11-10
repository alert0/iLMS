package com.hanthink.sw.model;

import java.text.SimpleDateFormat;
import java.util.List;

import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.model.AbstractModel;
import com.mysql.jdbc.StringUtils;

@SuppressWarnings("serial")
public class SwZCOrderModel  extends AbstractModel<String>{
	
	/**
	 * 工厂
	 */
	private String factoryNo;
	
	/**
	 * 工厂名
	 */
	private String factoryName;

	/**
	 * 资材仓库代码
	 */
	private String depotAddr;
	
	/**
	 * 订购人
	 */
	private String planner;
	
	/**
	 * 规格
	 */
	private String partSpec;
	
	/**
	 * 财务仓库代码
	 */
	private String depotNo;
	
	/**
	 * 
	 */
	private String depotAddress;
	
	/**
	 * 到货日期 至
	 */
	private String arriveDateEnd;
	
	/**
	 * 二维码
	 */
	private Object qrCode;
	
	/**
	 * 条形码
	 */
	private Object brCode;
	
	/**
	 * logo
	 */
	private Object logo;
	
	/**
	 * 打印人
	 */
	private String printUser;
	
	/**
	 * 最后修改IP
	 */
	private String lastModifiedIp;
	
	/**
	 * 最后修改人
	 */
	private String lastModifiedUser;
	
	/** 创建人 */
	private String creationUser;
	
	/** 工厂代码  */
	private String factoryCode;
	/** 用户ID */
	private String userId;
	/** 用户类型 */
	private String userType;
	/**
	 * 资材名称
	 */
	private String partNameCn;
	/**
	 * 反馈状态
	 */
	private String feedBackStatus;
	
	
	protected String id;
	private String  purchaseNo; //订单号
	private String  purchaseRowNo;//订单行号
	private String  invType; //库存区分
	private String  partNo; //物料编号
	private String  costCode; //费用中心代码
	private String  partName; //资材名称
	private String  costCenter; //成本中心代码
	private String  standPackage; //规格
	private String arriveDate; //要求交货日期
	private String orderQty; //订购数量
	private String orderStatus; //订单状态
	private String orderUnit; //订购单位
	private String printStatus; //打印状态
	private String printStatusDesc; //打印状态
	private String orderDate; //订购日期
	private String printTime; //打印时间
	private String recUser; //订购人
	private String returnStatus; //反馈状态
	private String recTel; //收货联系方式
	private String returnTime; //反馈时间
	private String recAdress; //收货地址
	private String planNum; //计划交货数
	private String supplierNo; //供应商代码
	private String returnMsg; //反馈备注
	private String arriveDateFrom;//要求到货日期从
	private String arriveDateTo;//要求到货日期到
	private String supplierName;//供应商名称
	private String planTime;//计划交货日期
	private String wareCode;//仓库
	private String lasetModifiedUser;//最后修改用户
	private String replySeqNo;//
	
	private String tmpId;//
	private String returnCount;//已反馈条数汇总
	private String returnSum;//反馈数量汇总
	
	private String checkResult;//检测结果
	private String importStatus;//导入状态
	private String importStatusDesc;
	private String checkResultDesc;
	
	private String uuid;//导入uuid
	private String checkInfo;//错误信息
	private String excelImportStatus;
	private String  excelCheckResult;
	
	private String  partShortNo;//零件简号
	
	public String getPlanner() {
		return planner;
	}

	public void setPlanner(String planner) {
		this.planner = planner;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getPurchaseRowNo() {
		return purchaseRowNo;
	}

	public void setPurchaseRowNo(String purchaseRowNo) {
		this.purchaseRowNo = purchaseRowNo;
	}

	public String getInvType() {
		return invType;
	}

	public void setInvType(String invType) {
		this.invType = invType;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getCostCode() {
		return costCode;
	}

	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getStandPackage() {
		return standPackage;
	}

	public void setStandPackage(String standPackage) {
		this.standPackage = standPackage;
	}

	public String getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}

	public String getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderUnit() {
		return orderUnit;
	}

	public void setOrderUnit(String orderUnit) {
		this.orderUnit = orderUnit;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}

	public String getRecUser() {
		return recUser;
	}

	public void setRecUser(String recUser) {
		this.recUser = recUser;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getRecTel() {
		return recTel;
	}

	public void setRecTel(String recTel) {
		this.recTel = recTel;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getRecAdress() {
		return recAdress;
	}

	public void setRecAdress(String recAdress) {
		this.recAdress = recAdress;
	}

	public String getPlanNum() {
		return planNum;
	}

	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getArriveDateFrom() {
		return arriveDateFrom;
	}

	public void setArriveDateFrom(String arriveDateFrom) {
		this.arriveDateFrom = arriveDateFrom;
	}

	public String getArriveDateTo() {
		return arriveDateTo;
	}

	public void setArriveDateTo(String arriveDateTo) {
		this.arriveDateTo = arriveDateTo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPlanTime() {
		return planTime;
	}

	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}

	public String getWareCode() {
		return wareCode;
	}

	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}

	public String getLasetModifiedUser() {
		return lasetModifiedUser;
	}

	public void setLasetModifiedUser(String lasetModifiedUser) {
		this.lasetModifiedUser = lasetModifiedUser;
	}

	public String getReplySeqNo() {
		return replySeqNo;
	}

	public void setReplySeqNo(String replySeqNo) {
		this.replySeqNo = replySeqNo;
	}

	public String getTmpId() {
		return tmpId;
	}

	public void setTmpId(String tmpId) {
		this.tmpId = tmpId;
	}

	public String getReturnCount() {
		return returnCount;
	}

	public void setReturnCount(String returnCount) {
		this.returnCount = returnCount;
	}

	public String getReturnSum() {
		return returnSum;
	}

	public void setReturnSum(String returnSum) {
		this.returnSum = returnSum;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getImportStatus() {
		return importStatus;
	}

	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public String getExcelImportStatus() {
		return excelImportStatus;
	}

	public void setExcelImportStatus(String excelImportStatus) {
		this.excelImportStatus = excelImportStatus;
	}

	public String getExcelCheckResult() {
		return excelCheckResult;
	}

	public void setExcelCheckResult(String excelCheckResult) {
		this.excelCheckResult = excelCheckResult;
	}

	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}

	public String getPrintStatusDesc() {
		return printStatusDesc;
	}

	public void setPrintStatusDesc(String printStatusDesc) {
		this.printStatusDesc = printStatusDesc;
	}

	public String getFeedBackStatus() {
		return feedBackStatus;
	}

	public void setFeedBackStatus(String feedBackStatus) {
		this.feedBackStatus = feedBackStatus;
	}

	public String getLastModifiedIp() {
		return lastModifiedIp;
	}

	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getPrintUser() {
		return printUser;
	}

	public void setPrintUser(String printUser) {
		this.printUser = printUser;
	}

	public Object getQrCode() {
		return qrCode;
	}

	public void setQrCode(Object qrCode) {
		this.qrCode = qrCode;
	}

	public Object getBrCode() {
		return brCode;
	}

	public void setBrCode(Object brCode) {
		this.brCode = brCode;
	}

	public Object getLogo() {
		return logo;
	}

	public void setLogo(Object logo) {
		this.logo = logo;
	}
	
	public String getImportStatusDesc() {
		return importStatusDesc;
	}

	public void setImportStatusDesc(String importStatusDesc) {
		this.importStatusDesc = importStatusDesc;
	}

	public String getCheckResultDesc() {
		return checkResultDesc;
	}

	public void setCheckResultDesc(String checkResultDesc) {
		this.checkResultDesc = checkResultDesc;
	}
	
	public String getArriveDateEnd() {
		return arriveDateEnd;
	}

	public void setArriveDateEnd(String arriveDateEnd) {
		this.arriveDateEnd = arriveDateEnd;
	}

	public String getDepotNo() {
		return depotNo;
	}

	public void setDepotNo(String depotNo) {
		this.depotNo = depotNo;
	}

	public String getDepotAddress() {
		return depotAddress;
	}

	public void setDepotAddress(String depotAddress) {
		this.depotAddress = depotAddress;
	}

	public String getPartSpec() {
		return partSpec;
	}

	public void setPartSpec(String partSpec) {
		this.partSpec = partSpec;
	}

	public String getDepotAddr() {
		return depotAddr;
	}

	public void setDepotAddr(String depotAddr) {
		this.depotAddr = depotAddr;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getFactoryNo() {
		return factoryNo;
	}

	public void setFactoryNo(String factoryNo) {
		this.factoryNo = factoryNo;
	}

	/**
	 * @Description: 校验导入信息  
	 * @param: @param importList
	 * @param: @param m    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月11日
	 */
	public static void checkImportData(List<SwZCOrderModel> importList, SwZCOrderModel m) {
		StringBuffer checkInfo = new StringBuffer();
		if(StringUtils.isNullOrEmpty(m.getPurchaseNo())) {
			checkInfo.append("订单号不能为空;");
		}
		if(StringUtils.isNullOrEmpty(m.getPlanTime())) {
			checkInfo.append("计划交货日期不能为空;");
		}
		if(StringUtils.isNullOrEmpty(m.getPlanNum())) {
			checkInfo.append("计划交货数量不能为空;");
		}
		if(!StringUtils.isNullOrEmpty(m.getPlanTime())) {
			SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
			try {
				format.parse(m.getPlanTime());
			} catch (Exception e) {
				checkInfo.append("计划交货日期格式不正确;");
				m.setPlanTime("");
			}
		}
		if(StringUtils.isNullOrEmpty(checkInfo.toString())){
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
		}else{
			m.setCheckResult(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
			m.setCheckInfo(checkInfo.toString());
		}
		
	}

	
	
}
