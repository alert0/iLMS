package com.hotent.sys.persistence.manager;

import net.sf.json.JSONArray;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.ConditionScript;
 

public interface ConditionScriptManager extends Manager<String, ConditionScript> {

	/**
	 * 根据类名获取方法
	 * @param className
	 * @param conditionScript	:初始化对象
	 * @param type:1条件脚本，2人员脚本
	 * @return
	 */
	public JSONArray getMethodsByClassName(String className, ConditionScript conditionScript,Integer type) throws Exception;

}
