package com.hotent.bo.api.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 开发公司:广州宏天软件有限公司 开发人员:何一帆 创建时间:2014-02-25 09:49:56
 */
public class BOService extends AbstractModel<String> {
	public final static class BOSERVICE_TYPE {
		/**
		 * http类型服务
		 */
		public final static String HTTP = "http";
		/**
		 * web类型服务
		 */
		public final static String WS = "ws";
	}

	private static final long serialVersionUID = 1L;
	protected String id; /* 服务配置ID */
	protected String defId; /* 对象定义ID */
	protected String serviceName; /* 服务名称 */
	protected String group; /* 所属分组 */
	protected String serviceType; /* 服务类型 */
	protected char isDefault; /* 是否默认配置 */
	protected String description; /* 服务说明 */

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 服务配置ID
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	/**
	 * 返回 对象定义ID
	 * 
	 * @return
	 */
	public String getDefId() {
		return this.defId;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * 返回 服务名称
	 * 
	 * @return
	 */
	public String getServiceName() {
		return this.serviceName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * 返回 服务类型
	 * 
	 * @return
	 */
	public String getServiceType() {
		return this.serviceType;
	}

	public void setIsDefault(char isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * 返回 是否默认配置
	 * 
	 * @return
	 */
	public char getIsDefault() {
		return this.isDefault;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 返回 服务说明
	 * 
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("defId", this.defId)
				.append("serviceName", this.serviceName)
				.append("serviceType", this.serviceType)
				.append("default", this.isDefault)
				.append("description", this.description).toString();
	}

}