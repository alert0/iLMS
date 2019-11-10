package com.hanthink.dpm.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月14日 上午10:36:18
 * </pre>
 * @author luoxq
 */
public class DpmDepPersonModel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6806900589581227241L;

	/**逻辑主键**/
	private String id; 
	
	/**工厂**/
	private String factoryCode;
	
	/**人员id**/
	private String userId; 	
	
	/**用户名**/
	private String account;
	
	/**姓名**/
	private String fullname;
	
	/**部门**/
	private String name;
	
	/**责任组**/
	private String depCode;	
	
	/**责任组名称**/
	private String depName;
	
	/**部门审核人**/
	private String depChecker;
	
	/**所属科室**/
	private String belongDep;
	
	/**默认发现区域**/
	private String defaultDiscoArea;
	
	/**创建人**/
	private String creationUser;
	
	/**修改人**/
	private String lastModifiedUser;
	
	/**账号--名称（部门）**/
	private String accountName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBelongDep() {
		return belongDep;
	}

	public void setBelongDep(String belongDep) {
		this.belongDep = belongDep;
	}

	public String getDefaultDiscoArea() {
		return defaultDiscoArea;
	}

	public void setDefaultDiscoArea(String defaultDiscoArea) {
		this.defaultDiscoArea = defaultDiscoArea;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepChecker() {
		return depChecker;
	}

	public void setDepChecker(String depChecker) {
		this.depChecker = depChecker;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	


}
