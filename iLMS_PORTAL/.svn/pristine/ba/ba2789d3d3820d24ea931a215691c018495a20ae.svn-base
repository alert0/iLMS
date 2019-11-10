package com.hotent.bpmx.core.engine.inst;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.hotent.bpmx.api.model.process.task.BpmTaskOpinion;
import com.hotent.bpmx.api.service.BpmOpinionService;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;

public class DefaultBpmOpinionService implements BpmOpinionService {
	
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;  

	@Override
	public List<BpmTaskOpinion> getTaskOpinions(String procInstId) {
		List<DefaultBpmCheckOpinion> defaultBpmCheckOpinions = bpmCheckOpinionManager.getByInstId(procInstId);
		
		return convert(defaultBpmCheckOpinions);
	}
	
	private List<BpmTaskOpinion> convert(List<DefaultBpmCheckOpinion> list){
		List<BpmTaskOpinion> rtnList=new ArrayList<BpmTaskOpinion>();
		for(DefaultBpmCheckOpinion opinion:list){
			rtnList.add(opinion);
		}
		return rtnList;
	}

	@Override
	public List<BpmTaskOpinion> getByInstNodeId(String instId, String nodeId) {
		return (List)bpmCheckOpinionManager.getByInstNodeId(instId, nodeId);
	}

}
