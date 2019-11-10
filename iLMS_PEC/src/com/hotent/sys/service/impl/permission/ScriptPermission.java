package com.hotent.sys.service.impl.permission;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.sys.api.permission.IPermission;
import com.hotent.sys.util.ContextUtil;

/**
 * 脚本权限计算。
 * @author ray
 *
 */
public class ScriptPermission implements IPermission {
	
	@Resource
	GroovyScriptEngine groovyScriptEngine;

	@Override
	public String getTitle() {
		return "脚本";
	}

	@Override
	public String getType() {
		return "script";
	}

	@Override
	public boolean hasRight(String json, Map<String, Set<String>> currentMap) {
		JSONObject jsonObj=JSONObject.fromObject(json);
		String script=jsonObj.getString("id");
		Set<String> set= (Set<String>) groovyScriptEngine.executeObject(script, null);
		if(BeanUtils.isEmpty(set)) return false;
		
		String userId=ContextUtil.getCurrentUserId();
		if(set.contains(userId)) return true;
	
		return false;
	}

	@Override
	public Set<String> getCurrentProfile() {
		return null;
	}

}
