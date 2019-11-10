package com.hotent.org.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.OrgDao;
import com.hotent.org.persistence.model.Org;

import org.springframework.stereotype.Repository;

/**
 * 
 * <pre> 
 * 描述：组织架构 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-28 15:13:03
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class OrgDaoImpl extends MyBatisDaoImpl<String, Org> implements OrgDao{

    @Override
    public String getNamespace() {
        return Org.class.getName();
    }
 
	public Org getByCode(String code) {
    	return this.getUnique("getByCode", code);
	}

	public List<Org> getOrgListByUserId(String userId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		return this.getList("getOrgListByUser", params);
	}

	@Override
	public List<Org> getByParentId(String parentId) {
		return this.getByKey("getByParentId", parentId);
	}

	@Override
	public List<Org> getByOrgName(String orgName) {
		return this.getByKey("getByOrgName", orgName);
	}

	@Override
	public List<Org> getByPathName(String pathName) {
		return this.getByKey("getByPathName", pathName);
	}

	@Override
	public List<Org> getByParentAndDem(QueryFilter queryFilter) {
		return this.getByQueryFilter("getByParentAndDem", queryFilter);
	}

	@Override
	public Org getByDemIdAndSonId(String demId, String sonId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("demId", demId);
		params.put("sonId", sonId);
		return this.getUnique("getByDemIdAndSonId", params);
	}

	@Override
	public Org getOrgIdUserId(String id) {
		return this.getUnique("getOrgIdUserId", id);
	}

//	public List<Org> getOrgListByAccount(String account) {
//		Map<String,Object> params=new HashMap<String, Object>();
//		params.put("account", account);
//		return this.getList("getOrgListByUser", params);
//	}
	 
}

