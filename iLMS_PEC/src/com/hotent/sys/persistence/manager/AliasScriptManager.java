package com.hotent.sys.persistence.manager;

import net.sf.json.JSONArray;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.AliasScript;
 

public interface AliasScriptManager extends Manager<String, AliasScript>{
	/**
	 * 根据脚本别名获取别名脚本
	 * @param alias
	 * @return
	 */
	public AliasScript getByAlias(String alias);
	
	/**
	 * 根据类名获取方法
	 * @param className
	 * @return
	 */
	public JSONArray getMethodsByClassName(String className) throws Exception;

}
