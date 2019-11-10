package com.hotent.bpmx.activiti.ext.listener;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.constant.ScriptType;
import com.hotent.bpmx.api.event.StartFlowEvent;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;

/**
 * 
 * <pre> 
 * 描述：流程启动时的监听事件。
 * 构建组：x5-bpmx-activiti
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014-2-14-上午10:40:48
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class StartEventListener extends AbstractExecutionListener   {

	/**
	 * serialVersionUID
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 9022216448150068516L;
	
	@Override
	public EventType getBeforeTriggerEventType() {
		return EventType.START_EVENT;
	}

	@Override
	public EventType getAfterTriggerEventType() {
		return EventType.START_POST_EVENT;
	}

	@Override
	public void beforePluginExecute(BpmDelegateExecution bpmDelegateExecution) {
	}

	@Override
	public void triggerExecute(BpmDelegateExecution bpmDelegateExecution) {
		StartFlowEvent ev=new StartFlowEvent(bpmDelegateExecution);
		AppUtil.publishEvent(ev);
	}

	@Override
	public void afterPluginExecute(BpmDelegateExecution bpmDelegateExecution) {
	}

	@Override
	protected ScriptType getScriptType() {
		return ScriptType.START;
	}

	
		
}
