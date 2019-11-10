package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.RoleResource;
 


public interface RoleResourceDao extends Dao<String, RoleResource> {
	/**
	 * 获取某角色所有的资源
	 * @param roleId
	 * @param systemId
	 * @return
	 */
	List<RoleResource> getByRoleSystem(String roleId, String systemId);
	/**
	 * 通过角色和系统删除角色资源的对应关系
	 * @param roleId
	 * @param systemId
	 */
	void deleteByRoleSystem(String roleId, String systemId);
}
