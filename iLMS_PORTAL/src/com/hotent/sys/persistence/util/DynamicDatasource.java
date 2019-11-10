package com.hotent.sys.persistence.util;

import javax.annotation.Resource;

import com.hotent.base.core.util.PropertyUtil;
import com.hotent.base.db.api.IDynamicDatasource;
import com.hotent.base.db.datasource.DataSourceUtil;
import com.hotent.sys.persistence.dao.SysDataSourceDao;
import com.hotent.sys.persistence.model.SysDataSource;

public class DynamicDatasource implements IDynamicDatasource {
	
	@Resource
	SysDataSourceDao sysDataSourceDao;

	@Override
	public String getDbTypeByAlias(String alias) {
		if(alias.equals(DataSourceUtil. DEFAULT_DATASOURCE)||alias.equals(DataSourceUtil.LOCAL)){
			return PropertyUtil.getJdbcType();
		}
		SysDataSource ds= sysDataSourceDao.getByAlias(alias);
		if(ds!=null) return ds.getDbType();
		
		return "";
	}

}
