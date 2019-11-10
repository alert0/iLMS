package com.hotent.bpmx.core.engine.task.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.Page;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.AopType;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.TaskType;
import com.hotent.bpmx.api.event.DoNextEvent;
import com.hotent.bpmx.api.event.DoNextModel;
import com.hotent.bpmx.api.helper.identity.BpmIdentityExtractService;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.service.BpmTaskService;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.persistence.manager.BpmTaskCandidateManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTurnManager;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskCandidate;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.model.IdentityType;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.util.ContextUtil;

@Service
public class DefaultBpmTaskService implements BpmTaskService {
	@Resource
	BpmTaskCandidateManager bpmTaskCandidateManager;
	@Resource
	BpmTaskManager bpmTaskManager;	 
	@Resource
	IUserGroupService defaultUserGroupService;
	@Resource
	IUserService userServiceImpl;
	@Resource
	BpmTaskTurnManager bpmTaskTurnManager;
	@Resource
	BpmIdentityExtractService bpmIdentityExtractService;
	
	@Override
	public List<BpmTask> getChildsByTaskId(String taskId) {		
		return convert(bpmTaskManager.getChildsByTaskId(taskId));
	}

	
	@Override
	public List<BpmTask> getTasksByUserId(String userId) {		
		return convert(bpmTaskManager.getByUserId(userId));
	}

