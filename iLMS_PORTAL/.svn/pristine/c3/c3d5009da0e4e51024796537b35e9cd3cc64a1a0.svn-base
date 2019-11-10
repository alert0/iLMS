package com.hotent.sys.persistence.manager;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.SysDataSource;

public interface SysDataSourceManager extends Manager<String, SysDataSource> {

	/**
	 * 根据sysDataSource实例构建数据源实例。
	 * 
	 * @param sysDataSource
	 * @return DataSource
	 */
	DataSource getDsFromSysSource(SysDataSource sysDataSource);

	/**
	 * 根据别名获取数据源。
	 * 
	 * @param alias
	 * @return SysDataSource
	 */
	SysDataSource getByAlias(String alias);

	/**
	 * 
	 * 检验这个ds可用否
	 * 
	 * @param dataSource
	 *            ：数据源
	 * @param dataSourcePool
	 *            ：数据池
	 * @return boolean
	 */
	boolean checkConnection(SysDataSource sysDataSource);

	/**
	 * 获取在数据库同时在bean容器里的数据源
	 * 
	 * @return List<SysDataSource>
	 */
	List<SysDataSource> getSysDataSourcesInBean();


	/**
	 * 从数据库中实例化数据源列表。
	 * 
	 * @return List&lt;DataSource>
	 */
	Map<String, DataSource> getDataSource();

	/**
	 * 获取默认数据源 因为默认数据源是不在sys_data_source表中的
	 */
	SysDataSource getDefaultDataSource();

	/**
	 * <pre>
	 * 设置进程中的数据源
	 * 注意！！该方法目前只支持在controller层（非事务保护层）做
	 * </pre>
	 * 
	 * @param alias
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	void setDbContextDataSource(String alias);
	/**
	 * 获取数据库类型
	 * @param alias	：数据源别名
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	String getDbType(String alias);
}
