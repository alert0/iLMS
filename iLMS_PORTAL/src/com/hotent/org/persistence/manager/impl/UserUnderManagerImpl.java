package com.hotent.org.persistence.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.UserUnderDao;
import com.hotent.org.persistence.model.UserUnder;
import com.hotent.org.persistence.manager.UserUnderManager;

/**
 * 
 * <pre> 
 * 描述：下属管理 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-25 09:24:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("userUnderManager")
public class UserUnderManagerImpl extends AbstractManagerImpl<String, UserUnder> implements UserUnderManager{
	@Resource
	UserUnderDao userUnderDao;
	@Override
	protected Dao<String, UserUnder> getDao() {
		return userUnderDao;
	}
	@Override
	public List<UserUnder> getUserUnder(QueryFilter queryFilter) {
		return userUnderDao.getUserUnder(queryFilter);
	}
	@Override
	public void delByUpIdAndUderId(Map<String, String> map) {
		userUnderDao.delByUpIdAndUderId(map);
	}
}
