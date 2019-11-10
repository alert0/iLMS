package com.hotent.sys.persistence.dao;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.AliasScript;


public interface AliasScriptDao extends Dao<String, AliasScript>{

	/**
	 * 根据脚本别名获取别名脚本
	 * @param alias
	 * @return
	 */
	public AliasScript getByAlias(String alias);
}
