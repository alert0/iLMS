package com.hotent.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.sys.api.model.SysType;
import com.hotent.sys.api.type.ISysTypeService;
import com.hotent.sys.persistence.manager.SysTypeManager;

@Service
public class DefaultSysTypeService implements ISysTypeService {
	@Resource
	SysTypeManager  sysTypeManager;

	@Override
	public SysType getById(String typeId) {
		SysType sysType= sysTypeManager.get(typeId);
		return sysType;
	}

}
