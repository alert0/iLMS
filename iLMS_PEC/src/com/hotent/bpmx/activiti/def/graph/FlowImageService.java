package com.hotent.bpmx.activiti.def.graph;

import java.io.InputStream;
import java.util.Map;

import org.activiti.engine.impl.interceptor.CommandExecutor;

import com.hotent.bpmx.activiti.cmd.GenFlowColorImageCmd;
import com.hotent.bpmx.activiti.cmd.GenFlowImageCmd;
import com.hotent.bpmx.activiti.util.ActivitiUtil;
import com.hotent.bpmx.natapi.graph.NatProcessImageService;

public class FlowImageService  implements NatProcessImageService{
	@Override
	public InputStream getProcessImageByBpmnXml(String bpmnXml) {
		CommandExecutor commandExecutor=ActivitiUtil.getCommandExecutor();
		InputStream inputStream= commandExecutor.execute(new GenFlowImageCmd(bpmnXml));
		return inputStream;
	}

	@Override
	public InputStream getProcessImageByBpmnXml(String bpmnXml, Map<String, String> colorMap) {
		CommandExecutor commandExecutor=ActivitiUtil.getCommandExecutor();
		InputStream inputStream= commandExecutor.execute(new GenFlowColorImageCmd(bpmnXml, colorMap));
		return inputStream;
	}
}
