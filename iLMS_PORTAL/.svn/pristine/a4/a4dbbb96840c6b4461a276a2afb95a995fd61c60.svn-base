package com.hotent.bpmx.persistence.manager;

import java.util.Map;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.persistence.model.BpmAgentCondition;

public interface BpmAgentConditionManager extends Manager<String, BpmAgentCondition>{

	/**
	 * 检查条件代理设置的条件，如果通过返回true，不通过返回false
	 * @param delegateTask 
	 * @param con
	 * @param busData
	 * @param vars
	 * @return
	 */
	boolean checkCondition(BpmDelegateTask delegateTask, BpmAgentCondition con);
	
}
