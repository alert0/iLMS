package com.hotent.org.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.UserRoleDao;
import com.hotent.org.persistence.model.UserRole;
import com.hotent.org.persistence.manager.UserRoleManager;

/**
 * 
 * <pre> 
 * 描述：用户角色管理 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:28:34
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("userRoleManager")
public class UserRoleManagerImpl extends AbstractManagerImpl<String, UserRole> implements UserRoleManager{
	@Resource
	UserRoleDao userRoleDao;
	@Override
	protected Dao<String, UserRole> getDao() {
		return userRoleDao;
	}
	public UserRole getByRoleIdUserId(String roleId, String userId) {
		 return userRoleDao.getByRoleIdUserId(roleId, userId);
	}
	public List<UserRole> getListByUserId(String userId) {
		 return userRoleDao.getListByUserId(userId);
	}
	public List<UserRole> getListByRoleId(String roleId) {
		 return userRoleDao.getListByRoleId(roleId);
	}
	public List<UserRole> getListByAlias(String alias) {
		return userRoleDao.getListByAlias(alias);
	}
	
	/**
	 *  根据用户ID查询该用户未添加的所有菜单角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月18日 上午11:15:57
	 */
	@Override
	public PageList<UserRole> queryAddUserMenuRoleByUserId(UserRole model, Page p) {
		return userRoleDao.queryAddUserMenuRoleByUserId(model, p);
	}
	
	/**
	 * 根据用户ID查询该用户未添加且在当前登录用户有的数据角色
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月18日 上午11:16:02
	 */
	@Override
	public PageList<UserRole> queryCurUserAddUserMenuRoleByUserId(UserRole model, Page p) {
		return userRoleDao.queryCurUserAddUserMenuRoleByUserId(model, p);
	}
}
