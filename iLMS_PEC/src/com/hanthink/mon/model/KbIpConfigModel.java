package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：看板IP配置 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class KbIpConfigModel extends AbstractModel<String>{

	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2019年2月18日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 2905298001733841685L;

	/**
     * 主表 看板IP配置
     */
	private String id;	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/** 工厂 */
	private String factoryCode; 
	/** 看板类型 */
	private String kbType;
	private String kbTypeName;
	private String kbId;
	private String runProcessNo;
	private String limitDelay;
	/**  看板IP */
	private String kbIp;
	/** 界面URL */
	private String pageURL;
	/** 看板名称 */
	private String kbName; 
	/** 查询参数字段 */
	private String reqParam;
	/** 创建人 */
	private String creationUser; 
	/** 创建时间 */
	private String creationTime; 
	/** 最后修改时间 */
	private String lastModifiedTime; 
	/** 最后修改人 */
	private String lastModifiedUser; 
	/**  最后修改IP */
	private String lastModifiedIp; 
	/** 主表ID */
	private String bussId;
	/** 工程名 */
	private String distriPerson;
	/** 站台 */
	private String station;
	/** 看板代码 */
	private String kbCode;
	/** 偏移进度 */
	private String shiftSchedule;	
	/** 是否可编辑 */
	private String isEdit;
	/** 大屏电视机网口 */
	private String combIp;
	private String paramName;
	private String paramValue;
	/** 车间 */
	private String workCenter;
	/** 绑定地址编号 */
	private String bindingAddr;
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getKbType() {
		return kbType;
	}
	public void setKbType(String kbType) {
		this.kbType = kbType;
	}
	public String getKbTypeName() {
		return kbTypeName;
	}
	public void setKbTypeName(String kbTypeName) {
		this.kbTypeName = kbTypeName;
	}
	public String getKbIp() {
		return kbIp;
	}
	public void setKbIp(String kbIp) {
		this.kbIp = kbIp;
	}
	public String getPageURL() {
		return pageURL;
	}
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}
	public String getKbName() {
		return kbName;
	}
	public void setKbName(String kbName) {
		this.kbName = kbName;
	}
	public String getReqParam() {
		return reqParam;
	}
	public void setReqParam(String reqParam) {
		this.reqParam = reqParam;
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
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	public String getLastModifiedIp() {
		return lastModifiedIp;
	}
	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}
	public String getBussId() {
		return bussId;
	}
	public void setBussId(String bussId) {
		this.bussId = bussId;
	}
	public String getDistriPerson() {
		return distriPerson;
	}
	public void setDistriPerson(String distriPerson) {
		this.distriPerson = distriPerson;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getKbCode() {
		return kbCode;
	}
	public void setKbCode(String kbCode) {
		this.kbCode = kbCode;
	}
	public String getShiftSchedule() {
		return shiftSchedule;
	}
	public void setShiftSchedule(String shiftSchedule) {
		this.shiftSchedule = shiftSchedule;
	}
	public String getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
	public String getKbId() {
		return kbId;
	}
	public void setKbId(String kbId) {
		this.kbId = kbId;
	}
	public String getRunProcessNo() {
		return runProcessNo;
	}
	public void setRunProcessNo(String runProcessNo) {
		this.runProcessNo = runProcessNo;
	}
	public String getLimitDelay() {
		return limitDelay;
	}
	public void setLimitDelay(String limitDelay) {
		this.limitDelay = limitDelay;
	}
	public String getCombIp() {
		return combIp;
	}
	public void setCombIp(String combIp) {
		this.combIp = combIp;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public String getBindingAddr() {
		return bindingAddr;
	}
	public void setBindingAddr(String bindingAddr) {
		this.bindingAddr = bindingAddr;
	}
}