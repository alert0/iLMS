package com.hotent.bo.api.instance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.bo.api.model.BaseAttribute;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;

/**
 * BO工具类。
 * @author ray
 *
 */
public class BoUtil {
	
	/**
	 * 将JSON转成BoData。
	 * @param json
	 * @return
	 */
	public static BoData transJSON(JSONObject json) {
		BoData data = new BoData();
		for (Object key : json.keySet()) {
			Object val = json.get(key);
			if (val instanceof JSONArray) {// 是子表
				for (int i = 0; i < ((JSONArray) val).size(); i++) {
					JSONObject jo = ((JSONArray) val).getJSONObject(i);
					String tmp=key.toString().replaceFirst("sub_", "");
					data.addSubRow(tmp, transJSON(jo));
				}
			} else {
				data.set(key.toString(), val);
			}
		}
		return data;
	}
	
	/**
	 * 将BoData对象转换成JSON对象。
	 * @param boData
	 * @return
	 */
	public static JSONObject toJSONObject(BoData boData,boolean needInitData) {
		Map<String,Map<String,Object>> initMap  =new HashMap<String, Map<String,Object>>();
		
		JSONObject json= toJSONObject(boData,initMap);
		
		if(needInitData){
			JSONObject jsonInit=new JSONObject();
			
			for (Map.Entry<String,Map<String,Object>> entry : initMap.entrySet()) {
				String key=entry.getKey();
				JSONObject rowJson=new JSONObject();
				
				for (Map.Entry<String,Object> row : entry.getValue().entrySet()){
					rowJson.put(row.getKey(), row.getValue());
				}
				jsonInit.put(key, rowJson);
			}
			
			json.put("initData", jsonInit);
		}
		
		
		
		return json;
	} 
	
	/**
	 * 将bo数据转换成json数据格式对象。
	 * @param boData
	 * @param initMap
	 * @return
	 */
	private static JSONObject toJSONObject(BoData boData,Map<String,Map<String,Object>> initMap) {
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<String, Object> entry : boData.getData().entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}
		
		Map<String, List<BoData>> subMap= boData.getSubMap();

		for (Map.Entry<String, List<BoData>> ent : subMap.entrySet()) {
			JSONArray ary = new JSONArray();
			for (BoData obj : ent.getValue()) {
				ary.add(toJSONObject(obj,initMap));
			}
			jsonObject.put("sub_" +ent.getKey(), ary);
		}
		initMap.putAll(boData.getInitDataMap());
		
		return jsonObject;
	}
	
	

	
	public static void main(String[] args) {
		BaseBoEnt ent=new BaseBoEnt();
		ent.setName("order");
		ent.setDesc("订单");
		
		BaseAttribute attr1=new BaseAttribute();
		attr1.setName("customer");
		attr1.setDesc("客户");
		
		BaseAttribute attr2=new BaseAttribute();
		attr2.setName("createtime");
		attr2.setDesc("日期");
		
		ent.addAttr(attr1);
		ent.addAttr(attr2);
		
		
		BaseBoEnt ent2=new BaseBoEnt();
		
		ent2.setName("orderItem");
		ent2.setDesc("订单项目");
		
		BaseAttribute attr3=new BaseAttribute();
		attr3.setName("customer");
		attr3.setDesc("客户");
		
		BaseAttribute attr4=new BaseAttribute();
		attr4.setName("createtime");
		attr4.setDesc("日期");
		
		ent2.addAttr(attr3);
		ent2.addAttr(attr4);
		
		ent.addEnt(ent2);
		
		JSONObject json=(JSONObject) JSONObject.toJSON(ent);
		
		System.out.println(json.toJSONString());
		
	}
	
	

}
