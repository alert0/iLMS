package com.hotent.bpmx.activiti.ext.identity;

import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;

public interface INodeDef {
	
	BpmNodeDef getNodeDef(String bpmnDefId,String nodeId);

}
