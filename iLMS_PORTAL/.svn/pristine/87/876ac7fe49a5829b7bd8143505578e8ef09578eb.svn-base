package com.hotent.bpmx.core.engine.task.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.constant.ActionType;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.SignNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.DefaultJumpRule;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.plugin.core.def.TaskActionHandlerDef;
import com.hotent.bpmx.api.plugin.core.session.TaskActionPluginSession;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.api.service.BpmTaskActionService;
import com.hotent.bpmx.api.service.JumpRuleCalc;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.natapi.inst.NatProInstanceService;
import com.hotent.bpmx.natapi.task.NatTaskService;
import com.hotent.bpmx.persistence.dao.ActExecutionDao;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmExeStackExecutorManager;
import com.hotent.bpmx.persistence.manager.BpmExeStackManager;
import com.hotent.bpmx.persistence.manager.BpmSignDataManager;
import com.hotent.bpmx.persistence.model.BpmExeStack;

/**
 * <pre>
 * 描述：任务同意处理时执行的操作
 * 构建组：x5-bpmx-plugin-core
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014-3-18-上午11:41:38
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class TaskActionApproveHandler extends AbstractTaskActionHandler
{

	@Resource
	NatTaskService natTaskService;
	@Resource
	JumpRuleCalc jumpRuleCalc;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmTaskActionService bpmTaskActionService;
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	BpmExeStackManager bpmExeStackManager;
	@Resource
	NatProInstanceService natProInstanceService;
	@Resource
	BpmSignDataManager bpmSignDataManager;
	@Resource
	BpmExeStackExecutorManager bpmExeStackExecutorManager;
	@Resource
	ActExecutionDao actExecutionDao;

	@Override
	public boolean isNeedCompleteTask()
	{
		return true;
	}

	@Override
	public void preActionHandler(TaskActionPluginSession pluginSession, TaskActionHandlerDef def){
		// 处理流程跳转规则。
		setDestination(pluginSession);
		// 如果是会签任务，添加会签数据。
		signVote(pluginSession);
	}

	/**
	 * 如果是会签任务，添加会签数据。
	 * 
	 * @param pluginSession
	 *            void
	 */
	private void signVote(TaskActionPluginSession pluginSession){
		DefaultTaskFinishCmd cmd = (DefaultTaskFinishCmd) pluginSession.getTaskFinishCmd();
		BpmTask bpmTask = (BpmTask) cmd.getTransitVars(BpmConstants.BPM_TASK);

		String defId = bpmTask.getProcDefId();
		// 流程节点定义。
		BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, bpmTask.getNodeId());
		// 如果是会签添加会签投票。
		if (bpmNodeDef instanceof SignNodeDef){
			SignNodeDef signNodeDef=(SignNodeDef)bpmNodeDef;
			//是否并行
			boolean isParallel= signNodeDef.isParallel();
			Short index = ((Integer) natTaskService.getVariable(bpmTask.getTaskId(), BpmConstants.NUMBER_OF_LOOPCOUNTER)).shortValue();
			
			String executeId=bpmTask.getExecId();
			//根据会签的数据结构，如果为并行在往上查找一级。
			/*if(isParallel){
				ActExecution execution= actExecutionDao.get(bpmTask.getExecId());
				executeId=execution.getParentId();
			}*/
			//获取execute 的父ID
			bpmSignDataManager.vote(executeId, bpmTask.getNodeId(), index, cmd.getActionName());
		}
	}

	/**
	 * 处理流程跳转规则。
	 * 
	 * <pre>
	 * 1.如果目标节点不为空，那么直接返回。
	 * 2.从堆栈中获取数据，如果堆栈中有目标节点，则跳转至目标节点。
	 * 	更新堆栈目标节点为空。
	 * 3.根据跳转规则获取目标节点。
	 * </pre>
	 * 
	 * @param pluginSession
	 *            void
	 */
	private void setDestination(TaskActionPluginSession pluginSession){

		// 1.如果目标节点不为空，那么直接返回。
		DefaultTaskFinishCmd cmd = (DefaultTaskFinishCmd) pluginSession.getTaskFinishCmd();
		if (StringUtil.isNotEmpty(cmd.getDestination())) return;
		
		String instId = cmd.getInstId();
		BpmTask bpmTask = (BpmTask) cmd.getTransitVars(BpmConstants.BPM_TASK);
		String nodeId = bpmTask.getNodeId();
		Object tokenObject = natProInstanceService.getVariableLocal(bpmTask.getExecId(), BpmConstants.TOKEN_NAME);
		String token = tokenObject != null ? tokenObject.toString() : null;
		// 2.从当前堆栈中获取。
		String targetNode = "";
		String targetToken="";
		BpmExeStack bpmExeStack = bpmExeStackManager.getStack(instId, nodeId, token);
		if (bpmExeStack == null)
			bpmExeStack = bpmExeStackManager.getStack(instId, nodeId, null);
		if (bpmExeStack != null){
			targetNode = bpmExeStack.getTargetNode();
			targetToken=bpmExeStack.getTargetToken();
			// 不会空跳转至该节点。设置该节点办理人为堆栈信息中的那个人
			if (StringUtil.isNotEmpty(targetNode)){
				BpmExeStack targetStack = bpmExeStackManager.getStack(instId, targetNode, targetToken);
				if (targetStack != null){
					cmd.addBpmIdentity(targetNode, bpmExeStackExecutorManager.getBpmIdentitysByStackId(targetStack.getId()));
				}
				cmd.setDestination(targetNode);
				return;
			}
		}

		// 3.计算跳转规则。
		calcJumpRules(bpmTask,cmd);
	}
	
	/**
	 * 计算跳转规则。
	 * @param bpmTask
	 * @param cmd 
	 * void
	 */
	private void calcJumpRules(BpmTask bpmTask,DefaultTaskFinishCmd cmd){
		String nodeId = bpmTask.getNodeId();
		String defId = bpmTask.getProcDefId();
		UserTaskNodeDef bpmNodeDef = (UserTaskNodeDef) bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		List<DefaultJumpRule> ruleList = bpmNodeDef.getJumpRuleList();
		if (BeanUtils.isEmpty(ruleList)) return ;

		Map<String, Object> vars = natTaskService.getVariables(bpmTask.getTaskId());
		//加入实例对象。
		Map<String,BoData> boMap= BpmContextUtil.getBoFromContext();
		
		vars.putAll(cmd.getVariables());
		vars.putAll(boMap);
		String targetNode = jumpRuleCalc.eval(ruleList, vars);
		if (StringUtil.isNotEmpty(targetNode)){
			cmd.setDestination(targetNode);
		}
	}

	@Override
	public void afterActionHandler(TaskActionPluginSession pluginSession, TaskActionHandlerDef def){

		DefaultTaskFinishCmd finishCmd = (DefaultTaskFinishCmd) pluginSession.getTaskFinishCmd();
		String instId = finishCmd.getInstId();
		BpmTask bpmTask=(BpmTask) finishCmd.getTransitVars(BpmConstants.BPM_TASK);
		String nodeId = BeanUtils.isEmpty(bpmTask)?"":bpmTask.getNodeId();
		// 处理跳过任务。
		handSkipTask(instId,nodeId);
		
	}

	/**
	 * 处理任务跳过。
	 * 
	 * <pre>
	 * 	处理两种跳过情况（为了防止死循环，当当前任务节点和下一个将要跳过的任务节点是同一个节点时，将不执行跳过操作）。
	 *  1.两个节点执行人相同的情况允许跳过。
	 *  2.系统配置了执行人允许为空，且执行人为空时允许跳过。
	 * </pre>
	 * 
	 * @param instId
	 *            void
	 */
	private void handSkipTask(String instId,String curentNodeId){
		Set<BpmTask> set = ContextThreadUtil.getByInstId(instId);
		
		if(BeanUtils.isEmpty(set)){
			instId = (String) ContextThreadUtil.getCommuVar(BpmConstants.PROCESS_INST_ID, instId);
			set = ContextThreadUtil.getByInstId(instId);
		}
		
		ContextThreadUtil.clearTaskByInstId(instId);
		// 删除相关的任务。
		if (BeanUtils.isEmpty(set))  return;
		for (Iterator<BpmTask> it = set.iterator(); it.hasNext();){
			BpmTask task = it.next();
			if(task.isGateWay()) continue;
			BpmUtil.setTaskSkip(task);

			if (task.getSkipResult().isSkipTask()&&!task.getNodeId().equals(curentNodeId)){
				BpmUtil.finishTask(task);
			}
		}
	}

	@Override
	public ActionType getActionType(){
		return ActionType.APPROVE;
	}

}
