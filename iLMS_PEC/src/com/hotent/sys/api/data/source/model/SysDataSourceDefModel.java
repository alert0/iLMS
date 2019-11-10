package com.hotent.sys.api.data.source.model;

import java.io.Serializable;

/**
 * 对象功能:sys_data_source_pool entity对象 开发公司:广州宏天软件有限公司 开发人员:liyj_aschs
 * 创建时间:2014-06-20 15:55:54
 */
public interface SysDataSourceDefModel extends Serializable {

	public String getId();

	public String getName();

	public String getClassPath();

	public String getSettingJson();

	public String getInitMethod();

	public Boolean getIsSystem();

	public String getCloseMethod();
}