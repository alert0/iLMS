package com.hanthink.gps.pub.vo;

import java.util.List;

import com.hanthink.gps.pub.vo.BaseVO;

public class UserVO extends BaseVO{

	private static final long serialVersionUID = -205074496010322966L;
	
	//**********************用户系统属性************************
	/** 登录名 */
	private String userName;
	/** 用户密码 */
	private String userPwd;
	/** 姓名 */
	private String name;
	/** 身份类型 :0-供应商 1-GAMC 2-物流公司 */
	private String userType;
	/** 用户状态:1-正常 2-禁用 */
	private String userStatus;
	/** 登录ip */
	private String loginIp;
	/** 登录时间 */
	private String loginTime;
	/** 登录次数 */
	private String loginNum;
	/** 密码错误次数 */
	private String pwdWrongNum;
	/** 登录状态 */
	private String loginStatus;
	
	//*************************共用属性******************************
	/** 角色ID */
	private String roleId;
	/** 角色列表 */
	private List<RoleVO> roles;
	/** 电话 */
	private String tel;
	/** 手机 */
	private String mobile;
	/** 传真 */
	private String fax;
	/** 电子邮箱 */
	private String email;
	/** 角色一览 */
	private String roleList;
	
	//*************************GAMC用户属性******************************
	
	/** 当前登录工厂 */
	private String curLoginFactory;
	/** 部门ID */
	private String departmentId;
	/** 部门名 */
	private String departmentName;
	/** 工厂列表 */
	private List<FactoryVO> factories;
	/** 工厂信息一览 */
	private String factoryList;
	
	
	//*************************供应商用户属性******************************
	
	/** 供应商代码 */
	private String supplierNo;
	/**  父供应商代码 */
	private String parentNo;
	/** 供应商名称 */
	private String supplierName;
	/** 供应商激活状态(0:未激活;1:已激活;2:已注册;3:已禁用) */
	private String activeStatus;
	/** 公司名称 */
	private String companyName;
	/**供应商数组*/
	private String supplierGroup;
	/**操作人*/
	private String opeUser;
	//------------------------------------------------------------
	
	
	
	// 登录id
	private String loginId;
	// 地址
	private String addr;
	// 详细地址
	private String detailAddr;
	// 供应商状态
	private String status;
	// 用户Id
	private String userId;
	private String roleType;

	//工厂代码
	private String factory;
	
	
	private String userConfirmType;
	
	private String userCName;
	
    
	
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getUserCName() {
		return userCName;
	}
	public void setUserCName(String userCName) {
		this.userCName = userCName;
	}
	public String getUserConfirmType() {
		return userConfirmType;
	}
	public void setUserConfirmType(String userConfirmType) {
		this.userConfirmType = userConfirmType;
	}
	private String subRoleType;
	// 删除标志
	private String delFlg;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	public String getPwdWrongNum() {
		return pwdWrongNum;
	}
	public void setPwdWrongNum(String pwdWrongNum) {
		this.pwdWrongNum = pwdWrongNum;
	}
	public String getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(String loginNum) {
		this.loginNum = loginNum;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public List<RoleVO> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
	
	public String getOpeUser() {
		return opeUser;
	}
	public void setOpeUser(String opeUser) {
		this.opeUser = opeUser;
	}
	public String getSupplierGroup() {
		return supplierGroup;
	}
	public void setSupplierGroup(String supplierGroup) {
		this.supplierGroup = supplierGroup;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * 获取 loginId
	 *
	 * @return loginId
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * 设定loginId
	 *
	 * @param loginId loginId
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * 获取 addr
	 *
	 * @return addr
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * 设定addr
	 *
	 * @param addr addr
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * 获取 detailAddr
	 *
	 * @return detailAddr
	 */
	public String getDetailAddr() {
		return detailAddr;
	}
	/**
	 * 设定detailAddr
	 *
	 * @param detailAddr detailAddr
	 */
	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}
	/**
	 * 获取 status
	 *
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设定status
	 *
	 * @param status status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取 loginIp
	 *
	 * @return loginIp
	 */
	public String getLoginIp() {
		return loginIp;
	}
	/**
	 * 设定loginIp
	 *
	 * @param loginIp loginIp
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	/**
	 * 获取 loginTime
	 *
	 * @return loginTime
	 */
	public String getLoginTime() {
		return loginTime;
	}
	/**
	 * 设定loginTime
	 *
	 * @param loginTime loginTime
	 */
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	/**
	 * 获取 parentNo
	 *
	 * @return parentNo
	 */
	public String getParentNo() {
		return parentNo;
	}
	
	public String getRoleList() {
		return roleList;
	}
	public void setRoleList(String roleList) {
		this.roleList = roleList;
	}
	/**
	 * 设定parentNo
	 *
	 * @param parentNo parentNo
	 */
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	public String getSubRoleType() {
		return subRoleType;
	}
	public void setSubRoleType(String subRoleType) {
		this.subRoleType = subRoleType;
	}
	
	/**
	 * 获取当前登录工厂 
	 * @return the curLoginFactory 
	 */
	public String getCurLoginFactory() {
		return curLoginFactory;
	}
	/**
	 * 设置 当前登录工厂 
	 * @param curLoginFactory
	 */
	public void setCurLoginFactory(String curLoginFactory) {
		this.curLoginFactory = curLoginFactory;
	}
	/**
	 * 获取用户状态:1-正常2-禁用
	 * @return the userStatus 用户状态:1-正常2-禁用
	 */
	public String getUserStatus() {
		return userStatus;
	}
	/**
	 * 设置用户状态:1-正常2-禁用
	 * @param userStatus the userStatus 用户状态:1-正常2-禁用
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getFactoryList() {
		return factoryList;
	}
	public void setFactoryList(String factoryList) {
		this.factoryList = factoryList;
	}
	public List<FactoryVO> getFactories() {
		return factories;
	}
	public void setFactories(List<FactoryVO> factories) {
		this.factories = factories;
	}
	/**
	 * 获取 供应商代码
	 * @return the supplierNo 
	 */
	public String getSupplierNo() {
		return supplierNo;
	}
	/**
	 * 设置 供应商代码
	 * @param supplierNo the supplierNo 
	 */
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	/**
	 * 获取供应商激活状态供应商激活状态(0:未激活;1:已激
	 * @return the activeStatus 供应商激活状态供应商激活状态(0:未激活;1:已激
	 */
	public String getActiveStatus() {
		return activeStatus;
	}
	/**
	 * 设置供应商激活状态供应商激活状态(0:未激活;1:已激
	 * @param activeStatus the activeStatus 供应商激活状态供应商激活状态(0:未激活;1:已激
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	
	
	
}
