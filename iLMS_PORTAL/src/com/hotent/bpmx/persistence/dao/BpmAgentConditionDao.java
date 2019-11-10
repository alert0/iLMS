package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmAgentCondition;


public interface BpmAgentConditionDao extends Dao<String, BpmAgentCondition> {
	
	/**
	 * 根据设定ID获取设定的条件。
	 * @param settingId
	 * @return List&lt;BpmAgentCondition>
	 */
	List<BpmAgentCondition> getBySettingId(String settingId);
	
	/**
	 * 根据settingId删除数据。
	 * @param settingId 
	 * void
	 */
	void removeBySettingId(String settingId);

	void removeBySettingIds(String[] ids);
}
