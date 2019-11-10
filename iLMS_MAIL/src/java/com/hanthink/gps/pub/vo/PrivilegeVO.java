package com.hanthink.gps.pub.vo;

import com.hanthink.gps.pub.vo.BaseVO;

/**
 * 权限表对象
 * <br> 权限表
 * @author jxia
 *
 */
public class PrivilegeVO extends BaseVO{
    /***/
	private static final long serialVersionUID = -5920192080421569383L;
	
	// 权限代码
    private String privilegeCode;  
    
    //父模块代码
    private String parentCode;
    
    //权限类型
    private String privilegeType;
    
    //权限说明
    private String privilegeDesc;
    
    //菜单名称
    private String menuName;
    
    //菜单路径
    private String menuUrl;
    
    //排序
    private String sort;

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getPrivilegeType() {
		return privilegeType;
	}

	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}

	public String getPrivilegeDesc() {
		return privilegeDesc;
	}

	public void setPrivilegeDesc(String privilegeDesc) {
		this.privilegeDesc = privilegeDesc;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String munuUrl) {
		this.menuUrl = munuUrl;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
    
    
    
    
}
