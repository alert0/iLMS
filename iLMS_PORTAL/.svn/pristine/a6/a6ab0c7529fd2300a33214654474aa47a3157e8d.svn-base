package com.hotent.base.db.datasource;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.base.core.util.AppUtil;

/**
 * 数据源工具。 可以动态添加删除数据源。
 * 
 * <pre>
 * 构建组：x5-base-db
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-4-11-下午2:56:04
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class DataSourceUtil {

	public static final String GLOBAL_DATASOURCE = "dataSource";

	public static final String DEFAULT_DATASOURCE = "dataSource_Default";

	public static final String TARGET_DATASOURCES = "targetDataSources";
	
	public static final String LOCAL = "LOCAL";
	/**
	 * 添加数据源 。
	 * 
	 * @param key
	 * @param dataSource
	 *            void
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static void addDataSource(String key, DataSource dataSource,boolean replace) throws IllegalAccessException, NoSuchFieldException {
		DynamicDataSource dynamicDataSource = (DynamicDataSource) AppUtil.getBean(GLOBAL_DATASOURCE);
		if(dynamicDataSource.isDataSourceExist(key)){
			if(!replace) return;
			dynamicDataSource.removeDataSource(key);
		}
		dynamicDataSource.addDataSource(key, dataSource);
	}

	

	/**
	 * 根据名字删除数据源。
	 * 
	 * @param key
	 *            void
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static void removeDataSource(String key) throws IllegalAccessException, NoSuchFieldException {
		DynamicDataSource dynamicDataSource = (DynamicDataSource) AppUtil.getBean(GLOBAL_DATASOURCE);
		dynamicDataSource.removeDataSource(key);
	}

	/**
	 * 取得数据源。
	 * 
	 * @return Map&lt;String,DataSource>
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static Map<Object, Object> getDataSources() throws IllegalAccessException, NoSuchFieldException {
		DynamicDataSource dynamicDataSource = (DynamicDataSource) AppUtil.getBean(GLOBAL_DATASOURCE);
		return dynamicDataSource.getDataSource();
	}

	
	
	/**
	 * 根据别名返回容器里对应的数据源
	 * @param alias
	 * @return
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException 
	 * DataSource
	 */
	private static DataSource getDataSourceByAlias(String alias) throws IllegalAccessException, NoSuchFieldException {
		Map<Object, Object> map = getDataSources();
		for (Object key : map.keySet()) {
			if (alias.equals(key.toString())) {
				return (DataSource) map.get(key);
			}
		}
		return null;
	}
	
	/**
	 * 根据数据源别名返回容器里对应的jdbctemp
	 * @param alias
	 * @return
	 * @throws Exception 
	 * JdbcTemplate
	 * @exception 
	 * @since  1.0.0
	 */
	public static JdbcTemplate getJdbcTempByDsAlias(String alias) throws Exception {
		if(alias.equals(DEFAULT_DATASOURCE)||alias.equals(LOCAL)){
			return (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		}
		return new JdbcTemplate(DataSourceUtil.getDataSourceByAlias(alias)); 
	}

}
