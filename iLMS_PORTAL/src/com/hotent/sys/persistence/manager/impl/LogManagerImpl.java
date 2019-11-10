package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.LogDao;
import com.hotent.sys.persistence.manager.LogManager;
import com.hotent.sys.persistence.model.Log;

/**
 * 
 * <pre>
 * 描述：系统操作日志 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-29 14:37:00
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("logManager")
public class LogManagerImpl extends AbstractManagerImpl<String, Log> implements
		LogManager {
	@Resource
	LogDao logDao;

	@Override
	protected Dao<String, Log> getDao() {
		return logDao;
	}
}
