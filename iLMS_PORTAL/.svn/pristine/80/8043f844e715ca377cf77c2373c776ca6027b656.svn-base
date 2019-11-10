package com.hotent.bpmx.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.ActionType;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.MultiInstanceType;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.constant.ProcessInstanceStatus;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.event.TaskCompleteEvent;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.SkipResult;
import com.hotent.bpmx.api.service.TaskCommuService;
import com.hotent.bpmx.core.engine.task.handler.TaskActionBackHandler;
import com.hotent.bpmx.natapi.inst.NatProInstanceService;
import com.hotent.bpmx.natapi.task.NatTaskService;
import com.hotent.bpmx.persistence.dao.ActExecutionDao;
import com.hotent.bpmx.persistence.dao.BpmExeStackDao;
import com.hotent.bpmx.persistence.dao.BpmExeStackExecutorDao;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmExeStackManager;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTaskCandidateManager;
import com.hotent.bpmx.persistence.manager.BpmTaskDueTimeManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTurnManager;
import com.hotent.bpmx.persistence.model.ActExecution;
import com.hotent.bpmx.persistence.model.BpmExeStack;
import com.hotent.bpmx.persistence.model.BpmExeStackExecutor;
import com.hotent.bpmx.persistence.model.BpmTaskDueTime;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.util.BpmStackRelationUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.calendar.ICalendarService;
import com.hotent.sys.util.ContextUtil;

@Service
public class TaskCompleteEventListener implements ApplicationListener<TaskCompleteEvent>, Ordered
{

	private static Logger log = LoggerFactory.getLogger(TaskActionBackHandler.class);
	@Resource
	ActExecutionDao actExecutionDao;
	@Resource
	NatTaskService natTaskService;
	@Resource
	BpmExeStackDao bpmExeStackDao;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmTaskCandidateManager bpmTaskCandidateManager;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	BpmProStatusManager bpmProStatusManager;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmTaskTurnManager bpmTaskTurnManager;
	@Resource
	TaskCommuService taskCommuService;
	@Resource
	NatProInstanceService natProInstanceService;
	@Resource
	BpmExeStackManager bpmExeStackManager;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmExeStackExecutorDao bpmExeStackExecutorDao;
	@Resource
	BpmTaskDueTimeManager bpmTaskDueTimeManager;
	@Resource
	ICalendarService iCalendarService;

	@Override
	public int getOrder()
	{
		return 1;
	}

