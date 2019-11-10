package com.hotent.sys.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.Resources;
import com.hotent.sys.persistence.model.RoleResource;


public interface RoleResourceManager extends Manager<String, RoleResource>{

	/**
	 * 通过角色，系统，获取资源，并选中
	 * @param roleId
	 * @param systemId
	 * @return
	 */
	List<Resources> getRoleResTreeChecked(String roleId, String systemId);
	/**
	 * 更新角色与资源的关系
	 * @param resIds
	 * @param roleId
	 * @param systemId
	 */
	void updateRoleRes(String[] resIds, String roleId, String systemId);
	
}
