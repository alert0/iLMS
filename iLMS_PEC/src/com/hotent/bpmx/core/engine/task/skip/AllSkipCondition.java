package com.hotent.bpmx.core.engine.task.skip;

import com.hotent.bpmx.api.inst.ISkipCondition;
import com.hotent.bpmx.api.model.process.task.BpmTask;

/**
 * 任何情况下都可以跳过。
 * @author ray
 *
 */
public class AllSkipCondition implements ISkipCondition {

	@Override
	public boolean canSkip(BpmTask task) {
		
		return true;
	}

	@Override
	public String getTitle() {
		return "无条件跳过";
	}

	@Override
	public String getType() {
		return "all";
	}

}
