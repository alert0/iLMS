package com.hanthink.inv.model;

import com.hotent.base.core.model.AbstractModel;
/** 
 * <pre> 
 * 功能描述:仓库管理数据实体类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月8日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public class InvWareHouseModel extends AbstractModel<String>{

	private static final long serialVersionUID = -6942320962394569826L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/** 工厂代码 */
	private String factoryCode;
	/** 仓库代码 */
	private String wareCode;
	/** ERP仓库代码 */
	private String erpWareCode;
	/** 仓库地址 */
	private String wareAddr;
	/** 仓库名称 */
	private String wareName;
	/** 仓库类型 */
	private String wareType;
	/** 创建人 */
	private String creationUser;
	/** 创建时间 */
	private String creationTime;
	/** 最后修改人 */
	private String lastModifiedUser;
	/** 最后修改时间 */
	private String lastModifiedTime;
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getWareCode() {
		return wareCode;
	}
	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}
	public String getErpWareCode() {
		return erpWareCode;
	}
	public void setErpWareCode(String erpWareCode) {
		this.erpWareCode = erpWareCode;
	}
	public String getWareAddr() {
		return wareAddr;
	}
	public void setWareAddr(String wareAddr) {
		this.wareAddr = wareAddr;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	public String getWareType() {
		return wareType;
	}
	public void setWareType(String wareType) {
		this.wareType = wareType;
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
	@Override
	public String toString() {
		return "InvWareHouseModel [id=" + id + ", factoryCode=" + factoryCode + ", wareCode=" + wareCode
				+ ", erpWareCode=" + erpWareCode + ", wareAddr=" + wareAddr + ", wareName=" + wareName + ", wareType="
				+ wareType + ", creationUser=" + creationUser + ", creationTime=" + creationTime + ", lastModifiedUser="
				+ lastModifiedUser + ", lastModifiedTime=" + lastModifiedTime + "]";
	}
}
