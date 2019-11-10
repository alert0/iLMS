package com.hanthink.pup.model;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;

/**
 * <pre> 
 * 功能描述:手工调整订单实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月24日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class PupManualOrderModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = -3403499973822293678L;

	protected String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	/** 订单号    */
	protected String purchaseNo; 
	
	/** 物流单号     */
	protected String orderNo; 
	
	/** 工厂代码     */
	protected String factoryCode; 
	
	/** 0:不取货   1:取货     */
	protected String pickupFlag; 
	
	/**
	 * 合并车次     */
	protected String mergeNo; 
	
	/** 区域     */
	protected String area; 
	
	/** 订单描述     */
	protected String orderDesc; 
	
	/** 出货地     */
	protected String supFactory; 
	
	/**
	 * 订单物流模式     */
	protected String pickupType; 
	
	/** 车型     */
	protected String carType; 
	
	/** 工作日     */
	protected String workday; 
	
	/** 取货日期     */
	protected String pickDate;
	/** 取货时间 */
	protected String pickTime;
	/** 到货日期 */
	protected String arriveDate; 
	/** 到货时间 */
	protected String arriveTime;
	
	/** 订单交货时间     */
	protected String orderDate; 
	
	/** 集货线路     */
	protected String routeCode; 
	
	/** 累计车次     */
	protected String totalNo; 
	
	/** 当日车次     */
	protected String todayNo; 
	
	/** 创建人   */
	protected String creationUser; 
	
	/** 创建时间     */
	protected String creationTime; 
	
	/** 最后修改人 */
	protected String lastModifiedUser; 
	
	/** 最后修改时间 */
	protected String lastModifiedTime;
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getPickupFlag() {
		return pickupFlag;
	}
	public void setPickupFlag(String pickupFlag) {
		this.pickupFlag = pickupFlag;
	}
	public String getMergeNo() {
		return mergeNo;
	}
	public void setMergeNo(String mergeNo) {
		this.mergeNo = mergeNo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public String getSupFactory() {
		return supFactory;
	}
	public void setSupFactory(String supFactory) {
		this.supFactory = supFactory;
	}
	public String getPickupType() {
		return pickupType;
	}
	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getWorkday() {
		return workday;
	}
	public void setWorkday(String workday) {
		this.workday = workday;
	}
	public String getPickDate() {
		return pickDate;
	}
	public void setPickDate(String pickDate) {
		this.pickDate = pickDate;
	}
	public String getPickTime() {
		return pickTime;
	}
	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getTotalNo() {
		return totalNo;
	}
	public void setTotalNo(String totalNo) {
		this.totalNo = totalNo;
	}
	public String getTodayNo() {
		return todayNo;
	}
	public void setTodayNo(String todayNo) {
		this.todayNo = todayNo;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	} 	
	
	private String uuid;
	private String checkResult;
	private String checkInfo;
	private String importStatus;
	private String opeType;
	
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
	
	public static void checkImportManualOrder(PupManualOrderModel orderModel) {
		StringBuffer checkInfo = new StringBuffer();
		
		if(StringUtil.isEmpty(orderModel.getPurchaseNo())) {
			checkInfo.append("订单号为空;");
		}
		if(StringUtil.isEmpty(orderModel.getOrderNo())) {
			checkInfo.append("物流单号为空;");
		}
		if (StringUtil.isEmpty(orderModel.getPickupFlag())) {
			checkInfo.append("取货标识为空;");
		}
		if (StringUtil.isEmpty(orderModel.getSupFactory())) {
			checkInfo.append("出货地为空;");
		}
		if (StringUtil.isEmpty(orderModel.getRouteCode())) {
			checkInfo.append("集货线路为空;");
		}
		
		if(checkInfo == null || "".equals(checkInfo.toString())){
            orderModel.setCheckResult("1");
            orderModel.setOpeType("I");
            orderModel.setCheckInfo("");
        }else{
            orderModel.setCheckResult("0");
            orderModel.setOpeType("U");
            orderModel.setCheckInfo(checkInfo.toString());
        }
	}
	@Override
	public String toString() {
		return "PupManualOrderModel [id=" + id + ", purchaseNo=" + purchaseNo + ", orderNo=" + orderNo
				+ ", factoryCode=" + factoryCode + ", pickupFlag=" + pickupFlag + ", mergeNo=" + mergeNo + ", area="
				+ area + ", orderDesc=" + orderDesc + ", supFactory=" + supFactory + ", pickupType=" + pickupType
				+ ", carType=" + carType + ", workday=" + workday + ", pickDate=" + pickDate + ", pickTime=" + pickTime
				+ ", arriveDate=" + arriveDate + ", arriveTime=" + arriveTime + ", orderDate=" + orderDate
				+ ", routeCode=" + routeCode + ", totalNo=" + totalNo + ", todayNo=" + todayNo + ", creationUser="
				+ creationUser + ", creationTime=" + creationTime + ", lastModifiedUser=" + lastModifiedUser
				+ ", lastModifiedTime=" + lastModifiedTime + ", uuid=" + uuid + ", checkResult=" + checkResult
				+ ", checkInfo=" + checkInfo + ", importStatus=" + importStatus + ", opeType=" + opeType + "]";
	}
}