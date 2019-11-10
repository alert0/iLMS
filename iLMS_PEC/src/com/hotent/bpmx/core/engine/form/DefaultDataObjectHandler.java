package com.hotent.bpmx.core.engine.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.bodef.BoDefService;
import com.hotent.bo.api.constant.BodefConstants;
import com.hotent.bo.api.instance.BoDataHandler;
import com.hotent.bo.api.instance.BoInstanceFactory;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.FieldInitSetting;
import com.hotent.bpmx.api.model.process.def.FormInitItem;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.ProcBoDef;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.api.service.DataObjectHandler;
import com.hotent.bpmx.core.engine.def.BpmDefUtil;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;

/**
 * 负责根据配置修改表单数据。
 * <pre> 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014年8月24日-下午4:01:32
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class DefaultDataObjectHandler implements DataObjectHandler {
	
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BoDataService boDataService;
	@Resource
	BoDefService boDefService;
	@Resource
	BoInstanceFactory boInstanceFactory;
	@Resource
	GroovyScriptEngine groovyScriptEngine;

	@Override
	public void handShowData(String defId,  List<BoData> boDatas) {
		FormInitItem formInitItem= getFormInitItem( defId);
		List<FieldInitSetting> fieldInitSettings= getFieldSetting(formInitItem,true);
		if(fieldInitSettings==null) return;
		
		setDataObject(fieldInitSettings,boDatas);
	}

	@Override
	public void handSaveData(BpmProcessInstance instance, List<BoData> boDatas) {
		FormInitItem formInitItem= getFormInitItem( instance.getProcDefId());
		List<FieldInitSetting> fieldInitSettings= getFieldSetting(formInitItem,false);
		if(fieldInitSettings==null) return;
		
		addUnUseBoData(instance,boDatas);
		setDataObject(fieldInitSettings,boDatas);
	}

	@Override
	public void handSaveData(BpmProcessInstance instance, String nodeId,List<BoData> boDatas) {
		//获取节点修改配置。
		FormInitItem formInitItem=getFormInitItem(instance, nodeId);
		List<FieldInitSetting> fieldInitSettings= getFieldSetting(formInitItem,false);
		if(fieldInitSettings==null) return;
		
		addUnUseBoData(instance,boDatas);
		setDataObject(fieldInitSettings,boDatas);
	}
	
	/**
	 * 添加未使用的BO对象
	 * @param instance
	 * @param boDatas
	 */
	private void addUnUseBoData(BpmProcessInstance instance,List<BoData> boDatas) {
		DefaultBpmProcessDefExt defExt=BpmDefUtil.getProcessExt(instance);
		String saveMode =  defExt.isBoSaveToDb() ? BodefConstants.SAVE_MODE_DB : BodefConstants.SAVE_MODE_BOOBJECT;
		BoDataHandler handler= boInstanceFactory.getBySaveType(saveMode);
		List<ProcBoDef> boList= defExt.getBoDefList();
		List<String> boDefCodes = new ArrayList<String>();
		for (ProcBoDef procBoDef : boList) {
			boDefCodes.add(procBoDef.getKey());
		}
		JSONObject jsonObject = BoDataUtil.hanlerData(boDatas);
		for (String boDefCode : boDefCodes) {
			if(!jsonObject.containsKey(boDefCode)){
				BoData boData= handler.getByBoDefCode(boDefCode);
				BaseBoDef boDef = boDefService.getByName(boDefCode);
				BaseBoEnt boEnt = (BaseBoEnt) boDef.getBoEnt();
				boData.setBoEnt(boEnt);
				boDatas.add(boData);
			}
		}
	}
	
	/**
	 * 修改BO数据。
	 * 
	 * @param fieldInitSettings
	 * @param dataObject 
	 * void
	 */
	private void setDataObject(List<FieldInitSetting> fieldInitSettings, List<BoData> boDatas){
		Map<String, Object> vars=new HashMap<String, Object>();
		
		for(BoData boData:boDatas){
			String boDefCode = boData.getBoDef().getAlias();
			vars.put(boDefCode, boData);
		}
		ActionCmd cmd = ContextThreadUtil.getActionCmd();
		if(BeanUtils.isNotEmpty(cmd)){
			vars.putAll(cmd.getVariables());
		}
		
		for(FieldInitSetting setting:fieldInitSettings){
			String script = setting.getSetting();
			if(StringUtil.isEmpty(script)) continue;
			groovyScriptEngine.execute(script, vars);
		}
		if(BeanUtils.isNotEmpty(cmd)){
			cmd.setBusData(BoDataUtil.hanlerData(boDatas).toString());
		}
	}
	
	

	@Override
	public void handShowData(BpmProcessInstance instance, String nodeId,List<BoData> boDatas) {
		FormInitItem formInitItem=getFormInitItem(instance, nodeId);
		List<FieldInitSetting> fieldInitSettings= getFieldSetting(formInitItem,true);
		if(fieldInitSettings==null) return;
		
		setDataObject(fieldInitSettings,boDatas);
		
	}
	
	private FormInitItem getFormInitItem(BpmProcessInstance instance, String nodeId){
		FormInitItem formInitItem=null;
		BpmNodeDef bpmNodeDef= bpmDefinitionAccessor.getBpmNodeDef(instance.getProcDefId(), nodeId);
		if(StringUtil.isNotZeroEmpty(instance.getParentInstId())){
			BpmProcessInstance topInstance= bpmProcessInstanceManager.getTopBpmProcessInstance(instance);
			String defKey=topInstance.getProcDefKey();
			formInitItem=bpmNodeDef.getFormInitItemByParentKey(defKey);
			if(formInitItem!=null) return formInitItem;
		}
		return bpmNodeDef.getFormInitItem();
	}
	
	private FormInitItem getFormInitItem(String defId){
		BpmNodeDef bpmNodeDef= bpmDefinitionAccessor.getStartEvent(defId);
		return bpmNodeDef.getFormInitItem();
	}

	
	private List<FieldInitSetting> getFieldSetting(FormInitItem formInitItem,boolean isShow){
		if(formInitItem==null) return null;
		if(isShow){
			return formInitItem.getShowFieldsSetting();
		}
		else{
			return formInitItem.getSaveFieldsSetting();
		}
	}

	
}
