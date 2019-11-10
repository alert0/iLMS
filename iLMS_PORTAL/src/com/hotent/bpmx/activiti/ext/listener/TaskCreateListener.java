package com.hotent.bpmx.activiti.ext.listener;

import javax.annotation.Resource;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.constant.ScriptType;
import com.hotent.bpmx.api.event.TaskCreateEvent;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.service.BpmTaskActionService;


/**
 * 任务创建的监听器。
 * <pre>
 * 主要处理人员的分配。
 * </pre>
 * @author ray
 *
 */
public class TaskCreateListener extends AbstractTaskListener {
	
	
	@Resource
	private BpmTaskActionService bpmTaskActionService;
	
	
	/**
	 * serialVersionUID
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = -7836822392037648008L;
	
	@Override
	public EventType getBeforeTriggerEventType() {
		return EventType.TASK_CREATE_EVENT;
	}

	@Override
	public EventType getAfterTriggerEventType() {
		return EventType.TASK_POST_CREATE_EVENT;
	}

	@Override
	public void beforePluginExecute(BpmDelegateTask delegateTask) {
		bpmTaskActionService.create(delegateTask);
	}

	@Override
	public void triggerExecute(BpmDelegateTask delegateTask) {
		TaskCreateEvent startEvent=new TaskCreateEvent(delegateTask);
		AppUtil.publishEvent(startEvent);
	}	
	
	@Override
	public void afterPluginExecute(BpmDelegateTask delegateTask) {
		
	}

	@Override
	protected ScriptType getScriptType() {
		return ScriptType.CREATE;
	}

	
	
}
