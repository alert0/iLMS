package com.hotent.org.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.SysOrgParamsDao;
import com.hotent.org.persistence.model.SysOrgParams;

/**
 * 
 * <pre> 
 * 描述：组织参数 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-11-04 11:39:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysOrgParamsDaoImpl extends MyBatisDaoImpl<String, SysOrgParams> implements SysOrgParamsDao{

    @Override
    public String getNamespace() {
        return SysOrgParams.class.getName();
    }

	@Override
	public List<SysOrgParams> getByOrgId(String id) {
		return this.sqlSessionTemplate.selectList(getNamespace()+".getByOrgId", id);
	}

	@Override
	public SysOrgParams getByOrgIdAndAlias(String orgId, String alias) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orgId", orgId);
		params.put("alias", alias);
		return this.sqlSessionTemplate.selectOne(getNamespace()+".getByOrgIdAndAlias", params);
	}

	@Override
	public void removeByOrgIds(String ... ids) {
		for (String orgId : ids) {
			this.deleteByKey("removeByOrgId", orgId);
		}
	}

	@Override
	public void removeByAlias(String alias) {
		this.deleteByKey("removeByAlias", alias);
	}
	
}

