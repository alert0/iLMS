package com.hanthink.dpm.model;


import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月12日 下午1:58:02
 * </pre>
 * @author luoxq
 */
public class DpmDepartmentModel extends AbstractModel<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4633217520865243906L;

	/**逻辑主键**/
	private String id;
	
	/**工厂代码**/
	private String factoryCode;
	
	/**责任组编码**/
	private String depCode;
	
	/**责任组名称**/
	private String depName;
	
	/**所属科室**/
	private String belongDep;
	
	/**部门审核人**/
	private String depChecker;
	
	/**用户姓名**/
	private String fullname;
	
	/**部门**/
	private String name;
	
	/**创建人**/
	private String creationUser;
	/**修改人**/
	private String lastModifiedUser;
	/**键**/
	private String value;
	/**值**/
	private String label;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getBelongDep() {
		return belongDep;
	}

	public void setBelongDep(String belongDep) {
		this.belongDep = belongDep;
	}

	public String getDepChecker() {
		return depChecker;
	}

	public void setDepChecker(String depChecker) {
		this.depChecker = depChecker;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	

}
