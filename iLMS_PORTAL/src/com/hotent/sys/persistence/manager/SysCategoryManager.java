package com.hotent.sys.persistence.manager;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.SysCategory;


public interface SysCategoryManager extends Manager<String, SysCategory>{
	
	/**
	 * 是否包含指定的groupKey。
	 * @param id
	 * @param groupKey
	 * @return  Boolean
	 */
	Boolean isKeyExist(String id, String groupKey);

	/**
	 * 根据typeKey获取类型分类对象。
	 * @param typeKey
	 * @return  SysCategory
	 */
	SysCategory getByTypeKey(String typeKey);
}
