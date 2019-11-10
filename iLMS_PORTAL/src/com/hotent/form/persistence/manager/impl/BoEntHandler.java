package com.hotent.form.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.db.model.Column;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bo.api.bodef.IBoEntHandler;
import com.hotent.bo.api.model.BaseAttribute;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.form.persistence.dao.BpmFormDefDao;
import com.hotent.form.persistence.dao.BpmFormFieldDao;
import com.hotent.form.persistence.model.BpmFormDef;
import com.hotent.form.persistence.model.BpmFormField;

@Service
public class BoEntHandler implements IBoEntHandler {
	
	@Resource
	BpmFormDefDao bpmFormDefDao;
	@Resource
	BpmFormFieldDao bpmFormFieldDao;

	@Override
	public void handlerEntChange(BaseBoEnt boEnt, List removeList, List addList) {
		String entId=boEnt.getId();
		List<BpmFormDef> defList= bpmFormDefDao.getByEntId(entId); 
		
		//修改字段expand
		for(BpmFormDef formDef:defList){
			handFormDef( formDef, removeList,  addList, boEnt);
		}
		//删除字段
		for(Object obj:removeList){
			BaseAttribute attr=(BaseAttribute)obj;
			String attrId=attr.getId();
			bpmFormFieldDao.removeByAttrId(attrId);
		}
		
	}
	
	private void getUpdFields(JSONObject tbJson,List removeList, List addList){
		JSONArray jsonAry=tbJson.getJSONArray("children");
		//删除字段
		for(Object obj:removeList){
			BaseAttribute attr=(BaseAttribute)obj;
			for(Object tmp:jsonAry ){
				JSONObject jsonObj=(JSONObject)tmp;
				if(jsonObj.getString("name").equals(attr.getName())){
					jsonAry.remove(jsonObj);
					break;
				}
			}
		}
		//添加字段
		for(Object obj:addList){
			BaseAttribute attr=(BaseAttribute)obj;
			JSONObject jsonAdd=getByAttr( tbJson, attr);
			jsonAry.add(jsonAdd);
		}
	}
	
	

	private JSONObject getByAttr(JSONObject tbJson,BaseAttribute attr){
		String relation=tbJson.getString("relation");
		boolean isMain="main".equals(relation);
		String path=  tbJson.getString("path");
		
		
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("name", attr.getName());
		jsonObj.put("desc", attr.getDesc());
		jsonObj.put("path", path);
		jsonObj.put("fieldType", "field");
		jsonObj.put("type", attr.getDataType());
		jsonObj.put("boAttrId", attr.getId());
		if(isMain){
			String boDefId=  tbJson.getString("boDefId");
			jsonObj.put("boDefId", boDefId);
		}
		jsonObj.put("entId", tbJson.getString("entId"));
		
		JSONObject rule= getRule(attr);
		
		jsonObj.put("validRule", rule);
		
		//ctrlType
		String ctrl= getByCtrl(attr);
		jsonObj.put("ctrlType", ctrl);
		
		return jsonObj;
	}
	/**
	 * 根据属性返回jsonobject。
	 * @param attr
	 * @return
	 */
	private String getByCtrl(BaseAttribute attr) {
		String str="onetext";
		if(Column.COLUMN_TYPE_VARCHAR.equals(attr.getDataType())){
			str="onetext";
		}
		if(Column.COLUMN_TYPE_DATE .equals(attr.getDataType())){
			str="date";
		}
		if(Column.COLUMN_TYPE_CLOB .equals(attr.getDataType())){
			str="multitext";
		}
		return str;
	}
	
	private JSONObject getRule(BaseAttribute attr){
		JSONObject jsonObj=new JSONObject();
	
		if(Column.COLUMN_TYPE_NUMBER.equals(attr.getDataType())){
			JSONArray jsonAry=new JSONArray();
			JSONObject obj=new JSONObject();
			obj.put("name", "数字");
			obj.put("text", "number");
			jsonAry.add(obj);
			jsonObj.put("rules", jsonAry);
		}
		
		if(Column.COLUMN_TYPE_DATE.equals(attr.getDataType())){
			JSONArray jsonAry=new JSONArray();
			JSONObject obj=new JSONObject();
			obj.put("name", "日期");
			obj.put("text", "date");
			jsonAry.add(obj);
			jsonObj.put("rules", jsonAry);
			return jsonObj;
		}
		return jsonObj;
	}
	
	private JSONObject getTableJson(JSONObject jsonObj,String entId){
		JSONArray jsonAry=jsonObj.getJSONArray("fields") ;
		for(Object obj:jsonAry){
			JSONObject tbJson=(JSONObject)obj;
			String boEntId=tbJson.getString("entId");
			if(entId.equals(boEntId)){
				return tbJson;
			}
		}
		return null;
	}
	
	void handFormDef(BpmFormDef formDef,List removeList, List addList,BaseBoEnt boEnt){
		String entId=boEnt.getId();
		String expand=formDef.getExpand();
		JSONObject jsonObj=JSONObject.parseObject(expand);
		JSONObject tableJson= getTableJson(jsonObj, entId);
		if(tableJson==null) return;
		
		getUpdFields( tableJson,removeList, addList);
		
		formDef.setExpand(jsonObj.toJSONString());
		
		bpmFormDefDao.update(formDef);
		//处理bpm_form_field
		handFields(formDef.getId() , entId, jsonObj, addList);
	}
	
	void handFields(String formDefId,String entId,JSONObject jsonObj,List addList){
		String boDefId="";
		JSONArray jsonAry=jsonObj.getJSONArray("fields") ;
		for(Object obj:jsonAry){
			JSONObject tbJson=(JSONObject)obj;
			if("main".equals( tbJson.getString("type"))){
				boDefId=tbJson.getString("boDefId");
			}
		}
		
		for(Object obj:addList){
			BaseAttribute attr=(BaseAttribute)obj;
			BpmFormField field=new BpmFormField();
			field.setId(UniqueIdUtil.getSuid());
			field.setName(attr.getFieldName());
			field.setDesc(attr.getDesc());
			field.setFormId(formDefId);
			field.setBoDefId(boDefId);
			field.setEntId(entId);
			field.setType(attr.getDataType());
			field.setBoAttrId(attr.getId());
			
			String ctrl= getByCtrl( attr);
			field.setCtrlType(ctrl);
			JSONObject ruleJson= getRule(  attr);
			field.setValidRule(ruleJson.toJSONString());
			field.setOption("{}");
			bpmFormFieldDao.create(field);
		}
	}
}
