package com.hotent.org.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.OrgRelDao;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.OrgReldef;
import com.hotent.org.persistence.model.User;

/**
 * 
 * <pre> 
 * 描述：组织关联关系 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:26:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class OrgRelDaoImpl extends MyBatisDaoImpl<String, OrgRel> implements OrgRelDao{

    @Override
    public String getNamespace() {
        return OrgRel.class.getName();
    }
    public OrgRel getByCode(String code) {
    	return this.getUnique("getByCode", code);
	}
	public List<OrgRel> getListByOrgId(String orgId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("orgId", orgId);
		return this.getList("queryInfoList", params);
	}

	public List<OrgRel> queryInfoList(QueryFilter queryFilter) {
		return this.getByQueryFilter("queryInfoList", queryFilter);
	}

	public OrgRel getByOrgIdRelDefId(String orgId, String relDefId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("relDefId", relDefId);
		return this.getUnique("getByOrgIdRelDefId", params);
	}
	
	public List<OrgRel> getListByUserId(String userId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		return this.getList("getRelListByParam", params);
	}
	public List<OrgRel> getListByAccount(String account) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("account", account);
		return this.getList("getRelListByParam", params);
	}
	@Override
	public void removeByOrgIds(String... ids) {
		for(String id : ids){
			this.deleteByKey("delByOrgId", id);
		}
	}
	@Override
	public List<OrgRel> getByReldefId(String reldefId) {
		return this.getByKey("getByReldefId", reldefId);
	}
 
}

