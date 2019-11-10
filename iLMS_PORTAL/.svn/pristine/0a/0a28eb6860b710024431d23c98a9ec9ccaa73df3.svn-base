package com.hotent.bpmx.persistence.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmTaskTurnDao;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.org.api.model.IUser;


@Repository
public class BpmTaskTurnDaoImpl extends MyBatisDaoImpl<String, DefaultBpmTaskTurn> implements BpmTaskTurnDao{

    @Override
    public String getNamespace() {
        return DefaultBpmTaskTurn.class.getName();
    }

	

	@Override
	public void updComplete(String taskId, IUser user) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("taskId", taskId)
				.addParam("execUserId", BeanUtils.isNotEmpty(user)?user.getUserId():-1)
				.addParam("execUserName", BeanUtils.isNotEmpty(user)?user.getFullname():"系统执行人")
				.addParam("finishTime", new Date())
				.getParams();
		
		this.updateByKey("updComplete", params);
	}

	@Override
	public void delByInstList(List<String> instList) {
		this.deleteByKey("delByInstList", instList);
		
	}



	@Override
	public DefaultBpmTaskTurn getByTaskId(String taskId) {
		return this.getUnique("getByTaskId", taskId);
	}




	@Override
	public List<DefaultBpmTaskTurn> getMyDelegate(String userId, QueryFilter filter) {
		filter.addParamsFilter("userId", userId);
		return this.getByQueryFilter("getMyDelegate", filter);
	}
    
	@Override
	public List<DefaultBpmTaskTurn> getByTaskIdAndAssigneeId(String taskId,
			String assigneeId) {
		Map<String, Object> params=buildMapBuilder()
				.addParam("taskId", taskId)
				.addParam("assigneeId", assigneeId)
				.getParams();
		
		return this.getByKey("getByTaskIdAndAssigneeId", params);
	}
    
    
	
}

