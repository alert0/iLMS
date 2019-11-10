package com.hotent.bpmx.persistence.manager;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmAgentSetting;

public interface BpmAgentSettingManager extends Manager<String, BpmAgentSetting>{
	
	
	/**
	 * 根据授权人和流程定义ID获取流程代理设定。
	 * @param authId
	 * @param flowKey
	 * @return BpmAgentSetting
	 */
	public BpmAgentSetting getSettingByFlowAndAuthidAndDate(String authId,String flowKey);
	
	/**
	 * 检查代理是否和已设置的代理是否有冲突
	 * @param setting
	 * @return 
	 * ResultMessage
	 */
	ResultMessage checkConflict(BpmAgentSetting setting);

	/**
	 * 通过id获取流程代理设定。（包含流程和 代理条件设定）
	 * @param entityId
	 * @return
	 */
	BpmAgentSetting getById(String entityId);
}
