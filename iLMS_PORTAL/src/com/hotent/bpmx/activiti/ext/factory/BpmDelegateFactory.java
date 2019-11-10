package com.hotent.bpmx.activiti.ext.factory;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;

import com.hotent.bpmx.activiti.ext.model.BpmDelegateExecutionImpl;
import com.hotent.bpmx.activiti.ext.model.BpmDelegateTaskImpl;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;

public class BpmDelegateFactory {
	
	/**
	 * 根据delegateExecution 获取 BpmDelegateExecution 对象。
	 * @param delegateExecution
	 * @return
	 */
	public static BpmDelegateExecution getBpmDelegateExecution(DelegateExecution delegateExecution) {
		BpmDelegateExecutionImpl bpmDelegateExecutionImpl = new BpmDelegateExecutionImpl();
		bpmDelegateExecutionImpl.setDelegateExecution(delegateExecution);
		return (BpmDelegateExecution)bpmDelegateExecutionImpl;
	}
	
	
	/**
	 * 根据delegateTask 获取 BpmDelegateTask 实例对象。
	 * @param delegateTask
	 * @return
	 */
	public static BpmDelegateTask getBpmDelegateTask(DelegateTask delegateTask) {
		BpmDelegateTaskImpl bpmDelegateTaskImpl = new BpmDelegateTaskImpl();
		bpmDelegateTaskImpl.setDelegateTask(delegateTask);
		return (BpmDelegateTask)bpmDelegateTaskImpl;
	}
}