	@Override
	public List<BpmTask> getTasksByUserId(String userId, Page page) {
		return convert(bpmTaskManager.getByUserId(userId,page));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BpmTask> getTasksByUserId(String userId, QueryFilter filter) {
		List list= bpmTaskManager.getByUserId(userId,filter);
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BpmTask> getAllTasks(QueryFilter filter) {
		List list=bpmTaskManager.query(filter);
		return list;
	}

	
	

	@Override
	public BpmTask getByTaskId(String taskId) {
		return bpmTaskManager.get(taskId);
	}

	@Override
	public List<BpmTask> getTasksInstId(String instId) {
		return convert(bpmTaskManager.getByInstId(instId));
	}

	@Override
	public List<BpmTask> getTaskByInstUser(String instId, String userId) {
		return convert(bpmTaskManager.getByInstUser(instId, userId));
	}

	@Override
	public boolean canAccessTask(String taskId,String userId) {
		return bpmTaskManager.canAccessTask(taskId, userId);
		
	}
	
	

	private BpmTask convert(DefaultBpmTask defaultBpmTask){
		BpmTask bpmTask = (BpmTask)defaultBpmTask;
		return bpmTask;
	}
	private List<BpmTask> convert(List<DefaultBpmTask> defaultBpmTasks){
		List<BpmTask> bpmTasks = new ArrayList<BpmTask>();
		for(DefaultBpmTask defaultBpmTask:defaultBpmTasks){
			bpmTasks.add(convert(defaultBpmTask));
		}
		return bpmTasks;
	}
	
	

	@Override
	public void lockTask(String taskId, String userId) {
		bpmTaskManager.lockTask(taskId, userId);
	}

	@Override
	public void unlockTask(String taskId) {
		bpmTaskManager.unLockTask(taskId);
	}
	
	@Override
	public void assigTask(String taskId, String assigneeId) {
		bpmTaskManager.assignTask(taskId, assigneeId);
	}

	@Override
	public void addCandidateUsers(String taskId, String[] userIds) {
		BpmTask bpmTask=bpmTaskManager.get(taskId);
		
		if(bpmTask==null) return;
		
		for(String userId:userIds){
			DefaultBpmTaskCandidate tmp=bpmTaskCandidateManager.getByTaskIdExeIdType(taskId, userId, IdentityType.USER);
			if(tmp!=null) continue;
			DefaultBpmTaskCandidate candidate=new DefaultBpmTaskCandidate();
			candidate.setId(UniqueIdUtil.getSuid());
			candidate.setExecutor(userId);
			candidate.setType(IdentityType.USER);
			candidate.setTaskId(bpmTask.getTaskId());
			candidate.setProcInstId(bpmTask.getProcInstId());
			bpmTaskCandidateManager.create(candidate);
		}
		
	}

	@Override
	public void addCandidates(String taskId, List<BpmIdentity> candidates) {
		BpmTask bpmTask=bpmTaskManager.get(taskId);
		
		if(bpmTask==null) return;
		
		for(BpmIdentity identity:candidates){
			String executorId=identity.getId();
			String type=BpmIdentity.TYPE_USER.equals(identity.getType())?BpmIdentity.TYPE_USER:identity.getGroupType();
			
			DefaultBpmTaskCandidate tmp=bpmTaskCandidateManager.getByTaskIdExeIdType(taskId, executorId, type);
			if(tmp!=null) continue;
			DefaultBpmTaskCandidate candidate=new DefaultBpmTaskCandidate();
			candidate.setId(UniqueIdUtil.getSuid());
			candidate.setExecutor(executorId);
			candidate.setType(type);
			candidate.setTaskId(bpmTask.getTaskId());
			candidate.setProcInstId(bpmTask.getProcInstId());
			bpmTaskCandidateManager.create(candidate);
		}
		
	}

	@Override
	public void setTaskSkip(BpmTask bpmTask) {
		BpmUtil.setTaskSkip(bpmTask);
		
	}

	@Override
	public List<BpmIdentity> getTaskCandidates(String taskId) {
		List<BpmIdentity> bpmIdentites=new ArrayList<BpmIdentity>();
		List<DefaultBpmTaskCandidate> candidateList=bpmTaskCandidateManager.queryByTaskId(taskId);
		
		for(DefaultBpmTaskCandidate candiate:candidateList){
			DefaultBpmIdentity bpmId=new DefaultBpmIdentity();
			//若为用户
			if(BpmIdentity.TYPE_USER.equals(candiate.getType())){
				bpmId.setType(BpmIdentity.TYPE_USER);
				IUser user=userServiceImpl.getUserById(candiate.getExecutor());
				bpmId.setName(user.getFullname());
			}else{
				bpmId.setType(BpmIdentity.TYPE_GROUP);
				bpmId.setGroupType(candiate.getType());
				IGroup group=defaultUserGroupService.getById(candiate.getType(),candiate.getExecutor());
				bpmId.setName(group.getName());
			}
			bpmId.setId(candiate.getExecutor());
			bpmIdentites.add(bpmId);
		}
		return bpmIdentites;
	}

	@Override
	public List<IUser> getUsersByTaskId(String taskId) {
		List<IUser> userList = new ArrayList<IUser>();
		if(StringUtil.isEmpty(taskId)) return userList;
		BpmTask task = bpmTaskManager.get(taskId);
		if(task==null) return userList;
		// 任务执行人
		if (StringUtil.isNotZeroEmpty(task.getAssigneeId())) {
			IUser user=userServiceImpl.getUserById(task.getAssigneeId());
			userList.add(user);
		}else{
			// 任务执行集合
			List<BpmIdentity> identities = getTaskCandidates(task.getId());
			List<IUser> users = bpmIdentityExtractService.extractUser(identities);
			userList.addAll(users);
		}
		
		return userList;
	}
	
	


	@Override
	public void saveDraft(TaskFinishCmd cmd) {
		DoNextModel donextModel = new DoNextModel(cmd, AopType.PREVIOUS);
		DoNextEvent donextEv = new DoNextEvent(donextModel);
		AppUtil.publishEvent(donextEv);
	}


	/**
	 * 根据用户ID个数判断。
	 *  1.如果只有一个人员，那么这个人就是任务的执行人。
	 *  2.如果有多个那么这些人员为任务的候选人。
	 *  3.如果任务只有一个那么需要通知原有的执行人。
	 * @param taskId
	 * @param userIds
	 */
	@Override
	public void setTaskExecutors(String taskId, String[] userIds,String notifyType,String opinion) {
		bpmTaskManager.setTaskExecutors(taskId, userIds, notifyType, opinion);
	}


	@Override
	public int canLockTask(String taskId) {
		return bpmTaskManager.canLockTask(taskId);
	}
	

}
