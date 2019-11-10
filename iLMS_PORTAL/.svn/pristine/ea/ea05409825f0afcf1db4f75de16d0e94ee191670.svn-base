package com.hotent.bpmx.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.ActExecutionDao;
import com.hotent.bpmx.persistence.manager.ActExecutionManager;
import com.hotent.bpmx.persistence.model.ActExecution;

@Service("actExecutionManager")
public class ActExecutionManagerImpl extends AbstractManagerImpl<String, ActExecution> implements ActExecutionManager{
	@Resource
	ActExecutionDao actExecutionDao;
	@Override
	protected Dao<String, ActExecution> getDao() {
		return actExecutionDao;
	}
	

	@Override
	public void delByInstList(List<String> instList) {
		//删除运行的数据
		//实例数据
		actExecutionDao.delVarsByInstList(instList);
		//删除候选人
		actExecutionDao.delCandidateByInstList(instList);
		//删除任务
		actExecutionDao.delTaskByByInstList(instList);
		//删除事件订阅
		actExecutionDao.delEventSubByInstList(instList);
		//删除实例
		actExecutionDao.delExectionByInstList(instList);
		
		//历史数据删除
		//历史变量清除
		actExecutionDao.delHiVarByInstList(instList);
		//历史候选人清除
		actExecutionDao.delHiCandidateByInstList(instList);
		//历史任务清除
		actExecutionDao.delHiTaskByInstList(instList);
		//历史实例清除
		actExecutionDao.delHiActInstByInstList(instList);
		//历史流程实例清除
		actExecutionDao.delHiProcinstByInstList(instList);
		
	}
	

	
	
}
