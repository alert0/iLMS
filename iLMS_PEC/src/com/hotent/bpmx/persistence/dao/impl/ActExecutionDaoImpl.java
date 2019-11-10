package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.ActExecutionDao;
import com.hotent.bpmx.persistence.model.ActExecution;


@Repository

public class ActExecutionDaoImpl extends MyBatisDaoImpl<String, ActExecution> implements ActExecutionDao{

    @Override
    public String getNamespace() {
        return ActExecution.class.getName();
    }

	@Override
	public List<String> getByParentsId(String parentId) {
		List<String> list= this.sqlSessionTemplate.selectList("getByParentsId", parentId);
		return list;
	}

	@Override
	public List<String> getBySupperId(String supperId) {
		List<String> list= this.sqlSessionTemplate.selectList("getBySupperId", supperId);
		return list;
	}

	@Override
	public void delTaskByByInstList(List<String> instList) {
		this.deleteByKey("delTaskByByInstList", instList);
	}

	@Override
	public void delCandidateByInstList(List<String> instList) {
		this.deleteByKey("delCandidateByInstList", instList);
	}

	@Override
	public void delEventSubByInstList(List<String> instList) {
		this.deleteByKey("delEventSubByInstList", instList);
	}

	@Override
	public void delVarsByInstList(List<String> instList) {
		this.deleteByKey("delVarsByInstList", instList);
	}

	@Override
	public void delHiVarByInstList(List<String> instList) {
		this.deleteByKey("delHiVarByInstList", instList);
	}

	@Override
	public void delHiTaskByInstList(List<String> instList) {
		this.deleteByKey("delHiTaskByInstList", instList);
	}

	@Override
	public void delHiProcinstByInstList(List<String> instList) {
		this.deleteByKey("delHiProcinstByInstList", instList);
	}

	@Override
	public void delHiCandidateByInstList(List<String> instList) {
		this.deleteByKey("delHiCandidateByInstList", instList);
	}

	@Override
	public void delHiActInstByInstList(List<String> instList) {
		this.deleteByKey("delHiActInstByInstList", instList);
	}

	@Override
	public void delExectionByInstList(List<String> instList) {
		this.deleteByKey("delExectionByInstList", instList);
	}
	
}

