package com.hanthink.dpm.model;

import com.hotent.base.core.model.AbstractModel;

public class DpmItemModel extends AbstractModel<String>{

	private static final long serialVersionUID = 2468955303396565013L;
	
	/**逻辑主键**/
	private String id;	
	/**工厂**/
	private String factoryCode;	
	/**不良品项目代码**/
	private String itemCode;
	/**不良品项目名称**/
	private String itemName;
	/**不良品项目描述**/
	private String itemDesc;
	/**创建人**/
	private String creationUser;
	/**最后修改人**/
	private String lastModifiedUser;
	/**键**/
	private String value;
	/**值**/
	private String label;


	public DpmItemModel(String id, String factoryCode, String itemCode, String itemName, String itemDesc) {
		super();
		this.id = id;
		this.factoryCode = factoryCode;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemDesc = itemDesc;
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

	public DpmItemModel() {
		super();
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
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




}
