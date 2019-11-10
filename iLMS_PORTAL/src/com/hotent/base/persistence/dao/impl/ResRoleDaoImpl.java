package com.hotent.base.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.persistence.dao.ResRoleDao;
import com.hotent.base.persistence.model.ResRole;

/**
 * 
 * <pre> 
 * 描述：角色资源分配 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 14:47:16
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class ResRoleDaoImpl extends MyBatisDaoImpl<String, ResRole> implements ResRoleDao{

    @Override
    public String getNamespace() {
        return ResRole.class.getName();
    }

	@Override
	public List<ResRole> getAllByRoleId(String roleId) {
		List<ResRole> test = getByKey("getByRoleId", roleId);
		return test;
	}

	@Override
	public void removeByRoleAndSystem(String roleId, String systemId) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("roleId", roleId);
		params.put("systemId", systemId);
		deleteByKey("removeByRoldeId", params);
	}

	@Override
	public List<ResRole> getUrlRoleBySystemId(String systemId) {
		List<ResRole> list = getByKey("getUrlRoleBySystemId", systemId);
		return list;
	}

	@Override
	public List<ResRole> getResRoleBySystemId(String systemId) {
		List<ResRole> list = getByKey("getResRoleBySystemId", systemId);
		return list;
	}
	
}

