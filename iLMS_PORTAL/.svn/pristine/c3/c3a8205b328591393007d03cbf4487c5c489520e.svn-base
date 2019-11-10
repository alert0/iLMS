package com.hotent.sys.api.permission;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.AppUtil;

/**
 * 权限工具类。
 * @author ray
 *
 */
public class PermissionUtil {
	
	/**
	 * 根据beanId 实例键 x5-sys-core.xml 的formPermissionCalcList。
	 * 返回格式：
	 * {"everyone":"所有人"},{"none":"无"},{type:"user",id:"",name:""}
	 * @param beanId
	 * @return
	 */
	public static JSONObject getPermissionList(String beanId){
		List<IPermission> list=(List<IPermission>) AppUtil.getBean(beanId);
		JSONObject rtnJson=new JSONObject();
		for(IPermission permission:list){
			rtnJson.put(permission.getType(), permission.getTitle());
		}
		return rtnJson;
	}

}
