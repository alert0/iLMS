package com.hanthink.pup.model;

/**
* 
* <pre>
* 描述：生产计划页面数据对象
* 构建组：x5-bpmx-platform
* 作者:zmj
* 日期:2018-09-13 10:49:09
* 版权：汉思信息技术有限公司
* </pre>
*/
public class PupProPlanPageModel {
	
	/** 混合车型排序 */
	private Integer mixSortId;
	
	/** 分车型排序 */
	private Integer singleSortId;
	
	/** 计划下线时间开始时间 */
	private String afoffTimeStart;
	
	/** 计划下线时间结束时间 */
	private String afoffTimeEnd;
	
	/** 订单编号 */
	private String orderNo;
	
	/** 车型 */
	private String carType;
	
	/** 周次 */
	private Integer week;
	
	/** 工厂*/
	private String factoryCode;
	
	/** 操作人*/
	private String opeId;

	public Integer getMixSortId() {
		return mixSortId;
	}
	public void setMixSortId(Integer mixSortId) {
		this.mixSortId = mixSortId;
	}
	public Integer getSingleSortId() {
		return singleSortId;
	}
	public void setSingleSortId(Integer singleSortId) {
		this.singleSortId = singleSortId;
	}
	public String getAfoffTimeStart() {
		return afoffTimeStart;
	}
	public void setAfoffTimeStart(String afoffTimeStart) {
		this.afoffTimeStart = afoffTimeStart;
	}
	public String getAfoffTimeEnd() {
		return afoffTimeEnd;
	}
	public void setAfoffTimeEnd(String afoffTimeEnd) {
		this.afoffTimeEnd = afoffTimeEnd;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	
	public String getFactoryCode() {
        return factoryCode;
    }
    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }
    
    public String getOpeId() {
        return opeId;
    }
    public void setOpeId(String opeId) {
        this.opeId = opeId;
    }
    @Override
	public String toString() {
		return "PupProPlanPageModel [mixSortId=" + mixSortId + ", singleSortId=" + singleSortId + ", afoffTimeStart="
				+ afoffTimeStart + ", afoffTimeEnd=" + afoffTimeEnd + ", orderNo=" + orderNo + ", carType=" + carType
				+ ", week=" + week + "]";
	}
}
