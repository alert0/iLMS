package com.hotent.sys.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.SysCategory;


public interface SysCategoryDao extends Dao<String, SysCategory> {
	Boolean isKeyExist(String id, String groupKey);
	/**
	 * 通过分类key 获取改分类
	 * @param key
	 * @return
	 */
	SysCategory getByKey(String key);
}
