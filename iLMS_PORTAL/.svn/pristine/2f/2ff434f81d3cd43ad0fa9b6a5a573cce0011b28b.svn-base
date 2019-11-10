package com.hotent.sys.api.permission;

import java.util.Map;
import java.util.Set;

/**
 * 权限接口。
 * @author ray
 *
 */
public interface IPermission {
	
	/**
	 * 权限规则名称。
	 * @return
	 */
	String getTitle();
	
	/**
	 * 权限类型。
	 * @return
	 */
	String getType();
	
	/**
	 * 根据规则判断是否有权限。
	 * 
	 * @param json 
	 *  格式如下：
	 *  {"type":"user","id":"1,2","name":"ray,tom"}
	 * @param currentMap
	 * 	currentMap 键代表算法，Set<String> 表示当前算法的配置数据。
	 * @return
	 */
	boolean hasRight(String json,Map<String, Set<String>> currentMap);
	
	/**
	 * 获取当前人的配置。
	 * @return
	 */
	Set<String> getCurrentProfile();
}
