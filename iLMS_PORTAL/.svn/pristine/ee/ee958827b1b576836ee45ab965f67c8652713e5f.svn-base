package com.hotent.sys.api.data.source;

import java.util.Map;

import javax.sql.DataSource;

import com.hotent.sys.api.data.source.model.SysDataSourceModel;

public interface SysDataSourceService {
	

	/**
	 * 根据别名获取数据源。
	 * TODO方法名称描述
	 * @param alias
	 * @return  SysDataSource
	 */
	SysDataSourceModel getByAlias(String alias);
	

	/**
	 * 改变当前数据源
	 * @param dsalias 数据源别名
	 * void
	 */
	void changeDataSource(String dsalias);
	
	/**
	 * 从数据库中实例化数据源列表。
	 * @return List&lt;DataSource>
	 */
	Map<String,DataSource> getDataSource();
}
