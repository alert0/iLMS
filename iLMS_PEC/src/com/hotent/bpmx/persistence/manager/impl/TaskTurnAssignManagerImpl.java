package com.hotent.bpmx.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.TaskTurnAssignDao;
import com.hotent.bpmx.persistence.model.TaskTurnAssign;
import com.hotent.bpmx.persistence.manager.TaskTurnAssignManager;

@Service("taskTurnAssignManager")
public class TaskTurnAssignManagerImpl extends AbstractManagerImpl<String, TaskTurnAssign> implements TaskTurnAssignManager{
	@Resource
	TaskTurnAssignDao taskTurnAssignDao;
	@Override
	protected Dao<String, TaskTurnAssign> getDao() {
		return taskTurnAssignDao;
	}
	@Override
	public List<TaskTurnAssign> getByTaskTurnId(String id) {
		return taskTurnAssignDao.getByTaskTurnId(id,true);
	}
	
	@Override
	public TaskTurnAssign getLastTaskTurn(String taskTurnId) {
		List<TaskTurnAssign>  list=taskTurnAssignDao.getByTaskTurnId(taskTurnId, false);
		return list.get(0);
	}
}
