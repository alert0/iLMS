package com.hotent.bpmx.activiti.ext.listener;

import javax.annotation.Resource;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.constant.ScriptType;
import com.hotent.bpmx.api.event.TaskSignCreateEvent;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.service.BpmTaskService;

/**
 * 会签任务创建监听器。
 * <pre> 
 * 构建组：x5-bpmx-activiti
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-3-30-下午10:21:16
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class TaskSignCreateListener extends AbstractTaskListener  {
	
	
	/**
	 * serialVersionUID
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 5830546066306214153L;
	
	

	@Override
	public EventType getBeforeTriggerEventType() {
		return EventType.TASK_SIGN_CREATE_EVENT;
	}

	@Override
	public EventType getAfterTriggerEventType() {
		return EventType.TASK_SIGN_POST_CREATE_EVENT;
	}

	@Override
	public void beforePluginExecute(BpmDelegateTask delegateTask) {

	}

	@Override
	public void triggerExecute(BpmDelegateTask delegateTask) {
		TaskSignCreateEvent ev=new TaskSignCreateEvent(delegateTask);
		AppUtil.publishEvent(ev);
	}

	@Override
	public void afterPluginExecute(BpmDelegateTask delegateTask) {
		
	}

	@Override
	protected ScriptType getScriptType() {
		return ScriptType.CREATE;
	}
}
