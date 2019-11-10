package com.hanthink.gps.pub.vo;

import com.hanthink.gps.pub.vo.BaseVO;

/**
 * 角色权限表对象
 * <br> 角色权限表
 * @author jxia
 *
 */
public class RolePrivilegeVO extends BaseVO {
	/***/
	private static final long serialVersionUID = 1L;
	// 角色ID
    private String roleId;    
    // 权限ID
    private String privilegeId;    
    // 操作ID
    private String operId;  
   
    private String roleType;
    // 权限名
    private String privilegeName;
    //操作描述
    private String opeName;
    //权限类型
    private String privilegeType;
    //-----------权限树
    //权限代码
    private String privilegeCode;
    //菜单名
    private String menuName;
    //父级代码
    private String parentCode;
    //根
    private String root;
    // 角色是否具有该操作权限
    private String checked;
    //
    private String level;
    
    
    
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPrivilegeCode() {
		return privilegeCode;
	}
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public String getPrivilegeType() {
		return privilegeType;
	}
	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}
	/**
      * 获取角色ID
      */
     public String getRoleId(){
        return roleId; 
    }
    /**
      * 设定角色ID
      * @param roleId 角色ID
      */
     public void setRoleId(String roleId){
          this.roleId = roleId;
     }    
    /**
      * 获取权限ID
      */
     public String getPrivilegeId(){
        return privilegeId; 
    }
    /**
      * 设定权限ID
      * @param privilegeId 权限ID
      */
     public void setPrivilegeId(String privilegeId){
          this.privilegeId = privilegeId;
     }    
    /**
      * 获取操作ID
      */
     public String getOperId(){
        return operId; 
    }
    /**
      * 设定操作ID
      * @param operId 操作ID
      */
     public void setOperId(String operId){
          this.operId = operId;
     }
    /**
     *  选中状态
     * @return 选中状态
     */
    public String getChecked() {
 		return checked;
 	}
    /**
     * 选中状态
     * @param checked 选中状态
     */
 	public void setChecked(String checked) {
 		this.checked = checked;
 	}
 	/**
 	 * 权限名
 	 * @return
 	 */
    public String getPrivilegeName() {
		return privilegeName;
	}
    /**
     * 权限名
     * @param privilegeName
     */
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getOpeName() {
		return opeName;
	}
	public void setOpeName(String opeName) {
		this.opeName = opeName;
	}
	
	
}
