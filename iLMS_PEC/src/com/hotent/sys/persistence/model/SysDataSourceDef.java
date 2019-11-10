package com.hotent.sys.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:sys_data_source_pool entity对象 开发公司:广州宏天软件有限公司 开发人员:liyj_aschs 创建时间:2014-06-20 15:55:54
 */
public class SysDataSourceDef extends AbstractModel<String> {
	
	private static final long serialVersionUID = 1L;
	protected String id; /* 主键 */
	protected String name; /* 数据源名称 */
	protected String classPath; /* 数据源全路径 */
	protected String settingJson; /* 属性配置 */
	protected String initMethod; /* 初始化方法，有些可以不填写 */
	protected String closeMethod; /* 关闭数据源的时候应该调用的方法，可不填 */
	protected Boolean isSystem; /* 是系统默认的 */

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 主键
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 数据源名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	/**
	 * 返回 数据源全路径
	 * 
	 * @return
	 */
	public String getClassPath() {
		return this.classPath;
	}

	public void setSettingJson(String settingJson) {
		this.settingJson = settingJson;
	}

	/**
	 * 返回 属性配置
	 * 
	 * @return
	 */
	public String getSettingJson() {
		return this.settingJson;
	}

	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
	}

	/**
	 * 返回 初始化方法，有些可以不填写
	 * 
	 * @return
	 */
	public String getInitMethod() {
		return this.initMethod;
	}

	
	/**
	 * isSystem
	 * @return  the isSystem
	 * @since   1.0.0
	 */
	
	public Boolean getIsSystem() {
		return isSystem;
	}

	/**
	 * @param isSystem the isSystem to set
	 */
	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	/**
	 * closeMethod
	 * @return  the closeMethod
	 * @since   1.0.0
	 */
	
	public String getCloseMethod() {
		return closeMethod;
	}

	/**
	 * @param closeMethod the closeMethod to set
	 */
	public void setCloseMethod(String closeMethod) {
		this.closeMethod = closeMethod;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("classPath", this.classPath).append("settingJson", this.settingJson).append("initMethod", this.initMethod).append("closeMethod", this.closeMethod).append("inSystem", this.isSystem).toString();
	}
}