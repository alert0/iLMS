package com.hotent.bpmx.activiti.ext.listener;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.constant.ScriptType;
import com.hotent.bpmx.api.event.ProcessInstanceEndEvent;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;

/**
 * 流程结束监听事件。
 * <pre> 
 * 描述：流程结束监听事件。
 * 构建组：x5-bpmx-activiti
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-3-20-下午5:05:10
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class EndEventListener extends AbstractExecutionListener {
	
	/**
	 * serialVersionUID
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = -9011829013817237607L;
	
	
	
	@Override
	public EventType getBeforeTriggerEventType() {
		return EventType.END_EVENT;
	}

	@Override
	public EventType getAfterTriggerEventType() {
		return EventType.END_POST_EVENT;
	}

	@Override
	public void beforePluginExecute(BpmDelegateExecution bpmDelegateExecution) {
	}

	@Override
	public void triggerExecute(BpmDelegateExecution bpmDelegateExecution) {
		//获得流程实例ID
		String bpmnInstId=bpmDelegateExecution.getBpmnInstId();
		
		//不是有效状态 或 当前的excutionId和主线程不相同时，直接返回
		if(!bpmDelegateExecution.isEnded() 
				|| !bpmDelegateExecution.getExecutionEntityId().equals(bpmnInstId)){ 
			return;
		}
		//发布流程结束事件。
		ProcessInstanceEndEvent event=new ProcessInstanceEndEvent(bpmDelegateExecution);
		AppUtil.publishEvent(event);
	}

	@Override
	public void afterPluginExecute(BpmDelegateExecution bpmDelegateExecution) {
	}

	@Override
	protected ScriptType getScriptType() {
		return ScriptType.END;
	}



}
