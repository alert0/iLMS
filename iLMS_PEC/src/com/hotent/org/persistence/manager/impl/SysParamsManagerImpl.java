package com.hotent.org.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.SysOrgParamsDao;
import com.hotent.org.persistence.dao.SysParamsDao;
import com.hotent.org.persistence.dao.SysUserParamsDao;
import com.hotent.org.persistence.manager.SysParamsManager;
import com.hotent.org.persistence.model.SysParams;

/**
 * 
 * <pre> 
 * 描述：组织参数 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-10-31 14:29:12
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysParamsManager")
public class SysParamsManagerImpl extends AbstractManagerImpl<String, SysParams> implements SysParamsManager{
	@Resource
	SysParamsDao sysParamsDao;
	@Resource 
	SysUserParamsDao sysUserParamsDao;
	@Resource 
	SysOrgParamsDao sysOrgParamsDao;
	
	@Override
	protected Dao<String, SysParams> getDao() {
		return sysParamsDao;
	}
	
	@Override
	public SysParams getByAlias(String alias) {
		return sysParamsDao.getByAlias(alias);
	}
	
	@Override
	public List<SysParams> getByType(String type) {
		return sysParamsDao.getByType(type);
	}

	@Override
	public void removeByIds(String... ids) {
		for(String id : ids){
			SysParams sysParams = sysParamsDao.get(id);
			sysUserParamsDao.removeByAlias(sysParams.getAlias());
			sysOrgParamsDao.removeByAlias(sysParams.getAlias());
		}
		super.removeByIds(ids);
	}
}
