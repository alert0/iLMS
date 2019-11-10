package com.hotent.bpmx.core.engine.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.AopType;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.event.DoNextEvent;
import com.hotent.bpmx.api.event.DoNextModel;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.BpmTaskTurn;
import com.hotent.bpmx.api.plugin.core.def.TaskActionHandlerDef;
import com.hotent.bpmx.api.plugin.core.factory.BpmPluginSessionFactory;
import com.hotent.bpmx.api.plugin.core.runtime.TaskActionHandler;
import com.hotent.bpmx.api.plugin.core.session.TaskActionPluginSession;
import com.hotent.bpmx.api.plugin.core.task.TaskActionHandlerConfig;
import com.hotent.bpmx.api.service.BpmTaskActionService;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.core.util.MessageUtil;
import com.hotent.bpmx.natapi.task.NatTaskService;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmTaskCandidateManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.manager.BpmTaskTurnManager;
import com.hotent.bpmx.persistence.manager.TaskTurnAssignManager;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.bpmx.persistence.util.BpmCheckOpinionUtil;
import com.hotent.bpmx.persistence.util.BpmStackRelationUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.model.IdentityType;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * <pre>
 * 描述：TODO
 * 构建组：x5-bpmx-core
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-4-4-下午4:16:15
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class DefaultBpmTaskActionService implements BpmTaskActionService {
	@Resource
	BpmTaskManager bpmTaskManager;

	@Resource
	NatTaskService natTaskService;

	@Resource
	BpmPluginSessionFactory bpmPluginSessionFactory;

	@Resource
	TaskActionHandlerConfig taskActionHandlerConfig;
	@Resource
	BpmTaskTurnManager bpmTaskTurnManager;
	@Resource
	TaskTurnAssignManager taskTurnAssignManager;

	@Resource
	BpmTaskCandidateManager bpmTaskCandidateManager;

	@Resource
	IUserService userServiceImpl;
	
	@Resource 
	BpmDefinitionAccessor bpmDefinitionAccessor;
	
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;

	@Override
	public boolean finishTask(TaskFinishCmd taskCmd) {

		DefaultTaskFinishCmd cmd = (DefaultTaskFinishCmd) taskCmd;
		ContextThreadUtil.setActionCmd(cmd);

		BpmTask bpmTask = (BpmTask) cmd.getTransitVars(BpmConstants.BPM_TASK);

		BpmDelegateTask task = null;
		// 流程引擎的任务。
		if (StringUtil.isNotZeroEmpty(bpmTask.getTaskId())) {
			task = natTaskService.getByTaskId(bpmTask.getTaskId());
		}

		// 前置事件抛出
		DoNextModel donextModel = new DoNextModel(cmd, AopType.PREVIOUS);
		DoNextEvent donextEv = new DoNextEvent(donextModel);
		AppUtil.publishEvent(donextEv);

		// 构造会话数据
		TaskActionPluginSession taskActionPluginSession = bpmPluginSessionFactory.buildTaskActionPluginSession(task, taskCmd);
		// 获得任务操作句柄
		TaskActionHandler handler = taskActionHandlerConfig.getTaskActionHandler(taskCmd.getActionName());
		// 获得任务操作定义
		TaskActionHandlerDef def = (TaskActionHandlerDef) taskActionHandlerConfig.getTaskActionHandlerDef(taskCmd.getActionName());
		// 执行和返回
		boolean result = handler.execute(taskActionPluginSession, def);

		// 后置事件抛出
		DoNextModel donextAfterModel = new DoNextModel(cmd, AopType.POST);
		DoNextEvent donextAfterEv = new DoNextEvent(donextAfterModel);
		AppUtil.publishEvent(donextAfterEv);
		
		// 退回时调整 调整Activiti的执行表数据
		ActionCmd finsActionCmd = ContextThreadUtil.getActionCmd();
		Object rejectAfterExecutionId = finsActionCmd.getTransitVars("rejectAfterExecutionId");
		if (BeanUtils.isNotEmpty(rejectAfterExecutionId)) {
			// 调整Activiti的执行表数据
			BpmStackRelationUtil.multipleInstancesRejectAdjust(rejectAfterExecutionId.toString());
		}else{
			Object rejectSingleExecutionId = finsActionCmd.getTransitVars("rejectSingleExecutionId");
			if (BeanUtils.isNotEmpty(rejectSingleExecutionId)) {
				// 调整Activiti的执行表数据
				BpmStackRelationUtil.singleInstancesRejectAdjust(rejectSingleExecutionId.toString());
			}
		}
		
		Object rejectDirectExecutionId = finsActionCmd.getTransitVars("rejectDirectExecutionId");
		if (BeanUtils.isNotEmpty(rejectDirectExecutionId)) {
			// 调整Activiti的执行表数据
			BpmStackRelationUtil.instancesRejectDirectAdjust(rejectDirectExecutionId.toString());
		}
		
		if(BeanUtils.isNotEmpty(task)){
			BpmStackRelationUtil.parallelGatewayRejectDirectAdjust(task.getExecutionId());
		}
		
		return result;
	}
	
	@Override
	public void create(BpmDelegateTask delegateTask) {
		BpmTask task = BpmUtil.convertTask(delegateTask);
		this.bpmTaskManager.create((DefaultBpmTask) task);

	}

	@Override
	public void remove(String taskId) {
		this.bpmTaskManager.delByRelateTaskId(taskId);
	}

	/**
	 * 任务转办
	 */
	public void delegate(String taskId, String toUser, String notifyType, String opinion) throws Exception {
		BpmTask bpmTask = bpmTaskManager.get(taskId);

		// 1.判断改任务是否处于转办中
		BpmTaskTurn bpmTaskTurn = bpmTaskTurnManager.getByTaskId(taskId);

		// 2.设置任务执行人。
		bpmTaskManager.delegate(taskId, toUser);
		IUser user = userServiceImpl.getUserById(toUser);
		List<IUser> userList = new ArrayList<IUser>();
		userList.add(user);

		// 3.添加BPM_TASK_TURN中。
		// 如果已经处于转办中.. 新增转办记录
		if (bpmTaskTurn == null) {
			bpmTaskTurnManager.add((DefaultBpmTask) bpmTask, ContextUtil.getCurrentUser(), user, opinion, BpmTaskTurn.TYPE_TURN);
			//新增一条转办审批意见
			DefaultBpmCheckOpinion checkOpinion = bpmCheckOpinionManager.getByTaskIdStatus(taskId,OpinionStatus.AWAITING_CHECK.getKey());
			checkOpinion.setId(UniqueIdUtil.getSuid());
			checkOpinion.setStatus(OpinionStatus.DELIVERTO.getKey());
			checkOpinion.setCompleteTime(new Date());
			checkOpinion.setAuditor(ContextUtil.getCurrentUserId());
			checkOpinion.setAuditorName(ContextUtil.getCurrentUser().getFullname());
			checkOpinion.setOpinion(StringUtil.isEmpty(opinion)?"任务转办":opinion);
			checkOpinion.setDurMs(0L);
			checkOpinion.setQualfieds("");
			checkOpinion.setQualfiedNames(ContextUtil.getCurrentUser().getFullname());
			bpmCheckOpinionManager.create(checkOpinion);
		} else {
			// 修改承接人，流程状态为running
			if(bpmTaskTurn.getAssigneeId().equals(toUser)){
				throw new RuntimeException("任务已交由【"+bpmTaskTurn.getAssigneeName()+"】处理！");
			}
			DefaultBpmTaskTurn turn = (DefaultBpmTaskTurn) bpmTaskTurn;
			turn.setStatus(BpmTaskTurn.STATUS_RUNNING);
			turn.setAssigneeId(user.getUserId());
			turn.setAssigneeName(user.getFullname());
			bpmTaskTurnManager.update(turn);
			// 创建一份转办信息
			bpmTaskTurnManager.addTurnAssign(turn.getId(), user, opinion);
		}
		
		//4、修改审批意见中的执行人
		DefaultBpmCheckOpinion checkOpinion = bpmCheckOpinionManager.getByTaskId(taskId);
		if(BeanUtils.isNotEmpty(checkOpinion)){
			checkOpinion.setCreateTime(new Date());
			List<BpmIdentity> identityList = new ArrayList<BpmIdentity>();
			BpmIdentity bpmIdentity = new DefaultBpmIdentity();
			bpmIdentity.setType(IdentityType.USER);
			bpmIdentity.setId(user.getUserId());
			bpmIdentity.setName(user.getFullname());
			identityList.add(bpmIdentity);
			checkOpinion.setQualfieds(BpmCheckOpinionUtil.getIdentityIds(identityList));
			checkOpinion.setQualfiedNames(user.getFullname());
			bpmCheckOpinionManager.update(checkOpinion);
		}

		// 5.通知相关人员。
		Map<String, Object> vars = getVars(bpmTask, opinion);

		MessageUtil.sendMsg(TemplateConstants.TYPE_KEY.BPM_HAND_TO, notifyType, userList, vars);
	}
	
	
	private Map<String, Object> getVars(BpmTask task, String opinion) {
		String baseUrl = SysPropertyUtil.getByAlias(TemplateConstants.TEMP_VAR.BASE_URL);
		IUser user = ContextUtil.getCurrentUser();
		Map<String, Object> map = new HashMap<String, Object>();
		// 转办人
		map.put(TemplateConstants.TEMP_VAR.DELEGATE, user.getFullname());
		// 任务标题
		map.put(TemplateConstants.TEMP_VAR.TASK_SUBJECT, task.getSubject());
		map.put(TemplateConstants.TEMP_VAR.BASE_URL, baseUrl);
		map.put(TemplateConstants.TEMP_VAR.TASK_ID, task.getId());
		// 意见
		map.put(TemplateConstants.TEMP_VAR.CAUSE, opinion);
		//任务名称
		map.put(TemplateConstants.TEMP_VAR.NODE_NAME,task.getName());
		//流程实例名称
		map.put(TemplateConstants.TEMP_VAR.INST_SUBJECT, task.getSubject());

		return map;

	}

	/**
	 * 根据任务ID终止流程。
	 * 
	 * <pre>
	 * 1.根据任务ID查询到BPM_TASK记录。
	 * 2.发送通知消息，通知相关人员。	
	 * 3.删除bpm_task_candidate对应记录。
	 * 4.删除BPM_TASK记录。
	 * 5.删除act_ru_identitylink记录。
	 * 6.删除act_ru_task记录。
	 * 7.删除act_ru_execution的记录.
	 * 8.标记bpm_pro_inst，bpm_pro_inst_hi为人工终止。
	 * 9.归档bpm_pro_inst 。
	 * </pre>
	 * 
	 * @param taskId
	 *            void
	 * @throws Exception
	 */
	@Override
	public void endProcessByTaskId(String taskId, String informType, String cause) throws Exception {
		bpmTaskManager.endProcessByTaskId(taskId, informType, cause);
	}

}