	public void onApplicationEvent(TaskCompleteEvent event)
	{
		// ## 数据获取和准备
		// 事件对象
		BpmDelegateTask delegateTask = (BpmDelegateTask) event.getSource();
		String currentNodeId = delegateTask.getTaskDefinitionKey();
		String currentExecutionId = delegateTask.getExecutionId();
		String instId = (String) delegateTask.getVariable(BpmConstants.PROCESS_INST_ID);

		// 任务完成数据
		TaskFinishCmd cmd = (TaskFinishCmd) ContextThreadUtil.getActionCmd();
		// 退回时的目标节点
		String rejectTargetNodeId = cmd.getDestination();
		// 节点状态
		NodeStatus nodeStatus = getNodeStatus(cmd);
		String bpmnTaskId = cmd.getTaskId();
		DefaultBpmTask bpmTask = bpmTaskManager.getByRelateTaskId(bpmnTaskId);
		BpmDelegateTask task = natTaskService.getByTaskId(bpmTask.getTaskId());
		// 解决嵌入子流程时有一条先完成时会导致另一条的执行ID变化了，需要更新
		if (!task.getExecutionId().equals(bpmTask.getExecId()))
		{
			bpmTask.setExecId(task.getExecutionId());
			bpmTaskManager.update((DefaultBpmTask) bpmTask);
		}

		ActionCmd actionCmd = ContextThreadUtil.getActionCmd();
		String skipType = (String) actionCmd.getTransitVars().get(BpmConstants.BPM_SKIP_TYPE);
		// ## 操作
		// 删除候选人和任务
		delCandidateAndTask(bpmnTaskId);
		// 更新审批意见
		updateCheckOpinion(cmd, skipType);
		// 更新节点状态
		updateProcStatus(instId, delegateTask, nodeStatus, actionCmd);
		// 更新转办代理任务。
		updTaskTurnComplte(cmd.getTaskId());
		// 删除沟通任务相关数据。
		updTaskCommuComplete(bpmTask.getId());
		// 更新堆栈数据。
		updStack(bpmTask, actionCmd);
		// 更新流程实例。
		updProcessInstance(cmd);
		// 更新审批剩余时间
		updDueTime(bpmTask);

		// 如果是驳回按流程图执行，则删除之后的所有任务和堆栈关系
		if (actionCmd != null){
			Object isBackCancelBoject = actionCmd.getTransitVars(BpmExeStack.HAND_MODE_NORMAL_IS_CANCLE_NODE_PATH_TASK);
			// 是否是按流图执行且退回到发起人
			if (isBackCancelBoject != null){
				String notIncludeExecuteIds = "";
				// 当前执行
				ActExecution currentExecution = actExecutionDao.get(currentExecutionId);
				String processInstanceId = delegateTask.getProcessInstanceId();

				// 多实例子流程驳回时需要特殊处理
				Object objToken = natProInstanceService.getVariable(currentExecutionId, BpmConstants.TOKEN_NAME);
				String currentToken = objToken != null ? objToken.toString() : null;
				// 是否是多实例子流程内部之间的退回
				boolean isMultiInnerReject = false;
				
				// 多实例子流程
				if (currentToken != null && !StringUtil.isEmpty(currentToken) && StringUtil.isNotZeroEmpty(currentToken))
				{
					isMultiInnerReject = true;
					// 取上级的执行线程
					currentExecution = actExecutionDao.get(currentExecution.getParentId());

					notIncludeExecuteIds = currentExecutionId + "," + currentExecution.getId();

					// 是否为多实例子流程内部退回到主流程
					boolean isHaveMultiGateway = BpmStackRelationUtil.isHaveMultiGatewayByBetweenNode(instId, rejectTargetNodeId, currentNodeId);

					if (isHaveMultiGateway){
						isMultiInnerReject = false;
						// 多实例子流程按流程图走且退到主流程
						String rejectAfterExecutionId = currentExecution.getId();
						cmd.addTransitVars("rejectAfterExecutionId", rejectAfterExecutionId);
					} 


					currentExecution = actExecutionDao.get(currentExecution.getParentId());
					notIncludeExecuteIds = notIncludeExecuteIds + "," + currentExecution.getId();

				} 
				else
				{
					currentExecution.setParentId(processInstanceId);
					actExecutionDao.update(currentExecution);

					// 是否为子流程或者并行网关内退回到主流程
					boolean isHaveGateway = BpmStackRelationUtil.isHaveAndOrGateway(instId, currentNodeId, "pre");
					if (isHaveGateway)
					{
						String rejectAfterExecutionId = currentExecution.getId();
						cmd.addTransitVars("rejectSingleExecutionId", rejectAfterExecutionId);
					}
				}

				String targetNodePath = (String) actionCmd.getTransitVars(BpmExeStack.HAND_MODE_NORMAL_TARGET_NODE_PATH);
			
				if (!isMultiInnerReject){
					if (notIncludeExecuteIds.equals(""))
						notIncludeExecuteIds = currentExecutionId;
					Object rejectAfterExecutionId = actionCmd.getTransitVars("rejectSingleExecutionId");
					if(rejectAfterExecutionId==null || StringUtil.isEmpty(rejectAfterExecutionId.toString())){
						// 删除Activiti的执行时表,当前主线程不可删除
						bpmExeStackDao.removeActRuExeCutionByPath(instId, targetNodePath, notIncludeExecuteIds);
					}
					
					bpmExeStackDao.removeBpmTaskCandidateByPath(instId, targetNodePath);
					// bpmExeStackDao.removeBpmTaskTurnByPath(instId,
					// targetNodePath);
					//在流程驳回和驳回发起人时，不要删除流程实例相关的bpmProStatus
//					bpmExeStackDao.removebpmProStatusPath(instId, targetNodePath);
					// 删除待办任务
					bpmExeStackDao.removeBpmTaskByPath(instId, targetNodePath);
					// 删除堆栈关系
					bpmExeStackDao.removeBpmExeStackRelation(instId, targetNodePath);
					// 删除堆栈
					bpmExeStackDao.removeByPath(instId, targetNodePath);
				} 
				else{
					// 多实例子流程同部间驳回的特殊处理，主要解决堆栈没有删除会导致重复问题，保需要删除堆栈和堆栈关系
					// 删除堆栈关系
					bpmExeStackDao.removeBpmExeStackRelation(instId, targetNodePath);
					// 删除堆栈
					bpmExeStackDao.removeByPath(instId, targetNodePath);
				}

				actionCmd.getTransitVars().remove(BpmExeStack.HAND_MODE_NORMAL_IS_CANCLE_NODE_PATH_TASK);
			}else{
				//处理动作
				String actionName = cmd.getActionName();
				//如果是驳回：reject或backToStart
				if(StringUtil.isNotEmpty(actionName) && ("reject".equals(actionName) || "backToStart".equals(actionName))){
					String handMode = (String) actionCmd.getTransitVars(BpmConstants.BACK_HAND_MODE);
					//如果驳回的模式是直来直往：direct
					if(StringUtil.isNotEmpty(handMode) && handMode.equals(BpmExeStack.HAND_MODE_DIRECT)){
						// 当前执行
						ActExecution currentExecution = actExecutionDao.get(currentExecutionId);
						List<BpmNodeDef> listBpmNodeDef = BpmStackRelationUtil.getHistoryListBpmNodeDef(instId,currentExecution.getActId(), "pre");
						//如果是同步网关
						if(BeanUtils.isNotEmpty(listBpmNodeDef) && listBpmNodeDef.get(0).getType().equals(NodeType.PARALLELGATEWAY)){
							//当前记录为并行并且父ID等于流程实例ID
							if(currentExecution.getIsConcurrent().toString().equals("1") ){
								cmd.addTransitVars("rejectDirectExecutionId", currentExecution.getId());
								cmd.addTransitVars("rejectDirectParentId", currentExecution.getParentId());
							}
						}
					}
				}
				
			}

		}

	}

