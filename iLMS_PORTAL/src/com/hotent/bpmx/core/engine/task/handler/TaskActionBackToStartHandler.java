package com.hotent.bpmx.core.engine.task.handler;

import java.util.List;

import javax.annotation.Resource;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.ActionType;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.NodeProperties;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.plugin.core.def.TaskActionHandlerDef;
import com.hotent.bpmx.api.plugin.core.session.TaskActionPluginSession;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.persistence.manager.BpmExeStackExecutorManager;
import com.hotent.bpmx.persistence.manager.BpmExeStackManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.BpmExeStack;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.org.api.service.IUserService;

/**
 * 退回到发起人策略。
 * @author ray
 *
 */
public class TaskActionBackToStartHandler extends AbstractTaskActionHandler{
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmInstService bpmInstService;
	@Resource
	IUserService userServiceImpl;
	@Resource
	BpmExeStackManager bpmExeStackManager;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmExeStackExecutorManager exeStrackExecutorManager;
	
	@Override
	public boolean isNeedCompleteTask() {
		return true;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.BACK_TO_START;
	}

	@Override
	public void preActionHandler(TaskActionPluginSession pluginSession, TaskActionHandlerDef def) {
		
		DefaultTaskFinishCmd cmd= (DefaultTaskFinishCmd) pluginSession.getTaskFinishCmd();
		String instId=cmd.getInstId();
		String targeNode=cmd.getDestination();
		String handMode=(String) cmd.getTransitVars(BpmConstants.BACK_HAND_MODE);
		BpmTask task=(BpmTask) cmd.getTransitVars(BpmConstants.BPM_TASK);
		
		//如果目标节点为空，那么去上级堆栈。
		if(StringUtil.isEmpty(targeNode)){
			//检查是否为外部子流程驳回到主流程发起人
			DefaultBpmProcessInstance inst= bpmProcessInstanceManager.get(instId);
			if(StringUtil.isNotZeroEmpty(inst.getParentInstId())){
				throw new RuntimeException("多实例的内嵌子流程不允许驳回到主流程!");
				//instId=inst.getParentInstId();
			}
			BpmExeStack stack= bpmExeStackManager.getInitStack(instId);
			cmd.setDestination(stack.getNodeId());
			
			if(StringUtil.isNotEmpty(stack.getTargetNode())) throw new RuntimeException("其他人已经驳回到此节点，不允许驳回!");
			
			String destinationNode=cmd.getDestination();
			//如果不是干预执行的情况  
			if(stack.getInterpose()==0 && (short)stack.getIsMulitiTask()==0 && 
					(cmd.getTransitVars("IsDoneUnused")==null || !(Boolean)cmd.getTransitVars("IsDoneUnused"))  ){
				//是否配置了驳回时候选人为节点插件人员，
				UserTaskNodeDef  backTargetNodeDef = (UserTaskNodeDef) bpmDefinitionAccessor.getBpmNodeDef(stack.getPrcoDefId(), destinationNode);
				NodeProperties nodeProperties= backTargetNodeDef.getLocalProperties();
				String backUserMode= nodeProperties.getBackUserMode();
				if(StringUtil.isEmpty(backUserMode)||backUserMode.equals("history")){
					//从堆栈中获取执行人作为任务的执行人
					List<BpmIdentity> identitys = exeStrackExecutorManager.getBpmIdentitysByStackId(stack.getId());
					cmd.addBpmIdentity(stack.getNodeId(), identitys);
				}
			}
		}
		//出栈
		bpmExeStackManager.popStartStack(instId, task.getNodeId(), handMode);
		
	}

	@Override
	public void afterActionHandler(TaskActionPluginSession pluginSession, TaskActionHandlerDef def) {
		
	}
	
	

}
