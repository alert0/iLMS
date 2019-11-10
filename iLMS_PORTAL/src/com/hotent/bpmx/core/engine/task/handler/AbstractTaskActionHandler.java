package com.hotent.bpmx.core.engine.task.handler;

import java.util.Map;

import javax.annotation.Resource;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.plugin.core.def.TaskActionHandlerDef;
import com.hotent.bpmx.api.plugin.core.runtime.TaskActionHandler;
import com.hotent.bpmx.api.plugin.core.session.TaskActionPluginSession;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.natapi.task.NatTaskService;

public abstract class AbstractTaskActionHandler implements TaskActionHandler{
	
	
	@Resource
	NatTaskService natTaskService; 
	
	@Override
	public Boolean execute(TaskActionPluginSession pluginSession,TaskActionHandlerDef def) {
		
		DefaultTaskFinishCmd finishCmd=(DefaultTaskFinishCmd)pluginSession.getTaskFinishCmd();
		
		BpmTask bpmTask=(BpmTask) finishCmd.getTransitVars(BpmConstants.BPM_TASK);
		//获得任务ID
		String taskId=bpmTask.getTaskId();		
				
		//准备数据
		prepare(pluginSession, taskId);
		
		//执行插件处理器前置
		preActionHandler(pluginSession,def);
		
		//完成任务
		if(isNeedCompleteTask()){	
			//仅仅完成任务。
			if(finishCmd.isOnlyFinishTask()){
				natTaskService.completeTaskOnly(taskId);
			}
			else{
				String destinationNode=finishCmd.getDestination();
				if(StringUtil.isEmpty(destinationNode)){
					natTaskService.completeTask(taskId);
				}
				else{
					//跳转到目标节点。
					natTaskService.completeTask(taskId,destinationNode);
				}
			}
		}
		
		//执行插件处理后置
		afterActionHandler(pluginSession,def);				
		
		return true;
	}
	
	/**
	 * 准备会话数据
	 * @param pluginSession
	 * @param taskId 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	private void prepare(TaskActionPluginSession pluginSession,String taskId){
		//获得Cmd数据
		DefaultTaskFinishCmd finishCmd=(DefaultTaskFinishCmd)pluginSession.getTaskFinishCmd();
		
		//将流程变量设置到session中
		Map<String,Object> vars=finishCmd.getVariables();
		if(vars.size()==0){
			vars= natTaskService.getVariables(taskId);	
			finishCmd.setVariables(vars);
		}
		else{
			natTaskService.setVariables(taskId, vars);
		}
	}
	
	public abstract void preActionHandler(TaskActionPluginSession pluginSession,TaskActionHandlerDef def);
	
	public abstract void afterActionHandler(TaskActionPluginSession pluginSession,TaskActionHandlerDef def);
	
}
