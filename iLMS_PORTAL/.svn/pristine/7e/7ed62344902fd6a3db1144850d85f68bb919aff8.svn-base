package com.hotent.bpmx.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmCommuReceiverDao;
import com.hotent.bpmx.persistence.model.BpmCommuReceiver;
import com.hotent.bpmx.persistence.manager.BpmCommuReceiverManager;

@Service("bpmCommuReceiverManager")
public class BpmCommuReceiverManagerImpl extends AbstractManagerImpl<String, BpmCommuReceiver> implements BpmCommuReceiverManager{
	@Resource
	BpmCommuReceiverDao bpmCommuReceiverDao;
	@Override
	protected Dao<String, BpmCommuReceiver> getDao() {
		return bpmCommuReceiverDao;
	}
	
	
	@Override
	public BpmCommuReceiver getByCommuUser(String commuId, String receiverId) {
		
		return bpmCommuReceiverDao.getByCommuUser(commuId, receiverId);
	}


	@Override
	public List<BpmCommuReceiver> getByCommuStatus(String commuId, String status) {
		return bpmCommuReceiverDao.getByCommuStatus(commuId,status);
	}


	@Override
	public boolean checkHasCommued(String commuId, String receiverId) {
		return bpmCommuReceiverDao.checkHasCommued(commuId,receiverId);
	}
}
