package com.hotent.bo.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bo.api.model.BOService;


public interface BOServiceDao extends Dao<String, BOService> {
	
	/**
	 * 通过对象定义ID获取服务配置
	 * @param defId
	 * @return 
	 * Lis<BOService>
	 * @exception 
	 * @since  1.0.0
	 */
	List<BOService> getServicesByDefId(String defId);
	
	/**
	 * 通过对象定义ID删除服务配置
	 * @param defId 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	void removeServicesByDefId(String defId);
}
