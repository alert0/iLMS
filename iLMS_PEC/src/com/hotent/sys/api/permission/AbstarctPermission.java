package com.hotent.sys.api.permission;

import java.util.Map;
import java.util.Set;

import com.hotent.base.core.util.string.StringUtil;

import net.sf.json.JSONObject;


/**
 * 
 * 权限计算抽象类，默认提供一个权限计算方法。
 * 
 * @author ray
 *
 */
public abstract class AbstarctPermission implements IPermission {

	
	/**
	 * 默认计算下面的配置：
	 * json结构：
	 * {type:"user",id:"1,2",name:"ray,tom"}
	 * 或:
	 * {type:"group",id:"1,2",name:"hotent,google"}
	 * 
	 * currentMap:当前人对算法的配置。
	 */
	@Override
	public boolean hasRight(String json, Map<String, Set<String>> currentMap) {
		JSONObject jsonObj = JSONObject.fromObject(json);
		String id=jsonObj.getString("id");
		if(StringUtil.isEmpty(id)) return false;
		String [] ids = jsonObj.get("id").toString().split(",");
		Set<String> set=currentMap.get(getType());
		for(String tmp:ids){
			if(set!=null && set.contains(tmp)){
				return true;
			}
		}
		return false;
	}

	

	

}
