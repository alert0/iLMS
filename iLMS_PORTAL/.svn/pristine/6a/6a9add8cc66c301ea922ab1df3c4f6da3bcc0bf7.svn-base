package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.MsgTemplateDao;
import com.hotent.sys.persistence.model.MsgTemplate;
import com.hotent.sys.persistence.manager.MsgTemplateManager;

@Service("msgTemplateManager")
public class MsgTemplateManagerImpl extends AbstractManagerImpl<String, MsgTemplate> implements MsgTemplateManager{
	@Resource
	MsgTemplateDao msgTemplateDao;
	@Override
	protected Dao<String, MsgTemplate> getDao() {
		return msgTemplateDao;
	}
	@Override
	public MsgTemplate getDefault(String typeKey) {
		return msgTemplateDao.getDefault(typeKey);	
	}
	
	@Override
	public MsgTemplate getByKey(String templateKey) {
		return msgTemplateDao.getByKey(templateKey);	
	}
		
}
