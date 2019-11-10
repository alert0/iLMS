package com.hotent.sys.persistence.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.RoleResourceDao;
import com.hotent.sys.persistence.model.RoleResource;



@Repository

public class RoleResourceDaoImpl extends MyBatisDaoImpl<String, RoleResource> implements RoleResourceDao{

    @Override
    public String getNamespace() {
        return RoleResource.class.getName();
    }

	@Override
	public List<RoleResource> getByRoleSystem(String roleId, String systemId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("roleId", roleId); 
		map.put("systemId", systemId);
		return this.getByKey("getByRoleSystem", map);
	}

	@Override
	public void deleteByRoleSystem(String roleId, String systemId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("roleId", roleId); 
		map.put("systemId", systemId);
		this.getByKey("deleteByRoleSystem", map);
	}
	
}

