package com.hanthink.mp.model;
import java.text.SimpleDateFormat;

import com.hotent.base.core.model.AbstractModel;


 /**
  * <pre> 
  * 描述：订单履历历史表 实体对象
  * 作者:lz
  * 日期:2018-09-17 11:46:26
  * </pre>
  */
public class MpOrderRecordHisModel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7606619713610553054L;

	/**
	 * 工厂     */
	private String factoryCode; 
	
	/**
	 * 零件号     */
	private String partNo; 
	
	/**
	 * 供应商代码     */
	private String supplierNo; 
	
	/**
	 * 出货地     */
	private String supFactory; 
	
	/**
	 * 计算队列     */
	private String unloadPort; 
	
	/**
	 * 临时单号     */
	private String logisticsOrder; 
	
	/**
	 * 物流单号      */
	private String orderNo; 
	
	/**
	 * 物流标识     */
	private String logisticsFlag; 
	
	/**
	 * 分组号     */
	private Integer groupId; 
	
	private String groupIdStr; 
	
	/**
	 * dockrange范围     */
	private Integer drSortIdStart; 
	
	private String drSortIdStartStr; 
	
	/**
	 * dockrange范围     */
	private Integer drSortIdEnd; 
	
	private String drSortIdEndStr; 
	
	/**
	 * linesiderange范围     */
	private Integer lrSortIdStart; 
	
	private String lrSortIdStartStr; 
	
	/**
	 * linesiderange范围     */
	private Integer lrSortIdEnd; 
	
	private String lrSortIdEndStr; 
	
	/**
	 * 零件不良     */
	private Integer defectNum; 
	
	private String defectNumStr; 
	
	/**
	 * 安全库存     */
	private Integer safeNum; 
	
	private String safeNumStr; 
	
	/**
	 * 计划调整差异     */
	private Integer adjDiffNum; 
	
	private String adjDiffNumStr; 
	
	/**
	 * 净需求     */
	private Integer necessaryNetNum; 
	
	private String necessaryNetNumStr; 
	
	/**
	 * 必要数订购剩余量     */
	private Integer necessaryOrderResidual; 
	
	private String necessaryOrderResidualStr; 
	
	/**
	 * 必要数实际订购数量      */
	private Integer necessaryRealOrderNum; 
	
	private String necessaryRealOrderNumStr; 
	
	/**
	 * 必要数计划数量      */
	private Integer necessaryPlanNum; 
	
	private String necessaryPlanNumStr; 
	
	/**
	 * 必要数实际剩余量     */
	private Integer necessaryRealResidualNum; 
	
	private String necessaryRealResidualNumStr; 
	
	/**
	 * 订购数量     */
	private Integer orderNum; 
	
	private String orderNumStr; 
	
	/**
	 * 总订购数量     */
	private Integer totalOrderNum; 
	
	private String totalOrderNumStr; 
	
	/**
	 * 订购箱数     */
	private Integer totalOrderBox; 
	
	private String totalOrderBoxStr; 
	
	/**
	 * 调整箱数     */
	private Integer adjBox; 
	
	private String adjBoxStr; 
	
	/**
	 * 调整订购数量     */
	private Integer adjOrderNum; 
	
	private String adjOrderNumStr; 
	
	/**
	 * 到达时间     */
	private java.util.Date arriveTime;
	
	/**
	 * 到达时间     */
	private String arriveTimeStr;
	
	/**
	 * 订单状态     */
	private String orderStatus; 
	
	/**
	 * 订单发行日      */
	private java.util.Date orderIssueDate; 
	
	private String orderIssueDateStr; 
	
	/**
	 * 厂家出货时间      */
	private java.util.Date shipmentDate; 
	
	private String shipmentDateStr; 
	
	/**
	 * 采购部门      */
	private String purchaseDept; 
	
	/**
	 * 订货仓库      */
	private String orderDepot; 
	
	/**
	 * 台套数     */
	private Integer calculateNum; 
	
	private String calculateNumStr; 
	
	private String startCreationTime;
	private String endCreationTime;
	
	/**
	 * 创建时间     */
	private java.util.Date creationTime; 
	
	private String creationTimeStr; 
	
	/**
	 * 创建人     */
	private String creationUser; 
	
	/**
	 * 零件单位    */
	private String partUnit; 
	
	/**
	 * 计划订单号 流水号     */
	private String planOrderId; 
	
	/**
	 * 订购使用的包装数     */
	private Integer orderPackage; 
	
	private String orderPackageStr; 
	
	/**
	 * 采购类型     */
	private String purchaseType; 
	
	/**
	 * 零件分组唯一标识     */
	private Integer partGroupId; 
	
	private String partGroupIdStr;
	
	/**
	 * 订单号     */
	private String purchaseNo; 
	
	/**
	 * 车型    */
	private String modelCode;
	
	/**
	 * 副表  物料主数据
	 */
	/**
	 * 简号     
	 * */
	private String partShortNo; 
	
	/**
	 * 零件名称    
	 * */
	private String partNameCn; 
	
	/**
	 * 副表  供应商表
	 */
	/**
	 * 供应商名称    
	 * */
	private String supplierName; 
	
	/**
	 * 副表  数据字典表
	 */
	/**
	 * 数据值
	 */
	private String codeValueName;
	
	/**
	 * 数据值
	 */
	private String codeValueNameF;
	
	/**
	 * 数据值
	 */
	private String codeValueNameG;
	
	/**
	 * 返回 工厂     * @return
	 */
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	
	public String getFactoryCode() {
		return this.factoryCode;
	}
	
	/**
	 * 返回 零件号     * @return
	 */
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	
	public String getPartNo() {
		return this.partNo;
	}
	
	/**
	 * 返回 供应商代码     * @return
	 */
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	
	public String getSupplierNo() {
		return this.supplierNo;
	}
	
	/**
	 * 返回 出货地     * @return
	 */
	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}
	
	public String getSupFactory() {
		return this.supFactory;
	}
	
	/**
	 * 返回 计算队列     * @return
	 */
	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}
	
	public String getUnloadPort() {
		return this.unloadPort;
	}
	
	/**
	 * 返回 临时单号     * @return
	 */
	public void setLogisticsOrder(String logisticsOrder) {
		this.logisticsOrder = logisticsOrder;
	}
	
	public String getLogisticsOrder() {
		return this.logisticsOrder;
	}
	
	/**
	 * 返回 物流单号      * @return
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
	
	/**
	 * 返回 物流标识     * @return
	 */
	public void setLogisticsFlag(String logisticsFlag) {
		this.logisticsFlag = logisticsFlag;
	}
	
	public String getLogisticsFlag() {
		return this.logisticsFlag;
	}
	
	/**
	 * 返回 分组号     * @return
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	public Integer getGroupId() {
		return this.groupId;
	}
	
	/**
	 * 返回 dockrange范围     * @return
	 */
	public void setDrSortIdStart(Integer drSortIdStart) {
		this.drSortIdStart = drSortIdStart;
	}
	
	public Integer getDrSortIdStart() {
		return this.drSortIdStart;
	}
	
	/**
	 * 返回 dockrange范围     * @return
	 */
	public void setDrSortIdEnd(Integer drSortIdEnd) {
		this.drSortIdEnd = drSortIdEnd;
	}
	
	public Integer getDrSortIdEnd() {
		return this.drSortIdEnd;
	}
	
	/**
	 * 返回 linesiderange范围     * @return
	 */
	public void setLrSortIdStart(Integer lrSortIdStart) {
		this.lrSortIdStart = lrSortIdStart;
	}
	
	public Integer getLrSortIdStart() {
		return this.lrSortIdStart;
	}
	
	/**
	 * 返回 linesiderange范围     * @return
	 */
	public void setLrSortIdEnd(Integer lrSortIdEnd) {
		this.lrSortIdEnd = lrSortIdEnd;
	}
	
	public Integer getLrSortIdEnd() {
		return this.lrSortIdEnd;
	}
	
	/**
	 * 返回 零件不良     * @return
	 */
	public void setDefectNum(Integer defectNum) {
		this.defectNum = defectNum;
	}
	
	public Integer getDefectNum() {
		return this.defectNum;
	}
	
	/**
	 * 返回 安全库存     * @return
	 */
	public void setSafeNum(Integer safeNum) {
		this.safeNum = safeNum;
	}
	
	public Integer getSafeNum() {
		return this.safeNum;
	}
	
	/**
	 * 返回 计划调整差异     * @return
	 */
	public void setAdjDiffNum(Integer adjDiffNum) {
		this.adjDiffNum = adjDiffNum;
	}
	
	public Integer getAdjDiffNum() {
		return this.adjDiffNum;
	}
	
	/**
	 * 返回 净需求     * @return
	 */
	public void setNecessaryNetNum(Integer necessaryNetNum) {
		this.necessaryNetNum = necessaryNetNum;
	}
	
	public Integer getNecessaryNetNum() {
		return this.necessaryNetNum;
	}
	
	/**
	 * 返回 必要数订购剩余量     * @return
	 */
	public void setNecessaryOrderResidual(Integer necessaryOrderResidual) {
		this.necessaryOrderResidual = necessaryOrderResidual;
	}
	
	public Integer getNecessaryOrderResidual() {
		return this.necessaryOrderResidual;
	}
	
	/**
	 * 返回 必要数实际订购数量      * @return
	 */
	public void setNecessaryRealOrderNum(Integer necessaryRealOrderNum) {
		this.necessaryRealOrderNum = necessaryRealOrderNum;
	}
	
	public Integer getNecessaryRealOrderNum() {
		return this.necessaryRealOrderNum;
	}
	
	/**
	 * 返回 必要数计划数量      * @return
	 */
	public void setNecessaryPlanNum(Integer necessaryPlanNum) {
		this.necessaryPlanNum = necessaryPlanNum;
	}
	
	public Integer getNecessaryPlanNum() {
		return this.necessaryPlanNum;
	}
	
	/**
	 * 返回 必要数实际剩余量     * @return
	 */
	public void setNecessaryRealResidualNum(Integer necessaryRealResidualNum) {
		this.necessaryRealResidualNum = necessaryRealResidualNum;
	}
	
	public Integer getNecessaryRealResidualNum() {
		return this.necessaryRealResidualNum;
	}
	
	/**
	 * 返回 订购数量     * @return
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	public Integer getOrderNum() {
		return this.orderNum;
	}
	
	/**
	 * 返回 总订购数量     * @return
	 */
	public void setTotalOrderNum(Integer totalOrderNum) {
		this.totalOrderNum = totalOrderNum;
	}
	
	public Integer getTotalOrderNum() {
		return this.totalOrderNum;
	}
	
	/**
	 * 返回 订购箱数     * @return
	 */
	public void setTotalOrderBox(Integer totalOrderBox) {
		this.totalOrderBox = totalOrderBox;
	}
	
	public Integer getTotalOrderBox() {
		return this.totalOrderBox;
	}
	
	/**
	 * 返回 调整箱数     * @return
	 */
	public void setAdjBox(Integer adjBox) {
		this.adjBox = adjBox;
	}
	
	public Integer getAdjBox() {
		return this.adjBox;
	}
	
	/**
	 * 返回 调整订购数量     * @return
	 */
	public void setAdjOrderNum(Integer adjOrderNum) {
		this.adjOrderNum = adjOrderNum;
	}
	
	public Integer getAdjOrderNum() {
		return this.adjOrderNum;
	}
	
	/**
	 * 返回 到达时间     * @return
	 */
	public void setArriveTime(java.util.Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	public java.util.Date getArriveTime() {
		return this.arriveTime;
	}
	
	/**
	 * 返回 到达时间     * @return
	 */
	public String getArriveTimeStr() {
		return arriveTimeStr;
	}

	public void setArriveTimeStr(String arriveTimeStr) {
		this.arriveTimeStr = arriveTimeStr;
	}

	/**
	 * 返回 订单状态     * @return
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getOrderStatus() {
		return this.orderStatus;
	}
	
	/**
	 * 返回 订单发行日      * @return
	 */
	public void setOrderIssueDate(java.util.Date orderIssueDate) {
		this.orderIssueDate = orderIssueDate;
	}
	
	public java.util.Date getOrderIssueDate() {
		return this.orderIssueDate;
	}
	
	/**
	 * 返回 厂家出货时间      * @return
	 */
	public void setShipmentDate(java.util.Date shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	
	public java.util.Date getShipmentDate() {
		return this.shipmentDate;
	}
	
	/**
	 * 返回 采购部门      * @return
	 */
	public void setPurchaseDept(String purchaseDept) {
		this.purchaseDept = purchaseDept;
	}
	
	public String getPurchaseDept() {
		return this.purchaseDept;
	}
	
	/**
	 * 返回 订货仓库      * @return
	 */
	public void setOrderDepot(String orderDepot) {
		this.orderDepot = orderDepot;
	}
	
	public String getOrderDepot() {
		return this.orderDepot;
	}
	
	/**
	 * 返回 台套数     * @return
	 */
	public void setCalculateNum(Integer calculateNum) {
		this.calculateNum = calculateNum;
	}
	
	public Integer getCalculateNum() {
		return this.calculateNum;
	}
	
	/**
	 * 返回 CREATION_TIME     * @return
	 */
	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}
	
	public java.util.Date getCreationTime() {
		return this.creationTime;
	}
	
	/**
	 * 返回 CREATION_USER     * @return
	 */
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	
	public String getCreationUser() {
		return this.creationUser;
	}
	
	/**
	 * 返回 PART_UNIT     * @return
	 */
	public void setPartUnit(String partUnit) {
		this.partUnit = partUnit;
	}
	
	public String getPartUnit() {
		return this.partUnit;
	}
	
	/**
	 * 返回 计划订单号 流水号     * @return
	 */
	public void setPlanOrderId(String planOrderId) {
		this.planOrderId = planOrderId;
	}
	
	public String getPlanOrderId() {
		return this.planOrderId;
	}
	
	/**
	 * 返回 订购使用的包装数     * @return
	 */
	public void setOrderPackage(Integer orderPackage) {
		this.orderPackage = orderPackage;
	}
	
	public Integer getOrderPackage() {
		return this.orderPackage;
	}
	
	/**
	 * 返回 采购类型     * @return
	 */
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	
	public String getPurchaseType() {
		return this.purchaseType;
	}
	
	/**
	 * 返回 零件分组唯一标识     * @return
	 */
	public void setPartGroupId(Integer partGroupId) {
		this.partGroupId = partGroupId;
	}
	
	public Integer getPartGroupId() {
		return this.partGroupId;
	}
	
	/**
	 * 返回 订单号     * @return
	 */
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	
	public String getPurchaseNo() {
		return this.purchaseNo;
	}

	/**
	 * 车型
	 */
	
	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	/**
	 * 副表  物料主数据表
	 */
	public String getPartShortNo() {
		return partShortNo;
	}

	public void setPartShortNo(String partShortNo) {
		this.partShortNo = partShortNo;
	}

	public String getPartNameCn() {
		return partNameCn;
	}

	public void setPartNameCn(String partNameCn) {
		this.partNameCn = partNameCn;
	}
	
	/**
	 * 副表 供应商表
	 */
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	
	/**
	 * 副表 数据字典表
	 */
	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}
	
	public String getCodeValueNameF() {
		return codeValueNameF;
	}

	public void setCodeValueNameF(String codeValueNameF) {
		this.codeValueNameF = codeValueNameF;
	}

	public String getCodeValueNameG() {
		return codeValueNameG;
	}

	public void setCodeValueNameG(String codeValueNameG) {
		this.codeValueNameG = codeValueNameG;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/** 导入UUID */
	private String  uuid;
	/** 检查结果(0错误;1：通过;2:重复存在) */
	private String  checkResult;
	/** 检查结果信息 */
	private String  checkInfo;
	/** 导入状态 */
	private String  importStatus;
	/** 操作类型状态 */
	private String  opeType;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}

	/**
     * 订单履历导入数据检查
     * @param vo
     * @author linzhuo  2018-9-12
     */
    public static void checkImportData(MpOrderRecordHisModel m) {
        StringBuffer checkInfo = new StringBuffer();
        
        if(m.getFactoryCode()== null || "".equals(m.getFactoryCode())){
            checkInfo.append("工厂代码为空;");
        }
        if(m.getPartNo() == null || "".equals(m.getPartNo())){
            checkInfo.append("零件号为空;");
        }
        if(m.getSupplierNo() == null || "".equals(m.getSupplierNo())){
            checkInfo.append("供应商代码为空;");
        }
        if(m.getSupFactory() == null || "".equals(m.getSupFactory())){
            checkInfo.append("出货地代码为空;");
        }
        if(m.getUnloadPort() == null || "".equals(m.getUnloadPort())){
            checkInfo.append("计算队列为空;");
        }
        if(m.getLogisticsOrder() == null || "".equals(m.getLogisticsOrder())){
            checkInfo.append("临时单号为空;");
        }
        if(m.getGroupIdStr()== null || "".equals(m.getGroupIdStr())){
            checkInfo.append("分组号为空;");
        }
        if(m.getOrderStatus() == null || "".equals(m.getOrderStatus())){
            checkInfo.append("订单状态为空;");
        }
        if(m.getArriveTimeStr() == null || "".equals(m.getArriveTimeStr())){
        	 checkInfo.append("到货时间为空;");
        }else {
        	try {
         		 SimpleDateFormat DateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         		 DateFormat.parse(m.getArriveTimeStr());
         	}catch (Exception e) {
         		m.setArriveTimeStr(null);
         		checkInfo.append("不是日期;");
  			}
        }
        
        if(checkInfo == null || "".equals(checkInfo.toString())){
            m.setCheckResult("1");
            m.setOpeType("I");
            m.setCheckInfo("");
        }else{
            m.setCheckResult("0");
            m.setOpeType("U");
            m.setCheckInfo(checkInfo.toString());
        }
        
    }

	public String getGroupIdStr() {
		return groupIdStr;
	}

	public void setGroupIdStr(String groupIdStr) {
		this.groupIdStr = groupIdStr;
	}

	public String getDrSortIdStartStr() {
		return drSortIdStartStr;
	}

	public void setDrSortIdStartStr(String drSortIdStartStr) {
		this.drSortIdStartStr = drSortIdStartStr;
	}

	public String getDrSortIdEndStr() {
		return drSortIdEndStr;
	}

	public void setDrSortIdEndStr(String drSortIdEndStr) {
		this.drSortIdEndStr = drSortIdEndStr;
	}

	public String getLrSortIdStartStr() {
		return lrSortIdStartStr;
	}

	public void setLrSortIdStartStr(String lrSortIdStartStr) {
		this.lrSortIdStartStr = lrSortIdStartStr;
	}

	public String getLrSortIdEndStr() {
		return lrSortIdEndStr;
	}

	public void setLrSortIdEndStr(String lrSortIdEndStr) {
		this.lrSortIdEndStr = lrSortIdEndStr;
	}

	public String getDefectNumStr() {
		return defectNumStr;
	}

	public void setDefectNumStr(String defectNumStr) {
		this.defectNumStr = defectNumStr;
	}

	public String getSafeNumStr() {
		return safeNumStr;
	}

	public void setSafeNumStr(String safeNumStr) {
		this.safeNumStr = safeNumStr;
	}

	public String getAdjDiffNumStr() {
		return adjDiffNumStr;
	}

	public void setAdjDiffNumStr(String adjDiffNumStr) {
		this.adjDiffNumStr = adjDiffNumStr;
	}

	public String getNecessaryNetNumStr() {
		return necessaryNetNumStr;
	}

	public void setNecessaryNetNumStr(String necessaryNetNumStr) {
		this.necessaryNetNumStr = necessaryNetNumStr;
	}

	public String getNecessaryOrderResidualStr() {
		return necessaryOrderResidualStr;
	}

	public void setNecessaryOrderResidualStr(String necessaryOrderResidualStr) {
		this.necessaryOrderResidualStr = necessaryOrderResidualStr;
	}

	public String getNecessaryRealOrderNumStr() {
		return necessaryRealOrderNumStr;
	}

	public void setNecessaryRealOrderNumStr(String necessaryRealOrderNumStr) {
		this.necessaryRealOrderNumStr = necessaryRealOrderNumStr;
	}

	public String getNecessaryPlanNumStr() {
		return necessaryPlanNumStr;
	}

	public void setNecessaryPlanNumStr(String necessaryPlanNumStr) {
		this.necessaryPlanNumStr = necessaryPlanNumStr;
	}

	public String getNecessaryRealResidualNumStr() {
		return necessaryRealResidualNumStr;
	}

	public void setNecessaryRealResidualNumStr(String necessaryRealResidualNumStr) {
		this.necessaryRealResidualNumStr = necessaryRealResidualNumStr;
	}

	public String getOrderNumStr() {
		return orderNumStr;
	}

	public void setOrderNumStr(String orderNumStr) {
		this.orderNumStr = orderNumStr;
	}

	public String getTotalOrderNumStr() {
		return totalOrderNumStr;
	}

	public void setTotalOrderNumStr(String totalOrderNumStr) {
		this.totalOrderNumStr = totalOrderNumStr;
	}

	public String getTotalOrderBoxStr() {
		return totalOrderBoxStr;
	}

	public void setTotalOrderBoxStr(String totalOrderBoxStr) {
		this.totalOrderBoxStr = totalOrderBoxStr;
	}

	public String getAdjBoxStr() {
		return adjBoxStr;
	}

	public void setAdjBoxStr(String adjBoxStr) {
		this.adjBoxStr = adjBoxStr;
	}

	public String getAdjOrderNumStr() {
		return adjOrderNumStr;
	}

	public void setAdjOrderNumStr(String adjOrderNumStr) {
		this.adjOrderNumStr = adjOrderNumStr;
	}

	public String getOrderIssueDateStr() {
		return orderIssueDateStr;
	}

	public void setOrderIssueDateStr(String orderIssueDateStr) {
		this.orderIssueDateStr = orderIssueDateStr;
	}

	public String getShipmentDateStr() {
		return shipmentDateStr;
	}

	public void setShipmentDateStr(String shipmentDateStr) {
		this.shipmentDateStr = shipmentDateStr;
	}

	public String getCalculateNumStr() {
		return calculateNumStr;
	}

	public void setCalculateNumStr(String calculateNumStr) {
		this.calculateNumStr = calculateNumStr;
	}

	public String getCreationTimeStr() {
		return creationTimeStr;
	}

	public void setCreationTimeStr(String creationTimeStr) {
		this.creationTimeStr = creationTimeStr;
	}

	public String getOrderPackageStr() {
		return orderPackageStr;
	}

	public void setOrderPackageStr(String orderPackageStr) {
		this.orderPackageStr = orderPackageStr;
	}

	public String getPartGroupIdStr() {
		return partGroupIdStr;
	}

	public void setPartGroupIdStr(String partGroupIdStr) {
		this.partGroupIdStr = partGroupIdStr;
	}

	public String getStartCreationTime() {
		return startCreationTime;
	}

	public void setStartCreationTime(String startCreationTime) {
		this.startCreationTime = startCreationTime;
	}

	public String getEndCreationTime() {
		return endCreationTime;
	}

	public void setEndCreationTime(String endCreationTime) {
		this.endCreationTime = endCreationTime;
	}
    
}