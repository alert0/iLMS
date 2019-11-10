package com.hotent.bpmx.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.Page;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.persistence.dao.BpmProcessInstanceDao;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.org.api.model.IGroup;


@Repository
public class BpmProcessInstanceDaoImpl extends MyBatisDaoImpl<String, DefaultBpmProcessInstance> implements BpmProcessInstanceDao{

    @Override
    public String getNamespace() {
        return DefaultBpmProcessInstance.class.getName();
    }
	
    
    /**
     * 添加历史流程实例。
     * @param processInstance 
     * void
     * @exception 
     * @since  1.0.0
     */
    @Override
    public void createHistory(DefaultBpmProcessInstance processInstance){
        this.insertByKey("createHistory", processInstance);
    }
    
    @Override
    public void updateHistory(DefaultBpmProcessInstance processInstance){
        this.updateByKey("updateHistory", processInstance);
    }

	@Override
	public DefaultBpmProcessInstance getBpmProcessInstanceHistory(String procInstId) {
                return (DefaultBpmProcessInstance)this.getOne("getHistory", procInstId);
	}
	@Override
	public DefaultBpmProcessInstance getBpmProcessInstanceHistoryByBpmnInstId(String bpmnInstId) {
                return (DefaultBpmProcessInstance)this.getOne("getHistoryByBpmnInstId", bpmnInstId);
	}
 

	@Override
	public DefaultBpmProcessInstance getBpmnInstId(String bpmnInstId) {
		return this.getUnique("getBpmnInstId", bpmnInstId);
	}
    
    @Override
    public List<DefaultBpmProcessInstance> getByUserId(String userId) {
    	return this.getByKey("getByUserId", userId);
    }
    
    @Override
    public PageList<DefaultBpmProcessInstance> getByUserId(String userId, Page page) {
    	return (PageList) this.getByKey("getByUserId", userId,page);
    }


    @Override
    public void updateStatusByInstanceId(String processInstanceId, String status) {
            Map<String,Object> params=new HashMap<String, Object>();
            params.put("processInstanceId",processInstanceId);
            params.put("status",status);
            this.updateByKey("updateStatusByInstanceId", params);
    }


    @Override
    public void updateStatusByBpmnInstanceId(String processInstanceId, String status) {
            Map<String,Object> params=new HashMap<String, Object>();
            params.put("bpmnInstId",processInstanceId);
            params.put("status",status);
            this.updateByKey("updateStatusByInstanceId", params);
    }
    
