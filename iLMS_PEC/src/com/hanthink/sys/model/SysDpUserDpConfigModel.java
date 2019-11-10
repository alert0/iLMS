package com.hanthink.sys.model;
import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：用户数据权限 用户数据角色配置
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class SysDpUserDpConfigModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = 146710511966733061L;
	
	private String id;

	/**
	* 用户ID
	*/
	private String userId; 
	
	/**
	* 数据角色ID
	*/
	private String dataRoleId; 
	
	/** 数据角色名称 */
	private String dataRoleName;
	
	/** 数据角色分类名称 */
	private String dataRoleTypeName;
	
	/** 当前登录用户ID */
	private String curLoginUserId;
	
	
	/**
	 * 创建人
	 */
	private String createUser; 
	
	/**
	* 创建时间
	*/
	private java.util.Date createTime; 
	
	

	/**
	 * @return the userId 用户ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * the userId 用户ID to set
	 * @param userId 
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return the dataRoleTypeName 数据角色分类名称
	 */
	public String getDataRoleTypeName() {
		return dataRoleTypeName;
	}

	/**
	 * the dataRoleTypeName 数据角色分类名称 to set
	 * @param dataRoleTypeName 
	 */
	public void setDataRoleTypeName(String dataRoleTypeName) {
		this.dataRoleTypeName = dataRoleTypeName;
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
	 * @return the curLoginUserId 当前登录用户ID
	 */
	public String getCurLoginUserId() {
		return curLoginUserId;
	}

	/**
	 * the curLoginUserId 当前登录用户ID to set
	 * @param curLoginUserId 
	 */
	public void setCurLoginUserId(String curLoginUserId) {
		this.curLoginUserId = curLoginUserId;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	} 

	


}