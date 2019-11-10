package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmAgentDefDao;
import com.hotent.bpmx.persistence.model.BpmAgentDef;


@Repository

public class BpmAgentDefDaoImpl extends MyBatisDaoImpl<String, BpmAgentDef> implements BpmAgentDefDao{

    @Override
    public String getNamespace() {
        return BpmAgentDef.class.getName();
    }

	@Override
	public List<BpmAgentDef> getBySettingId(String id) {
		return this.getByKey("getBySettingId", id);
	}

	@Override
	public void removeBySettingIds(String[] ids) {
      if(ids!=null){
            for(String pk:ids){
            	removeBySettingId(pk);
            }
        }
	}

	@Override
	public void removeBySettingId(String settingId) {
		this.deleteByKey("removeBySettingId", settingId);
	}
	

	
}

