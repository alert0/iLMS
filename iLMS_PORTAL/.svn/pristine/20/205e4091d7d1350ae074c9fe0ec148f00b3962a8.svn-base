package com.hotent.bpmx.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmAgentDefDao;
import com.hotent.bpmx.persistence.model.BpmAgentDef;
import com.hotent.bpmx.persistence.manager.BpmAgentDefManager;

@Service("bpmAgentDefManager")
public class BpmAgentDefManagerImpl extends AbstractManagerImpl<String, BpmAgentDef> implements BpmAgentDefManager{
	@Resource
	BpmAgentDefDao bpmAgentDefDao;
	@Override
	protected Dao<String, BpmAgentDef> getDao() {
		return bpmAgentDefDao;
	}
}
