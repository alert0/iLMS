package com.hotent.bpmx.persistence.manager.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.constant.TaskType;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.persistence.dao.ActExecutionDao;
import com.hotent.bpmx.persistence.dao.ActHiTaskInstDao;
import com.hotent.bpmx.persistence.dao.ActTaskDao;
import com.hotent.bpmx.persistence.dao.BpmTaskDao;
import com.hotent.bpmx.persistence.manager.ActTaskManager;
import com.hotent.bpmx.persistence.model.ActExecution;
import com.hotent.bpmx.persistence.model.ActHiTaskInst;
import com.hotent.bpmx.persistence.model.ActTask;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.org.api.model.IUser;

@Service("actTaskManager")
public class ActTaskManagerImpl extends AbstractManagerImpl<String, ActTask> implements ActTaskManager{
	@Resource
	ActTaskDao actTaskDao;
	
	@Resource
	ActExecutionDao actExecutionDao;
	
	@Resource
	ActHiTaskInstDao actHiTaskInstDao;
	@Resource
	BpmTaskDao bpmTaskDao ; 
	
	@Override
	protected Dao<String, ActTask> getDao() {
		return actTaskDao;
	}
	
	/**
	 * 添加用户任务。
	 * <pre>
	 * 实现方式
	 * 1.根据任务Id查询任务实例。
	 * 2.根据这个任务实例创建EXECUTION实例。
	 * 3.创建历史任务实例。
	 * 4.创建BPM_TASK数据实例。
	 * 5.创建BPM_TASK数据实例审批意见。
	 * 6.持久化数据到数据库。
	 * </pre>
	 * @param bpmnTaskId	流程引擎的任务ID。		
	 * @param userId 
	 * void
	 */
	@Override
	public ActTask createTask(String taskId, String userId) {
		String newTaskId=UniqueIdUtil.getSuid();
		String executionId=UniqueIdUtil.getSuid();
		
		ActTask actTask=actTaskDao.get(taskId);
		
		ActExecution  actExecution=actExecutionDao.get(actTask.getExecutionId());
		ActHiTaskInst actHiTaskInst=actHiTaskInstDao.get(taskId);
		DefaultBpmTask defaultBpmTask = bpmTaskDao.getByRelateTaskId(taskId);
		
		//任务
		ActTask newActTask=(ActTask)actTask.clone();
		newActTask.setId(newTaskId);
		newActTask.setExecutionId(executionId);
		newActTask.setAssignee(userId);
		newActTask.setOwner(userId);
		//execution
		ActExecution  newActExecution=(ActExecution)actExecution.clone();
		newActExecution.setId(executionId);
		//历史数据
		ActHiTaskInst newActHiTask=(ActHiTaskInst)actHiTaskInst.clone();
		newActHiTask.setId(newTaskId);
		newActHiTask.setExecutionId(executionId);
		newActHiTask.setAssignee(userId);
		newActHiTask.setOwner(userId);
		//DefaultBpmTask
		DefaultBpmTask newBpmTask=(DefaultBpmTask) defaultBpmTask.clone();
		newBpmTask.setId(newTaskId);
		newBpmTask.setTaskId(newTaskId);
		newBpmTask.setExecId(executionId);
		newBpmTask.setAssigneeId(userId);
		newBpmTask.setOwnerId(userId);
	
		
		//添加数据库表。
		actExecutionDao.create(newActExecution);
		actTaskDao.create(newActTask);
		actHiTaskInstDao.create(newActHiTask);
		bpmTaskDao.create(newBpmTask);
		
		return newActTask;
		
	}

	@Override
	public ActTask createTask(ActExecution actExecution,BpmProcessInstance instance, BpmNodeDef bpmNodeDef) {
		//创建ACT_RU_TASK任务
		ActTask actTask=createActTask(actExecution, bpmNodeDef,instance);
		//历史任务
		createHiActTask(actTask);
		//创建BPM_TASK
		createBpmTask(actTask,instance);
		
		return actTask;
	}
	
	/**
	 * 创建ACT_RU_TASK记录。
	 * @param actExecution
	 * @param bpmNodeDef
	 * @param assigneeId
	 * @return  ActTask
	 */
	private ActTask createActTask(ActExecution actExecution,BpmNodeDef bpmNodeDef,BpmProcessInstance instance){
		//创建ACT_RU_TASK任务
		ActTask actTask=new ActTask();

		actTask.setId(UniqueIdUtil.getSuid());
		actTask.setRev(1);
		actTask.setExecutionId(actExecution.getId());
		actTask.setProcInstId(actExecution.getProcInstId());
		actTask.setProcDefId(actExecution.getProcDefId());
		actTask.setName(bpmNodeDef.getName());
		actTask.setTaskDefKey(bpmNodeDef.getNodeId());
		actTask.setOwner(instance.getCreateBy());
		actTask.setAssignee(instance.getCreateBy());
		actTask.setPriority(50);
		actTask.setCreateTime(new Date());
		actTask.setSuspensionState(1);
		
		actTaskDao.create(actTask);
		
		
		return actTask;
	}

	/**
	 * 创建历史任务。
	 * @param actTask 
	 * void
	 */
	private void createHiActTask(ActTask actTask){

		ActHiTaskInst taskInst=new ActHiTaskInst();
		taskInst.setId(actTask.getId());
		taskInst.setProcDefId(actTask.getProcDefId());
		taskInst.setTaskDefKey(actTask.getTaskDefKey());
		taskInst.setProcInstId(actTask.getProcInstId());
		taskInst.setExecutionId(actTask.getExecutionId());
		taskInst.setName(actTask.getName());
		taskInst.setParentTaskId(actTask.getParentTaskId());
		taskInst.setOwner(actTask.getOwner());
		taskInst.setAssignee(actTask.getAssignee());
		taskInst.setStartTime(actTask.getCreateTime());
		taskInst.setPriority(actTask.getPriority());
		
	
		actHiTaskInstDao.create(taskInst);
	}
	
	/**
	 * 创建BPM_TASK记录。
	 * @param actTask
	 * @param instance 
	 * void
	 */
	private void createBpmTask(ActTask actTask,BpmProcessInstance instance){
		DefaultBpmTask bpmTask=new DefaultBpmTask();
		
		bpmTask.setId(actTask.getId());
		bpmTask.setName(actTask.getName());
		bpmTask.setSubject(instance.getSubject());
		bpmTask.setTaskId(actTask.getId());
		bpmTask.setExecId(actTask.getExecutionId());
		bpmTask.setNodeId(actTask.getTaskDefKey());
		bpmTask.setProcInstId(instance.getId());
		bpmTask.setProcDefId(instance.getProcDefId());
		bpmTask.setProcDefName(instance.getProcDefName());
		bpmTask.setOwnerId(actTask.getOwner());
		bpmTask.setAssigneeId(actTask.getAssignee());
		bpmTask.setStatus(TaskType.NORMAL.name());
		bpmTask.setPriority(50L);
		bpmTask.setCreateTime(actTask.getCreateTime());
		bpmTask.setSuspendState((short)0);
		bpmTask.setBpmnInstId(actTask.getProcInstId());
		bpmTask.setBpmnDefId(actTask.getProcDefId());
		bpmTask.setTypeId(instance.getTypeId());
		bpmTask.setProcDefKey(instance.getProcDefKey());
		bpmTaskDao.create(bpmTask);
		
	}
	
}
