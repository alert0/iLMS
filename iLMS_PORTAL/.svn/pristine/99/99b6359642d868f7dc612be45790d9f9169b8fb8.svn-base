package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.ResUrl;



public interface ResUrlDao extends Dao<String, ResUrl> {
	
	/**
	 * 根据资源ID 删除 url
	 * @param resId
	 */
	void deleteByResId(String resId);
	
	/**
	 * 通过资源ID获取所有资源地址
	 * @param resId
	 * @return
	 */
	List<ResUrl> getByResId(String resId);
}
