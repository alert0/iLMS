package com.hotent.bpmx.plugin.task.tasknotify.plugin;

import java.util.Map;
import java.util.Set;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.plugin.core.def.BpmTaskPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmTaskPluginSession;
import com.hotent.bpmx.api.service.BpmTaskService;
import com.hotent.bpmx.plugin.core.runtime.AbstractBpmTaskPlugin;
import com.hotent.bpmx.plugin.task.tasknotify.def.TaskNotifyPluginDef;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyItem;
import com.hotent.bpmx.plugin.task.tasknotify.def.model.NotifyVo;
import com.hotent.bpmx.plugin.task.tasknotify.helper.NotifyHelper;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * 通知插件。
 * <pre> 
 * 构建组：x5-bpmx-plugin
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-4-2-下午10:57:17
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class TaskNotifyPlugin extends AbstractBpmTaskPlugin{
	
	private NotifyHelper notifyHelper = AppUtil.getBean(NotifyHelper.class);

	
	public Void execute(BpmTaskPluginSession pluginSession,BpmTaskPluginDef pluginDef) {
		String baseUrl=SysPropertyUtil.getByAlias(TemplateConstants.TEMP_VAR.BASE_URL);
		//获得流程变量
		Map<String,Object> variables = pluginSession.getBpmDelegateTask().getVariables();	
		variables.put(TemplateConstants.TEMP_VAR.BASE_URL, baseUrl);
		String procInstId=(String) variables.get(BpmConstants.PROCESS_INST_ID);		
		
		//如果当前任务为跳过，则不需要发送通知
		if(isSkip(procInstId,pluginSession.getBpmDelegateTask().getId())){
			return null;
		}													
		
		//获取通知项
		NotifyVo notifyVo = ((TaskNotifyPluginDef)pluginDef).getNotifyVos().get(pluginSession.getEventType());
		
		if(notifyVo==null) return null;
			
		variables.put(TemplateConstants.TEMP_VAR.NODE_NAME, pluginSession.getBpmDelegateTask().getName());
		
		//发送消息给插件定义的用户
		for(NotifyItem notifyItem:notifyVo.getNotifyItemList()){
			if(notifyVo.getEventType().equals(EventType.TASK_POST_CREATE_EVENT)){
				notifyHelper.notify(notifyItem, TemplateConstants.TYPE_KEY.TASK_CREATE, variables);	
			}else if(notifyVo.getEventType().equals(EventType.TASK_COMPLETE_EVENT)){
				notifyHelper.notify(notifyItem, TemplateConstants.TYPE_KEY.TASK_COMPLETE, variables);
			}								
		}
		
		return null;
	}
	
	private boolean isSkip(String procInstId,String currentTaskId) {
		boolean isSkip = false;
				
		Set<BpmTask> bpmTaskSet=ContextThreadUtil.getByInstId(procInstId);		
		if(bpmTaskSet!=null && bpmTaskSet.size()>0){
			//获得当前任务
			BpmTask currentTask = null;
			for(BpmTask bpmTask:bpmTaskSet){
				if(bpmTask.getTaskId().equals(currentTaskId)){
					currentTask = bpmTask;
					break;
				}
			}
			
			BpmTaskService taskService = AppUtil.getBean(BpmTaskService.class);
			taskService.setTaskSkip(currentTask);
		}
		return isSkip;
	}

}
