package com.hotent.bpmx.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskCandidateDao;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskCandidate;

@Repository
public class BpmTaskCandidateDaoImpl extends MyBatisDaoImpl<String,DefaultBpmTaskCandidate> implements BpmTaskCandidateDao{

    @Override
    public String getNamespace() {
        return DefaultBpmTaskCandidate.class.getName();
    }

	@Override
	public void removeByTaskId(String taskId) {
		this.deleteByKey("removeByTaskId", taskId);
	}

	@Override
	public List<DefaultBpmTaskCandidate> queryByTaskId(String taskId) {
		return getByKey("queryByTaskId", buildMapBuilder().addParam("taskId",taskId).getParams());
	}
	
	@Override
	public DefaultBpmTaskCandidate getByTaskIdExeIdType(String taskId,
			String executorId, String type) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("taskId", taskId);
		params.put("executorId", executorId);
		params.put("type", type);
		return (DefaultBpmTaskCandidate)this.getOne("getByTaskIdExeIdType", params);
	}

	
	@Override
	public void delByInstList(List<String> instList) {
		this.deleteByKey("delByInstList", instList);
	}

	@Override
	public List<DefaultBpmTaskCandidate> getByInstList(List<String> instList) {
		
		return this.getByKey("getByInstList",instList);
	}
	
	
	
}

