package com.hotent.portal.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.portal.persistence.dao.SysIndexLayoutManageDao;
import com.hotent.portal.persistence.model.SysIndexLayoutManage;

/**
 * <pre>
 * 对象功能:布局管理 Dao类
 * 开发公司:广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysIndexLayoutManageDaoImpl extends MyBatisDaoImpl<String, SysIndexLayoutManage> implements SysIndexLayoutManageDao{
	@Override
	public String getNamespace() {
		return SysIndexLayoutManage.class.getName();
	}

	
	@Override
	public List<SysIndexLayoutManage> getManageLayout(String orgIds, Short isDef) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgIds", orgIds);
		params.put("isDef", isDef);
		if (BeanUtils.isEmpty(orgIds))
			params.put("isNullOrg", true);

		return this.getByKey("getManageLayout", params);
	}

	public List<SysIndexLayoutManage> getByUserIdFilter(QueryFilter filter) {
		return getByKey("getByUserIdFilter",filter);
	}

	@Override
	public void updateIsDef(String orgId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		this.updateByKey("updateIsDef", params);
	}

	@Override
	public SysIndexLayoutManage getByOrgId(String orgId) {
		return this.getUnique("getByOrgId", orgId);
	}

	@Override
	public List<SysIndexLayoutManage> getByUserIdFilter(Map<String, Object> params) {
		return this.getByKey("getByUserIdFilter", params);
	}


	@Override
	public Boolean isExistName(String name) {
		Integer count = (Integer) this.getOne("getByName", name);
		return count > 0;
	}
	@Override
	public SysIndexLayoutManage getByOrgIdAndLayoutType(String orgId,Short layoutType) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("layoutType", layoutType);
		return this.getUnique("getByOrgIdAndLayoutType", map);
	}


	@Override
	public void cancelOrgIsDef(String orgId, Short layoutType) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("layoutType", layoutType);
		this.updateByKey("cancelOrgIsDef", map);
	}


	@Override
	public SysIndexLayoutManage getByOrgIdAndLayoutTypeAndLayoutId(String orgId, short layoutType, String layoutId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("layoutType", layoutType);
		map.put("layoutId", layoutId);
		return this.getUnique("getByOrgIdAndLayoutTypeAndLayoutId", map);
	}


	@Override
	public SysIndexLayoutManage getByIdAndType(String id, Short type) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("type", type);
		return this.getUnique("getByIdAndType", map);
	}
}