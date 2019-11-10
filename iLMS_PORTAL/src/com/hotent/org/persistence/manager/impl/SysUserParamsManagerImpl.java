package com.hotent.org.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.SysUserParamsDao;
import com.hotent.org.persistence.manager.SysUserParamsManager;
import com.hotent.org.persistence.model.SysUserParams;

/**
 * 
 * <pre> 
 * 描述：用户参数 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-11-01 17:11:33
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysUserParamsManager")
public class SysUserParamsManagerImpl extends AbstractManagerImpl<String, SysUserParams> implements SysUserParamsManager{
	@Resource
	SysUserParamsDao sysUserParamsDao;
	@Override
	protected Dao<String, SysUserParams> getDao() {
		return sysUserParamsDao;
	}
	@Override
	public List<SysUserParams> getByUserId(String id) {
		return sysUserParamsDao.getByUserId(id);
	}
	@Override
	public void saveParams(String userId, List<JSONObject> lists) {
		sysUserParamsDao.removeByUserIds(userId);
		for (JSONObject jsonObject : lists) {
			SysUserParams sysUserParams = new SysUserParams();
			sysUserParams.setAlias(jsonObject.getString("alias"));
			sysUserParams.setValue(jsonObject.getString("value"));
			sysUserParams.setUserId(userId);
			sysUserParamsDao.create(sysUserParams);
		}
	}
	@Override
	public SysUserParams getByUserIdAndAlias(String userId, String key) {
		return sysUserParamsDao.getByUserIdAndAlias(userId, key);
	}
}
