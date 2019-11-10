package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.ActTaskDao;
import com.hotent.bpmx.persistence.model.ActTask;


@Repository

public class ActTaskDaoImpl extends MyBatisDaoImpl<String, ActTask> implements ActTaskDao{

    @Override
    public String getNamespace() {
        return ActTask.class.getName();
    }

	@Override
	public void delByInstList(List<String> list) {
		this.deleteByKey("delByInstList", list);
	}

	@Override
	public void delCandidateByInstList(List<String> list) {
		this.deleteByKey("delCandidateByInstList", list);
	}

	@Override
	public void delSpecVarsByInstList(List<String> list) {
		this.deleteByKey("delSpecVarsByInstList", list);		
	}

	@Override
	public List<ActTask> getByInstId(String instId) {
		return this.getByKey("getByInstId", instId);
	}
	
}

