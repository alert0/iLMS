package com.hotent.portal.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.portal.persistence.dao.SysLayoutSettingDao;
import com.hotent.portal.persistence.model.SysLayoutSetting;

/**
 * 
 * <pre> 
 * 描述：布局设置 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-07 16:18:52
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysLayoutSettingDaoImpl extends MyBatisDaoImpl<String, SysLayoutSetting> implements SysLayoutSettingDao{

    @Override
    public String getNamespace() {
        return SysLayoutSetting.class.getName();
    }

	@Override
	public SysLayoutSetting getByLayoutId(String layoutId) {
		return this.getUnique("getByLayoutId", layoutId);
	}
	
}

