package com.hotent.org.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.dao.UserRoleDao;
import com.hotent.org.persistence.model.UserRole;

/**
 * 
 * <pre> 
 * 描述：用户角色管理 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:28:34
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class UserRoleDaoImpl extends MyBatisDaoImpl<String, UserRole> implements UserRoleDao{

    @Override
    public String getNamespace() {
        return UserRole.class.getName();
    }

	public UserRole getByRoleIdUserId(String roleId, String userId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("roleId", roleId);
	    return this.getUnique("getByRoleIdUserId", params);
	}

	public List<UserRole> getListByUserId(String userId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
	    return this.getList("query", params);
	}

	public List<UserRole> getListByRoleId(String roleId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("roleId", roleId);
	    return this.getList("query", params);
	}

	public List<UserRole> getListByAlias(String alias) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("alias", alias);
	    return this.getList("query", params);
	}

	@Override
	public void removeByUserIds(String[] ids) {
		for (String userId : ids) {
			this.deleteByKey("removeByUserId", userId);
		}
	}

	@Override
	public void removeByRoleId(String roleId) {
		this.deleteByKey("removeByRoleId", roleId);
	}

	/**
	 * 根据用户ID查询该用户未添加的所有菜单角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月18日 上午11:16:55
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<UserRole> queryAddUserMenuRoleByUserId(UserRole model, Page p) {
		return this.getListByKey("queryAddUserMenuRoleByUserId", model, p);
	}

	/**
	 * 根据用户ID查询该用户未添加且在当前登录用户有的数据角色
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月18日 上午11:16:46
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<UserRole> queryCurUserAddUserMenuRoleByUserId(UserRole model, Page p) {
		return this.getListByKey("queryCurUserAddUserMenuRoleByUserId", model, p);
	}
	
}

