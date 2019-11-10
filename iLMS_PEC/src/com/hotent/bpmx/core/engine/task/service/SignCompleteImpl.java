package com.hotent.bpmx.core.engine.task.service;

import java.util.List;

import javax.annotation.Resource;

import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.ActionType;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.MultiInstanceType;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.plugin.core.execution.sign.SignActionHandler;
import com.hotent.bpmx.api.plugin.core.execution.sign.SignResult;
import com.hotent.bpmx.api.service.SignComplete;
import com.hotent.bpmx.core.engine.execution.sign.handler.SignActionHandlerContainer;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;
import com.hotent.bpmx.persistence.manager.BpmSignDataManager;
import com.hotent.bpmx.persistence.manager.BpmTaskCandidateManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;

/**
 * 判断会签是否完成。
 * <pre>
 * 会签完成后的动作。
 * 1.更新会签节点的状态。
 * 2.清除会签结果数据。
 * 3.更新未审批的意见状态为取消。
 * 4.如果完成类型为审批，那么设置最终结果的流程变量，供以后的分支做判断使用。
 *  
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱：zhangyg@jee-soft.cn
 * 日期：2014-4-1-上午10:38:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class SignCompleteImpl implements SignComplete{	

	@Resource
	SignActionHandlerContainer actionHandlerContainer; 
	@Resource
	BpmProStatusManager bpmProStatusManager;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	BpmSignDataManager bpmSignDataManager; 
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmTaskCandidateManager bpmTaskCandidateManager;
	
	
	@Override
	public boolean isComplete(BpmDelegateExecution bpmDelegateExecution) {
		String instId=(String) bpmDelegateExecution.getVariable(BpmConstants.PROCESS_INST_ID);
		//获得任务数据
		TaskFinishCmd taskFinishCmd=(TaskFinishCmd)ContextThreadUtil.getActionCmd();
		//根据actiontype 获取处理器。
		SignActionHandler handler=actionHandlerContainer.getSignActionHandler(taskFinishCmd.getActionType().getKey());
		
		SignResult result= handler.handByActionType(taskFinishCmd,bpmDelegateExecution);
		
		handComplete(result,instId,bpmDelegateExecution,taskFinishCmd.getActionType());
		
		return result.isComplete();
	}
	
	
	/**
	 * 根据会签结果完成最终会签结果的处理。
	 * @param result
	 * @param instId
	 * @param bpmDelegateExecution
	 * @param actionType 
	 * void
	 */
	private void handComplete(SignResult result,String instId,BpmDelegateExecution bpmDelegateExecution,ActionType actionType){
		
		if(!result.isComplete()) return;
		
		String nodeId=bpmDelegateExecution.getNodeId();
		String nodeName=bpmDelegateExecution.getNodeName();
		
		String defId=bpmDefinitionManager.getDefIdByBpmnDefId(bpmDelegateExecution.getBpmnDefId());
		
		//更新节点状态。,结果为同意的修改为：会签通过，否则为会签不通过状态
		NodeStatus signNodeStatus = null;
		if(result.getNodeStatus().getKey().equals(NodeStatus.AGREE.getKey())){
			signNodeStatus = NodeStatus.SIGN_PASS;
		}else if(result.getNodeStatus().getKey().equals(NodeStatus.OPPOSE.getKey())){
			signNodeStatus = NodeStatus.SIGN_NO_PASS;
		}else{
			//获得任务数据
			TaskFinishCmd taskFinishCmd=(TaskFinishCmd)ContextThreadUtil.getActionCmd();
			signNodeStatus = getNodeStatus(taskFinishCmd);
		}
		bpmProStatusManager.createOrUpd(instId, defId, nodeId, nodeName,signNodeStatus);
		

		boolean isParallel=!MultiInstanceType.SEQUENTIAL.equals(bpmDelegateExecution.multiInstanceType());
		
		String executeParentId=isParallel?bpmDelegateExecution.getParentExecution().getParentExecution().getId():bpmDelegateExecution.getParentId();
		
		//删除之前的非活动审批记录。
		bpmSignDataManager.removeByNotActive(executeParentId, nodeId);
		//更新会签记录为不活动。
		bpmSignDataManager.updByNotActive(executeParentId, nodeId);
		//删除任务和候选人，更新代审批意见数据为取消
		updOpinion(executeParentId, nodeId,result.getOpinionStatus().getKey());
		
		if(ActionType.APPROVE.equals(actionType)){
			String resultVarName=BpmConstants.SIGN_RESULT + nodeId;
			//设置最终审批结果,以供其后的分支网关做判断。
			bpmDelegateExecution.setVariable(resultVarName, result.getNodeStatus().getKey());
		}
		//删除串行会签节点人员变量。
		String varName=BpmConstants.SIGN_USERIDS + nodeId;
		if(MultiInstanceType.SEQUENTIAL.equals(bpmDelegateExecution.multiInstanceType())){
			bpmDelegateExecution.removeVariable(varName);
		}
	}
	
	
	private void updOpinion(String executeParentId,String nodeId,String status){
		List<DefaultBpmTask> bpmTasks= bpmTaskManager.getByExeIdAndNodeId(executeParentId, nodeId);
		for(DefaultBpmTask task:bpmTasks){
			String taskId=task.getId();
			// 更新意见状态为取消。
			bpmCheckOpinionManager.updStatusByWait(taskId,status);
			//删除候选人 
			bpmTaskCandidateManager.removeByTaskId(taskId);
			//删除任务
			bpmTaskManager.remove(taskId);
		}
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
	
	

}
