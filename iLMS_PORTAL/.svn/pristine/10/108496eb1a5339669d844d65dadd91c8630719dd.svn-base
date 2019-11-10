package com.hotent.mobile.weixin.util;

import java.util.ArrayList;
import java.util.List;

import com.hotent.base.core.util.AppUtil;
import com.hotent.mobile.weixin.model.WxOrg;
import com.hotent.mobile.weixin.model.WxUser;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.impl.service.DefaultOrgService;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.service.IOrgService;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.SysUser;
import com.hotent.org.persistence.model.User;


public class OrgConvertUtil {
	
	public static WxUser userToWxUser(User user){
		WxUser wxUser = new WxUser();
		wxUser.setEmail((String)user.getEmail());
		wxUser.setGender((String)user.getSex());
		wxUser.setMobile((String)user.getMobile());
		wxUser.setName(user.getFullname());
		wxUser.setUserid(user.getAccount());
		//wxUser.setWeixinid(user.getWeixin());
		
		//获取组织岗位
		IOrgService orgService = AppUtil.getBean(DefaultOrgService.class);
		
		IUserGroupService userGroupService = orgService.getUserGroupService();
		List<IGroup> orgs = userGroupService.getGroupsByGroupTypeUserId(GroupTypeConstant.ORG.key(),user.getUserId());
		List<IGroup> postList = userGroupService.getGroupsByGroupTypeUserId(GroupTypeConstant.POSITION.key(), user.getUserId());
		
		String [] department = new String[orgs.size()];
		for (int i = 0; i < orgs.size(); i++) {
			Org org=(Org) orgs.get(i);
			department[i]=org.getId();
			if(!"".equals(org.getWxOrgId())){
				department[i]=org.getWxOrgId();
			}
		}

		wxUser.setDepartment(department);
		
		String postStr = "";
		for (IGroup post : postList) {
			if(!postStr.equals(""))postStr+="/";
			postStr+=post.getName();
		}
		wxUser.setPosition(postStr);
		
		return wxUser;
	}
	
	public static WxUser sysUserToWxUser(SysUser sysUser){
		WxUser user = new WxUser();
		user.setEmail((String)sysUser.getEmail());
		user.setGender((String)sysUser.getSex());
		user.setMobile((String)sysUser.getMobile());
		user.setName(sysUser.getFullname());
		user.setUserid(sysUser.getAccount());
		
		
		//获取组织岗位
		IOrgService orgService = AppUtil.getBean(DefaultOrgService.class);
		
		IUserGroupService userGroupService = orgService.getUserGroupService();
		List<IGroup> orgs = userGroupService.getGroupsByGroupTypeUserId(GroupTypeConstant.ORG.key(),sysUser.getUserId());
		List<IGroup> postList = userGroupService.getGroupsByGroupTypeUserId(GroupTypeConstant.POSITION.key(), sysUser.getUserId());
		
		String [] department = new String[orgs.size()];
		for (int i = 0; i < orgs.size(); i++) {
			department[i] =orgs.get(i).getGroupId();
		}
		user.setDepartment(department);
		
		String postStr = "";
		for (IGroup post : postList) {
			if(!postStr.equals(""))postStr+="/";
			postStr+=post.getName();
		}
		user.setPosition(postStr);
		
		return user;
	}
	
	
	public static WxOrg sysOrgToWxOrg(Org org){
		WxOrg wxorg = new WxOrg();
		wxorg.setId(org.getId());
		wxorg.setParentid(org.getParentId());
		wxorg.setName(org.getName());
		return wxorg;
	}
	
	public static List<WxUser> sysUsersToWxUsers(List<SysUser> users){
		List<WxUser> wxUserList = new ArrayList<WxUser>();
		for (SysUser sysUser : users) {
			wxUserList.add(OrgConvertUtil.sysUserToWxUser(sysUser));
		}
		
		return wxUserList;
	}
}