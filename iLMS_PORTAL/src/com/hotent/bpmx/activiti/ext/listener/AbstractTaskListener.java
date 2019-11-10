package com.hotent.bpmx.activiti.ext.listener;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.activiti.ext.factory.BpmDelegateFactory;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.constant.ScriptType;
import com.hotent.bpmx.api.context.BpmContextUtil;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.exception.BusinessException;
import com.hotent.bpmx.api.exception.WorkFlowException;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.plugin.core.cmd.ExecutionCommand;
import com.hotent.bpmx.api.plugin.core.cmd.TaskCommand;
import com.hotent.bpmx.api.service.BpmDefinitionService;

/**
 * <pre> 
 * 描述：任务监听器
 * 构建组：x5-bpmx-activiti
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014-2-14-上午10:42:09
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public abstract class AbstractTaskListener implements TaskListener{
	
	private static final long serialVersionUID = -296298349312307694L;
	
	@Resource
	BpmDefinitionService bpmDefinitionService;
	
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	
	@Resource
	GroovyScriptEngine groovyScriptEngine;
	
	
	
	private List<TaskCommand> taskCommands;
	
	private List<ExecutionCommand> executionCommands;
		
	
	/**
	 * 触发子类执行动作之前，需要执行的插件事件类型。
	 * @return 
	 * EventType
	 */
	public abstract EventType getBeforeTriggerEventType();
	
	/**
	 * 触发子类执行动作之后，需要执行的插件事件类型。
	 * @return 
	 * EventType
	 */
	public abstract EventType getAfterTriggerEventType();
	
	/**
	 * 在所有插件执行之前执行的逻辑
	 * @param delegateTask 
	 * void
	 */
	public abstract void beforePluginExecute(BpmDelegateTask delegateTask);
	
	/**
	 * 触发子类的执行操作
	 * @param delegateTask 
	 * void
	 */
	public abstract void triggerExecute(BpmDelegateTask delegateTask);	
	
	/**
	 * 在所有插件执行之后执行的逻辑
	 * @param delegateTask 
	 * void
	 */
	public abstract void afterPluginExecute(BpmDelegateTask delegateTask);
	
	@Override
	public void notify(DelegateTask delegateTask) {
		//转换数据		
		BpmDelegateTask task= BpmDelegateFactory.getBpmDelegateTask(delegateTask);		
		//在插件之前执行逻辑
		beforePluginExecute(task);
		//执行插件（在触发子类执行之前的插件，对应getBeforeTriggerEventType）
		if(taskCommands!=null && getBeforeTriggerEventType()!=null){
			for(TaskCommand cmd:taskCommands){
				cmd.execute(getBeforeTriggerEventType(),task);
			}	
		}
		
		//执行全局节点插件（在触发子类执行之前的插件，对应getBeforeTriggerEventType）
		if(executionCommands!=null && getBeforeTriggerEventType()!=null){
			ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
			taskCmd.getVariables().put("restful_task", task);
			for(ExecutionCommand cmd:executionCommands){
				BpmDelegateExecution  exection = BpmDelegateFactory.getBpmDelegateExecution(delegateTask.getExecution());
				if(BeanUtils.isNotEmpty(exection)){
					cmd.execute(getBeforeTriggerEventType(),BpmDelegateFactory.getBpmDelegateExecution(delegateTask.getExecution()));
				}
			}	
		}
		//触发子类执行
		triggerExecute(task);	
		
		//执行插件（在触发子类执行之后的插件，对应getAfterTriggerEventType）
		if(taskCommands!=null && getAfterTriggerEventType()!=null){
			for(TaskCommand cmd:taskCommands){
				cmd.execute(getAfterTriggerEventType(),task);
			}	
		}
		
		//在插件全部执行完之后执行逻辑
		afterPluginExecute(task);
		//执行事件脚本。
		exeEventScript(task);
	}
	
	/**
	 * 脚本类型。
	 * @return  ScriptType
	 */
	protected abstract ScriptType getScriptType();
	
	private void exeEventScript(BpmDelegateTask delegateTask){
		String bpmnDefId=delegateTask.getBpmnDefId();
		String defId =bpmDefinitionService.getDefIdByBpmnDefId(bpmnDefId);
		String nodeId=delegateTask.getTaskDefinitionKey();
		BpmNodeDef nodeDef= bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		
		ScriptType scriptType= getScriptType();
		String script=nodeDef.getScripts().get(scriptType);
		if(StringUtil.isEmpty(script)) return;
		
	 
		Map<String, Object> vars=delegateTask.getVariables();
		ActionCmd cmd= ContextThreadUtil.getActionCmd();
		
		Map<String,BoData> boMap= BpmContextUtil.getBoFromContext();
		if(BeanUtils.isNotEmpty(boMap)){
			vars.putAll(boMap); 
		}
		 
		vars.put("nodeDef", nodeDef);
		vars.put("task", delegateTask);
		vars.put("cmd", cmd);
		
		try {
			groovyScriptEngine.execute(script, vars);
		} catch (BusinessException e) {
			throw new WorkFlowException(e.getMessage());
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer();
			sb.append("<br/><br/>流程在节点："+nodeDef.getName()+"("+nodeDef.getNodeId()+")执行"+scriptType.getValue()+"时出现异常情况！");
			sb.append("<br/>请联系管理员！");
			sb.append("<br/>可能原因为："+e.getMessage());
			sb.append("<br/>执行脚本为："+script);
			sb.append("脚本变量："+vars.toString());
			throw new WorkFlowException(sb.toString());
		}
		
	}
	

	public List<TaskCommand> getTaskCommands() {
		return taskCommands;
	}

	public void setTaskCommands(List<TaskCommand> taskCommands) {
		this.taskCommands = taskCommands;
	}

	public List<ExecutionCommand> getExecutionCommands() {
		return executionCommands;
	}

	public void setExecutionCommands(List<ExecutionCommand> executionCommands) {
		this.executionCommands = executionCommands;
	}
	
}
