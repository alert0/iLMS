package com.hotent.bpmx.api.service;

import java.util.Map;

/**
 * 流程实例的状态接口
 * @author helh
 *
 */
public interface FlowStatusService {
	/**
	 * 根据流程实例ID获取流程实例的节点状态及其颜色
	 * @param bpmnInstId
	 * @return
	 */
	public Map<String, String> getProcessInstanceStatus(String bpmnInstId);
}
