package com.hotent.sys.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysDataSourceDao;
import com.hotent.sys.persistence.model.SysDataSource;


@Repository("sysDataSourceDao")
public class SysDataSourceDaoImpl extends MyBatisDaoImpl<String, SysDataSource> implements SysDataSourceDao{

    @Override
    public String getNamespace() {
        return SysDataSource.class.getName();
    }

	@Override
	public List<SysDataSource> getDataSource(boolean enabled, boolean isStart) {

		Integer initOnStart=isStart?1:0;
		Integer enable=enabled?1:0;
		
		Map<String, Object> params=buildMapBuilder()
					.addParam("initOnStart", initOnStart)
					.addParam("enabled", enable)
					.getParams();
		
		return this.getByKey("getDataSource", params);
		
	}

	@Override
	public SysDataSource getByAlias(String alias) {
		return (SysDataSource) this.getOne("getByAlias", alias);
	}
	
}

