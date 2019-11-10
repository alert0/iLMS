package com.hotent.restful.params;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 获取流程图（状态）参数
 * @author liangqf
 *
 */
@ApiModel
public class BpmImageParamObject {
	
	@ApiModelProperty(name="defId",notes="流程定义id")
	private String defId;
	
	@ApiModelProperty(name="bpmnInstId",notes="BPMN流程实例ID")
	private String bpmnInstId;
	
	@ApiModelProperty(name="procInstId",notes="流程实例id")
	private String procInstId;
	
	@ApiModelProperty(name="taskId",notes="任务id")
	private String taskId;

	public String getDefId() {
		return defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public String getBpmnInstId() {
		return bpmnInstId;
	}

	public void setBpmnInstId(String bpmnInstId) {
		this.bpmnInstId = bpmnInstId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
}
