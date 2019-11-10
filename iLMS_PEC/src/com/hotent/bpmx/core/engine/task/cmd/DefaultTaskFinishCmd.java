package com.hotent.bpmx.core.engine.task.cmd;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.cmd.BaseActionCmd;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.ActionType;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.plugin.core.def.TaskActionHandlerDef;
import com.hotent.bpmx.api.plugin.core.task.TaskActionHandlerConfig;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;

/**
 * 完成任务审批对象。
 * <pre> 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-11-4-上午9:55:42
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class DefaultTaskFinishCmd extends BaseActionCmd implements TaskFinishCmd {
	/**
	 * 任务ID
	 */
	private String taskId="";
	
	/**
	 * BPMN流程实例ID。
	 */
	private String bpmnInstId="";
	
	
	/**
	 * 意见标识名称。
	 */
	private String opinionIdentity="";
	
	/**
	 * 文字意见。
	 */
	private String opinion="";
	
	/**
	 * 意见附件文件。
	 */
	private String files="";
	
	/**
	 * 是否干预执行。
	 */
	private boolean interpose=false;
	
	
	private boolean onlyFinishTask=false;
	
	
	public String getTaskId() {
		return this.taskId;
	}
	
	/**
	 * 设置任务ID是设置流程实例。
	 * @param taskId 
	 * void
	 */
	public void setTaskId(String taskId) {
		//设置流程实例。
		BpmTaskManager bpmTaskMgr=AppUtil.getBean(BpmTaskManager.class);
		BpmProcessInstanceManager instanceManager=AppUtil.getBean(BpmProcessInstanceManager.class);
		
		BpmTask bpmTask= bpmTaskMgr.get(taskId);
		if(bpmTask==null) throw new RuntimeException("任务不存在，可能已经被处理！");
		
		String instId=bpmTask.getProcInstId();
		//清除线程中的任务。
		ContextThreadUtil.clearTaskByInstId(instId);
		
		BpmProcessInstance bpmProcessInstance=instanceManager.get(instId);
		this.setInstId(instId);
		this.addTransitVars(BpmConstants.PROCESS_INST, bpmProcessInstance);
		this.addTransitVars(BpmConstants.BPM_TASK, bpmTask);
		
		this.taskId=taskId;
	}
	


	

	@Override
	public String getApprovalOpinion() {
		return this.opinion;
	}
	
	
	public void setApprovalOpinion(String opinion) {
		this.opinion=opinion;
	}
	
	public void setBpmnInstId(String bpmnInstId){
		this.bpmnInstId=bpmnInstId;
	}
	

	@Override
	public String getBpmnInstId() {
		
		return this.bpmnInstId;
	}

	
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	@Override
	public ActionType getActionType() {
		TaskActionHandlerConfig config=AppUtil.getBean(TaskActionHandlerConfig.class);
		TaskActionHandlerDef def = (TaskActionHandlerDef)config.getTaskActionHandlerDef(this.getActionName());
		return def.getActionType();
	}

	@Override
	public boolean isInterpose() {
		return interpose;
	}

	/**
	 * 设置是否干预执行。
	 * @param _interpose 
	 * void
	 */
	void setIsInterpose(boolean _interpose){
		interpose=_interpose;
	}

	@Override
	public boolean isOnlyFinishTask() {
		return this.onlyFinishTask;
	}
	
	
	public void setOnlyFinishTask(boolean _onlyFinshTask) {
		this.onlyFinishTask=_onlyFinshTask;
	}

	@Override
	public String getOpinionIdentity() {
		return opinionIdentity;
	}
	
	/**
	 * 设置意见标识。
	 * @param opinionIndentity 
	 * void
	 */
	public void setOpinionIdentity(String opinionIndentity){
		this.opinionIdentity=opinionIndentity;
	}

	
	

}
