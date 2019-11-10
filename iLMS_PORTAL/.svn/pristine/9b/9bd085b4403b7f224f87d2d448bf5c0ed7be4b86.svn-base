package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.api.model.SysJobLog;
import com.hotent.sys.persistence.dao.SysJobLogDao;
import com.hotent.sys.persistence.manager.SysJobLogManager;
 

/**
 * 对象功能:SYS_JOBLOG Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:phl
 * 创建时间:2011-12-28 17:01:51
 */
@Service
public class SysJobLogManagerImpl extends AbstractManagerImpl<String, SysJobLog> implements SysJobLogManager<String,SysJobLog>{

	@Resource
	private SysJobLogDao sysJobLogDao;
	
	@Override
	protected Dao<String, SysJobLog> getDao() {
		return sysJobLogDao;
	}
}
