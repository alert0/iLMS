package com.hotent.org.api.impl.Script;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.engine.script.IUserScript;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.helper.identity.BpmIdentityConverter;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IParamService;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.sys.util.ContextUtil;

/**
 * 人员脚本 作用：可用于节点处理人
 * @author Administrator
 */
@Service
public class BpmUserScript implements IUserScript {
	@Resource
	IUserService userService;
	@Resource
	IUserGroupService userGroupService;
	@Resource
	IParamService paramService;
	@Resource 
	OrgManager orgManager;

	/**
	 * 根据角色编码获取人员列表
	 * @param roleId
	 * @return
	 */
	public Set<BpmIdentity> getListUserByRoleId(String roleCode) {
		IGroup role = userGroupService.getByCode(GroupTypeConstant.ROLE.key(), roleCode);
		List<IUser> list = userService.getUserListByGroup(GroupTypeConstant.ROLE.key(), role.getGroupId());
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<BpmIdentity> listIdentity = bpmIdentityConverter.convertUserList(list);
		Set<BpmIdentity> set = new LinkedHashSet<BpmIdentity>(listIdentity);
		return set;
	}
	/**
	 * 根据岗位编码获取人员列表
	 * @param roleId
	 * @return
	 */
	public Set<BpmIdentity> getListUserByRelCode(String relCode) {
		IGroup rel = userGroupService.getByCode(GroupTypeConstant.POSITION.key(), relCode);
		if(BeanUtils.isEmpty(rel)){
			throw new RuntimeException(String.format("该岗位:%s不存在", relCode));
		}
		List<IUser> list = userService.getUserListByGroup(GroupTypeConstant.POSITION.key(), rel.getGroupId());
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<BpmIdentity> listIdentity = bpmIdentityConverter.convertUserList(list);
		Set<BpmIdentity> set = new LinkedHashSet<BpmIdentity>(listIdentity);
		return set;
	}
	
	/**
	 * 获取当前用户上级部门中指角色编码的人员
	 * @param roleId
	 * @return
	 */
	public Set<BpmIdentity> getListUserByParentOrgRoleCode(String roleCode) {
		IGroup role = userGroupService.getByCode(GroupTypeConstant.ROLE.key(), roleCode);
		List<IUser> list = userService.getUserListByGroup(GroupTypeConstant.ROLE.key(), role.getGroupId());
		// 求解出当前用户主组织的上级部门的人员
		IUser curser = ContextUtil.getCurrentUser();
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<BpmIdentity> listIdentity = bpmIdentityConverter.convertUserList(list);
		Set<BpmIdentity> set = new LinkedHashSet<BpmIdentity>(listIdentity);
		return set;
	}
	
	/**
	 * 获取申请人所在主组织的副总（配置组织参数：副总裁实现）
	 * @param roleId
	 * @return
	 */
	public Set<BpmIdentity> getUserByOrgParamsAlias(String alias) {
		ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
		String userId =  (String) taskCmd.getVariables().get(BpmConstants.START_USER);
		Org org =  orgManager.getMainGroup(userId); 
		if(org==null){
			 return new LinkedHashSet<BpmIdentity>();
		}
		String value = paramService.getParamByGroup(org.getGroupId(), alias)+"";
		List<IUser> list = new ArrayList<IUser>();
		IUser iUser = userService.getUserById(value);
		list.add(iUser);
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<BpmIdentity> listIdentity = bpmIdentityConverter.convertUserList(list);
		Set<BpmIdentity> set = new LinkedHashSet<BpmIdentity>(listIdentity);
		return set;
	}
	
	
}
