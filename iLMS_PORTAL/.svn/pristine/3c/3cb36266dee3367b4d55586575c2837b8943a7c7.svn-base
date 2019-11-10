package com.hotent.bpmx.persistence.manager.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.BpmTaskTurn;
import com.hotent.bpmx.persistence.dao.BpmTaskDao;
import com.hotent.bpmx.persistence.dao.BpmTaskTurnDao;
import com.hotent.bpmx.persistence.dao.TaskTurnAssignDao;
import com.hotent.bpmx.persistence.manager.BpmTaskTurnManager;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.bpmx.persistence.model.TaskTurnAssign;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

@Service("bpmTaskTurnManager")
public class BpmTaskTurnManagerImpl extends AbstractManagerImpl<String, DefaultBpmTaskTurn> implements BpmTaskTurnManager{
	@Resource
	BpmTaskTurnDao bpmTaskTurnDao;
	@Resource
	BpmTaskDao bpmTaskDao;
	@Resource
	TaskTurnAssignDao taskTurnAssignDao;
	@Override
	protected Dao<String, DefaultBpmTaskTurn> getDao() {
		return bpmTaskTurnDao;
	}
	
	
	/**
	* 根据流程实例列表删除任务。
	* @param instList 
	* void
	*/
	public void delByInstList(List<String> instList){
		bpmTaskTurnDao.delByInstList(instList);
	}


	@Override
	public void updComplete(String taskId, IUser user) {
		//TODO  对办理人更改信息
		bpmTaskTurnDao.updComplete(taskId, user);
	}


	@Override
	public BpmTaskTurn getByTaskId(String taskId) {
		return bpmTaskTurnDao.getByTaskId(taskId);
	}


	@Override
	public List<DefaultBpmTaskTurn> getMyDelegate(String userId, QueryFilter queryFilter) {
		List<DefaultBpmTaskTurn> taskTurn =bpmTaskTurnDao.getMyDelegate(userId,queryFilter);
		return taskTurn;
	}


	@Override
	public List<TaskTurnAssign> getTurnAssignByTaskTurnId(String taskTurnId) {
		return taskTurnAssignDao.getByTaskTurnId(taskTurnId,true);
	}


	@Override
	public void add(DefaultBpmTask bpmTask, IUser owner, IUser agent,String opinion,String type) {
		DefaultBpmTaskTurn taskTurn=new DefaultBpmTaskTurn();
		taskTurn.setId(UniqueIdUtil.getSuid());
		taskTurn.setTaskId(bpmTask.getId());
		taskTurn.setTaskName(bpmTask.getName());
		taskTurn.setTaskSubject(bpmTask.getSubject());
		taskTurn.setNodeId(bpmTask.getNodeId());
		taskTurn.setProcInstId(bpmTask.getProcInstId());
		taskTurn.setOwnerId(owner.getUserId()); 
		taskTurn.setOwnerName(owner.getFullname());
		taskTurn.setAssigneeId(agent.getUserId());
		taskTurn.setAssigneeName(agent.getFullname());
		taskTurn.setStatus(BpmTaskTurn.STATUS_RUNNING);
		taskTurn.setTurnType(type);
		taskTurn.setCreateTime(new Date());
		taskTurn.setTypeId(bpmTask.getTypeId());
		bpmTaskTurnDao.create(taskTurn);
		
		addTurnAssign(taskTurn.getId(),agent,opinion);
			
	}

	@Override
	public void addTurnAssign(String turnId, IUser user, String opinion) {
		IUser fromUser= ContextUtil.getCurrentUser();
		TaskTurnAssign taskTurnAssign = new TaskTurnAssign();
		taskTurnAssign.setId(UniqueIdUtil.getSuid()); 
		taskTurnAssign.setTaskTurnId(turnId);
		taskTurnAssign.setFromUser(fromUser.getFullname());
		taskTurnAssign.setFromUserId(fromUser.getUserId());
		taskTurnAssign.setReceiverId(user.getUserId());
		taskTurnAssign.setReceiver(user.getFullname());
		taskTurnAssign.setComment(opinion);
		taskTurnAssignDao.create(taskTurnAssign);
	}

	@Override
	public List<DefaultBpmTaskTurn> getByTaskIdAndAssigneeId(String taskId,
			String assigneeId) {
		return bpmTaskTurnDao.getByTaskIdAndAssigneeId(taskId, assigneeId);
	}
}
