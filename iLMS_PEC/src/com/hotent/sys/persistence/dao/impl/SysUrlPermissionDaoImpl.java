package com.hotent.sys.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysUrlPermissionDao;
import com.hotent.sys.persistence.model.SysUrlPermission;



@Repository

public class SysUrlPermissionDaoImpl extends MyBatisDaoImpl<String, SysUrlPermission> implements SysUrlPermissionDao{

    @Override
    public String getNamespace() {
        return SysUrlPermission.class.getName();
    }

	@Override
	public List<SysUrlPermission> getAllByEnable(Short enable) {
		return this.sqlSessionTemplate.selectList("getAllByEnable", enable);
	}
	
}

