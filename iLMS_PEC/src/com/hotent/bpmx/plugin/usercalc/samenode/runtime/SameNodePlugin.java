package com.hotent.bpmx.plugin.usercalc.samenode.runtime;

import java.util.ArrayList;
import java.util.List;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.task.BpmTaskOpinion;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;
import com.hotent.bpmx.plugin.core.runtime.AbstractUserCalcPlugin;
import com.hotent.bpmx.plugin.usercalc.samenode.def.SameNodePluginDef;
import com.hotent.org.api.model.IUser;

public class SameNodePlugin  extends AbstractUserCalcPlugin{

	@Override
	public List<BpmIdentity> queryByPluginDef(BpmUserCalcPluginSession pluginSession, BpmPluginDef pluginDef) {
		String processInstanceId = (String) pluginSession.getVariables().get(BpmConstants.PROCESS_INST_ID);
		SameNodePluginDef sameNodeDef = (SameNodePluginDef)pluginDef;
		List<BpmIdentity> bpmIdentities = new ArrayList<BpmIdentity>();
		
		List<BpmTaskOpinion> taskOpinionList =  pluginSession.getBpmxEngine().getBpmOpinionService().getByInstNodeId(processInstanceId, sameNodeDef.getNodeId());
		
		if(taskOpinionList.size() >0 ){
			
			// 会签多人处理情况
			for (BpmTaskOpinion bpmTaskOpinion : taskOpinionList) {
				if(StringUtil.isEmpty(bpmTaskOpinion.getAuditor())) continue;
				IUser user = pluginSession.getOrgEngine().getUserService().getUserById(bpmTaskOpinion.getAuditor());
				if(user != null){ 
					BpmIdentity bpmIdentity= getBpmIdentityConverter().convertUser(user);
					bpmIdentities.add(bpmIdentity);
				}
			}
		}
		
		return bpmIdentities;
	}

	@Override
	public boolean supportPreView() {
		return false;
	}

}
