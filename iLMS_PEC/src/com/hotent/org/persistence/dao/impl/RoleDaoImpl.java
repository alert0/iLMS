package com.hotent.org.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.RoleDao;
 
import com.hotent.org.persistence.model.Role;
import com.hotent.org.persistence.model.User;

/**
 * 
 * <pre> 
 * 描述：角色管理 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:28:04
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class RoleDaoImpl extends MyBatisDaoImpl<String, Role> implements RoleDao{

    @Override
    public String getNamespace() {
        return Role.class.getName();
    }
    
    /**
     * 根据角色别名获取角色
     */
    public Role getByAlias(String alias) {
		return this.getUnique("getByAlias", alias);
	}

	public List<Role> getListByUserId(String userId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		return this.getList("getList", params);
	}

	public List<Role> getListByAccount(String account) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("account", account);
		return this.getList("getList", params);
	}

	@Override
	public boolean isRoleExist(Role role) {
		Integer rtn= (Integer) this.getOne("isRoleExist", role);
		return rtn>0;
	}
    
}

