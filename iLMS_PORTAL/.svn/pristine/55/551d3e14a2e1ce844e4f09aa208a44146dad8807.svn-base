package com.hotent.base.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.persistence.dao.SysResourceDao;
import com.hotent.base.persistence.model.SysResource;

/**
 * 
 * <pre> 
 * 描述：子系统资源 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 14:47:16
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysResourceDaoImpl extends MyBatisDaoImpl<String, SysResource> implements SysResourceDao{
	
    @Override
    public String getNamespace() {
        return SysResource.class.getName();
    }

	@Override
	public List<SysResource> getBySystemId(String systemId) {
		return this.getByKey("getBySystemId", systemId);
	}

	@Override
	public List<SysResource> getBySystemAndRole(String systemId, String roleId) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("systemId", systemId);
		params.put("roleId", roleId);
		return getByKey("getBySystemAndRole", params);
	}

	@Override
	public boolean isExist(SysResource resource) {
		int rtn=(Integer) this.getOne("isExist", resource);
		return rtn>0;
	}

	@Override
	public List<SysResource> getByParentId(String parentId) {
		List<SysResource> list=this.getByKey("getByParentId", parentId);
		return list;
	}

	@Override
	public List<SysResource> getBySystemAndUser(String systemId, String userId) {
		MapBuilder build= buildMapBuilder().addParam("systemId", systemId)
		.addParam("userId", userId);
		List<SysResource> list=this.getByKey("getBySystemAndUser", build.getParams());
		return list;
	}
	
	
}

