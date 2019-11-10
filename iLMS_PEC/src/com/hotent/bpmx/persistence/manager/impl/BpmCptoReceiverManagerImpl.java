package com.hotent.bpmx.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmCptoReceiverDao;
import com.hotent.bpmx.persistence.model.BpmCptoReceiver;
import com.hotent.bpmx.persistence.model.CopyTo;
import com.hotent.bpmx.persistence.manager.BpmCptoReceiverManager;

@Service("bpmCptoReceiverManager")
public class BpmCptoReceiverManagerImpl extends AbstractManagerImpl<String, BpmCptoReceiver> implements BpmCptoReceiverManager{
	@Resource
	BpmCptoReceiverDao bpmCptoReceiverDao;
	@Override
	protected Dao<String, BpmCptoReceiver> getDao() {
		return bpmCptoReceiverDao;
	}
	@Override
	public BpmCptoReceiver getByCopyToId(String copToId,String userId) {
		// TODO Auto-generated method stub
		return bpmCptoReceiverDao.getByCopyToId(copToId,userId);
	}
}
