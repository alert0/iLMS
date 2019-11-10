package com.hotent.sys.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:sys_data_source entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:liyj_aschs
 * 创建时间:2014-06-24 11:41:52
 */
public class SysDataSource extends AbstractModel<String>{

	private static final long serialVersionUID = 1L;
	protected String id; /*主键*/
	protected String name; /*name_*/
	protected String alias; /*别名*/
	protected String classPath;/*类名*/
	protected String dbType; /*数据源id*/
	protected String settingJson; /*Json存储配置*/
	protected Boolean initOnStart; /*在启动时，启动连接池，并添加到spring容器中管理。*/
	protected Boolean enabled; /*是否生效*/
	protected String initMethod; /*初始化方法，有些可以不填写 */
	protected String closeMethod; /*关闭数据源的时候应该调用的方法，可不填 */
	
	

	/**
	 * id
	 * @return  the id
	 * @since   1.0.0
	 */
	
	public String getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * name
	 * @return  the name
	 * @since   1.0.0
	 */
	
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * alias
	 * @return  the alias
	 * @since   1.0.0
	 */
	
	public String getAlias() {
		return alias;
	}



	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}



	/**
	 * dbType
	 * @return  the dbType
	 * @since   1.0.0
	 */
	
	public String getDbType() {
		return dbType;
	}



	/**
	 * @param dbType the dbType to set
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}



	/**
	 * settingJson
	 * @return  the settingJson
	 * @since   1.0.0
	 */
	
	public String getSettingJson() {
		return settingJson;
	}



	/**
	 * @param settingJson the settingJson to set
	 */
	public void setSettingJson(String settingJson) {
		this.settingJson = settingJson;
	}



	/**
	 * initOnStart
	 * @return  the initOnStart
	 * @since   1.0.0
	 */
	
	public Boolean getInitOnStart() {
		return initOnStart;
	}



	/**
	 * @param initOnStart the initOnStart to set
	 */
	public void setInitOnStart(Boolean initOnStart) {
		this.initOnStart = initOnStart;
	}



	/**
	 * enabled
	 * @return  the enabled
	 * @since   1.0.0
	 */
	
	public Boolean getEnabled() {
		return enabled;
	}



	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}



	/**
	 * initMethod
	 * @return  the initMethod
	 * @since   1.0.0
	 */
	
	public String getInitMethod() {
		return initMethod;
	}



	/**
	 * @param initMethod the initMethod to set
	 */
	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
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
	 * classPath
	 * @return  the classPath
	 * @since   1.0.0
	 */
	
	public String getClassPath() {
		return classPath;
	}



	/**
	 * @param classPath the classPath to set
	 */
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("alias", this.alias) 
		.append("dsId", this.dbType) 
		.append("settingJson", this.settingJson) 
		.append("initOnStart", this.initOnStart) 
		.append("enabled", this.enabled)
		.append("initMethod", this.initMethod) 
		.append("closeMethod", this.closeMethod)
		.append("classPath", this.classPath) 
		.toString();
	}
}