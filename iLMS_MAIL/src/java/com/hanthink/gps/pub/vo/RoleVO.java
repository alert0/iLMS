package com.hanthink.gps.pub.vo;


import com.hanthink.gps.pub.vo.BaseVO;

/**
 * 角色管理
 */
public class RoleVO extends BaseVO {

	/***/
	private static final long serialVersionUID = -6637724812617800002L;
	
    // 角色ID
    private String roleId;    
    // 角色名
    private String roleName;    
    // 角色说明
    private String roleDesc;
    // 角色状态
    private String roleStatus;    
	// 部门ID
    private String departmentId;
    // 部门名字
    private String departmentName;
    
    //使用用户的数量
    private String roleCount;
    private String roleType;
    
    private String createUser;
    
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
      * 获取角色名
      */
     public String getRoleName(){
        return roleName; 
    }
    /**
      * 设定角色名
      * @param roleName 角色名
      */
     public void setRoleName(String roleName){
          this.roleName = roleName;
     }    
    /**
      * 获取角色说明
      */
     public String getRoleDesc(){
        return roleDesc; 
    }
    /**
      * 设定角色说明
      * @param roleDesc 角色说明
      */
     public void setRoleDesc(String roleDesc){
          this.roleDesc = roleDesc;
     }    
    /**
      * 获取部门ID
      */
     public String getDepartmentId(){
        return departmentId; 
    }
    /**
      * 设定部门ID
      * @param departmentId 部门ID
      */
     public void setDepartmentId(String departmentId){
          this.departmentId = departmentId;
     }
     /**
      * 获取部门名称
      */
     public String getDepartmentName(){
        return departmentName; 
    }
    /**
      * 设定部门名称
      * @param departmentName 部门名称
      */
     public void setDepartmentName(String departmentName){
          this.departmentName = departmentName;
     }
     /**
      * 返回角色状态
      * @return 角色状态
      */
     public String getRoleStatus() {
 		return roleStatus;
 	}
     /**
      * 设定角色状态
      * @return 角色状态
      */
 	public void setRoleStatus(String roleStatus) {
 		this.roleStatus = roleStatus;
 	}
	public String getRoleCount() {
		return roleCount;
	}
	public void setRoleCount(String roleCount) {
		this.roleCount = roleCount;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	
 	
}
