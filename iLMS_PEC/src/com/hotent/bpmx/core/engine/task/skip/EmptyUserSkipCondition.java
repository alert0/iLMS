package com.hotent.bpmx.core.engine.task.skip;

import java.util.List;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.inst.ISkipCondition;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.SkipResult;

/**
 * 用户为空时跳过。
 * @author ray
 *
 */
public class EmptyUserSkipCondition implements ISkipCondition {

	@Override
	public boolean canSkip(BpmTask task) {
		List<BpmIdentity> identityList = task.getIdentityList();
		if(BeanUtils.isEmpty(identityList)  ) return true;
		return false;
	}

	@Override
	public String getTitle() {
		return "用户为空跳过";
	}

	@Override
	public String getType() {
		return SkipResult.SKIP_EMPTY_USER;
	}

}
