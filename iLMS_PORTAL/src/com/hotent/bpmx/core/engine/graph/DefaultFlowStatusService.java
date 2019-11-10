package com.hotent.bpmx.core.engine.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.bpmx.api.service.FlowStatusService;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProStatus;

public class DefaultFlowStatusService implements FlowStatusService {
	
	@Resource
	BpmProStatusManager bpmProStatusManager;
	
	private Map<String, String> statusColorMap = new HashMap<String, String>();
	
	public void setStatusColor(Map<String, String> statusColorMap) {
		this.statusColorMap = statusColorMap;
	}


	@Override
	public Map<String, String> getProcessInstanceStatus(String bpmnInstId) {
		DefaultQueryFilter filter = new DefaultQueryFilter();
		filter.addFilter("proc_inst_id_", bpmnInstId, QueryOP.EQUAL);
		filter.setPage(null);
		List<DefaultBpmProStatus> statusList = bpmProStatusManager.query(filter);
		if(BeanUtils.isEmpty(statusList)){
			statusList = bpmProStatusManager.queryHistorys(bpmnInstId);
		}
		Map<String, String> colourMap = new HashMap<String, String>();
		if(BeanUtils.isEmpty(statusList)){
			return colourMap;
		}else{
			for(DefaultBpmProStatus bpmProStatus:statusList){
				String nodeId = bpmProStatus.getNodeId();
				String status = bpmProStatus.getStatus();
				String color = statusColorMap.get(status);
				colourMap.put(nodeId, color);
			}
		}
		return colourMap;
	}

}
