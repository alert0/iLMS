package com.hotent.bpmx.activiti.cmd;

import java.io.InputStream;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import com.hotent.bpmx.activiti.def.graph.ilog.activiti.ProcessDiagramGenerator;

public class GenFlowImageCmd implements Command<InputStream> {
	
	
	private String bpmnDefId="";
	private String bpmnXml = "";
	
	
	public GenFlowImageCmd(String bpmnXml){
		this.bpmnXml=bpmnXml;
	}

	@Override
	public InputStream execute(CommandContext context) {
		
//		RepositoryService repositoryService=(RepositoryService) AppUtil.getBean("repositoryService");
//		
//		
//		BpmnModel bpmnModel= repositoryService.getBpmnModel(bpmnDefId);
	    
	    InputStream inputStream = ProcessDiagramGenerator.generatePngDiagram(bpmnXml);
		return inputStream;
	}

}
