package com.hotent.bpmx.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmCommuReceiverDao;
import com.hotent.bpmx.persistence.model.BpmCommuReceiver;


@Repository

public class BpmCommuReceiverDaoImpl extends MyBatisDaoImpl<String, BpmCommuReceiver> implements BpmCommuReceiverDao{

    @Override
    public String getNamespace() {
        return BpmCommuReceiver.class.getName();
    }

	@Override
	public BpmCommuReceiver getByCommuUser(String commuId, String receiverId) {
		Map<String, Object> params= buildMapBuilder()
				.addParam("commuId", commuId).addParam("receiverId", receiverId).getParams();
		BpmCommuReceiver commuReceiver=this.getUnique("getByCommuUser", params);
		return commuReceiver;
	}

	@Override
	public List<BpmCommuReceiver> getByCommuStatus(String commuId, String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("commuId", commuId);
		params.put("status", status);
		return this.getByKey("getByCommuStatus", params);
	}

	@Override
	public boolean checkHasCommued(String commuId, String receiverId) {
		Map<String, Object> params= buildMapBuilder().addParam("commuId", commuId).addParam("receiverId", receiverId).getParams();
		
		int count = (Integer) this.getOne("checkHasCommued", params);
		
		if(count>0) return true;
		return false;
	}
	
}

