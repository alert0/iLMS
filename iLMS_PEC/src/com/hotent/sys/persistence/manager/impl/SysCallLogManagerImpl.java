package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.api.model.SysCallLog;
import com.hotent.sys.persistence.dao.SysCallLogDao;
import com.hotent.sys.persistence.manager.SysCallLogManager;

/**
 * 
 * <pre> 
 * 描述：sys_call_log 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:zhangxw
 * 邮箱:zhangxw@jee-soft.cn
 * 日期:2017-10-26 11:40:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysCallLogManager")
public class SysCallLogManagerImpl extends AbstractManagerImpl<String, SysCallLog> implements SysCallLogManager<String,SysCallLog>{
	@Resource
	SysCallLogDao sysCallLogDao;
	@Override
	protected Dao<String, SysCallLog> getDao() {
		return sysCallLogDao;
	}
}
