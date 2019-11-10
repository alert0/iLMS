package com.hotent.bpmx.persistence.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmCptoReceiverDao;
import com.hotent.bpmx.persistence.model.BpmCptoReceiver;


@Repository

public class BpmCptoReceiverDaoImpl extends MyBatisDaoImpl<String, BpmCptoReceiver> implements BpmCptoReceiverDao{

    @Override
    public String getNamespace() {
        return BpmCptoReceiver.class.getName();
    }

	@SuppressWarnings("unchecked")
	@Override
	public BpmCptoReceiver getByCopyToId(String copToId,String userId) {
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("copToId", copToId);
		map.put("receiverId", userId);
		return this.getUnique("getByCopyToId", map);
	}
	
}

