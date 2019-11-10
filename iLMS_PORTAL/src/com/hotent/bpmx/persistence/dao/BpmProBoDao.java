package com.hotent.bpmx.persistence.dao;
import java.util.List;
import java.util.Map;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmProBo;


public interface BpmProBoDao extends Dao<String, BpmProBo> {
	
	/**
	 * 根据流程信息（ID或者KEY）删除流程和业务对象的绑定信息
	 * @param map 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	void removeByProcess(Map<String,Object> params);
	
	/**
	 * 根据业务对象的标识（code）删除流程和业务对象的绑定信息
	 * @param boCode 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	void removeByBoCode(String boCode);
	
	/**
	 * 根据流程信息（ID或者KEY）获得流程和业务对象的绑定信息
	 * @param map
	 * @return 
	 * List<BpmProBo>
	 * @exception 
	 * @since  1.0.0
	 */
	List<BpmProBo> getByProcess(Map<String,Object> params);
	
	/**
	 * 根据业务对象的标识（code）获得流程和业务对象的绑定信息
	 * @param boCode
	 * @return 
	 * List<BpmProBo>
	 * @exception 
	 * @since  1.0.0
	 */
	List<BpmProBo> getByBoCode(String boCode);
	
}
