package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmBusLink;


public interface BpmBusLinkDao extends Dao<String, BpmBusLink> {

	
	BpmBusLink getByBusinesKey(String businessKey,String formIdentity,boolean isNumber);
	BpmBusLink getByBusinesKey(String businessKey, boolean isNumber);
	/**
	 * 根据业务主键删除数据。
	 * @param businessKey
	 * @param formIdentity
	 * @param isNumber 
	 * void
	 */
	void delByBusinesKey(String businessKey,String formIdentity,boolean isNumber);
	
	/**
	 * 根据流程实例获取关联数据。
	 * @param instId
	 * @return 
	 * List&lt;BpmBusLink>
	 */
	List<BpmBusLink> getByInstId(String instId);


	String getMysqlVersion();
	
	/**
	 * 根据流程ID获取关联数据。
	 * @param instId
	 * @return 
	 * List&lt;BpmBusLink>
	 */
	List<BpmBusLink> getByDefId(String defId);
	
}
