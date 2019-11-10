package com.hotent.bpmx.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmCptoReceiver;


public interface BpmCptoReceiverDao extends Dao<String, BpmCptoReceiver> {

	BpmCptoReceiver getByCopyToId(String copToId,String userId);
}
