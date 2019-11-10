package com.hanthink.sys.model;
import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：数据权限角色管理表 实体对象
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class SysDpRoleManageModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = 146710511966733061L;

	/** 数据权限基础数据ID */
	private String dpBaseId;
	
	/**
	* 数据角色ID
	*/
	private String dataRoleId; 
	
	/**
	* 数据角色名称
	*/
	private String dataRoleName; 
	
	/**
	 * 角色分类编码
	 */
	private String dataRoleTypeCode; 
	
	/**
	 * 角色分类名称
	 */
	private String dataRoleTypeName; 
	
	/** 顺序号 */
	private String sortNum;
	
	/** 角色备注信息 */
	private String remark;
	
	/**
	 * 权限分类代码
	 */
	private String typeCode;
	/**
	 * 权限分类名称
	 */
	private String typeName;
	/**
	 * 权限值
	 */
	private String valueCode;
	
	/**
	 * 权限值描述
	 */
	private String valueDesc;
	
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
	

	/**
	 * @return the dpBaseId 数据权限基础数据ID
	 */
	public String getDpBaseId() {
		return dpBaseId;
	}

	/**
	 * the dpBaseId 数据权限基础数据ID to set
	 * @param dpBaseId 
	 */
	public void setDpBaseId(String dpBaseId) {
		this.dpBaseId = dpBaseId;
	}

	/**
	 * @return the dataRoleId 数据角色ID
	 */
	public String getDataRoleId() {
		return dataRoleId;
	}

	/**
	 * the dataRoleId 数据角色ID to set
	 * @param dataRoleId 
	 */
	public void setDataRoleId(String dataRoleId) {
		this.dataRoleId = dataRoleId;
	}

	/**
	 * @return the dataRoleName 数据角色名称
	 */
	public String getDataRoleName() {
		return dataRoleName;
	}

	/**
	 * the dataRoleName 数据角色名称 to set
	 * @param dataRoleName 
	 */
	public void setDataRoleName(String dataRoleName) {
		this.dataRoleName = dataRoleName;
	}

	/**
	 * @return the dataRoleTypeCode 角色分类编码
	 */
	public String getDataRoleTypeCode() {
		return dataRoleTypeCode;
	}

	/**
	 * the dataRoleTypeCode 角色分类编码 to set
	 * @param dataRoleTypeCode 
	 */
	public void setDataRoleTypeCode(String dataRoleTypeCode) {
		this.dataRoleTypeCode = dataRoleTypeCode;
	}

	/**
	 * @return the dataRoleTypeName 角色分类名称
	 */
	public String getDataRoleTypeName() {
		return dataRoleTypeName;
	}

	/**
	 * the dataRoleTypeName 角色分类名称 to set
	 * @param dataRoleTypeName 
	 */
	public void setDataRoleTypeName(String dataRoleTypeName) {
		this.dataRoleTypeName = dataRoleTypeName;
	}

	/**
	 * @return the typeCode 权限分类代码
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * the typeCode 权限分类代码 to set
	 * @param typeCode 
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * @return the typeName 权限分类名称
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * the typeName 权限分类名称 to set
	 * @param typeName 
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the valueCode 权限值
	 */
	public String getValueCode() {
		return valueCode;
	}

	/**
	 * the valueCode 权限值 to set
	 * @param valueCode 
	 */
	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	/**
	 * @return the valueDesc 权限值描述
	 */
	public String getValueDesc() {
		return valueDesc;
	}

	/**
	 * the valueDesc 权限值描述 to set
	 * @param valueDesc 
	 */
	public void setValueDesc(String valueDesc) {
		this.valueDesc = valueDesc;
	}

	/**
	 * @return the createUser 创建人
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * the createUser 创建人 to set
	 * @param createUser 
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the createTime 创建时间
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}

	/**
	 * the createTime 创建时间 to set
	 * @param createTime 
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastModifyUser 最后修改人
	 */
	public String getLastModifyUser() {
		return lastModifyUser;
	}

	/**
	 * the lastModifyUser 最后修改人 to set
	 * @param lastModifyUser 
	 */
	public void setLastModifyUser(String lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}

	/**
	 * @return the lastModifyTime 最后修改时间
	 */
	public java.util.Date getLastModifyTime() {
		return lastModifyTime;
	}

	/**
	 * the lastModifyTime 最后修改时间 to set
	 * @param lastModifyTime 
	 */
	public void setLastModifyTime(java.util.Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	
	
	/**
	 * @return the sortNum 顺序号
	 */
	public String getSortNum() {
		return sortNum;
	}

	/**
	 * the sortNum 顺序号 to set
	 * @param sortNum 
	 */
	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}

	/**
	 * @return the remark 角色备注信息
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * the remark 角色备注信息 to set
	 * @param remark 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public void setId(String id) {
		this.dataRoleId = id;
	}

	@Override
	public String getId() {
		return dataRoleId;
	} 

}