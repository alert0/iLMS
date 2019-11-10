package com.hotent.bpmx.api.inst;

import com.hotent.bpmx.api.model.process.task.BpmTask;

/**
 * 跳过条件接口。
 * @author ray
 *
 */
public interface ISkipCondition {
	/**
	 * 判断流程任务是否可以跳过。
	 * @param task
	 * @return
	 */
	boolean canSkip(BpmTask task);
	/**
	 * 策略标题。
	 * @return
	 */
	String getTitle();
	/**
	 * 获取跳过的类型。
	 * @return
	 */
	String getType();
}
