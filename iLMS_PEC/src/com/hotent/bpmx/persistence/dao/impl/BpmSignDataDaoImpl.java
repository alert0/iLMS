package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmSignDataDao;
import com.hotent.bpmx.persistence.model.BpmSignData;


@Repository
public class BpmSignDataDaoImpl extends MyBatisDaoImpl<String, BpmSignData> implements BpmSignDataDao{

    @Override
    public String getNamespace() {
        return BpmSignData.class.getName();
    }

	@Override
	public List<BpmSignData> getVoteByExecuteNode(String executeId, String nodeId,Integer isActive) {
		Map<String,Object> params=buildMapBuilder()
				.addParam("executeId", executeId)
				.addParam("nodeId", nodeId)
				.addParam("isActive", isActive)
				.getParams()	;
		return this.getByKey("getVoteByExecuteNode", params);
	}

	

//	@Override
//	public void removeByExecuteNode(String executeId, String nodeId) {
//		Map<String,Object> params=buildMapBuilder().addParam("executeId", executeId).addParam("nodeId", nodeId).getParams();
//            this.deleteByKey("removeByExecuteNode", params);
//	}

	@Override
	public void delByInstList(List<String> instList) {
		this.deleteByKey("delByInstList", instList);
	}



	@Override
	public BpmSignData getByExeNodeIndex(String executeId, String nodeId,
			Short index) {
		Map<String,Object> params=buildMapBuilder()
				.addParam("executeId", executeId)
				.addParam("nodeId", nodeId)
				.addParam("index", index)
				.getParams();
		
		return this.getUnique("getByExeNodeIndex", params);
	}

	@Override
	public void updByNotActive(String executeId, String nodeId) {
		Map<String,Object> params=buildMapBuilder()
				.addParam("executeId", executeId)
				.addParam("nodeId", nodeId)
				.getParams();
		this.updateByKey("updByNotActive", params);
	}

	@Override
	public void removeByNotActive(String executeId, String nodeId) {
		Map<String,Object> params=buildMapBuilder()
				.addParam("executeId", executeId)
				.addParam("nodeId", nodeId)
				.getParams();
		this.deleteByKey("removeByNotActive", params);
	}

	@Override
	public BpmSignData getByInstanIdAndUserId(String instancId, String userId) {
		Map<String,Object> params=buildMapBuilder()
				.addParam("instancId", instancId)
				.addParam("userId", userId)
				.getParams()	;
		return (BpmSignData)this.getOne("getByInstanIdAndUserId", params);
	}
	
	

}