    @Override
    public List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId, List<IGroup> groupList) {
    	Map<String,Object> params=new HashMap<String,Object>();
    	params.put("userId", userId);
    	params.put("groupList",groupList);
    	return this.getByKey("getByUserIdGroupList", params);
    }
    
    @Override
    public List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId, List<IGroup> groupList, Page page) {
    	Map<String,Object> params=new HashMap<String,Object>();
    	params.put("userId", userId);
    	params.put("groupList",groupList);
    	return this.getByKey("getByUserIdGroupList", params,page);
    }
    
    @Override
    public List<DefaultBpmProcessInstance> getByAttendUserId(String usreId) {
    	Map<String,Object> params=new HashMap<String,Object>();
    	params.put("userId", usreId);
    	return this.getByKey("getByAttendUserId", params);
    }
    
    @Override
    public List getByAttendUserId(String usreId, Page page) {
    	Map<String,Object> params=new HashMap<String,Object>();
    	params.put("userId", usreId);
    	return this.getByKey("getByAttendUserId", params,page);
    }

    
	@Override
	public List<String> getBpmnByInstList(List<String> instList) {
		List<String> rtnList=this.sqlSessionTemplate.selectList("getBpmnByInstList", instList);
		return rtnList;
	}


	@Override
	public List<DefaultBpmProcessInstance> getListByBpmnDefKey(String bpmnDefKey) {
		List<DefaultBpmProcessInstance> list=this.getByKey("getListByBpmnDefKey", bpmnDefKey);
		return list;
	}


	@Override
	public List getByAttendUserId(String userId,QueryFilter queryFilter) {
		
		queryFilter.addFilter("userId", userId, QueryOP.EQUAL);
		
		return  this.getByQueryFilter("getByAttendUserId", queryFilter);
		
		
	}


	@Override
	public List<DefaultBpmProcessInstance> getByUserIdGroupList(String userId,
			List<IGroup> groupList, QueryFilter queryFilter) {

		queryFilter.addFilter("userId", userId, QueryOP.EQUAL);
		queryFilter.addFilter("groupList", groupList, QueryOP.EQUAL);
		
		return this.getByQueryFilter("getByUserIdGroupList", queryFilter);
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultBpmProcessInstance> getByUserId(String userId,
			QueryFilter queryFilter) {
		queryFilter.addFilter("userId", userId, QueryOP.EQUAL);
		return this.getByQueryFilter("getByUserId", queryFilter);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultBpmProcessInstance> getMyRequestByUserId(String userId,
			QueryFilter queryFilter) {
		queryFilter.addParamsFilter("userId", userId);
		return this.getByQueryFilter("getMyRequestByUserId", queryFilter);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultBpmProcessInstance> getMyCompletedByUserId(
			String userId, QueryFilter queryFilter) {
		queryFilter.addParamsFilter("userId", userId);
		return this.getByQueryFilter("getMyCompletedByUserId", queryFilter);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultBpmProcessInstance> getDraftsByUserId(String userId,
			QueryFilter queryFilter) {
		
		queryFilter.addParamsFilter("userId", userId);
		return this.getByQueryFilter("getDraftsByUserId", queryFilter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getHandledByUserId(String userId,
			QueryFilter queryFilter) {
		queryFilter.addParamsFilter("userId", userId);
		return this.getByQueryFilter("getHandledByUserId", queryFilter);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultBpmProcessInstance> getCompletedByUserId(String userId,
			QueryFilter queryFilter) {
		queryFilter.addParamsFilter("userId", userId);
		return this.getByQueryFilter("getCompletedByUserId", queryFilter);
	}


	@Override
	public void updForbiddenByDefKey(String defKey, Integer isForbidden) {
		Map<String,Object> params=buildMapBuilder()
				.addParam("isForbidden", isForbidden)
				.addParam("defKey", defKey)
				.getParams();
		this.updateByKey("updForbiddenByDefKey", params);
	}


	@Override
	public void updForbiddenByInstId(String instId, Integer isForbidden) {
		Map<String,Object> params=buildMapBuilder()
				.addParam("isForbidden", isForbidden)
				.addParam("id", instId)
				.getParams();
		this.updateByKey("updForbiddenByInstId", params);
	}

	@Override
	public List<DefaultBpmProcessInstance> getByDefKeyFormal(String defKey,
			String formal) {
		Map<String,Object> params=buildMapBuilder()
				.addParam("defKey", defKey)
				.addParam("formal", formal)
				.getParams();
		return this.getByKey("getByDefKeyFormal", params);
	}


	@Override
	public List<DefaultBpmProcessInstance> getByParentId(String parentInstId) {
		
		List<DefaultBpmProcessInstance> list=this.getByKey("getByParentId", parentInstId);
		
		return list;
	}


	@Override
	public List<DefaultBpmProcessInstance> getListByDefId(String procDefId) {
		List<DefaultBpmProcessInstance> list= this.getByKey("getListByDefId", procDefId);
		return list;
	}


 
	@Override
	public List<BpmProcessInstance> getBpmnByParentIdAndSuperNodeId(String parentInstId, String superNodeId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("parentInstId", parentInstId);
		params.put("superNodeId", superNodeId);
		return this.getList("getBpmnByParentIdAndSuperNodeId", params);
	}


	@Override
	public List<BpmProcessInstance> getHiBpmnByParentIdAndSuperNodeId(String parentInstId, String superNodeId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("parentInstId", parentInstId);
		params.put("superNodeId", superNodeId);
		return this.getList("getHiBpmnByParentIdAndSuperNodeId", params);
	}


 
	@Override
	public DefaultBpmProcessInstance getByBusinessKey(String businessKey) {
		DefaultBpmProcessInstance instance= this.getUnique("getByBusinessKey", businessKey);
		return instance;
	}


 
	
    
}

