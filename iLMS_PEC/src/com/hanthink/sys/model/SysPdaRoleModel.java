package com.hanthink.sys.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * @Desc		: PDA用户角色管理
 * @FileName	: SysPdaRoleModel.java
 * @CreateOn	: 2019年1月22日 上午11:52:28
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2019年1月22日		V1.0		ZUOSL		新建
 */
public class SysPdaRoleModel extends AbstractModel<String>{

	private static final long serialVersionUID = 7272782024573648332L;
	
	/** PDA角色ID */
	private String pdaRoleId;
	/** 角色名称 */
	private String roleName;
	/** 角色备注 */
	private String roleRemark;
	/** 用户ID */
	private String userId;
	/** 菜单ID */
	private String menuId;
	/** 菜单名称描述 */
	private String menuNameDesc;
	/** 当前登录用户ID */
	private String curLoginUserId;

	@Override
	public void setId(String id) {
		this.pdaRoleId = id;
	}

	@Override
	public String getId() {
		return this.pdaRoleId;
	}

	/**
	 * @return the pdaRoleId PDA角色ID
	 */
	public String getPdaRoleId() {
		return pdaRoleId;
	}

	/**
	 * the pdaRoleId PDA角色ID to set
	 * @param pdaRoleId 
	 */
	public void setPdaRoleId(String pdaRoleId) {
		this.pdaRoleId = pdaRoleId;
	}

	/**
	 * @return the roleName 角色名称
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * the roleName 角色名称 to set
	 * @param roleName 
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleMark 角色备注
	 */
	public String getRoleRemark() {
		return roleRemark;
	}

	/**
	 * the roleMark 角色备注 to set
	 * @param roleMark 
	 */
	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}

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
	 * @return the menuId 菜单ID
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * the menuId 菜单ID to set
	 * @param menuId 
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the menuNameDesc 菜单名称描述
	 */
	public String getMenuNameDesc() {
		return menuNameDesc;
	}

	/**
	 * the menuNameDesc 菜单名称描述 to set
	 * @param menuNameDesc 
	 */
	public void setMenuNameDesc(String menuNameDesc) {
		this.menuNameDesc = menuNameDesc;
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
	
	
}
