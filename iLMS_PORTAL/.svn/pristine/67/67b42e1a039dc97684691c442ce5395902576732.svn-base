package com.hotent.portal.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.portal.persistence.dao.UserSettingDao;
import com.hotent.portal.persistence.model.UserSetting;

/**
 * 
 * <pre> 
 * 描述：用户配置 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-01 17:18:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class UserSettingDaoImpl extends MyBatisDaoImpl<Long, UserSetting> implements UserSettingDao{

    @Override
    public String getNamespace() {
        return UserSetting.class.getName();
    }

	@Override
	public UserSetting getUserSettingByUserId(String userId) {
		return this.getUnique("getUserSettingByUserId", userId);
	}
	
}

