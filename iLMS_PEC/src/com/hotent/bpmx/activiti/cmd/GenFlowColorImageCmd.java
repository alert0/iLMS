package com.hotent.bpmx.activiti.cmd;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import com.hotent.bpmx.activiti.def.graph.ilog.activiti.ProcessDiagramGenerator;

public class GenFlowColorImageCmd implements Command<InputStream> {

	private String bpmnDefId="";
	private String bpmnXml = "";
	private Map<String, String> colorMap = Collections.<String, String> emptyMap();
	
	public GenFlowColorImageCmd(String bpmnXml, Map<String, String> colorMap){
		this.bpmnXml=bpmnXml;
		this.colorMap = colorMap;
	}

	@Override
	public InputStream execute(CommandContext context) {
		
//		RepositoryService repositoryService=(RepositoryService) AppUtil.getBean("repositoryService");
//		
//		BpmnModel bpmnModel= repositoryService.getBpmnModel(bpmnDefId);
	    
	    InputStream inputStream = ProcessDiagramGenerator.generatePngDiagram(bpmnXml, colorMap);
		return inputStream;
	}
}
