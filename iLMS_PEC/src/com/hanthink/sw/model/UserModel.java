package com.hanthink.sw.model;

import com.hotent.base.core.model.AbstractModel;

public class UserModel extends AbstractModel<String>{
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 7730470084940897616L;

	private String id;
	
	private String userFlag;
	
	private String supplier;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	
}
