package com.hotent.bpmx.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmDefinitionDao;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;

@Repository
public class BpmDefinitionDaoImpl extends MyBatisDaoImpl<String, DefaultBpmDefinition> implements BpmDefinitionDao{

    @Override
    public String getNamespace() {
        return DefaultBpmDefinition.class.getName();
    }

	

	@Override
	public DefaultBpmDefinition getMainByDefKey(String defKey) {
		return this.getUnique("getMainByDefKey", buildMap("defKey", defKey));
	}

	
	@Override
	public Integer getMaxVersion(String defKey) {
		return (Integer) this.getOne("getMaxVersion", buildMap("defKey", defKey));
	}

	
	@Override
	public List<DefaultBpmDefinition> queryByDefKey(String defKey) {
		return this.getByKey("queryByDefKey", buildMap("defKey", defKey));
	}

	
	@Override
	public List<DefaultBpmDefinition> queryHistorys(String defKey) {
		return this.getByKey("queryHistorys", buildMap("defKey", defKey));
	}
	
	@Override
	public String getDefIdByBpmnDefId(String bpmnDefId) {
		return (String)this.getOne("getDefIdByBpmnDefId", buildMap("bpmnDefId",bpmnDefId));
	}
	
	@Override
	public DefaultBpmDefinition getByBpmnDefId(String bpmnDefId) {
		return (DefaultBpmDefinition)this.getOne("getByBpmnDefId", buildMap("bpmnDefId",bpmnDefId));
	}
	
	@Override
	public DefaultBpmDefinition getByBpmnDeployId(String bpmnDeployId) {
		return (DefaultBpmDefinition)this.getOne("getByBpmnDeployId", buildMap("bpmnDeployId",bpmnDeployId));
	}

	@Override
	public void updateToMain(String defId) {
		//更新相关的流程定义
		this.updateByKey("updateNotMainVersion", defId);
		this.updateByKey("updateToMainVersion", defId);
	}
	
	@Override
	public void updateStatus(String defId, String status) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("defId", defId);
		params.put("status", status);
		this.updateByKey("updateStatus", params);
	}

	


	@Override
	public void delByKey(String defKey) {
		this.deleteByKey("delByKey", defKey);
	}



	@Override
	public void delActDeploy(String defKey) {
		this.deleteByKey("delActDeploy", defKey);
	}



	@Override
	public void delActByteArray(String defKey) {
		this.deleteByKey("delActByteArray", defKey);
	}



	@Override
	public void delActDef(String defKey) {
		this.deleteByKey("delActDef", defKey);
	}
	
	



	@Override
	public List<DefaultBpmDefinition> getByDefKey(String defKey) {
		List<DefaultBpmDefinition> list=this.getByKey("getByDefKey", defKey);
		return list;
	}



	@Override
	public List<DefaultBpmDefinition> queryByCreateBy(String userId)
	{
		return this.getByKey("getByCreateBy", buildMap("userId", userId));
	}



	@Override
	public List<DefaultBpmDefinition> queryListByMap(Map<String, Object> map) {
		return this.getByKey("queryListByMap", map);
	}



	@Override
	public void updDefType(String typeId, List<String> defList) {
		Map<String,Object> maps=new HashMap<String, Object>();
		maps.put("typeId", typeId);
		maps.put("defList", defList);
		this.updateByKey("updDefType", maps);
		
	}



	@Override
	public void delActDeployByDefId(String defId) {
		this.deleteByKey("delActDeployByDefId", defId);
	}



	@Override
	public void delActDefByDefId(String defId) {
		this.deleteByKey("delActDefByDefId", defId);
	}



	@Override
	public void delActByteArrayByDefId(String defId) {
		this.deleteByKey("delActByteArrayByDefId", defId);
	}



	@Override
	public void delActTask(String defId) {
		this.deleteByKey("delActTask", defId);
	}

}
