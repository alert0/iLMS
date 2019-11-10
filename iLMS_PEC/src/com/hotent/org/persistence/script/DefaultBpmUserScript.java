package com.hotent.org.persistence.script;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.engine.script.IUserScript;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.helper.identity.BpmIdentityConverter;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgUserManager;
import com.hotent.org.persistence.manager.RoleManager;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.model.OrgUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 人员脚本
 * 作用：可用于节点处理人
 * @author luoxw
 *
 */
@Service
public class DefaultBpmUserScript implements IUserScript {
	@Resource 
    RoleManager roleManager;
	@Resource 
    UserManager userManager;
	@Resource 
	OrgUserManager orgUserManager;
	@Resource 
	OrgManager orgManager;
	@Resource
	IUserService userService;
	@Resource
	IUserGroupService userGroupService;
	
	
	/**
	 * 根据角色ID获取人员列表
	 * @param roleId
	 * @return
	 */
	public Set<BpmIdentity> getListUserByRoleId(String roleId) {
		List<IUser> list=userService.getUserListByGroup(GroupTypeConstant.ROLE.key(), roleId);
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<BpmIdentity> listIdentity= bpmIdentityConverter.convertUserList(list);
		Set<BpmIdentity> set = new LinkedHashSet<BpmIdentity>(listIdentity);
		return set;
	}
	
	/**
	 * 根据岗位relId获取人员列表
	 * @param relId
	 * @return
	 */
	public Set<BpmIdentity> getListUserByRelCode(String relId) {
		List<IUser> list=userService.getUserListByGroup(GroupTypeConstant.POSITION.key(), relId);
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<BpmIdentity> listIdentity= bpmIdentityConverter.convertUserList(list);
		Set<BpmIdentity> set = new HashSet<BpmIdentity>(listIdentity);
		return set;
	}
	
	/**
	 * 获取当前用户上级部门中指角色编码的人员
	 * @param roleId
	 * @return
	 */
	public Set<BpmIdentity> getListUserByParentOrgRoleCode(String roleId) {
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<IUser> listResult=new ArrayList<IUser>();
		//角色中的人员
		List<IUser> list=userService.getUserListByGroup(GroupTypeConstant.ROLE.key(), roleId);
		//求解出当前用户主组织的所有上级部门的人员
		IUser curser=ContextUtil.getCurrentUser();
		OrgUser orgUser= orgUserManager.getOrgUserMaster(curser.getUserId());
		String parentId= orgManager.get(orgUser.getOrgId()).getParentId();
		//上级部门下的人员
		List<IUser> listUser=(List)userManager.getUserListByOrgId(parentId);
		//取交集
		if(BeanUtils.isEmpty(listUser)){
			listResult=list;
		}
		else{
			for (IUser user : listUser) {
				for (IUser itemUser : list) {
					if(user.getUserId().equals(itemUser.getUserId())){
						listResult.add(itemUser);
					}
				}
			}
		}
		List<BpmIdentity> listIdentity=bpmIdentityConverter.convertUserList(listResult);
		Set<BpmIdentity> set = new HashSet<BpmIdentity>(listIdentity);
		return set;
	}
	
}
