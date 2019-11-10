package com.hotent.bpmx.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmCheckOpinionDao;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;

@Repository
public class BpmCheckOpinionDaoImpl extends MyBatisDaoImpl<String, DefaultBpmCheckOpinion> implements BpmCheckOpinionDao{

    @Override
    public String getNamespace() {
        return DefaultBpmCheckOpinion.class.getName();
    }
    public DefaultBpmCheckOpinion getByTaskId(String taskId){
    	List<DefaultBpmCheckOpinion> bpmCheckOpinions = this.getByKey("getByTaskId", buildMapBuilder().addParam("taskId", taskId).getParams());
    	if(bpmCheckOpinions.size()>0){
    		return bpmCheckOpinions.get(0);
    	}
    	return null;
    }
    
	@Override
	public void archiveHistory(String procInstId) {		
          this.updateByKey("archiveHistory", procInstId);
	}
	@Override
	public List<DefaultBpmCheckOpinion> getByInstIds(List<String> instIdList) {
		
		return getByKey("getByInstIds", instIdList);
	}
	
	@Override
	public List<String> getBySupInstId(String supInstId) {
		return  this.sqlSessionTemplate.selectList(this.getNamespace() +".getBySupInstId" , supInstId);
	}
	@Override
	public String getSupInstByInstId(String instId) {
		return (String) this.getOne("getSupInstByInstId", instId);
	}
	
	@Override
	public void delByInstList(List<String> instList) {
		this.deleteByKey("delByInstList", instList);
		
	}
	@Override
	public List<DefaultBpmCheckOpinion> getByInstNodeId(String instId, String nodeId) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("instId", instId); 
		params.put("nodeId", nodeId);
		return this.getByKey("getByInstNodeId", params);
	}
	
	
	@Override
	public void updStatusByWait(String taskId, String status) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("taskId", taskId); 
		params.put("status", status);
		this.updateByKey("updStatusByWait", params);
		
	}
	

	@Override
	public List<DefaultBpmCheckOpinion> getByInstIdsAndWait(List<String> list) {
		return this.getByKey("getByInstIdsAndWait", list);
	}
	@Override
	public DefaultBpmCheckOpinion getByTaskIdStatus(String taskId,String taskAction) {
		Map map =  buildMapBuilder()
				.addParam("taskAction", taskAction)
				.addParam("taskId", taskId).getParams();
		
		List<DefaultBpmCheckOpinion> bpmCheckOpinions = this.getByKey("getByTaskIdAction",map);
    	
		if(bpmCheckOpinions.size()>0) return bpmCheckOpinions.get(0);
    	return null;
	}
	
	@Override
	public List<DefaultBpmCheckOpinion> getByInstNodeIdAndAgree(String instId,
			String nodeId) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("instId", instId); 
		params.put("nodeId", nodeId);
		return this.getByKey("getByInstNodeIdAgree", params);
	}
	
}
