package com.hotent.bpmx.api.model.process.def;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.BpmConstants;

public class BpmFormInit {
	
	private String parentDefKey="";
	
	
	/**
	 * 表单初始化项。
	 */
	private List<FormInitItem> formInitItems=new ArrayList<FormInitItem>();
	
	
	@JSONField(serialize=false)
	public String getParentDefKey() {
		if(StringUtil.isEmpty(parentDefKey)){
			return BpmConstants.LOCAL;
		}
		return parentDefKey;
	}

	public void setParentDefKey(String parentDefKey) {
		this.parentDefKey = parentDefKey;
	}

	public List<FormInitItem> getFormInitItems() {
		return formInitItems;
	}
	
	/**
	 * 表单数据初始化Map。
	 * @return 
	 * Map<String,FormInitItem>
	 */
	@JSONField(serialize=false)
	public Map<String,FormInitItem> getFormInitItemMap(){
		Map<String,FormInitItem> map=new HashMap<String, FormInitItem>();
		for(FormInitItem item:formInitItems){
			map.put(item.getNodeId(),item);
		}
		return map;
	}
	
	public void setFormInitItems(List<FormInitItem> formInitItems) {
		if(StringUtil.isNotEmpty(this.parentDefKey)){
			for(FormInitItem prop:this.formInitItems){
				prop.setParentDefKey(parentDefKey);
			}
		}
		this.formInitItems = formInitItems;
	}
	
	public BpmFormInit addFormInitItem(FormInitItem item) {
		formInitItems.add(item);
		return this;
	}
	
	public BpmFormInit addFormInitItems(List<FormInitItem> items) {
		formInitItems.addAll(items);
		return this;
	}
	
	


	
	public static void main(String[] args) {
		FormInitItem item=new FormInitItem();
		item.setNodeId("userTask1");
		item.setParentDefKey("qingjia");
		
		FieldInitSetting setting1=new FieldInitSetting();
		setting1.setDescription("描述");
		setting1.setSetting("return \"1\";");
		
		FieldInitSetting setting2=new FieldInitSetting();
		
		setting2.setDescription("描述");
		setting2.setSetting("return \"1\";");
		
		item.addSaveSetting(setting1);
		item.addShowFieldsSetting(setting2);
		
		
		FormInitItem item1=new FormInitItem();
		item1.setNodeId("userTask1");
		item1.setParentDefKey("local");
		
		FieldInitSetting setting3=new FieldInitSetting();
	
		setting3.setDescription("描述");
		setting3.setSetting("return \"1\";");
		
		FieldInitSetting setting4=new FieldInitSetting();
		setting4.setDescription("描述");
		setting4.setSetting("return \"1\";");
		
		item1.addSaveSetting(setting3);
		item1.addShowFieldsSetting(setting4);
		
		BpmFormInit init=new BpmFormInit();
		//init.addFormInitItem(item).addFormInitItem(item1);
		
		JSONObject json= (JSONObject) JSONObject.toJSON(init);
		
		
	 	
//		String json1=json.toString();
//		
//		BpmFormInit it=JSONObject.parseObject(json1, BpmFormInit.class);
		
		System.out.println(json);
	}

}
