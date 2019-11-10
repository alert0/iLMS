package com.hotent.bpmx.core.engine.execution.sign.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.constant.PrivilegeMode;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.nodedef.ext.SignNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.PrivilegeItem;
import com.hotent.bpmx.api.plugin.core.factory.BpmPluginSessionFactory;
import com.hotent.bpmx.api.plugin.core.session.BpmUserCalcPluginSession;
import com.hotent.bpmx.api.service.SignService;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.plugin.core.util.UserAssignRuleQueryHelper;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.jms.JmsProducerWithTemplate;
import com.hotent.sys.api.jms.constants.ContentType;
import com.hotent.sys.api.jms.constants.NeedSubject;
import com.hotent.sys.api.msg.constants.MsgType;
import com.hotent.sys.api.template.TemplateService;
import com.hotent.sys.api.template.constants.TemplateConstants;
import com.hotent.sys.api.template.model.TemplateVo;
import com.hotent.sys.util.ContextUtil;

@Service
public class SignServiceImpl implements SignService{
	
	@Resource
	private BpmPluginSessionFactory bpmPluginSessionFactory;
	
	
	@Resource
	private IUserGroupService defaultUserGroupService;
	
	@Resource
	private IUserService userServiceImpl;
	@Resource
	private BpmTaskManager bpmTaskManager;
	@Resource
	private JmsProducerWithTemplate jmsProducerWithTemplate;
	@Resource
	private TemplateService templateService;	

	
	
	@Override
	public ResultMessage addSignTask(String taskId, String[] aryUsers) {
		return bpmTaskManager.addSignTask(taskId, aryUsers);
	}

	/**
	 * 判断该用户组集合是否包含指定用户组
	 * @param groups
	 * @param groupId
	 * @return  boolean
	 */
	private boolean containGroup(List<IGroup> groups,String groupId){
		for(IGroup group:groups){
			if(group.getGroupId().equals(groupId)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 返回之前对集合进行处理
	 * @param privilegeModes 
	 * void
	 */
	private void after(List<PrivilegeMode> privilegeModes){
		boolean hasAll = false;
		for(PrivilegeMode mode:privilegeModes){
			if(mode.equals(PrivilegeMode.ALL)){
				hasAll = true;
				break;
			}
		}
		if(hasAll){
			privilegeModes.clear();
			privilegeModes.add(PrivilegeMode.ALL);
		}
	}

	

	@Override
	public List<PrivilegeMode> getPrivilege(String userId, SignNodeDef signNodeDef, Map<String, Object> variables) {
		List<PrivilegeItem> privilegeList= signNodeDef.getPrivilegeList();
		List<PrivilegeMode> privilegeModes = new ArrayList<PrivilegeMode>();
		if(BeanUtils.isEmpty(privilegeList)) return privilegeModes;
		
		BpmUserCalcPluginSession pluginSession = bpmPluginSessionFactory.buildBpmUserCalcPluginSession(variables);
		//查询该用户所有关联的用户组
		List<IGroup> groups = defaultUserGroupService.getGroupsByUserId(userId);
		
		//遍历特权
		for(PrivilegeItem item:privilegeList){
			//查询该特权设置的用户
			List<BpmIdentity> bpmIdentities = UserAssignRuleQueryHelper.query(item.getUserRuleList(), pluginSession);
			//遍历，根据类型做不同的判断处理
			for(BpmIdentity bpmIdentity:bpmIdentities){
				//相同
				if(bpmIdentity.getType().equals(BpmIdentity.TYPE_USER) && bpmIdentity.getId().equals(userId)){	
					privilegeModes.add(item.getPrivilegeMode());
					break;
				}
				//包含该用户组	
				else if(bpmIdentity.getType().equals(BpmIdentity.TYPE_GROUP) && containGroup(groups, bpmIdentity.getId())){	
					privilegeModes.add(item.getPrivilegeMode());
					break;
				}
				//在该id串中
				else if(bpmIdentity.getType().equals(BpmIdentity.TYPE_GROUP_USER) &&bpmIdentity.getId().indexOf(userId)>0){	
					privilegeModes.add(item.getPrivilegeMode());
					break;
				}
			}
		}
		//当特权模式包含ALL 时，只保留ALL
		after(privilegeModes);
		return privilegeModes;
	}

	@Override
	public void sendNotify(List<IUser> receiverUsers, List<String> msgTypeKeys,
			String typeKey, Map<String, Object> vars) {
		IUser sender = ContextUtil.getCurrentUser();
		TemplateVo templateVo = templateService.getDefaultTemplate(typeKey);
		for(String msgTypeKey:msgTypeKeys){			
			if(msgTypeKey.equals(MsgType.SMS.key())){
				jmsProducerWithTemplate.send(msgTypeKey,templateVo, vars,sender, receiverUsers, NeedSubject.NO, ContentType.PLAIN);
			}else{
				jmsProducerWithTemplate.send(msgTypeKey,templateVo, vars,sender, receiverUsers, NeedSubject.YES,ContentType.HTML);		
			}
		}
	}
}
