package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.TaskTurnAssignDao;
import com.hotent.bpmx.persistence.model.TaskTurnAssign;


@Repository
public class TaskTurnAssignDaoImpl extends MyBatisDaoImpl<String, TaskTurnAssign> implements TaskTurnAssignDao{

    @Override
    public String getNamespace() {
        return TaskTurnAssign.class.getName();
    }

	public List<TaskTurnAssign> getByTaskTurnId(String taskTurnId,boolean isAsc) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("taskTurnId", taskTurnId)
				.addParam("isAsc", isAsc)
				.getParams();
		return this.getByKey("getByTaskTurnId", params);
	}

	
	
}

