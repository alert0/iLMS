package com.hanthink.sys.model;
import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：数据权限基础数据表 实体对象
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class SysDpBaseDataModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = 146710511966733061L;
	
	private String dpBaseId;

	/**
	 * 类型编码
	 */
	private String typeKey;
	
	/**
	 * 类型名称
	 */
	private String name;
	
	/**
	* 权限分类编码
	*/
	private String typeCode; 
	
	/**
	* 权限值
	*/
	private String valueCode; 
	
	/**
	* 权限值描述
	*/
	private String valueDesc; 
	
	/** 排序号 */
	private String sortNum;
	
	/**
	* 创建人
	*/
	private String createUser; 
	
	/**
	* 创建时间
	*/
	private java.util.Date createTime; 
	
	/**
	* 最后修改人
	*/
	private String lastModifyUser; 
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifyTime;

	public String getDpBaseId() {
		return dpBaseId;
	}

	public void setDpBaseId(String dpBaseId) {
		this.dpBaseId = dpBaseId;
	}

	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getValueCode() {
		return valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	public String getValueDesc() {
		return valueDesc;
	}

	public void setValueDesc(String valueDesc) {
		this.valueDesc = valueDesc;
	}
	
	/**
	 * @return the sortNumSysDpBaseDataModel.java
	 */
	public String getSortNum() {
		return sortNum;
	}

	/**
	 * @param sortNum the sortNum to set
	 */
	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyUser() {
		return lastModifyUser;
	}

	public void setLastModifyUser(String lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}

	public java.util.Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(java.util.Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
	@Override
	public void setId(String id) {
		this.dpBaseId = id;
	}

	@Override
	public String getId() {
		return this.dpBaseId;
	} 

}