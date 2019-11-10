package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

public class EmptyReturnKbModel extends AbstractModel<String>{
	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2019年3月5日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 7687377729960852971L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 作业状态 */
	private String workStatus;
	/** 车间 */
	private String workCenter;
	/** 参数代码 */
	private String paramCode;
	/** 参数值 */
	private String paramVal;
	/** IP */
	private String ip;
	/** 看板类型 */
	private String kbType;
	/** DCS单号 */
	private String planSheetNo;
	/** 反空站台 */
	private String ret;
	/** 反空站台 */
	private String retEmptyPlatform;
	/** 工厂 */
	private String factoryCode;
	/** 计划发车时间 */
	private java.util.Date planStartTime;
	/** 计划发车时间 */
	private java.util.Date planArriveTime;
	/** 计划发车时间 */
	private String planStartTimeStr;
	/** 计划到货时间*/
	private String planArriveTimeStr;
	/** 实际到货时间*/
	private String actualArriveTimeStr;
	/** 作业时间(系统参数)*/
	private String workTimeStr;
	/** 供应商*/
	private String supplierNo;
	/** 供应商名称*/
	private String supplierName;
	/** 托数/铁*/
	private String palletIron;
	/** 托数/箱*/
	private String palletBox;
	/** 箱种*/
	private String boxType;
	/** 订单号*/
	private String orderNo;
	
	/** 传入参数*/
	private String reqParameter;
	
	private String plateNum;
	
	/** 看板代码*/
	private String kbCode;
	/** 配送工程*/
	private String distriPerson;
	/** 当前操作人*/
	private String opeUser;
	/** combIp*/
	private String combIp;
	/** 工作时间*/
	private String workTime;
	/** 大物时间*/
	private String bigWorkTime;
	/** 小物时间*/
	private String smallWorkTime;
	/** 到货间隔时间*/
	private String restWorkTime;
	/** 卸货时间*/
	private String downWorkTime;
	
	
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public String getParamVal() {
		return paramVal;
	}
	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getPlanSheetNo() {
		return planSheetNo;
	}
	public void setPlanSheetNo(String planSheetNo) {
		this.planSheetNo = planSheetNo;
	}
	public String getRetEmptyPlatform() {
		return retEmptyPlatform;
	}
	public void setRetEmptyPlatform(String retEmptyPlatform) {
		this.retEmptyPlatform = retEmptyPlatform;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public java.util.Date getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(java.util.Date planStartTime) {
		this.planStartTime = planStartTime;
	}
	public String getPlanStartTimeStr() {
		return planStartTimeStr;
	}
	public void setPlanStartTimeStr(String planStartTimeStr) {
		this.planStartTimeStr = planStartTimeStr;
	}
	public String getPlanArriveTimeStr() {
		return planArriveTimeStr;
	}
	public void setPlanArriveTimeStr(String planArriveTimeStr) {
		this.planArriveTimeStr = planArriveTimeStr;
	}
	public String getWorkTimeStr() {
		return workTimeStr;
	}
	public void setWorkTimeStr(String workTimeStr) {
		this.workTimeStr = workTimeStr;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getPalletIron() {
		return palletIron;
	}
	public void setPalletIron(String palletIron) {
		this.palletIron = palletIron;
	}
	public String getPalletBox() {
		return palletBox;
	}
	public void setPalletBox(String palletBox) {
		this.palletBox = palletBox;
	}
	public String getBoxType() {
		return boxType;
	}
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getReqParameter() {
		return reqParameter;
	}
	public void setReqParameter(String reqParameter) {
		this.reqParameter = reqParameter;
	}
	public String getKbCode() {
		return kbCode;
	}
	public void setKbCode(String kbCode) {
		this.kbCode = kbCode;
	}
	public String getDistriPerson() {
		return distriPerson;
	}
	public void setDistriPerson(String distriPerson) {
		this.distriPerson = distriPerson;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getKbType() {
		return kbType;
	}
	public void setKbType(String kbType) {
		this.kbType = kbType;
	}
	public String getOpeUser() {
		return opeUser;
	}
	public void setOpeUser(String opeUser) {
		this.opeUser = opeUser;
	}
	public String getCombIp() {
		return combIp;
	}
	public void setCombIp(String combIp) {
		this.combIp = combIp;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getBigWorkTime() {
		return bigWorkTime;
	}
	public void setBigWorkTime(String bigWorkTime) {
		this.bigWorkTime = bigWorkTime;
	}
	public String getSmallWorkTime() {
		return smallWorkTime;
	}
	public void setSmallWorkTime(String smallWorkTime) {
		this.smallWorkTime = smallWorkTime;
	}
	public String getRestWorkTime() {
		return restWorkTime;
	}
	public void setRestWorkTime(String restWorkTime) {
		this.restWorkTime = restWorkTime;
	}
	public String getDownWorkTime() {
		return downWorkTime;
	}
	public void setDownWorkTime(String downWorkTime) {
		this.downWorkTime = downWorkTime;
	}
	public String getActualArriveTimeStr() {
		return actualArriveTimeStr;
	}
	public void setActualArriveTimeStr(String actualArriveTimeStr) {
		this.actualArriveTimeStr = actualArriveTimeStr;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public java.util.Date getPlanArriveTime() {
		return planArriveTime;
	}
	public void setPlanArriveTime(java.util.Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}
	public String getPlateNum() {
		return plateNum;
	}
	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	
	
}
