package com.hanthink.mp.model;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：新车型计划维护 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpTrialPlanModel extends AbstractModel<String>{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4166727366976171609L;
    /**
     * 主表 新车型计划维护
     */
	/**
	* 生产排序号
	*/
	private String sortId; 
	
	/**
	 * 生产排序号(开始）
	 */
	private String sortIdStrStart;
	
	/**
	 * 生产排序号(结束）
	 */
	private String sortIdStrEnd;
	
	/**
	* 工厂
	*/
	private String factoryCode; 
	
	/**
	* 车型
	*/
	private String carType; 
	
	/**
	* 订单号
	*/
	private String orderNo; 
	
	/**
	* 总装下线日期
	*/
	private java.util.Date afoffDate; 
	
	/**
	* 总装下线日期
	*/
	private String afoffDateStr; 
	
	/**
	* 总装下线日期（开始）
	*/
	private String afoffDateStrStart; 
	
	/**
	* 总装下线日期（结束）
	*/
	private String afoffDateStrEnd; 
	
	/**
	* 生产阶段
	*/
	private String proPhase; 
	
	/**
	* WE上线时间
	*/
	private java.util.Date weonTime; 
	
	/**
	* WE上线时间(字符)
	*/
	private String weonTimeStr; 
	
	/**
	* 创建人
	*/
	private String creationUser; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 最后修改人
	*/
	private String  lastModifiedUser; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime; 
	
	/**
	* 计算状态
	*/
	private Integer calStatus;

	/**
	 * 副表  数据字典表
	 */
	/**
	 * 数据值
	 */
	private String codeValueNameB;
	/**
	 * 数据值
	 */
	private String codeValueNameC;
	/**
	 * 数据值
	 */
	private String codeValueNameD;
	/**
	 * 数据值
	 */
	private String codeValueName;
	/**
	 * 数据值
	 */
	private String codeValueNameP;
	
	/**
	 * 主表  新车型计划维护
	 * @return
	 */
	public java.util.Date getWeonTime() {
		return weonTime;
	}

	public void setWeonTime(java.util.Date weonTime) {
		this.weonTime = weonTime;
	}

	public String getWeonTimeStr() {
		return weonTimeStr;
	}

	public void setWeonTimeStr(String weonTimeStr) {
		this.weonTimeStr = weonTimeStr;
	}
	
	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public java.util.Date getAfoffDate() {
		return afoffDate;
	}

	public void setAfoffDate(java.util.Date afoffDate) {
		this.afoffDate = afoffDate;
	}

	public String getAfoffDateStr() {
		return afoffDateStr;
	}

	public void setAfoffDateStr(String afoffDateStr) {
		this.afoffDateStr = afoffDateStr;
	}

	public String getAfoffDateStrStart() {
		return afoffDateStrStart;
	}

	public void setAfoffDateStrStart(String afoffDateStrStart) {
		this.afoffDateStrStart = afoffDateStrStart;
	}

	public String getAfoffDateStrEnd() {
		return afoffDateStrEnd;
	}

	public void setAfoffDateStrEnd(String afoffDateStrEnd) {
		this.afoffDateStrEnd = afoffDateStrEnd;
	}

	public String getProPhase() {
		return proPhase;
	}

	public void setProPhase(String proPhase) {
		this.proPhase = proPhase;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Integer getCalStatus() {
		return calStatus;
	}

	public void setCalStatus(Integer calStatus) {
		this.calStatus = calStatus;
	}

	/**
	 * 数据字典表
	 * @return
	 */
	public String getCodeValueNameB() {
		return codeValueNameB;
	}
	public void setCodeValueNameB(String codeValueNameB) {
		this.codeValueNameB = codeValueNameB;
	}
	public String getCodeValueNameC() {
		return codeValueNameC;
	}
	public void setCodeValueNameC(String codeValueNameC) {
		this.codeValueNameC = codeValueNameC;
	}
	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}

	public String getCodeValueNameP() {
		return codeValueNameP;
	}

	public void setCodeValueNameP(String codeValueNameP) {
		this.codeValueNameP = codeValueNameP;
	}

	public String getCodeValueNameD() {
		return codeValueNameD;
	}

	public void setCodeValueNameD(String codeValueNameD) {
		this.codeValueNameD = codeValueNameD;
	}

	public String getSortIdStrStart() {
		return sortIdStrStart;
	}

	public void setSortIdStrStart(String sortIdStrStart) {
		this.sortIdStrStart = sortIdStrStart;
	}

	public String getSortIdStrEnd() {
		return sortIdStrEnd;
	}

	public void setSortIdStrEnd(String sortIdStrEnd) {
		this.sortIdStrEnd = sortIdStrEnd;
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