package com.hotent.bpmx.plugin.usercalc.grouprel.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;
import com.hotent.bpmx.plugin.core.runtime.AbstractUserCalcPlugin;
import com.hotent.bpmx.plugin.usercalc.UserCalcHelper;
import com.hotent.bpmx.plugin.usercalc.cuserrel.def.ExecutorVar;
import com.hotent.bpmx.plugin.usercalc.grouprel.def.GroupRelPluginDef;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.util.ContextUtil;

public class GroupRelPlugin extends AbstractUserCalcPlugin{

	@Override
	public List<BpmIdentity> queryByPluginDef(
			BpmUserCalcPluginSession pluginSession, BpmPluginDef pluginDef) {
		GroupRelPluginDef def=(GroupRelPluginDef)pluginDef;
		
		List<IGroup> list= getGroup(def,pluginSession);
		List<BpmIdentity> identityList=new ArrayList<BpmIdentity>();
		//是否支持关系类型，取人员。
		if(def.isSupportRelation()){
			identityList=getByGroup(list,def.getRelationType(),pluginSession);
		}
		else{
			identityList=getBpmIdentityConverter().convertGroupList(list);
		}
		

		return identityList;
	}
	
	/**
	 * 根据关系类型获取人员。
	 * @param list
	 * @param relationType
	 * @param pluginSession
	 * @return 
	 * List&lt;BpmIdentity>
	 */
	private List<BpmIdentity> getByGroup(List<IGroup> list,String relationType,BpmUserCalcPluginSession pluginSession){
		IUserService userService=pluginSession.getOrgEngine().getUserService();
		List<BpmIdentity> identityList=new ArrayList<BpmIdentity>();
		for(IGroup group:list){
			//List<IUser> users= userService.getByGroupRelation(group.getGroupCode(), group.getGroupType(), relationType);
			//identityList.addAll(getBpmIdentityConverter().convertUserList(users));
		}
		return identityList;
	}
	
	
	/**
	 * 根据配置获取所在的组。
	 * @param def
	 * @param pluginSession
	 * @return 
	 * List&lt;Group>
	 */
	private List<IGroup> getGroup(GroupRelPluginDef def,BpmUserCalcPluginSession pluginSession){
		String dimKey=def.getGroupType();
		Map<String,Object> vars=pluginSession.getVariables();
		
		IUserGroupService userGroupService=pluginSession.getOrgEngine().getUserGroupService();		
		String source=def.getSource();

		
		List<IGroup> list=new ArrayList<IGroup>();
		
		if("start".equals(source)){
			String startId=(String) vars.get(BpmConstants.START_USER);
			list=pluginSession.getOrgEngine().getUserGroupService().getGroupsByGroupTypeUserId(dimKey, startId);
		}
		else if("prev".equals(source)){
			String prevId=ContextUtil.getCurrentUser().getUserId();
			list=userGroupService.getGroupsByGroupTypeUserId(dimKey, prevId);
		}
		else if("var".equals(source)){
			ExecutorVar executorVar = def.getVar();
			
			List<String> pks = UserCalcHelper.calcVarValue(executorVar, pluginSession,true);
			for(String pk : pks){
				if(ExecutorVar.EXECUTOR_TYPE_USER.equals(executorVar.getExecutorType()))
					list.addAll(userGroupService.getGroupsByGroupTypeUserId(dimKey, pk));
				else{
					if(executorVar.getDimension().equals(dimKey)) //维度相同
						list.add(userGroupService.getByCode(dimKey,pk));
				}
			}
				
		}
		
		//TODO 变量处理
		/*else if("userVar".equals(source)){
			String userIds=(String) vars.get(def.getUserVar());
			String[] aryUser=userIds.split(",");
			for(String userId:aryUser){
				List<Group> tmp=groupService.getGroupsByDimKeyUserId(dimKey, userId);
				list.addAll(tmp);
			}
		}
		else if("groupVar".equals(source)){
			String groupIds=(String) vars.get(def.getGroupVar());
			if(groupIds == null) return Collections.emptyList();
			list=getByGroupIds(groupIds,dimKey, pluginSession);
			
		}*/
		else if("spec".equals(source)){
			String groupIds=def.getGroupKey();
			if(groupIds == null) return Collections.emptyList();
			list=getByGroupIds(groupIds,dimKey, pluginSession);
		}
		
		return list;
	}
	
	private List<IGroup> getByGroupIds(String groupKeys,String dimKey,BpmUserCalcPluginSession pluginSession) {
		List<IGroup> list=new ArrayList<IGroup>();
		IUserGroupService groupService=pluginSession.getOrgEngine().getUserGroupService();
		String[] aryGroup=groupKeys.split(",");
		for(String groukey:aryGroup){
			IGroup group= groupService.getByCode(dimKey,groukey);
			list.add(group);
		}
		
		return list;
	}

}
