package com.hotent.bpmx.plugin.task.userassign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.helper.identity.BpmIdentityExtractService;
import com.hotent.bpmx.api.helper.identity.UserQueryPluginHelper;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.UserAssignRule;
import com.hotent.bpmx.api.plugin.core.context.BpmPluginContext;
import com.hotent.bpmx.api.plugin.core.context.UserQueryPluginContext;
import com.hotent.bpmx.api.plugin.core.factory.BpmPluginSessionFactory;
import com.hotent.bpmx.api.plugin.core.runtime.BpmUserCalcPlugin;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;
import com.hotent.bpmx.plugin.core.runtime.AbstractUserCalcPlugin;
import com.hotent.bpmx.plugin.core.util.UserAssignRuleQueryHelper;
import com.hotent.bpmx.plugin.task.userassign.context.UserAssignPluginContext;
import com.hotent.bpmx.plugin.task.userassign.def.UserAssignPluginDef;
import com.hotent.org.api.model.IUser;

/**
 * <pre> 
 * 描述：TODO
 * 构建组：x5-bpmx-core
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-4-3-上午10:06:39
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class UserQueryPluginHelperImpl implements UserQueryPluginHelper{
	
	public List<BpmIdentity> query(List<BpmPluginContext> bpmPluginContexts,Map<String, Object> variables) {
		List<BpmIdentity>allBpmIdentities = new ArrayList<BpmIdentity>();
		
		if(BeanUtils.isEmpty(bpmPluginContexts)) return allBpmIdentities;
		
		
		for(BpmPluginContext pluginContext:bpmPluginContexts){
			//如果是用户查询插件上下文
			if(!(pluginContext instanceof UserQueryPluginContext)) continue;
			
			//获得该运行时				
			BpmUserCalcPlugin userQueryPlugin = (BpmUserCalcPlugin)AppUtil.getBean(((UserQueryPluginContext)pluginContext).getUserQueryPluginClass());
			
			//构造会话数据
			BpmPluginSessionFactory bpmPluginSessionFactory = AppUtil.getBean(BpmPluginSessionFactory.class);										
			BpmUserCalcPluginSession bpmUserCalcPluginSession = bpmPluginSessionFactory.buildBpmUserCalcPluginSession(variables);
			
			//计算用户
			List<BpmIdentity> newBpmIdentities = userQueryPlugin.execute(bpmUserCalcPluginSession, pluginContext.getBpmPluginDef());
			
			//加到返回集合中
			allBpmIdentities.addAll(newBpmIdentities);
		}
		
		return allBpmIdentities;
	}

	public List<IUser> queryUsers(List<BpmPluginContext> bpmPluginContexts,
			Map<String, Object> variables) {
		//查询bi集合
		List<BpmIdentity> bpmIdentities = query(bpmPluginContexts, variables);
		//强制抽取
		BpmIdentityExtractService bpmIdentityExtractService = (BpmIdentityExtractService)AppUtil.getBean(BpmIdentityExtractService.class);
		
		List<IUser> users = bpmIdentityExtractService.extractUser(bpmIdentities);
		
		return users;
	}

	@Override
	public List<IUser> queryUsersByConditions(String conditionJson,Map map) {
		AbstractUserCalcPlugin.setPreviewMode(true); 
		UserAssignPluginContext context=AppUtil.getBean(UserAssignPluginContext.class);
		BpmIdentityExtractService bpmIdentityExtractService = (BpmIdentityExtractService)AppUtil.getBean(BpmIdentityExtractService.class);
		BpmPluginSessionFactory bpmPluginSessionFactory = AppUtil.getBean(BpmPluginSessionFactory.class);										
		BpmUserCalcPluginSession bpmUserCalcPluginSession = bpmPluginSessionFactory.buildBpmUserCalcPluginSession(map);//放入前天提供的数据
			
		try{
			context.parse(conditionJson);
			UserAssignPluginDef userAssignDef = (UserAssignPluginDef) context.getBpmPluginDef();
			
			List<UserAssignRule>  userAssignRuleList = userAssignDef.getRuleList(); 
			if(BeanUtils.isEmpty(userAssignRuleList)) return Collections.emptyList();
			
			List<BpmIdentity> identityList = UserAssignRuleQueryHelper.queryExtract(userAssignRuleList, bpmUserCalcPluginSession);
			if(BeanUtils.isEmpty(identityList)) return Collections.emptyList();
			
			List<IUser> users = bpmIdentityExtractService.extractUser(identityList);
			return users ;
		}finally{
			AbstractUserCalcPlugin.cleanPreviewMode();
		}
	}

}
