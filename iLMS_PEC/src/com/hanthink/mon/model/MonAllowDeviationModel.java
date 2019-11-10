package com.hanthink.mon.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * <pre> 
 * 描述：允许误差 实体对象
 * </pre>
 */
public class MonAllowDeviationModel extends AbstractModel<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2413267483955796563L;
	
	/**
	 * id
	 */
	protected String id;
	/**
	 * 集货路线
	 */
	protected String routeCode;
	
	/**
	 * 允许误差(分)
	 */
	protected String errorDate;
	
	/**
	 * 工厂代码
	 */
	protected String factoryCode;
	
	/**
	 * 创建用户
	 */
	protected String creationUser;
	
	/**
	 * 创建时间
	 */
	protected String creationTime;

	/**
	 * 修改用户
	 */
	protected String lastModifiedUser;

	/**
	 * 修改时间
	 */
	protected String lastModifiedTime;

	@Override
	public void setId(String id) {
		this.id=id;
		
	}

	@Override
	public String getId() {
		return id;
	}

	

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getErrorDate() {
		return errorDate;
	}

	public void setErrorDate(String errorDate) {
		this.errorDate = errorDate;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	
	
}
