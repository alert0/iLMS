package com.hotent.bpmx.plugin.execution.procnotify.plugin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.plugin.core.def.BpmExecutionPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmExecutionPluginSession;
import com.hotent.bpmx.api.service.BpmCopyToService;
import com.hotent.bpmx.plugin.core.runtime.AbstractBpmExecutionPlugin;
import com.hotent.bpmx.plugin.core.util.UserAssignRuleQueryHelper;
import com.hotent.bpmx.plugin.execution.procnotify.def.ProcNotifyPluginDef;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyItem;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyVo;
import com.hotent.bpmx.plugin.task.tasknotify.helper.NotifyHelper;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

public class ProcNotifyPlugin extends AbstractBpmExecutionPlugin{

	private NotifyHelper notifyHelper = AppUtil.getBean(NotifyHelper.class);
	
	@Resource
	private BpmCopyToService bpmCopyToService;
	
	public Void execute(BpmExecutionPluginSession pluginSession,
			BpmExecutionPluginDef pluginDef) {
		BpmDelegateExecution delegateExecution=pluginSession.getBpmDelegateExecution();
		//获得流程变量
		Map<String,Object> variables = delegateExecution.getVariables();		
		//获取通知项
		NotifyVo notifyVo = ((ProcNotifyPluginDef)pluginDef).getNotifyVoMap().get(pluginSession.getEventType());

		if(notifyVo!=null){		
			ActionCmd actionCmd = ContextThreadUtil.getActionCmd();
			BpmProcessInstance instance=(BpmProcessInstance) actionCmd.getTransitVars(BpmConstants.PROCESS_INST);
//			TemplateVarsContainer varsContainer = new TemplateVarsContainer(variables);
			if(actionCmd instanceof TaskFinishCmd){
				
				String baseUrl = SysPropertyUtil.getByAlias(TemplateConstants.TEMP_VAR.BASE_URL);
				variables.put(TemplateConstants.TEMP_VAR.BASE_URL, baseUrl);

				variables.put(TemplateConstants.TEMP_VAR.INST_ID, actionCmd.getInstId());
				
				BpmTask task = (BpmTask) actionCmd.getTransitVars(BpmConstants.BPM_TASK);
				variables.put(TemplateConstants.TEMP_VAR.TASK_ID, task.getId());
				// 任务标题
				variables.put(TemplateConstants.TEMP_VAR.TASK_SUBJECT, task.getSubject());
				//任务名称
				variables.put(TemplateConstants.TEMP_VAR.NODE_NAME,task.getName());
				//流程实例名称
				variables.put(TemplateConstants.TEMP_VAR.INST_SUBJECT, task.getSubject());
				
				IUser user = ContextUtil.getCurrentUser();
				if (user != null) {
					variables.put(TemplateConstants.TEMP_VAR.SENDER, user.getFullname());
					variables.put(TemplateConstants.TEMP_VAR.DELEGATE, user.getFullname());
					variables.put(TemplateConstants.TEMP_VAR.AGENT, user.getFullname());
				}
				
				String cause = ((TaskFinishCmd)actionCmd).getApprovalOpinion();
				// 意见
				variables.put(TemplateConstants.TEMP_VAR.CAUSE, cause);
			}
			
			
			//发送消息给插件定义的用户
			//模板类型指定为：TemplateConstants.TYPE_KEY.PROCESS_END
			for(NotifyItem notifyItem:notifyVo.getNotifyItemList()){
				
				List<IUser> toUsers = UserAssignRuleQueryHelper.queryUsersWithExtract(notifyItem.getUserAssignRules(), variables);
				bpmCopyToService.copyTo(toUsers, instance,delegateExecution.getNodeId());
				
				notifyHelper.notify(toUsers, notifyItem.getMsgTypes(), TemplateConstants.TYPE_KEY.PROCESS_END, variables);				
			}
		}
		
		return null;
	}

}
