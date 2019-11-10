package com.hotent.sys.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysSignRightDao;
import com.hotent.sys.persistence.manager.SysSignRightManager;
import com.hotent.sys.persistence.model.SysSignRight;
 
/**
 * 
 * <pre>
 * 描述：OFFICE模版印章授权 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:lyj@jee-soft.cn
 * 日期:2014-11-07 16:28:46
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysSignRightManager")
public class SysSignRightManagerImpl extends AbstractManagerImpl<String, SysSignRight> implements SysSignRightManager {
	@Resource
	SysSignRightDao sysSignRightDao;

	@Override
	protected Dao<String, SysSignRight> getDao() {
		return sysSignRightDao;
	}
	
	@Override
	public SysSignRight getBySignId(String signId) {
		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("sign_id_", signId, QueryOP.EQUAL);
		List<SysSignRight> sysSignRights = sysSignRightDao.query(queryFilter);
		if (sysSignRights == null || sysSignRights.isEmpty())
			return null;
		return sysSignRights.get(0);
	}
}
