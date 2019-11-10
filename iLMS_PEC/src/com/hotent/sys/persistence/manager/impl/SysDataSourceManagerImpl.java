
package com.hotent.sys.persistence.manager.impl;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.PropertyUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.datasource.DataSourceUtil;
import com.hotent.base.db.datasource.DbContextHolder;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysDataSourceDao;
import com.hotent.sys.persistence.manager.SysDataSourceManager;
import com.hotent.sys.persistence.model.SysDataSource;

@Service("sysDataSourceManager")
public class SysDataSourceManagerImpl extends AbstractManagerImpl<String, SysDataSource> implements SysDataSourceManager {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(SysDataSourceManagerImpl.class);
	
	@Resource
	SysDataSourceDao sysDataSourceDao;


	@Override
	protected Dao<String, SysDataSource> getDao() {
		return sysDataSourceDao;
	}


	/**
	 * 
	 * 利用Java反射机制把dataSource成javax.sql.DataSource对象
	 * 
	 * @param sysDataSource
	 * @param dataSourcePool
	 * @return javax.sql.DataSource
	 * @exception
	 * @since 1.0.0
	 */
	public DataSource getDsFromSysSource(SysDataSource sysDataSource) {

		try {
			// 获取对象
			Class<?> _class = null;
			_class = Class.forName(sysDataSource.getClassPath());
			javax.sql.DataSource sqldataSource = null;
			sqldataSource = (javax.sql.DataSource) _class.newInstance();// 初始化对象

			// 开始set它的属性
			String settingJson = sysDataSource.getSettingJson();
			JSONArray ja = JSONArray.fromObject(settingJson);

			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				Object value = BeanUtils.convertByActType(jo.getString("type"), jo.getString("value"));
				BeanUtils.setProperty(sqldataSource, jo.getString("name"), value);
			}

			// 如果有初始化方法，需要调用，必须是没参数的
			String initMethodStr = sysDataSource.getInitMethod();
			if (!StringUtil.isEmpty(initMethodStr)) {
				Method method = _class.getMethod(initMethodStr);
				method.invoke(sqldataSource);
			}

			return sqldataSource;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
		}

		return null;
	}



	@Override
	public boolean checkConnection(SysDataSource sysDataSource) {
		return checkConnection(getDsFromSysSource(sysDataSource), sysDataSource.getCloseMethod());
	}

	private boolean checkConnection(DataSource dataSource, String closeMethod) {
		boolean b = false;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			b = true;
		} catch (Exception e) {
			e.printStackTrace();

			// 调用关闭
			if (!StringUtil.isEmpty(closeMethod)) {
				String cp = closeMethod.split("\\|")[0];
				String mn = closeMethod.split("\\|")[1];

				try {
					Class<?> _class = Class.forName(cp);

					Method method = _class.getMethod(mn, null);
					method.invoke(null, null);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hotent.platform.system.manager.SysDataSourceManager#getSysDataSourcesInBean()
	 */
	@Override
	public List<SysDataSource> getSysDataSourcesInBean() {
		List<SysDataSource> result = new ArrayList<SysDataSource>();

		Map<Object, Object> map;
		try {
			map = DataSourceUtil.getDataSources();// 在容器的数据源
		} catch (Exception e) {
			return result;
		}

		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("enabled_", 1, QueryOP.EQUAL);
		List<SysDataSource> sysDataSources = sysDataSourceDao.query(queryFilter);// 用户配置在数据库的数据源

		// 容器的数据源 交集 数据库配置的数据源
		for (Object key : map.keySet()) {
			for (SysDataSource sysDataSource : sysDataSources) {
				if (sysDataSource.getAlias().equals(key.toString())) {
					result.add(sysDataSource);
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hotent.platform.system.manager.SysDataSourceManager#getByAlias(java.lang.String)
	 */
	@Override
	public SysDataSource getByAlias(String alias) {
		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("alias_", alias, QueryOP.EQUAL);
		List<SysDataSource> sysDataSources = sysDataSourceDao.query(queryFilter);
		if (sysDataSources != null && !sysDataSources.isEmpty()) {
			return sysDataSources.get(0);
		}
		return null;
	}
	
	@Override
	public Map<String, DataSource> getDataSource() {
		List<SysDataSource> list= sysDataSourceDao.getDataSource(true, true);
		Map<String, DataSource> maps=new HashMap<String, DataSource>();
		for(SysDataSource sysDataSource:list){
			DataSource ds=getDsFromSysSource(sysDataSource);
			if(ds==null) continue;
			maps.put(sysDataSource.getAlias(), ds);
			
		}
		return maps;
	}
	
	/**
	 * 获取默认数据源
	 * 
	 * @return SysDataSource
	 * @exception
	 * @since 1.0.0
	 */
	@Override
	public SysDataSource getDefaultDataSource() {
		SysDataSource defaultDataSource = new SysDataSource();
		defaultDataSource.setAlias(DataSourceUtil.LOCAL);
		defaultDataSource.setName("本地数据源");
		defaultDataSource.setDbType(PropertyUtil.getJdbcType());
		return defaultDataSource;
	}


	@Override
	public void setDbContextDataSource(String alias) {
		SysDataSource dataSource = getByAlias(alias);
		if(dataSource==null){
			dataSource =getDefaultDataSource();
		}
		DbContextHolder.setDataSource(dataSource.getAlias(), dataSource.getDbType());
	}


	@Override
	public String getDbType(String alias) {
		String dbType = PropertyUtil.getJdbcType();// 默认数据源类型，mysql,orcale,sqlserver
		SysDataSource sds = this.getByAlias(alias);
		if(sds!=null){
			dbType =sds.getDbType();
		}
		return dbType;
	}
}
