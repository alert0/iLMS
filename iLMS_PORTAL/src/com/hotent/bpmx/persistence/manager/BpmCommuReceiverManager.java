package com.hotent.bpmx.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmCommuReceiver;

public interface BpmCommuReceiverManager extends Manager<String, BpmCommuReceiver>{

	BpmCommuReceiver getByCommuUser(String commuId, String receiverId);
	/**
	 * 通过沟通ID获取所有的接收人 如果 status == null 获取所有
	 * @param commuId
	 * @param status
	 * 	COMMU_NO="no";             
		COMMU_RECEIVE="receive";   
		COMMU_FEEDBACK="feedback"; 
	 * @return
	 */
	public List<BpmCommuReceiver> getByCommuStatus(String commuId,String status);
	/**检查是否已经沟通过*/
	boolean checkHasCommued(String commuId, String userId);
}
