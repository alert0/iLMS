package com.hotent.bpmx.persistence.manager;

import com.hotent.base.manager.api.Manager;
import com.hotent.bpmx.persistence.model.BpmSelectorDef;

public interface BpmSelectorDefManager extends Manager<String, BpmSelectorDef>{
	/**
	 * 检查别名是否唯一
	 * @param alias
	 * @param id
	 */
	boolean isExistAlias(String alias, String id);

	public BpmSelectorDef getByAlias(String alias);
}
