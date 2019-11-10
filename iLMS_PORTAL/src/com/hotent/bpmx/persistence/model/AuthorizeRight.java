package com.hotent.bpmx.persistence.model;


import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;





/**
 * 对象功能:流程授权
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 09:00:53
 */
public class AuthorizeRight {
	
	/**
	 * 获取创建人的权限。
	 * @return
	 */
	public static JSONObject getCreateRight(){
		String json="{\"m_edit\":true,\"m_del\":true,\"m_start\":false,\"m_set\":true, \"m_clean\":true, \"i_del\":false,\"i_log\":false}";
		JSONObject oldObj= JSONObject.parseObject(json);
		return oldObj;
	}
	
	/**
	 * 获取超管的默认权限。
	 * @return
	 */
	public static JSONObject getAdminRight(){
		String json="{\"m_edit\":true,\"m_del\":true,\"m_start\":true,\"m_set\":true, \"m_clean\":true, \"i_del\":true,\"i_log\":true}";
		JSONObject oldObj= JSONObject.parseObject(json);
		return oldObj;
	}
	
	/**
	 * 权限进行合并。
	 * @param oldJson
	 * @param newJson
	 * @return
	 */
	public static JSONObject mergeJson(JSONObject oldObj,String newJson){
//		JSONObject oldObj= JSONObject.parseObject(oldJson);
		JSONObject newObj= JSONObject.parseObject(newJson);
		Set<Entry<String, Object>> newSet= newObj.entrySet();
		for(Iterator<Entry<String, Object>> it=newSet.iterator();it.hasNext();){
			Entry<String, Object> ent=it.next();
			String key=ent.getKey();
			boolean val= (Boolean) ent.getValue();
			if(val){
				oldObj.put(key, val);
			}
		}
		return oldObj;
	}

}