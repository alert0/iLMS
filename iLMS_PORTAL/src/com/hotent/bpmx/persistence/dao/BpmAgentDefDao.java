package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmAgentDef;


public interface BpmAgentDefDao extends Dao<String, BpmAgentDef> {
	
	/**
	 * 根据代理设定删除。
	 */
	void removeBySettingId(String settingId);
	/**
	 * 根据代理设定id获取代理流程
	 * @param id
	 * @return
	 */
	List<BpmAgentDef> getBySettingId(String id);
	
	void removeBySettingIds(String[] ids);
}
