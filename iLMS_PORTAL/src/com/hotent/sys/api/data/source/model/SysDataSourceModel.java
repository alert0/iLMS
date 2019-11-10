package com.hotent.sys.api.data.source.model;
import java.io.Serializable;

/**
 * 对象功能:sys_data_source entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:liyj_aschs
 * 创建时间:2014-06-24 11:41:52
 */
public interface SysDataSourceModel  extends Serializable{
	
	public String getId();

	public String getName();

	public String getAlias();

	public String getDbType();
	
	public String getSettingJson();

	public Boolean getInitOnStart();

	public Boolean getEnabled() ;

	public String getInitMethod();
	
	public String getCloseMethod();
	
	public String getClassPath();
}