package com.hotent.bpmx.core.engine.task.skip;

import java.util.List;

import javax.annotation.Resource;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.inst.ISkipCondition;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.model.process.task.SkipResult;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.sys.util.ContextUtil;

/**
 * 只要审批过就允许跳过。
 * @author ray
 *
 */
public class ApproverSkipCondition implements ISkipCondition {
	
	@Resource
	private BpmCheckOpinionManager bpmCheckOpinionManager;
	

	@Override
	public boolean canSkip(BpmTask task) {
		String instId=task.getProcInstId();
		List<DefaultBpmCheckOpinion> list= bpmCheckOpinionManager.getByInstId(instId);
		for(DefaultBpmCheckOpinion opinion:list){
			if(opinion.getTaskKey().equals(task.getNodeId())) continue;
			if(isChecked(opinion.getAuditor(), task.getIdentityList())){
				ContextThreadUtil.putCommonVars(SkipResult.SKIP_APPROVER_AUDITOR, opinion.getAuditor());
				ContextThreadUtil.putCommonVars(SkipResult.SKIP_APPROVER_AUDITORNAME, opinion.getAuditorName());
				return true;
			}
		}
		return false;
	}

	//是否已审核过
	private boolean isChecked(String auditor,List<BpmIdentity> identitys){
		if(BeanUtils.isEmpty(identitys)) return false;
		for (BpmIdentity bpmIdentity : identitys) {
			if(bpmIdentity.getId().equals(auditor)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getTitle() {
		return "审批跳过";
	}

	@Override
	public String getType() {
		return "approver";
	}

}
