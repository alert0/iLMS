package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmAgentConditionDao;
import com.hotent.bpmx.persistence.model.BpmAgentCondition;


@Repository

public class BpmAgentConditionDaoImpl extends MyBatisDaoImpl<String, BpmAgentCondition> implements BpmAgentConditionDao{

    @Override
    public String getNamespace() {
        return BpmAgentCondition.class.getName();
    }

	@Override
	public List<BpmAgentCondition> getBySettingId(String settingId) {
		
		return this.getByKey("getBySettingId", settingId);
	}

	@Override
	public void removeBySettingId(String settingId) {
		this.deleteByKey("removeBySettingId", settingId);
	}

	@Override
	public void removeBySettingIds(String[] ids) {
		if(ids!=null){
            for(String pk:ids){
            	removeBySettingId(pk);
            }
        }
	}
	
}

