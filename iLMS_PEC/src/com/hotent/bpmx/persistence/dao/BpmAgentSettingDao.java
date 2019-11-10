package com.hotent.bpmx.persistence.dao;
import java.util.Date;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmAgentSetting;


public interface BpmAgentSettingDao extends Dao<String, BpmAgentSetting> {
	
	/**
	 * 根据授权人和流程定义ID获取流程代理设定。
	 * @param authId
	 * @param flowKey
	 * @return BpmAgentSetting
	 */
	BpmAgentSetting getSettingByFlowAndAuthidAndDate(String authId,String flowKey);
	
	/**
	 * 检查全局代理是否存在冲突。
	 * @param authId
	 * @param beginDate
	 * @param endDate
	 * @return  Integer
	 */
	Integer getByAuthAndDate(String settingId,String authId,Date beginDate,Date endDate);
	
	
	
	
	/**
	 * 检查流程定义指定的流程定义是否存在冲突。
	 * @param authId
	 * @param beginDate
	 * @param endDate
	 * @param flowKey
	 * @return Integer
	 */
	Integer getByAuthDateFlowKey(String settingId, String authId,Date beginDate,Date endDate,String flowKey);
	
	
	
	/**
	 * 检查条件流程冲突。
	 * @param authId
	 * @param beginDate
	 * @param endDate
	 * @param flowKey
	 * @return Integer
	 */
	Integer getForCondition(String settingId,String authId,Date beginDate,Date endDate,String flowKey);
	

	
}
