package com.hotent.bpmx.plugin.task.taskcopyto.plugin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.plugin.core.def.BpmTaskPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmTaskPluginSession;
import com.hotent.bpmx.api.service.BpmCopyToService;
import com.hotent.bpmx.plugin.core.runtime.AbstractBpmTaskPlugin;
import com.hotent.bpmx.plugin.core.util.UserAssignRuleQueryHelper;
import com.hotent.bpmx.plugin.task.taskcopyto.def.TaskCopyToPluginDef;
import com.hotent.bpmx.plugin.task.taskcopyto.def.model.CopyToItem;
import com.hotent.bpmx.plugin.task.tasknotify.helper.NotifyHelper;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.api.template.constants.TemplateConstants.TEMP_VAR;

public class TaskCopyToPlugin extends AbstractBpmTaskPlugin{	
	@Resource
	private BpmCopyToService bpmCopyToService;
	
	@Resource
	private NotifyHelper notifyHelper;
	
	public Void execute(BpmTaskPluginSession pluginSession,
			BpmTaskPluginDef pluginDef) {
		
		Map<String,Object> variables = pluginSession.getBpmDelegateTask().getVariables();		
		
		ActionCmd actionCmd = ContextThreadUtil.getActionCmd();
		BpmProcessInstance instance=(BpmProcessInstance) actionCmd.getTransitVars(BpmConstants.PROCESS_INST);
		
		TaskCopyToPluginDef taskCopyToPluginDef = (TaskCopyToPluginDef)pluginDef;
		for(CopyToItem copyToItem:taskCopyToPluginDef.getCopyToItems()){			
			List<IUser> toUsers = UserAssignRuleQueryHelper.queryUsersWithExtract(copyToItem.getUserAssignRules(), pluginSession.getBpmDelegateTask().getVariables());
			bpmCopyToService.copyTo(toUsers, instance,pluginSession.getBpmDelegateTask().getTaskDefinitionKey());
			
			//如果设置了消息类型，表示要发送消息
			//抄送重用“任务完成”的模板，如果需要定义独立的模板类型，请增加常量
			if(copyToItem.getMsgTypes().size()>0){
				
				variables.put(TEMP_VAR.NODE_NAME, pluginSession.getBpmDelegateTask().getName());
				
				notifyHelper.notify(toUsers, copyToItem.getMsgTypes(), TemplateConstants.TYPE_KEY.TASK_COMPLETE,  variables);
			}
		}
		return null;
	}

}
