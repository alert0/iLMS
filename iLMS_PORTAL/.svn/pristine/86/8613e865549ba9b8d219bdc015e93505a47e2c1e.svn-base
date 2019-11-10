package com.hotent.org.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.OrgAuthDao;
import com.hotent.org.persistence.model.OrgAuth;

/**
 * 
 * <pre> 
 * 描述：分级组织管理 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-20 14:30:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class OrgAuthDaoImpl extends MyBatisDaoImpl<String, OrgAuth> implements OrgAuthDao{

    @Override
    public String getNamespace() {
        return OrgAuth.class.getName();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<OrgAuth> getAllOrgAuth(QueryFilter queryFilter) {
		return this.getByQueryFilter("getAllOrgAuth", queryFilter);
	}

	@Override
	public OrgAuth getByOrgIdAndUserId(Map<String, String> map) {
		return this.getUnique("getByOrgIdAndUserId", map);
	}

	@Override
	public void removeByOrgIds(String... orgIds) {
		for(String orgId : orgIds){
			this.deleteByKey("delByOrgId",orgId);
		}
	}

	@Override
	public List<OrgAuth> getLayoutOrgAuth(String userId) {
		return this.getByKey("getLayoutOrgAuth", userId);
	}

	@Override
	public List<OrgAuth> getByUserId(String userId) {
		return this.getByKey("getByUserId", userId);
	}
	
}