	private void updDueTime(BpmTask bpmTask) {
		BpmTaskDueTime bpmTaskDueTime = bpmTaskDueTimeManager.getByTaskId(bpmTask.getId());
		if(BeanUtils.isEmpty(bpmTaskDueTime))return;
		int remainingTime=0;
		if("caltime".equals(bpmTaskDueTime.getDateType())){
			// getSecondDiff 秒
			remainingTime = TimeUtil.getSecondDiff(new Date(), bpmTaskDueTime.getStartTime())/60;
		}else{
			// getWorkTimeByUser 毫秒
			remainingTime =(int) (iCalendarService.getWorkTimeByUser(bpmTaskDueTime.getUserId(), bpmTaskDueTime.getStartTime(), new Date())/60000);
		}
		remainingTime = bpmTaskDueTime.getDueTime() - remainingTime;
		if(remainingTime<=0){
			remainingTime = 0;
		}
		bpmTaskDueTime.setRemainingTime(remainingTime);
		bpmTaskDueTimeManager.update(bpmTaskDueTime);
	}

	/**
	 * 更新流程实例状态。
	 * 
	 * @param cmd
	 *            void
	 */
	private void updProcessInstance(TaskFinishCmd cmd)
	{
		DefaultBpmProcessInstance bpmProcessInstance = (DefaultBpmProcessInstance) cmd.getTransitVars(BpmConstants.PROCESS_INST);
		ProcessInstanceStatus status = getInstStatus(cmd);

		if (!status.getKey().equals(bpmProcessInstance.getStatus())){
			bpmProcessInstance.setStatus(status.getKey());
			bpmProcessInstanceManager.update(bpmProcessInstance);
		}
	}

	/**
	 * 更新堆栈数据。 结束时
	 * 
	 * @param bpmTask
	 *            void
	 * @param delegateTask
	 */
	private void updStack(BpmTask bpmTask, ActionCmd cmd)
	{
		if (!(cmd instanceof TaskFinishCmd)) return;
		TaskFinishCmd finishCmd = (TaskFinishCmd) cmd;

		if (!ActionType.APPROVE.equals(finishCmd.getActionType())) 	return;
		Object tokenObj = natProInstanceService.getVariable(bpmTask.getExecId(), BpmConstants.TOKEN_NAME);
		String token = tokenObj == null ? null : tokenObj.toString();

		if (token == null)
		{
			BpmDelegateTask task = natTaskService.getByTaskId(bpmTask.getTaskId());
			token = task.getVariable(BpmConstants.TOKEN_NAME) != null ? task.getVariable(BpmConstants.TOKEN_NAME).toString() : null;
		}

		BpmExeStack bpmExeStack = bpmExeStackManager.getStack(bpmTask.getProcInstId(), bpmTask.getNodeId(), token);
		if (bpmExeStack == null)
			// 当驳回到并行之前的节点时不需要token
			bpmExeStack = bpmExeStackManager.getStack(bpmTask.getProcInstId(), bpmTask.getNodeId(), null);
		// TODO delete 兼容错误数据 旧流程任务没有堆栈信息，
		if (bpmExeStack == null)
			return;

		bpmExeStack.setEndTime(new Date());

		bpmExeStackManager.update(bpmExeStack);
		// 更新堆栈执行人信息。
		BpmExeStackExecutor executor = bpmExeStackExecutorDao.getByTaskId(bpmTask.getId());
		// 将变量设置临时变量。
		cmd.addTransitVars(BpmConstants.PARENT_STACK, bpmExeStack);

		// 驳回至该节点时。executor 通过taskId 取不到
		if (executor == null)
		{
			List<BpmExeStackExecutor> executorList = bpmExeStackExecutorDao.getByStackId(bpmExeStack.getId());
			if (executorList.size() == 1)
				executor = executorList.get(0);
			else
				return;
		}
		// 设置任务执行人
		executor.setAssigneeId(ContextUtil.getCurrentUserId());

		executor.setEndTime(new Date());
		// 是否干预
		if (finishCmd.isInterpose())
		{
			executor.setStatus(2);
		} else
		{
			executor.setStatus(1);
		}
		bpmExeStackExecutorDao.update(executor);
	}

