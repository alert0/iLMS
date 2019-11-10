package com.hanthink.pub.model;
import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：生产工作日历表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubTactTimeModel extends AbstractModel<Integer>{
	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年9月29日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 353155575527738231L;

	/**
	 * 主表  生产节拍表
	 */
	/**
	* 工厂
	*/
	private String factoryCode ; 
	
	/**
	* 车间
	*/
	private String workcenter; 
	
	/**
	* 生产线
	*/
	private String productionLine; 
	
	/**
	* 生效日期
	*/
	private java.util.Date effDate; 
	
	/**
	* 生效日期
	*/
	private String effDateStr; 
	
	/**
	* JPH
	*/
	private String jph; 
	
	/**
	* OEE(开动率)
	*/
	private String oee; 
	
	/**
	* 生产节拍
	*/
	private Integer produceTime; 
	
	/**
	* 产能体制
	*/
	private String proCapacity; 
	
	/**
	* 备注
	*/
	private String remark; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime; 
	
	/**
	 * 副表  生产日历明细表
	 */
	/**
	* 工作日历唯一标识
	*/
	private Integer idWorkCalendar; 
	
	/**
	* 休息开始时间
	*/
	private java.util.Date startTime; 
	
	/**
	* 休息开始时间
	*/
	private String startTimeStr; 
	
	/**
	* 休息结束时间
	*/
	private java.util.Date endTime; 
	
	/**
	* 休息结束时间
	*/
	private String endTimeStr; 
	
	/**
	* 时间段类型
	*/
	private String timeType;
	
	/**
	* 数据字典
	*/
	/**
	* 数据值
	*/
	private String codeValueName;
	/**
	* 数据值
	*/
	private String codeValueNameC;
	/**
	 * 主表 生产节拍表
	 */
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getProductionLine() {
		return productionLine;
	}

	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}

	public java.util.Date getEffDate() {
		return effDate;
	}

	public void setEffDate(java.util.Date effDate) {
		this.effDate = effDate;
	}

	public String getEffDateStr() {
		return effDateStr;
	}

	public void setEffDateStr(String effDateStr) {
		this.effDateStr = effDateStr;
	}

	public String getJph() {
		return jph;
	}

	public void setJph(String jph) {
		this.jph = jph;
	}

	public String getOee() {
		return oee;
	}

	public void setOee(String oee) {
		this.oee = oee;
	}

	public Integer getProduceTime() {
		return produceTime;
	}

	public void setProduceTime(Integer produceTime) {
		this.produceTime = produceTime;
	}

	public String getProCapacity() {
		return proCapacity;
	}

	public void setProCapacity(String proCapacity) {
		this.proCapacity = proCapacity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Integer getIdWorkCalendar() {
		return idWorkCalendar;
	}

	public void setIdWorkCalendar(Integer idWorkCalendar) {
		this.idWorkCalendar = idWorkCalendar;
	}

	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}
	
	public String getCodeValueNameC() {
		return codeValueNameC;
	}

	public void setCodeValueNameC(String codeValueNameC) {
		this.codeValueNameC = codeValueNameC;
	}

	@Override
	public void setId(Integer id) {
		
		
	}

	@Override
	public Integer getId() {
		
		return null;
	}

	
	
}