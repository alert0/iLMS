package com.hotent.sys.persistence.dao;

import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.Script;


public interface ScriptDao extends Dao<String, Script> {

	/**
	 * 返回所有脚本的分类
	 * @return
	 */
	public List<String> getDistinctCategory();
	
	/**
	 * 判断脚本名称是否存在
	 * @param name
	 * @return
	 */
	public Integer isNameExists(String name);
}