	/**
	 * 更新沟通任务， 主要是删除沟通任务。
	 * 
	 * @param taskId
	 *            void
	 */
	private void updTaskCommuComplete(String taskId)
	{
		taskCommuService.finishTask(taskId);
	}

	/**
	 * 更新转办代理任务为完成。
	 * 
	 * @param taskId
	 *            void
	 */
	private void updTaskTurnComplte(String taskId)
	{
		IUser user = ContextUtil.getCurrentUser();
		bpmTaskTurnManager.updComplete(taskId, user);
	}

	private ProcessInstanceStatus getInstStatus(TaskFinishCmd cmd)
	{
		ProcessInstanceStatus status = ProcessInstanceStatus.STATUS_RUNNING;
		String action = cmd.getActionName();
		switch (cmd.getActionType())
		{
		case APPROVE:
			status = ProcessInstanceStatus.STATUS_RUNNING;
			break;
		case BACK:
			if ("toStart".equals(action))
			{
				status = ProcessInstanceStatus.STATUS_BACK_TOSTART;
			} else
			{
				status = ProcessInstanceStatus.STATUS_BACK;
			}
			break;
		case RECOVER:
			if ("toStart".equals(action))
			{
				status = ProcessInstanceStatus.STATUS_REVOKE_TOSTART;
			} else
			{
				status = ProcessInstanceStatus.STATUS_REVOKE;
			}
			break;
		}
		return status;
	}

	private NodeStatus getNodeStatus(TaskFinishCmd cmd){
		NodeStatus nodeStatus = NodeStatus.AGREE;
		String action = cmd.getActionName();
		// 在已办中撤回
		Object isDoneUnused = cmd.getTransitVars("IsDoneUnused");
		if (isDoneUnused != null){
			nodeStatus = NodeStatus.RECOVER;
			return nodeStatus;
		}
		switch (cmd.getActionType()){
			case APPROVE:
				nodeStatus = NodeStatus.fromKey(cmd.getActionName());
				break;
			case BACK_TO_START:
				nodeStatus = NodeStatus.BACK_TO_START;
				break;
			case BACK:
				if ("toStart".equals(action) || "backToStart".equals(action)){
					nodeStatus = NodeStatus.BACK_TO_START;
				} 
				else{
					nodeStatus = NodeStatus.BACK;
				}
				break;
			case RECOVER:
				if ("toStart".equals(action)){
					nodeStatus = NodeStatus.RECOVER_TO_START;
				} 
				else{
					nodeStatus = NodeStatus.RECOVER;
				}
				break;
		}
		return nodeStatus;
	}

