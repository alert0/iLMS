package com.hanthink.mp.model;

import java.util.Date;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：零件分组  实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpPartSortModel extends AbstractModel<String>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8812601870595417423L;
	/**
	 * 主表  零件分组历史表
	 * @return
	 */
	/**
	* 逻辑主键id
	*/
	protected String id; 
	/**
	* 工厂信息
	*/
	protected String factoryCode; 
	
	/**
	* 临时单号
	*/
	protected String logisticsOrder; 
	
	/**
	* 供应商代码
	*/
	protected String supplierNo; 
	
	/**
	* 出货地代码
	*/
	protected String supFactory; 
	
	/**
	* 计算队列
	*/
	protected String unloadPort; 
	
	/**
	* 零件号
	*/
	private String partNo;
	
	/**
	* 分组号
	*/
	private Integer groupId;
	
	/**
	* dockrange范围
	*/
	private Integer drSortIdStart;
	
	/**
	* dockrange范围
	*/
	private Integer drSortIdEnd;
	
	/**
	* linesiderange范围
	*/
	private Integer lrSortIdStart;
	
	/**
	* linesiderange范围
	*/
	private Integer lrSortIdEnd;
	
	/**
	* 真实结束SortId
	*/
	private Integer realEndSortId;
	
	/**
	* 首台车下线时间
	*/
	private java.util.Date finalUnderlineTimeStart;
	
	/**
	* 首台车下线时间
	*/
	private String finalUnderlineTimeStartStr;
	
	/**
	* 末台车下线时间
	*/
	private java.util.Date finalUnderlineTimeEnd;
	
	/**
	* 末台车下线时间
	*/
	private String finalUnderlineTimeEndStr;
	
	/**
	* 首台车下线时间(差异)
	*/
	private java.util.Date finalUnderlineTimeStartNew;
	
	/**
	* 首台车下线时间(差异)
	*/
	private String finalUnderlineTimeStartNewStr;
	
	/**
	* 末台车下线时间(差异)
	*/
	private java.util.Date finalUnderlineTimeEndNew;
	
	/**
	* 末台车下线时间(差异)
	*/
	private String finalUnderlineTimeEndNewStr;
	
	/**
	* 订购包装
	*/
	private Integer orderPackage;
	
	/**
	* 分组用量
	*/
	private Integer groupQty;
	
	/**
	* 计算状态
	*/
	private Integer calStatus;
	
	/**
	* 分组用量(新）
	*/
	private Integer groupQtyNew;
	
	/**
	* 车间
	*/
	private String workcenter;
	
	/**
	* 分组用量(差异）
	*/
	private Integer groupQtyDiff;
	
	/**
	 * 零件订购台套数据
	 */
	private Integer itemCalculateNum;
	
	/**
	* 到货时间
	*/
	private java.util.Date arriveTime;
	
	/**
	* 到货时间
	*/
	private String arriveTimeStr;
	
	/**
	* 线边时间
	*/
	private java.util.Date lineSideTime;
	
	/**
	* 线边时间
	*/
	private String lineSideTimeStr;
	
	/**
	* 线边车号（混合车型）
	*/
	private Integer lineSideSortId;
	
	/**
	* 使用车号（分车型）
	*/
	private Integer useRangeSortIdStart;
	
	/**
	* 使用车号（分车型）
	*/
	private Integer useRangeSortIdEnd;
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime;
	
	/**
	* 车型
	*/
	private String carType;
	
	/**
	* 供货比率
	*/
	private Integer supplyRate;
	
	/**
	* 供应商准备时间 单位(天)
	*/
	private Integer InPlanForwardTime;
	
	/**
	* 采购类型
	*/
	private String purchaseType;

	/**
	 * 副表 物料主数据表 
	 */
	/**
	* 简号
	*/
	private String partShortNo;

	/**
	* 零件名称
	*/
	private String partNameCn;
	
	/**
	 * 副表 供应商表 
	 */
	/**
	* 供应商名称
	*/
	private String supplierName;
	
	/**
	 * 副表 数据字典 
	 */
	/**
	* 编码值
	*/
	private String codeValueName;
	
	/**
	 * 主表  零件分组表
	 * @return
	 */
	
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getLogisticsOrder() {
		return logisticsOrder;
	}

	public void setLogisticsOrder(String logisticsOrder) {
		this.logisticsOrder = logisticsOrder;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupFactory() {
		return supFactory;
	}

	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}

	public String getUnloadPort() {
		return unloadPort;
	}

	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getDrSortIdStart() {
		return drSortIdStart;
	}

	public void setDrSortIdStart(Integer drSortIdStart) {
		this.drSortIdStart = drSortIdStart;
	}

	public Integer getDrSortIdEnd() {
		return drSortIdEnd;
	}

	public void setDrSortIdEnd(Integer drSortIdEnd) {
		this.drSortIdEnd = drSortIdEnd;
	}

	public Integer getLrSortIdStart() {
		return lrSortIdStart;
	}

	public void setLrSortIdStart(Integer lrSortIdStart) {
		this.lrSortIdStart = lrSortIdStart;
	}

	public Integer getLrSortIdEnd() {
		return lrSortIdEnd;
	}

	public void setLrSortIdEnd(Integer lrSortIdEnd) {
		this.lrSortIdEnd = lrSortIdEnd;
	}

	public Integer getRealEndSortId() {
		return realEndSortId;
	}

	public void setRealEndSortId(Integer realEndSortId) {
		this.realEndSortId = realEndSortId;
	}

	public java.util.Date getFinalUnderlineTimeStart() {
		return finalUnderlineTimeStart;
	}

	public void setFinalUnderlineTimeStart(java.util.Date finalUnderlineTimeStart) {
		this.finalUnderlineTimeStart = finalUnderlineTimeStart;
	}

	public String getFinalUnderlineTimeStartStr() {
		return finalUnderlineTimeStartStr;
	}

	public void setFinalUnderlineTimeStartStr(String finalUnderlineTimeStartStr) {
		this.finalUnderlineTimeStartStr = finalUnderlineTimeStartStr;
	}

	public java.util.Date getFinalUnderlineTimeEnd() {
		return finalUnderlineTimeEnd;
	}

	public void setFinalUnderlineTimeEnd(java.util.Date finalUnderlineTimeEnd) {
		this.finalUnderlineTimeEnd = finalUnderlineTimeEnd;
	}

	public String getFinalUnderlineTimeEndStr() {
		return finalUnderlineTimeEndStr;
	}

	public void setFinalUnderlineTimeEndStr(String finalUnderlineTimeEndStr) {
		this.finalUnderlineTimeEndStr = finalUnderlineTimeEndStr;
	}

	public java.util.Date getFinalUnderlineTimeStartNew() {
		return finalUnderlineTimeStartNew;
	}

	public void setFinalUnderlineTimeStartNew(java.util.Date finalUnderlineTimeStartNew) {
		this.finalUnderlineTimeStartNew = finalUnderlineTimeStartNew;
	}

	public String getFinalUnderlineTimeStartNewStr() {
		return finalUnderlineTimeStartNewStr;
	}

	public void setFinalUnderlineTimeStartNewStr(String finalUnderlineTimeStartNewStr) {
		this.finalUnderlineTimeStartNewStr = finalUnderlineTimeStartNewStr;
	}

	public java.util.Date getFinalUnderlineTimeEndNew() {
		return finalUnderlineTimeEndNew;
	}

	public void setFinalUnderlineTimeEndNew(java.util.Date finalUnderlineTimeEndNew) {
		this.finalUnderlineTimeEndNew = finalUnderlineTimeEndNew;
	}

	public String getFinalUnderlineTimeEndNewStr() {
		return finalUnderlineTimeEndNewStr;
	}

	public void setFinalUnderlineTimeEndNewStr(String finalUnderlineTimeEndNewStr) {
		this.finalUnderlineTimeEndNewStr = finalUnderlineTimeEndNewStr;
	}

	public Integer getOrderPackage() {
		return orderPackage;
	}

	public void setOrderPackage(Integer orderPackage) {
		this.orderPackage = orderPackage;
	}

	public Integer getGroupQty() {
		return groupQty;
	}

	public void setGroupQty(Integer groupQty) {
		this.groupQty = groupQty;
	}

	public Integer getCalStatus() {
		return calStatus;
	}

	public void setCalStatus(Integer calStatus) {
		this.calStatus = calStatus;
	}

	public Integer getGroupQtyNew() {
		return groupQtyNew;
	}

	public void setGroupQtyNew(Integer groupQtyNew) {
		this.groupQtyNew = groupQtyNew;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public Integer getGroupQtyDiff() {
		return groupQtyDiff;
	}

	public void setGroupQtyDiff(Integer groupQtyDiff) {
		this.groupQtyDiff = groupQtyDiff;
	}

	public Integer getItemCalculateNum() {
		return itemCalculateNum;
	}

	public void setItemCalculateNum(Integer itemCalculateNum) {
		this.itemCalculateNum = itemCalculateNum;
	}

	public java.util.Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(java.util.Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getArriveTimeStr() {
		return arriveTimeStr;
	}

	public void setArriveTimeStr(String arriveTimeStr) {
		this.arriveTimeStr = arriveTimeStr;
	}

	public java.util.Date getLineSideTime() {
		return lineSideTime;
	}

	public void setLineSideTime(java.util.Date lineSideTime) {
		this.lineSideTime = lineSideTime;
	}

	public String getLineSideTimeStr() {
		return lineSideTimeStr;
	}

	public void setLineSideTimeStr(String lineSideTimeStr) {
		this.lineSideTimeStr = lineSideTimeStr;
	}

	public Integer getLineSideSortId() {
		return lineSideSortId;
	}

	public void setLineSideSortId(Integer lineSideSortId) {
		this.lineSideSortId = lineSideSortId;
	}

	public Integer getUseRangeSortIdStart() {
		return useRangeSortIdStart;
	}

	public void setUseRangeSortIdStart(Integer useRangeSortIdStart) {
		this.useRangeSortIdStart = useRangeSortIdStart;
	}

	public Integer getUseRangeSortIdEnd() {
		return useRangeSortIdEnd;
	}

	public void setUseRangeSortIdEnd(Integer useRangeSortIdEnd) {
		this.useRangeSortIdEnd = useRangeSortIdEnd;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public Integer getSupplyRate() {
		return supplyRate;
	}

	public void setSupplyRate(Integer supplyRate) {
		this.supplyRate = supplyRate;
	}

	public Integer getInPlanForwardTime() {
		return InPlanForwardTime;
	}

	public void setInPlanForwardTime(Integer inPlanForwardTime) {
		InPlanForwardTime = inPlanForwardTime;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	/**
	 * 副表  物料主数据
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
	 * 副表  供应商表
	 */
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * 副表  数据字典
	 */
	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
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

	
	
}