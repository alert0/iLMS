package com.hotent.bpmx.core.engine.task.skip;

import java.util.List;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.inst.ISkipCondition;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.sys.util.ContextUtil;

/**
 * 审批时新产生的任务执行人和当前执行人相同时可以跳过。
 * @author ray
 */
public class SameUserSkipCondition implements ISkipCondition {

	@Override
	public boolean canSkip(BpmTask task) {
		List<BpmIdentity> identityList = task.getIdentityList();
		if(BeanUtils.isEmpty(identityList) || identityList.size()>1 ) return false;
		// 跳过相同执行人
		String userId=ContextUtil.getCurrentUserId();
		BpmIdentity identity = identityList.get(0);
		if (identity.getId().equals(userId)) {
			return true;
		}
		return false;
	}

	@Override
	public String getTitle() {
		return "相同执行人";
	}

	@Override
	public String getType() {
		return "sameUser";
	}

}
