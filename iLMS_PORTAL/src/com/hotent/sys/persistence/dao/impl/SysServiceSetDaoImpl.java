package com.hotent.sys.persistence.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysServiceSetDao;
import com.hotent.sys.persistence.model.SysServiceSet;



@Repository

public class SysServiceSetDaoImpl extends MyBatisDaoImpl<String, SysServiceSet> implements SysServiceSetDao{

    @Override
    public String getNamespace() {
        return SysServiceSet.class.getName();
    }

	@Override
	public SysServiceSet getByAlias(String alias) {
		return getUnique("getByAlias", alias);
	}
	
}

