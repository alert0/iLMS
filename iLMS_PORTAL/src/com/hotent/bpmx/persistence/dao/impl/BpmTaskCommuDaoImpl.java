package com.hotent.bpmx.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskCommuDao;
import com.hotent.bpmx.persistence.model.BpmTaskCommu;


@Repository

public class BpmTaskCommuDaoImpl extends MyBatisDaoImpl<String, BpmTaskCommu> implements BpmTaskCommuDao{

    @Override
    public String getNamespace() {
        return BpmTaskCommu.class.getName();
    }

	@Override
	public BpmTaskCommu getByTaskId(String taskId) {
		BpmTaskCommu commu=this.getUnique("getByTaskId", taskId);
		return commu;
	}
	
}

