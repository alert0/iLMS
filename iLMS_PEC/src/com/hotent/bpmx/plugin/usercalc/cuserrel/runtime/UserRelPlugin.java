package com.hotent.bpmx.plugin.usercalc.cuserrel.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;
import com.hotent.bpmx.plugin.core.runtime.AbstractUserCalcPlugin;
import com.hotent.bpmx.plugin.usercalc.UserCalcHelper;
import com.hotent.bpmx.plugin.usercalc.cuserrel.def.ExecutorVar;
import com.hotent.bpmx.plugin.usercalc.cuserrel.def.UserRelPluginDef;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IOrgService;
import com.hotent.sys.util.ContextUtil;

public class UserRelPlugin extends AbstractUserCalcPlugin{

	@Override
	public List<BpmIdentity> queryByPluginDef(
			BpmUserCalcPluginSession pluginSession, BpmPluginDef pluginDef) {
		UserRelPluginDef def=(UserRelPluginDef)pluginDef;
		Map<String, Object> vars= pluginSession.getVariables();
		
		IOrgService orgEngine=pluginSession.getOrgEngine();
		
		List<String> users=getSourceUser(vars, pluginSession, def);
		
		String relationKey=def.getRelationKey();
		
		String relationParty=def.getRelationParty();
		
		
		List<BpmIdentity> rtnList=new ArrayList<BpmIdentity>();
		
		for(String userId:users){
			//取得用户的关联用户。 
//			List<IUser> userList= orgEngine.getUserService().getByUserIdRelation(userId, relationKey, relationParty);
//			for(IUser user:userList){
//				BpmIdentity identity=getBpmIdentityConverter().convertUser(user);
//				rtnList.add(identity);
//			}
		}
		
		return rtnList;
	}
	
	private List<String> getSourceUser(Map<String, Object> vars ,BpmUserCalcPluginSession pluginSession,UserRelPluginDef def){
		IOrgService orgEngine=pluginSession.getOrgEngine();
		String source=def.getSource();
		List<String> users=new ArrayList<String>();
		if("start".equals(source)){
			String startId=(String)vars.get(BpmConstants.START_USER);
			users.add(startId);
		}
		else if("prev".equals(source)){
			String userId=ContextUtil.getCurrentUser().getUserId();
			users.add(userId);
		}
		else if("spec".equals(source)){
			String userKeys=def.getAccount();
			String[] aryAccount=userKeys.split(",");
			for(String account:aryAccount){
				IUser user= orgEngine.getUserService().getUserByAccount(account);
				users.add(user.getUserId());
			}
		}
		else if("var".equals(source)){
			ExecutorVar executorVar = def.getVar();
			if(!ExecutorVar.EXECUTOR_TYPE_USER.equals(executorVar.getExecutorType())) return users;
			
			List<String> userIds = UserCalcHelper.calcVarValue(executorVar, pluginSession,true);
			users.addAll(userIds);
		}
		return users;
	}

}
