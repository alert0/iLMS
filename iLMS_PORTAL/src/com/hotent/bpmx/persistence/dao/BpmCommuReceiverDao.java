package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmCommuReceiver;


public interface BpmCommuReceiverDao extends Dao<String, BpmCommuReceiver> {
	
	/**
	 * 根据commuId和接收人ID取得接收人对象。 
	 * @param commuId
	 * @param receiverId
	 * @return 
	 * BpmCommuReceiver
	 */
	BpmCommuReceiver getByCommuUser(String commuId,String receiverId);
	/**
	 * 通过通知，状态获取所有的接收人消息
	 * @param commuId
	 * @param status
	 * @return
	 */
	List<BpmCommuReceiver> getByCommuStatus(String commuId, String status);
	
	boolean checkHasCommued(String commuId, String receiverId);
}
