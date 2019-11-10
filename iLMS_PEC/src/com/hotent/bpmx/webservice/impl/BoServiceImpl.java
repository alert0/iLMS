package com.hotent.bpmx.webservice.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.FastJsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.bodef.BoDefService;
import com.hotent.bo.api.instance.DataTransform;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.ProcBoDef;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.core.engine.def.BpmDefUtil;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.bpmx.listener.BusDataUtil;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.webservice.api.IBoService;
import com.hotent.restful.params.CommonResult;

@Service("IBoService")
public class BoServiceImpl implements IBoService {
	
	@Resource
	BoDataService boDataService;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	
	
	/**
	 * 获取bo数据
	 * @throws Exception 
	 */
	@Override
	public CommonResult<JSONObject> getBoData(String proInstId,String defId,String nodeId) throws Exception {
		if(StringUtil.isEmpty(defId) && StringUtil.isEmpty(proInstId) ){
			return new CommonResult<JSONObject>(false, "流程定义id,  实例id 至少传递一个", new JSONObject());
		}
		JSONObject jsondata = null;
		if(StringUtil.isNotEmpty(proInstId)){
			DefaultBpmProcessInstance instance = bpmProcessInstanceManager.get(proInstId);
			List<BoData> boDatas =boDataService.getDataByInst(instance);
			if(StringUtil.isEmpty(nodeId)){
				jsondata=BoDataUtil.hanlerData(defId,boDatas);
			}else{
				jsondata =BoDataUtil.hanlerData(instance,nodeId, boDatas);
			}
		}else{
			List<BoData> boDatas = boDataService.getDataByDefId(defId);
			jsondata=BoDataUtil.hanlerData(defId,boDatas);
		}
		return new CommonResult<JSONObject>(true, "获取bo数据成功", jsondata);
	}


	@Override
	public CommonResult<String> updataBoData(String proInstId,String boJson) throws RuntimeException {
		DefaultBpmProcessInstance instance = bpmProcessInstanceManager.get(proInstId);
		DataTransform dataTransform=AppUtil.getBean(DataTransform.class);
		BoDefService boDefService=AppUtil.getBean(BoDefService.class);
		DefaultBpmProcessDefExt bpmProcessDefExt = BpmDefUtil.getProcessExt(instance);
		JSONObject jsonObj = JSONObject.parseObject(boJson);
		// 验证BO数据。
		BoDataUtil.validBo(bpmProcessDefExt, jsonObj);
		// BO Map数据。
		Map<String, JSONObject> jsonMap = BoDataUtil.getMap(jsonObj);
		List<ProcBoDef> list = bpmProcessDefExt.getBoDefList();
		List<BoData> boDatas=new ArrayList<BoData>();
		
		for (ProcBoDef boDef : list) {
			String key = boDef.getKey();
			if(!jsonMap.containsKey(key)) continue;
			JSONObject json = jsonMap.get(key);
			
			BaseBoDef def= boDefService.getByName(key);
			BaseBoEnt boEnt=def.getBoEnt();
			
			//BO数据。
			BoData curData= dataTransform.parse(json.toJSONString());
			curData.setBoDef(def);
			curData.setBoEnt(boEnt);
			
			boDatas.add(curData);
		}
		
		for (BoData boData : boDatas) {
			BusDataUtil.updateBoData(proInstId, boData);
		}
		// 将BO放入cmd上下文中。
		return new CommonResult<String>(true, "更新数据成功", "");
	}


}
