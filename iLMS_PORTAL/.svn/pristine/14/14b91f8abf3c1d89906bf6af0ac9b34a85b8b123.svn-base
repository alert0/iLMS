package com.hotent.portal.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.portal.persistence.dao.SysLayoutSettingDao;
import com.hotent.portal.persistence.model.SysLayoutSetting;
import com.hotent.portal.persistence.manager.SysLayoutSettingManager;

/**
 * 
 * <pre> 
 * 描述：布局设置 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-07 16:18:52
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysLayoutSettingManager")
public class SysLayoutSettingManagerImpl extends AbstractManagerImpl<String, SysLayoutSetting> implements SysLayoutSettingManager{
	@Resource
	SysLayoutSettingDao sysLayoutSettingDao;
	@Override
	protected Dao<String, SysLayoutSetting> getDao() {
		return sysLayoutSettingDao;
	}
	@Override
	public SysLayoutSetting getByLayoutId(String layoutId) {
		return sysLayoutSettingDao.getByLayoutId(layoutId);
	}
}
