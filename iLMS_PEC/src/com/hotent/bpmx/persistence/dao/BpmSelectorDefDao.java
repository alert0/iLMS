package com.hotent.bpmx.persistence.dao;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmSelectorDef;

public interface BpmSelectorDefDao extends Dao<String, BpmSelectorDef> {
	/**
	 * 检查别名是否唯一
	 * @param alias
	 * @param id
	 */
	boolean isExistAlias(String alias, String id);
	
	BpmSelectorDef getByAlias(String alias); 
}
