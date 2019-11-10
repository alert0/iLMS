package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.persistence.dao.BpmProStatusDao;
import com.hotent.bpmx.persistence.model.DefaultBpmProStatus;


@Repository
public class BpmProStatusDaoImpl extends MyBatisDaoImpl<String, DefaultBpmProStatus> implements BpmProStatusDao{

    @Override
    public String getNamespace() {
        return DefaultBpmProStatus.class.getName();
    }

	

	@Override
	public List<DefaultBpmProStatus> queryHistorys(String procInstId) {		
		Map<String,Object> params=buildMapBuilder().addParam("procInstId",procInstId).getParams();
		 return this.getByKey( "queryHistorys", params);	
	}



	@Override
	public void archiveHistory(String procInstId) {
                this.insertByKey("archiveHistory", procInstId);
	}



	@Override
	public DefaultBpmProStatus getByInstNodeId(String instId, String nodeId) {
		Map<String, Object> map=buildMapBuilder().addParam("procInstId", instId).addParam("nodeId", nodeId).getParams();
		DefaultBpmProStatus rtn=this.getUnique( "getByInstNodeId", map);
		
		return rtn;
	}



	@Override
	public void delByInstList(List<String> instList) {
		this.deleteByKey("delByInstList", instList);
	}



	@Override
	public void updStatusByInstList(List<String> list, NodeStatus status) {
		Map<String,Object> params=buildMapBuilder()
				.addParam("list", list)
				.addParam("status", status.getKey())
				.getParams();
		this.updateByKey("updStatusByInstList", params);
		
	}
	
	
}

