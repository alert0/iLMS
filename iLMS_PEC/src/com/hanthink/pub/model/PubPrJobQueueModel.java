package com.hanthink.pub.model;
import com.hotent.base.core.model.AbstractModel;

 /**
 * 
 * <pre> 
 * 描述：打印任务队列 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class PubPrJobQueueModel extends AbstractModel<Integer>{

	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年11月22日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = -6985221186973024032L;

	/**
	 * 主表  打印任务队列
	 */
	/**
	* 打印任务序号
	*/
	private Integer jobNo ; 
	
	/**
	* 业务名称
	*/
	private String business; 
	
	/**
	* 任务名称
	*/
	private String jobName; 
	
	/**
	* 打印类型
	*/
	private String printType; 
	
	/**
	* 任务类型
	*/
	private String jobType; 
	
	/**
	* 打印方式
	*/
	private String classes; 
	
	/**
	* 打印状态
	*/
	private String status; 
	
	/**
	* 序列号
	*/
	private String serialNumber; 
	
	/**
	* 关键值
	*/
	private Integer atrowKey; 
	
	/**
	* 参数
	*/
	private String parameters; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime;  
	
	/**
	* 创建时间字符
	*/
	private String creationTimeStr;  
	
	/**
	* 创建时间（开始）
	*/
	private String creationTimeStrStart;  
	
	/**
	* 创建时间（结束）
	*/
	private String creationTimeStrEnd;  
	
	/**
	* 最后修改人
	*/
	private String lastModifiedUser;  
	
	/**
	* 最后修改IP
	*/
	private String lastModifiedIp;
	
	/**
	* 工厂
	*/
	private String factoryCode; 
	
	/**
	 * 数据字典表
	 */
	/**
	 * 数据值A
	 */
	private String codeValueNameA;
	
	/**
	 * 数据值B
	 */
	private String codeValueNameB;
	
	public Integer getJobNo() {
		return jobNo;
	}

	public void setJobNo(Integer jobNo) {
		this.jobNo = jobNo;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getAtrowKey() {
		return atrowKey;
	}

	public void setAtrowKey(Integer atrowKey) {
		this.atrowKey = atrowKey;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getCreationTimeStr() {
		return creationTimeStr;
	}

	public void setCreationTimeStr(String creationTimeStr) {
		this.creationTimeStr = creationTimeStr;
	}

	public String getCreationTimeStrStart() {
		return creationTimeStrStart;
	}

	public void setCreationTimeStrStart(String creationTimeStrStart) {
		this.creationTimeStrStart = creationTimeStrStart;
	}

	public String getCreationTimeStrEnd() {
		return creationTimeStrEnd;
	}

	public void setCreationTimeStrEnd(String creationTimeStrEnd) {
		this.creationTimeStrEnd = creationTimeStrEnd;
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

	public String getCodeValueNameA() {
		return codeValueNameA;
	}

	public void setCodeValueNameA(String codeValueNameA) {
		this.codeValueNameA = codeValueNameA;
	}

	public String getCodeValueNameB() {
		return codeValueNameB;
	}

	public void setCodeValueNameB(String codeValueNameB) {
		this.codeValueNameB = codeValueNameB;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

}