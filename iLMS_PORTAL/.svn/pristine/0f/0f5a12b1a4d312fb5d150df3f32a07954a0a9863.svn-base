package com.hotent.form.persistence.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.bodef.BoDefService;
import com.hotent.bo.api.constant.BodefConstants;
import com.hotent.bo.api.instance.BoDataHandler;
import com.hotent.bo.api.instance.BoInstanceFactory;
import com.hotent.bo.api.instance.DataTransform;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;
import com.hotent.bo.api.model.BoResult;
import com.hotent.form.persistence.manager.BpmFormDefManager;
import com.hotent.form.persistence.manager.BpmFormManager;
import com.hotent.form.persistence.manager.FormBusManager;
import com.hotent.form.persistence.manager.FormBusSetManager;
import com.hotent.form.persistence.model.BpmForm;
import com.hotent.form.persistence.model.BpmFormDef;
import com.hotent.form.persistence.model.FormBusSet;

/**
 * 
 * <pre> 
 * 描述：form_bus_set 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-08-23 13:54:13
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("formBusManager")
public class FormBusManagerImpl implements FormBusManager{
	@Resource
	BoInstanceFactory boInstanceFactory;
	@Resource
	BoDefService boDefService;
	@Resource
	DataTransform dataTransform;
	@Resource
	FormBusSetManager formBusSetManager;
	@Resource
	BpmFormManager bpmFormManager;
	@Resource
	BpmFormDefManager bpmFormdefManager;
	@Resource
	GroovyScriptEngine groovyScriptEngine;

	@Override
	public BoData getBoData(String boKey, String id) {
		BoDataHandler boDataHandler= boInstanceFactory.getBySaveType(BodefConstants.SAVE_MODE_DB);
		
		if(StringUtil.isNotEmpty(id)){
			return boDataHandler.getById(id, boKey);
		}
		
		return boDataHandler.getByBoDefCode(boKey);
	}

	@Override
	public void saveData(String formKey, String json) {
		BoDataHandler boDataHandler= boInstanceFactory.getBySaveType(BodefConstants.SAVE_MODE_DB);
		String boCode  =getBoCodeByForm(formKey);
		
		BaseBoDef boDef= boDefService.getByName(boCode);
		BaseBoEnt boEnt=boDef.getBoEnt();
		
		BoData curData= dataTransform.parse(json);
		curData.setBoDef(boDef);
		curData.setBoEnt(boEnt);
		
		FormBusSet busSet = formBusSetManager.getByFormKey(formKey);
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("boData", curData);
		
		//前置脚本
		if(StringUtil.isNotEmpty(busSet.getPreScript())){
			groovyScriptEngine.execute(busSet.getPreScript(), param);
		}
		// 保存
		List<BoResult> listResult = boDataHandler.save("", "", curData);
		//后置脚本
		if(StringUtil.isNotEmpty(busSet.getAfterScript())){
			groovyScriptEngine.execute(busSet.getAfterScript(), param);
		}
		
		
		
		if(BeanUtils.isNotEmpty(listResult)){
			if("add".equals(listResult.get(0).getAction())){
				ThreadMsgUtil.addMsg("添加成功！");
			}else{
				ThreadMsgUtil.addMsg("编辑成功！");
			}
		}
		
		
		
	}
	

	@Override
	public void removeByIds(String[] aryIds, String formKey) {
		BoDataHandler boDataHandler= boInstanceFactory.getBySaveType(BodefConstants.SAVE_MODE_DB);
		String boCode = getBoCodeByForm(formKey);
		
		boDataHandler.removeBoData(boCode,aryIds);
	}
	
	
	
	private String getBoCodeByForm(String formKey){
		BpmForm form = bpmFormManager.getMainByFormKey(formKey);
		BpmFormDef formDef= bpmFormdefManager.get(form.getDefId());
		List<String> boCode = bpmFormdefManager.getBOCodeByFormId(formDef.getId());
		
		if(boCode.size()!=1) throw new RuntimeException(formKey+"表单所对应的BO数据不支持改操作！");
		return boCode.get(0);
	}

	@Override
	public JSONArray getList(String formKey, Map<String, Object> param) {
		String boCode =  getBoCodeByForm(formKey);
		BoDataHandler boDataHandler= boInstanceFactory.getBySaveType(BodefConstants.SAVE_MODE_DB);
		
		List<Map<String, Object>> list =boDataHandler.getList(boCode,param);
		JSONArray jsonArray = (JSONArray) JSON.toJSON(list);
		return jsonArray;
	}

}
