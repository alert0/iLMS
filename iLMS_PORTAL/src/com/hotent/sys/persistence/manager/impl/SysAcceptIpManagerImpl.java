package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysAcceptIpDao;
import com.hotent.sys.persistence.manager.SysAcceptIpManager;
import com.hotent.sys.persistence.model.SysAcceptIp;
 

@Service("sysAcceptIpManager")
public class SysAcceptIpManagerImpl extends AbstractManagerImpl<String, SysAcceptIp> implements SysAcceptIpManager{
	@Resource
	SysAcceptIpDao sysAcceptIpDao;
	@Override
	protected Dao<String, SysAcceptIp> getDao() {
		return sysAcceptIpDao;
	}
}
