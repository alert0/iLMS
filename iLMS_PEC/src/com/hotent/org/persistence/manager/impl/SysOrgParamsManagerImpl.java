package com.hotent.org.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.SysOrgParamsDao;
import com.hotent.org.persistence.manager.SysOrgParamsManager;
import com.hotent.org.persistence.model.SysOrgParams;

/**
 * 
 * <pre> 
 * 描述：组织参数 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-11-04 11:39:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysOrgParamsManager")
public class SysOrgParamsManagerImpl extends AbstractManagerImpl<String, SysOrgParams> implements SysOrgParamsManager{
	@Resource
	SysOrgParamsDao sysOrgParamsDao;
	@Override
	protected Dao<String, SysOrgParams> getDao() {
		return sysOrgParamsDao;
	}
	@Override
	public List<SysOrgParams> getByOrgId(String id) {
		return sysOrgParamsDao.getByOrgId(id);
	}
	@Override
	public void saveParams(String orgId, List<JSONObject> lists) {
		sysOrgParamsDao.removeByOrgIds(orgId);
		for (JSONObject jsonObject : lists) {
			SysOrgParams sysOrgParams = new SysOrgParams();
			sysOrgParams.setAlias(jsonObject.getString("alias"));
			sysOrgParams.setValue(jsonObject.getString("value"));
			sysOrgParams.setOrgId(orgId);
			sysOrgParamsDao.create(sysOrgParams);
		}
	}
	@Override
	public SysOrgParams getByOrgIdAndAlias(String groupId, String key) {
		return sysOrgParamsDao.getByOrgIdAndAlias(groupId, key);
	}
}
