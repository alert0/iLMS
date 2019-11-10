package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.LogErrDao;
import com.hotent.sys.persistence.manager.LogErrManager;
import com.hotent.sys.persistence.model.LogErr;

/**
 * 
 * <pre> 
 * 描述：错误日志 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-28 17:40:56
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysLogErrManager")
public class LogErrManagerImpl extends AbstractManagerImpl<String, LogErr> implements LogErrManager{
	@Resource
	LogErrDao sysLogErrDao;
	@Override
	protected Dao<String, LogErr> getDao() {
		return sysLogErrDao;
	}
}
