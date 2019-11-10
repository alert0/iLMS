package com.hotent.oa.persistence.manager.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.oa.persistence.dao.DemoUserDao;
import com.hotent.oa.persistence.model.DemoUser;
import com.hotent.oa.persistence.manager.DemoUserManager;

/**
 * 
 * <pre>
 * 描述：DemoUser 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-02 11:05:51
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("demoUserManager")
public class DemoUserManagerImpl extends AbstractManagerImpl<String, DemoUser>
		implements DemoUserManager {
	@Resource
	DemoUserDao demoUserDao;

	@Override
	protected Dao<String, DemoUser> getDao() {
		return demoUserDao;
	}
 

}
