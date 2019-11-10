package com.hotent.org.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.RoleDao;
import com.hotent.org.persistence.dao.UserRoleDao;
import com.hotent.org.persistence.manager.RoleManager;
import com.hotent.org.persistence.model.Role;
import com.hotent.sys.api.system.ResRoleService;

/**
 * 
 * <pre>
 * 描述：角色管理 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:28:04
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("roleManager")
public class RoleManagerImpl extends AbstractManagerImpl<String, Role> implements RoleManager {
	@Resource
	RoleDao roleDao;
	@Resource
	ResRoleService resRoleService;
	@Resource
	UserRoleDao userRoleDao;

	@Override
	protected Dao<String, Role> getDao() {
		return roleDao;
	}

	public Role getByAlias(String alias) {
		return roleDao.getByAlias(alias);
	}

	public List<Role> getListByUserId(String userId) {
		return roleDao.getListByUserId(userId);
	}

	public List<Role> getListByAccount(String account) {
		return roleDao.getListByAccount(account);
	}

	@Override
	public boolean isRoleExist(Role role) {
		return roleDao.isRoleExist(role);
	}
	
	@Override
	public void remove(String roleId) {
		super.remove(roleId);
		
		// 删除角色跟资源的关系
		resRoleService.removeByRoleAndSystem(roleId, null);
		// 删除角色跟用户的关系
		userRoleDao.removeByRoleId(roleId);
	}
 
	 
}
