package com.hotent.form.service;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.hotent.form.api.service.BpmFormRightsService;
import com.hotent.form.persistence.manager.BpmFormRightManager;



public class DefaultBpmFormRightsService implements BpmFormRightsService{
	@Resource
	BpmFormRightManager bpmFormRightManager;
	
	public String getPermission(String formKey, String userId, String flowKey,String parentFlowKey, String nodeId) {
		JSONObject permission = bpmFormRightManager.getPermission(formKey, flowKey, parentFlowKey, nodeId, 1);
		return permission.toString();
	}

	
	@Override
	public String getInstPermission(String formKey, String userId,String flowKey) {
		JSONObject permission  = bpmFormRightManager.getPermission(formKey, flowKey, "", "", 2);
		return permission.toString();
	}

	
	@Override
	public String getStartPermission(String formKey, String flowKey, String nodeId, String nextNodeId) {
		JSONObject permission  = bpmFormRightManager.getStartPermission(formKey,flowKey,nodeId,nextNodeId);
		return permission.toString();
	}
}
