package com.hotent.sys.persistence.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.ResourcesDao;
import com.hotent.sys.persistence.model.Resources;



@Repository
public class ResourcesDaoImpl extends MyBatisDaoImpl<String,Resources> implements ResourcesDao{

    @Override
    public String getNamespace() {
        return Resources.class.getName();
    }
    
    public List<Resources> getResoucesByParentId(String parentId){
    	return null;
    }
    
    
	/**
	 * 根据系统id获取所有的资源列表。
	 * @param systemId
	 * @return
	 */
    @Override
	public List<Resources> getBySystemId(String systemId){
		return this.getByKey("getBySystemId", systemId);
	}
    
	/**
	 * 判断别名在该系统中是否存在。
	 * @param systemId	系统id
	 * @param alias		系统别名
	 * @return
	 */
    @Override
	public Integer isAliasExists(String systemId,String alias){
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("alias", alias);
		params.put("systemId", systemId);
		return (Integer)this.getOne("isAliasExists", params);
	}
	
	
	/**
	 * 判断别名是否存在。
	 * @param systemId
	 * @param resId
	 * @param alias
	 * @return
	 */
	@Override
	public Integer isAliasExistsForUpd(String systemId,String id, String alias){
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("alias", alias);
		params.put("systemId", systemId);
		params.put("id", id);
		return (Integer)this.getOne("isAliasExistsForUpd", params);
	}

	@Override
	public List<Resources> getResourceByUrl(String url) {
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("url", url);
		return this.getByKey("getResourceByUrl", params);
	}

	@Override
	public List<Resources> getByRoleIdsSystemId(List<String> roleIds, String systemId) {
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("roleIds", roleIds);
		params.put("systemId", systemId);
		return this.getByKey("getByRoleIdsSystemId", params);
	}

}

