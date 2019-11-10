package com.hotent.bpmx.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.instance.BoUtil;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.ProcBoDef;
import com.hotent.bpmx.api.service.DataObjectHandler;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;



public class BoDataUtil {
	
	
	
	
	/**
	 * 验证BO必填。
	 * @param bpmProcessDefExt
	 * @param jsonObj 
	 * void
	 */
	public static void validBo(DefaultBpmProcessDefExt bpmProcessDefExt,JSONObject jsonObj){
		
		List<ProcBoDef> list= bpmProcessDefExt.getBoDefList();
		if(BeanUtils.isEmpty(list)){
			throw new RuntimeException("流程没有定义Bo列表");
		}
		Set<String> set=jsonObj.keySet();
		for(ProcBoDef boDef :list){
			String name = boDef.getName();
			if(!boDef.isRequired()) continue;

			if(!set.contains(name)){
				throw new RuntimeException("提交数据不包含:"+ boDef.getName());
			}
		}
	}
	
	
	/**
	 * 将bo数据按照bo定义组装成map。
	 * @param jsonObj
	 * @return  Map<String,String>
	 */
	public static  Map<String, JSONObject> getMap(JSONObject jsonObj){
		Map<String, JSONObject> map=new HashMap<String, JSONObject>();
		Iterator<Entry<String, Object>> it= jsonObj.entrySet().iterator();
		
		while(it.hasNext()){
			Entry<String, Object> ent=it.next();
			String key=ent.getKey();
			try {
				JSONObject val=(JSONObject) ent.getValue();
				map.put(key, val);
			} catch (Exception e) {}
		}
		return map;
	}
	
	
	/**
	 * 处理表单json数据。
	 * @param defId //只通过表单获取初始化数据 可以为空
	 * @param boDatas
	 * @return
	 */
	public static JSONObject hanlerData(String defId, List<BoData> boDatas){
		JSONObject jsondata = new JSONObject();
		
		DataObjectHandler dataObjectHandler=AppUtil.getBean(DataObjectHandler.class);
		if(StringUtil.isNotEmpty(defId)){
			dataObjectHandler.handShowData(defId, boDatas);
		}
		for(BoData data:boDatas){
			JSONObject json= BoUtil.toJSONObject(data,true);
			jsondata.put(data.getBoDef().getAlias(), json);
		}
		return jsondata;
	}
	
	/**
	 * 处理表单数据。
	 * @param instance
	 * @param nodeId
	 * @param boDatas
	 * @return
	 */
	public static JSONObject hanlerData(BpmProcessInstance instance,String nodeId, List<BoData> boDatas) throws Exception {
		JSONObject jsondata = new JSONObject();
		DataObjectHandler dataObjectHandler=AppUtil.getBean(DataObjectHandler.class);
		dataObjectHandler.handShowData(instance, nodeId, boDatas);
		//设置bo数据到上下文。
		BpmContextUtil.setBoToContext(boDatas);
		
		for(BoData data:boDatas){
			JSONObject json= BoUtil.toJSONObject(data,true);
			jsondata.put(data.getBoDef().getAlias(), json);
		}
		return jsondata;
	}

	/**
	 * 转成JSON数据。
	 * @param boDatas
	 * @return
	 */
	public static JSONObject hanlerData( List<BoData> boDatas){
		JSONObject jsondata = new JSONObject();
		for(BoData data:boDatas){
			
			JSONObject json= BoUtil.toJSONObject(data,true);
			jsondata.put(data.getBoDef().getAlias(), json);
		}
		return jsondata;
	}
	

	
	

}
