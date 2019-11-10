package com.hotent.org.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.dao.OrgUserDao;
import com.hotent.org.persistence.model.OrgUser;

/**
 * 
 * <pre> 
 * 描述：用户组织关系 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:27:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class OrgUserDaoImpl extends MyBatisDaoImpl<String, OrgUser> implements OrgUserDao{

    @Override
    public String getNamespace() {
        return OrgUser.class.getName();
    }

	public int updateUserPost(String id, String relId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(id, id);
		params.put("relId", relId);
		return this.updateByKey("updateUserPost", params);
		
	}

	public OrgUser getOrgUser(String orgId, String userId, String relId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("orgId", orgId);
	
		params.put("userId", userId);
		if(StringUtil.isEmpty(relId)){
		   params.put("relIdNull", "");
		}
		else {
		   params.put("relId", relId);
		}
		return this.getUnique("getByParms", params);
	 
	}
	public List<OrgUser> getListByOrgIdUserId(String orgId, String userId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("userId", userId);
		return this.getByKey("getByParms", params);
	 
	}
	 
	public  int removeByOrgIdUserId(String orgId,String userId){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("userId", userId);
		return this.deleteByKey("removeByOrgIdUserId", params);
	}
	
	public boolean setMaster(String id) {
		Map<String, Object> params=new  HashMap<String,Object>();
		params.put("id", id);
		return this.updateByKey("updateUserMasterOrg", params)>0;
	}
	
	public boolean cancelUserMasterOrg(String userId) {
		return this.updateByKey("cancelUserMasterOrg", userId)>0;
	}

	public OrgUser getOrgUserMaster(String userId) {
		Map<String, Object> params=new  HashMap<String,Object>();
		params.put("userId", userId);
		params.put("isMaster", 1);
		return this.getUnique("getByParms", params);
		 
	}

	@Override
	public List getUserByGroup(QueryFilter queryFilter) {
		return this.getByQueryFilter("getUserByGroup", queryFilter);
	}

	@Override
	public void removeByUserIds(String[] ids) {
		for(String id : ids){
			this.deleteByKey("removeByUserId", id);
		}
	}

	@Override
	public void updateCancleMainCharge(String orgId) {
		this.updateByKey("cancleMainCharge", orgId);
	}

	@Override
	public List<OrgUser> getChargesByOrgId(String orgId, Boolean isMain) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("orgId", orgId);
		if(isMain){
			params.put("isCharge", 2);
		}
		return this.getByKey("getChargesByOrgId", params);
	}
	@Override
	public void removeByOrgIds(String... orgIds) {
		for(String orgId :orgIds){
			this.deleteByKey("delByOrgId", orgId);
		}
	}

	@Override
	public List<OrgUser> getByUserId(String userId) {
		Map<String, Object> params=new  HashMap<String,Object>();
		params.put("userId", userId);
		return this.getByKey("getByParms", params);
	}

	@Override
	public List<OrgUser> getByOrgId(String orgId) {
		Map<String, Object> params=new  HashMap<String,Object>();
		params.put("orgId", orgId);
		return this.getByKey("getByParms", params);
	}

	@Override
	public OrgUser getByUserIdAndRelId(String userId, String relId) {
		Map<String, Object> params=new  HashMap<String,Object>();
		params.put("userId", userId);
		params.put("relId", relId);
		return this.getUnique("getByParms", params);
	}
	
	@Override
	public List getUserAndGroup(DefaultQueryFilter queryFilter) {
		return this.getByQueryFilter("getUserAndGroup", queryFilter);
	}

	@Override
	public List getSerachUser(DefaultQueryFilter queryFilter) {
		return this.getByQueryFilter("getSerachUser", queryFilter);
	}
	
}