	/**
	 * 更新审批意见
	 * 
	 * @param cmd
	 * @param taskId
	 *            void
	 */
	private void updateCheckOpinion(TaskFinishCmd cmd, String skipType)
	{
		boolean isSkip = BeanUtils.isNotEmpty(skipType);

		DefaultBpmCheckOpinion bpmCheckOpinion = bpmCheckOpinionManager.getByTaskId(cmd.getTaskId());

		if (bpmCheckOpinion == null) 	return;

		IUser user = ContextUtil.getCurrentUser();

		String status = getStatus(cmd, isSkip);

		bpmCheckOpinion.setStatus(status);
		bpmCheckOpinion.setCompleteTime(new java.util.Date());
		long durMs = bpmCheckOpinion.getCompleteTime().getTime() - bpmCheckOpinion.getCreateTime().getTime();
		bpmCheckOpinion.setDurMs(durMs);
		if (isSkip && SkipResult.SKIP_EMPTY_USER.equals(skipType)){
			bpmCheckOpinion.setAuditor("");
			bpmCheckOpinion.setAuditorName("");
		}else if(SkipResult.SKIP_APPROVER.equals(skipType)){
			bpmCheckOpinion.setAuditor(String.valueOf(ContextThreadUtil.getCommuVar(SkipResult.SKIP_APPROVER_AUDITOR,"")));
			bpmCheckOpinion.setAuditorName(String.valueOf(ContextThreadUtil.getCommuVar(SkipResult.SKIP_APPROVER_AUDITORNAME, "")));
		} 
		else{
			String userId = BpmConstants.SYSTEM_USER_ID;
			String userName = BpmConstants.SYSTEM_USER_NAME;
			if (user != null){
				userId = user.getUserId();
				userName = user.getFullname();
			}
			bpmCheckOpinion.setAuditor(userId);
			bpmCheckOpinion.setAuditorName(userName);
		}

		String opinion = cmd.getApprovalOpinion();
		if (isSkip){
			if (SkipResult.SKIP_FIRST.equals(skipType)){
				opinion = "跳过第一个任务节点";
			} 
			else if (SkipResult.SKIP_EMPTY_USER.equals(skipType)){
				opinion = "执行人为空";
			} 
			else if (SkipResult.SKIP_SAME_USER.equals(skipType)){
				opinion = "和上一个节点执行人相同跳过!";
			}else if( SkipResult.SKIP_APPROVER.equals(skipType) ){
				opinion = "审批跳过";
			}
		}
		// 干预的情况。
		if (cmd.isInterpose())
		{
			bpmCheckOpinion.setInterpose(1);
		}

		/**
		 * 设置意见标识。
		 */
		bpmCheckOpinion.setFormName(cmd.getOpinionIdentity());

		bpmCheckOpinion.setOpinion(opinion);
		bpmCheckOpinion.setFiles(cmd.getFiles());

		bpmCheckOpinionManager.update(bpmCheckOpinion);
		
	}

	/**
	 * 获取意见状态。
	 * 
	 * @param cmd
	 * @param isSkip
	 * @return String
	 */
	private String getStatus(TaskFinishCmd cmd, boolean isSkip)
	{
		if (isSkip){
			return OpinionStatus.SKIP.getKey();
		}
		// 在已办中撤回
		Object isDoneUnused = cmd.getTransitVars("IsDoneUnused");
		if (isDoneUnused != null){
			return OpinionStatus.REVOKER.getKey();
		}
		return cmd.getActionName();

	}

	/**
	 * 删除候选人，删除任务。
	 * 
	 * @param taskId
	 *            void
	 */
	private void delCandidateAndTask(String taskId)
	{
		bpmTaskCandidateManager.removeByTaskId(taskId);
		bpmTaskManager.remove(taskId);
	}

	/**
	 * 更新流程状态
	 * 
	 * @param delegateTask
	 * @param nodeStatus
	 *            void
	 */
	private void updateProcStatus(String instId, BpmDelegateTask delegateTask, NodeStatus nodeStatus,ActionCmd actionCmd)
	{
		String bpmnDefId = delegateTask.getBpmnDefId();
		String nodeId = delegateTask.getTaskDefinitionKey();

		MultiInstanceType type = delegateTask.multiInstanceType();

		if (MultiInstanceType.NO.equals(type))
		{
			bpmProStatusManager.createOrUpd(instId, bpmnDefId, nodeId, delegateTask.getName(), nodeStatus);
		}
		
		//更新流程相关实例状态
		if(nodeStatus.getKey().equals(NodeStatus.BACK_TO_START.getKey())||nodeStatus.getKey().equals(NodeStatus.BACK.getKey())){
			if (actionCmd != null){
				String backHandMode = (String) actionCmd.getTransitVars().get(BpmConstants.BACK_HAND_MODE);
				if(StringUtil.isEmpty(backHandMode)){
					throw new RuntimeException("backHandMode不能为空");
				}
				if(backHandMode.equals("normal")){
					List<String> instList=new ArrayList<String>();
					instList.add(instId);
					List<DefaultBpmCheckOpinion> checkOpinions = bpmCheckOpinionManager.getByInstIdsAndWait(instList);
					for(DefaultBpmCheckOpinion checkOpinion:checkOpinions){
						// 更新意见状态为驳回取消。
						bpmCheckOpinionManager.updStatusByWait(checkOpinion.getTaskId(),OpinionStatus.SIGN_BACK_CANCEL.getKey());
					}
					// 节点的代办状态更新为驳回取消
					bpmProStatusManager.updStatusByInstList(instList, NodeStatus.SIGN_BACK_CANCLE);
				}
			}
		}
		
	}
	
	
}
