package com.hotent.bpmx.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskTransDao;
import com.hotent.bpmx.persistence.model.BpmTaskTrans;


@Repository
public class BpmTaskTransDaoImpl extends MyBatisDaoImpl<String, BpmTaskTrans> implements BpmTaskTransDao{

    @Override
    public String getNamespace() {
        return BpmTaskTrans.class.getName();
    }

	@Override
	public BpmTaskTrans getByTaskId(String taskId) {
		BpmTaskTrans bpmTaskTrans=this.getUnique("getByTaskId", taskId);
		return bpmTaskTrans;
	}
	
}

