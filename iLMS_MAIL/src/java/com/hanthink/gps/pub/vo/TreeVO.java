package com.hanthink.gps.pub.vo;

import java.util.List;

/**
 * 操作权限树节点
 * @author qzhang
 */
public class TreeVO {
	// 操作权限id
	private String operId;
	// 角色id
	private String roleId;
	// 权限id
	private String privilegeId;
	// 操作权限(权限)名
	private String text;
	// 是否是叶子节点
	private boolean leaf;
	// 是否是已保存数据
	private boolean saved;
	// 子节点
	private List<TreeVO> children;
	// 是否选中
	private boolean checked;
	// 是否展开
	private boolean expanded;
	// 父节点选中状态
	private String checkStatus;
	
	private String roleType;
	
	private String opeDesc;
	
	private String  level;
	
	private  String parentId;
	
	private String id;
	
	
	
	
    
   

	
	
	
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getOpeDesc() {
		return opeDesc;
	}
	public void setOpeDesc(String opeDesc) {
		this.opeDesc = opeDesc;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public boolean isSaved() {
		return saved;
	}
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	public List<TreeVO> getChildren() {
		return children;
	}
	public void setChildren(List<TreeVO> children) {
		this.children = children;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public void setChecked(String checked) {
		this.checked = "0".equals(checked)?false:true;
	}
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * 获取 expanded
	 *
	 * @return expanded
	 */
	public boolean isExpanded() {
		return expanded;
	}
	/**
	 * 设定expanded
	 *
	 * @param expanded expanded
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	/**
	 * 获取 checkStatus
	 *
	 * @return checkStatus
	 */
	public String getCheckStatus() {
		return checkStatus;
	}
	/**
	 * 设定checkStatus
	 *
	 * @param checkStatus checkStatus
	 */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
}
