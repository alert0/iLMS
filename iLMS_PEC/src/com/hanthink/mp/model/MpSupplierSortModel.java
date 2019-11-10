package com.hanthink.mp.model;

import java.util.Date;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：供应商分组  实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class MpSupplierSortModel extends AbstractModel<String>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7648712348869144349L;
	/**
	 * 主表  供应商分组历史表
	 * @return
	 */
	/**
	* 供应商代码
	*/
	protected String supplierNo; 
	/**
	* 出货地
	*/
	protected String supFactory; 
	
	/**
	* 工厂
	*/
	protected String factoryCode; 
	
	/**
	* 计算队列
	*/
	protected String unloadPort; 
	
	/**
	* dockrange范围
	*/
	protected Integer drSortIdStart; 
	
	/**
	* dockrange范围
	*/
	protected Integer drSortIdEnd; 
	
	/**
	* 分组号
	*/
	private Integer groupId;
	
	/**
	* 计算状态
	*/
	private Integer calStatus;
	
	/**
	* 首台车下线时间
	*/
	private java.util.Date finalUnderlineTimeStart;
	
	/**
	* 首台车下线时间
	*/
	private String finalUnderlineTimeStartStr;
	
	/**
	* 首台车下线时间开始
	*/
	private String finalUnderlineTimeStartStrStart;
	
	/**
	* 首台车下线时间结束
	*/
	private String finalUnderlineTimeStartStrEnd;
	
	/**
	* 末台车下线时间
	*/
	private java.util.Date finalUnderlineTimeEnd;
	
	/**
	* 末台车下线时间
	*/
	private String finalUnderlineTimeEndStr;
	
	/**
	* 创建时间
	*/
	protected Integer creationTime;

	/**
	 * 副表  数据字典
	 * @return
	 */
	/**
	* 编码值名称
	*/
	protected String codeValueName;
	
	/**
	 * 副表  供应商表
	 * @return
	 */
	/**
	* 供应商名称
	*/
	protected String supplierName;
	
	
	/**
	 * 主表  供应商分组历史表
	 * @return
	 */
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

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getUnloadPort() {
		return unloadPort;
	}

	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
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

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getCalStatus() {
		return calStatus;
	}

	public void setCalStatus(Integer calStatus) {
		this.calStatus = calStatus;
	}

	public java.util.Date getFinalUnderlineTimeStart() {
		return finalUnderlineTimeStart;
	}

	public void setFinalUnderlineTimeStart(java.util.Date finalUnderlineTimeStart) {
		this.finalUnderlineTimeStart = finalUnderlineTimeStart;
	}

	public String getFinalUnderlineTimeStartStrStart() {
		return finalUnderlineTimeStartStrStart;
	}

	public void setFinalUnderlineTimeStartStrStart(String finalUnderlineTimeStartStrStart) {
		this.finalUnderlineTimeStartStrStart = finalUnderlineTimeStartStrStart;
	}

	public String getFinalUnderlineTimeStartStrEnd() {
		return finalUnderlineTimeStartStrEnd;
	}

	public void setFinalUnderlineTimeStartStrEnd(String finalUnderlineTimeStartStrEnd) {
		this.finalUnderlineTimeStartStrEnd = finalUnderlineTimeStartStrEnd;
	}

	public java.util.Date getFinalUnderlineTimeEnd() {
		return finalUnderlineTimeEnd;
	}

	public void setFinalUnderlineTimeEnd(java.util.Date finalUnderlineTimeEnd) {
		this.finalUnderlineTimeEnd = finalUnderlineTimeEnd;
	}

	public Integer getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Integer creationTime) {
		this.creationTime = creationTime;
	}

	
	
	public String getFinalUnderlineTimeStartStr() {
		return finalUnderlineTimeStartStr;
	}

	public void setFinalUnderlineTimeStartStr(String finalUnderlineTimeStartStr) {
		this.finalUnderlineTimeStartStr = finalUnderlineTimeStartStr;
	}

	public String getFinalUnderlineTimeEndStr() {
		return finalUnderlineTimeEndStr;
	}

	public void setFinalUnderlineTimeEndStr(String finalUnderlineTimeEndStr) {
		this.finalUnderlineTimeEndStr = finalUnderlineTimeEndStr;
	}

	/**
	 * 副表 数据字典
	 */
	public String getCodeValueName() {
		return codeValueName;
	}

	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
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