package com.hotent.bpmx.api.service;

import java.util.List;

import com.hotent.bpmx.api.model.identity.BpmIdentity;

public interface BpmSignDataService {
	
	/**
	 * 获取会签历史审批人。
	 * @param executeId
	 * @param nodeId
	 * @return
	 */
	List<BpmIdentity> getHistoryAuditor(String executeId,String nodeId);

}
