package com.hotent.sys.persistence.manager;

import net.sf.json.JSONArray;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.PersonScript;
 

public interface PersonScriptManager extends Manager<String, PersonScript> {

	/**
	 * 根据类名获取方法
	 * @param className	：类名
	 * @param personScript :若是编辑模式则传进来编辑对象（因为要初始化方法的描叙），若是新增模式，则为null
	 * @return
	 */
	public JSONArray getMethodsByClassName(String className, PersonScript personScript) throws Exception;

}
