package com.hotent.mini.ext.script;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.engine.script.IUserScript;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.biz.meeting.manager.MeetingAppointManager;
import com.hotent.biz.meeting.manager.MeetingroomManager;
import com.hotent.biz.meeting.model.MeetingAppoint;
import com.hotent.biz.meeting.model.Meetingroom;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.ExtractType;
import com.hotent.bpmx.api.helper.identity.BpmIdentityConverter;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.model.IdentityType;
import com.hotent.org.api.service.IParamService;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgReldefManager;
import com.hotent.org.persistence.manager.OrgUserManager;
import com.hotent.org.persistence.manager.RoleManager;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgReldef;
import com.hotent.org.persistence.model.OrgUser;
import com.hotent.org.persistence.model.Role;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.util.ContextUtil;

@Component
public class UserScript implements IUserScript {
	
	@Resource
	RoleManager roleManager;
	@Resource
	OrgManager orgManager;
	@Resource
	UserManager userManager;
	@Resource
	OrgUserManager orgUserManager;
	@Resource
	IUserService userService;
	@Resource
	IParamService paramService;
	@Resource
	OrgReldefManager orgReldefManager;
	@Resource
	BpmDefUserManager bpmDefUserManager;
	@Resource
	MeetingroomManager meetingroomManager;
	@Resource
	MeetingAppointManager meetingAppointManager;
	@Resource  
	BpmProcessInstanceManager bpmProcessInstanceManager;
	/**
	 * 根据角色别名获取角色。
	 * @param alias
	 * @return
	 */
	public Set<BpmIdentity> getRoleByAlias(String alias){
		Set<BpmIdentity> set=new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isEmpty(alias)) return set;
		
		String[] aryAlias=alias.split(",");
		for(String tmp:aryAlias){
			Role role=roleManager.getByAlias(tmp);
			BpmIdentity identity=new DefaultBpmIdentity(role.getId(),role.getName(),BpmIdentity.TYPE_GROUP);
			identity.setType(BpmIdentity.TYPE_GROUP);
			identity.setExtractType(ExtractType.EXACT_NOEXACT);
			identity.setGroupType( GroupTypeConstant.ROLE.key());
			set.add(identity);
		}
		
		return set;
	}
	
	
	public Set<BpmIdentity> getRoleById(String roleId){
		Set<BpmIdentity> set=new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isEmpty(roleId)) return set;
		String[] aryRoleIds=roleId.split(",");
		for(String tmp:aryRoleIds){
			Role role=roleManager.get(tmp);
			BpmIdentity identity=new DefaultBpmIdentity(role.getId(),role.getName(),BpmIdentity.TYPE_GROUP);
			identity.setType(BpmIdentity.TYPE_GROUP);
			identity.setExtractType(ExtractType.EXACT_NOEXACT);
			identity.setGroupType( GroupTypeConstant.ROLE.key());
			set.add(identity);
		}
		return set;
	}
	
	
	
	/**
	 * 根据组织Id来获取BpmIdentity 
	 * ids "12,34"
	 */
	public Set<BpmIdentity> getOrgById(String ids){
		Set<BpmIdentity> set=new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isEmpty(ids)) return set;
		String[] idsArr = ids.split(",");
		for (String id : idsArr) {
			Org org =  orgManager.get(id);
		    BpmIdentity identity=new DefaultBpmIdentity(org.getId(),org.getName(),BpmIdentity.TYPE_GROUP);
		    identity.setGroupType(BpmIdentity.GROUP_TYPE_ORG);
		    identity.setExtractType(ExtractType.EXACT_NOEXACT);
		    set.add(identity);
		}
		return set;
	}
	
	/**
	 * List<BoData> + key
	 * type:"org","role","user"
	 * mainKey
	 * @return
	 */
	public Set<BpmIdentity> getUserById(String mainKey, List<BoData> list,
			String subKey) {
		Set<BpmIdentity> set = new LinkedHashSet<BpmIdentity>();
		if (BeanUtils.isNotEmpty(mainKey)) {
			String[] idsArr = mainKey.split(",");
			for (String userId : idsArr) {
				User user = userManager.get(userId);
				if (BeanUtils.isNotEmpty(user)) {
					BpmIdentity bpmIdentity = new DefaultBpmIdentity();
					bpmIdentity.setType(IdentityType.USER);
					bpmIdentity.setId(user.getId());
					bpmIdentity.setName(user.getFullname());
					set.add(bpmIdentity);
				}
			}
		}
		if (BeanUtils.isNotEmpty(list)) {
			for (BoData boData : list) {
				String userId = (String) boData.getByKey(subKey);
				User user = userManager.get(userId);
				if (BeanUtils.isNotEmpty(user)) {
					BpmIdentity bpmIdentity = new DefaultBpmIdentity();
					bpmIdentity.setType(IdentityType.USER);
					bpmIdentity.setId(user.getId());
					bpmIdentity.setName(user.getFullname());
					set.add(bpmIdentity);
				}
			}
		}

		return set;
	}
	
	public Set<BpmIdentity> getUser(){
		Set<BpmIdentity> set = new LinkedHashSet<BpmIdentity>();
		BpmIdentity bpmIdentity = new DefaultBpmIdentity();
		bpmIdentity.setType(IdentityType.USER);
		bpmIdentity.setId("1234");
		bpmIdentity.setName("demoUser");
		set.add(bpmIdentity);
		
		bpmIdentity.setId("5678");
		bpmIdentity.setName("demoUser2");
		set.add(bpmIdentity);
		
		return set;
	}
	
	/**
	 * 根据组织ID获取该组织中为主组织的人员列表
	 * @param orgId
	 * @return
	 */
	public Set<BpmIdentity> getMainByOrgId(String orgId){
		Set<BpmIdentity> set=new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isEmpty(orgId)) return set;
		String[] aryOrgIds=orgId.split(",");
		for(String tmp:aryOrgIds){
			List<User> users = userManager.getOrgUserRelByOrgId(tmp);
			if(BeanUtils.isNotEmpty(users)){
				for(int i=0;i<users.size();i++){
					Map map = (Map) users.get(i);
					if(map.get("isMaster").toString().equals("1") && map.get("orgId").toString().equals(tmp)){
						OrgUser	orgUser = orgUserManager.get(map.get("orgUserId").toString());
						if(BeanUtils.isNotEmpty(orgUser)){
							User user = userManager.get(orgUser.getUserId());
							if(BeanUtils.isNotEmpty(user)){
								BpmIdentity identity=new DefaultBpmIdentity(user);
								set.add(identity);
							}
						}
					}
				}
			}
		}
		return set;
	}
	
	
	/**
	 * 获取组织负责人（根据组织参数获取）
	 * @param orgId 组织ID
	 * @param alias 组织参数别名
	 * @return
	 */
	public Set<BpmIdentity> getLeanderByOrgId(String orgId,String alias){
		if(StringUtil.isEmpty(orgId)||StringUtil.isEmpty(alias)) return new LinkedHashSet<BpmIdentity>();
		Org org =  orgManager.get(orgId);
		if(BeanUtils.isEmpty(org)){
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
	
	/**
	 * 获取上级组织的负责人（根据组织参数获取）
	 * @param orgId 组织ID
	 * @param alias 组织参数别名
	 * @return
	 */
	public Set<BpmIdentity> getUpLeanderByOrgId(String orgId,String alias){
		if(StringUtil.isEmpty(orgId)||StringUtil.isEmpty(alias)) return new LinkedHashSet<BpmIdentity>();
		Org org =  orgManager.get(orgId);
		if(BeanUtils.isEmpty(org)){
			 return new LinkedHashSet<BpmIdentity>();
		}
		Org upOrg =  orgManager.get(org.getParentId());
		if(BeanUtils.isEmpty(upOrg)){
			 return new LinkedHashSet<BpmIdentity>();
		}
		String value = paramService.getParamByGroup(upOrg.getGroupId(), alias)+"";
		List<IUser> list = new ArrayList<IUser>();
		IUser iUser = userService.getUserById(value);
		list.add(iUser);
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<BpmIdentity> listIdentity = bpmIdentityConverter.convertUserList(list);
		Set<BpmIdentity> set = new LinkedHashSet<BpmIdentity>(listIdentity);
		return set;
	}
	
	/**
	 * 获取上一节点执行人的部门负责人
	 * @param alias
	 * @return
	 */
	public Set<BpmIdentity> getLeanderByPreNode(String alias){
		if(StringUtil.isEmpty(alias)) return new LinkedHashSet<BpmIdentity>();
		Org org =  orgManager.getMainGroup(ContextUtil.getCurrentUser().getUserId()); 
		if(BeanUtils.isEmpty(org)){
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
	
	/**
	 * 根据用户ID获取
	 * ids "12,34"
	 */
	public Set<BpmIdentity> getUsersById(String ids){
		Set<BpmIdentity> set=new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isEmpty(ids)) return set;
		String[] idsArr = ids.split(",");
		List<IUser> iUsers = userService.getUserByIds(idsArr);
		if(BeanUtils.isNotEmpty(iUsers)){
			for(int i=0;i<iUsers.size();i++){
				IUser iUser = iUsers.get(i);
				BpmIdentity identity=new DefaultBpmIdentity(iUser.getUserId(),iUser.getFullname(),BpmIdentity.TYPE_USER);
				set.add(identity);
			}
		}
		return set;
	}
	
	/**
	 * 获取组织，岗位中的人员
	 * @param orgId
	 * @param relId
	 * @return
	 */
	public Set<BpmIdentity> getByOrgIdOrRelId(String orgId, String relId){
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("rel_id_", relId, QueryOP.EQUAL);
		queryFilter.addFilter("org_id_", orgId, QueryOP.EQUAL);
		List<OrgUser> orgUsers = orgUserManager.query(queryFilter);
		List<IUser> list = new ArrayList<IUser>();
		for (OrgUser orgUser : orgUsers) {
			IUser iUser = userService.getUserById(orgUser.getUserId());
			list.add(iUser);
		}
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<BpmIdentity> listIdentity = bpmIdentityConverter.convertUserList(list);
		Set<BpmIdentity> set = new LinkedHashSet<BpmIdentity>(listIdentity);
		return set;
	}
	
	
	/**
	 * 获取orgId的上级部门的， 岗位（relId）下的人员
	 * @param orgId
	 * @param relId
	 * @return
	 */
	public Set<BpmIdentity> getPByOrgIdOrRelId(String orgId, String relId){
		
		Org org = orgManager.get(orgId);
		org = orgManager.get(org.getParentId());
		orgId = org.getId();
		
		return getByOrgIdOrRelId(orgId, relId);
	}
	
	/**
	 * 通过职位代码获取职位
	 * @param code 职位代码，多个职位 ',' 分割
	 * @return
	 */
	public Set<BpmIdentity> getByJobCode(String code){
		Set<BpmIdentity> set=new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isEmpty(code)) return set;
		
		String[] codeAry=code.split(",");
		for(String tmp : codeAry){
			OrgReldef orgRelDef = orgReldefManager.getByCode(tmp);
			BpmIdentity identity=new DefaultBpmIdentity(orgRelDef.getId(),orgRelDef.getName(),BpmIdentity.TYPE_GROUP);
			identity.setType(BpmIdentity.TYPE_GROUP);
			identity.setExtractType(ExtractType.EXACT_NOEXACT);
			identity.setGroupType( GroupTypeConstant.JOB.key());
			set.add(identity);
		}
		return set;
	}
	
	
	/**
	 * 根据会议室id获取
	 * ids "12,34"
	 */
	public Set<BpmIdentity> getPendingUsersByMeetingId(String id){
		Meetingroom meetingroom=meetingroomManager.get(id);
		String ids=meetingroom.getPendingUserId();
		Set<BpmIdentity> set=new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isEmpty(ids)) return set;
		String[] idsArr = ids.split(",");
		List<IUser> iUsers = userService.getUserByIds(idsArr);
		if(BeanUtils.isNotEmpty(iUsers)){
			for(int i=0;i<iUsers.size();i++){
				IUser iUser = iUsers.get(i);
				BpmIdentity identity=new DefaultBpmIdentity(iUser.getUserId(),iUser.getFullname(),BpmIdentity.TYPE_USER);
				set.add(identity);
			}
		}
		return set;
	}	

	/**
	 * 更新或新增预约
	 * status=0,新增，status=1.更新
	 */
	public void updateAppointMent(String hysId,BpmDelegateTask task,String meetingName,String hostessName,Date appointBenTime,Date appointEndTime,String status){
		MeetingAppoint meetingAppoint =null;
		String meetingId=(String) task.getVariable(BpmConstants.PROCESS_INST_ID);
		meetingAppoint =meetingAppointManager.getByMeetingId(meetingId);
		try {
			
			if(status=="0" && BeanUtils.isEmpty(meetingAppoint)){
				 meetingAppoint =new MeetingAppoint();
				meetingAppoint.setId(UniqueIdUtil.getSuid());
				meetingAppoint.setMeetingroomId(hysId);
				meetingAppoint.setMeetingId(meetingId);
				meetingAppoint.setMeetingName(meetingName);
				meetingAppoint.setHostessName(hostessName);
				meetingAppoint.setAppointmentBegTime(appointBenTime);
				meetingAppoint.setAppointmentEndTime(appointEndTime);
				meetingAppoint.setAppointmentStatus(0);
				meetingAppointManager.create(meetingAppoint);
			}else{
				meetingAppoint =meetingAppointManager.getByMeetingId(meetingId);
				meetingAppoint.setMeetingroomId(hysId);
				meetingAppoint.setMeetingId(meetingId);
				meetingAppoint.setMeetingName(meetingName);
				meetingAppoint.setHostessName(hostessName);
				meetingAppoint.setAppointmentBegTime(appointBenTime);
				meetingAppoint.setAppointmentEndTime(appointEndTime);
				meetingAppoint.setAppointmentStatus(1);
				meetingAppointManager.update(meetingAppoint);
			}
		} catch (Exception e) {
		}
	}	
	public String getHasRightObjs(String objType) {
		List<String> idsList = bpmDefUserManager.getAuthorizeIdsByUserMap(objType);
		return StringUtil.join(idsList.toArray(new String[]{}), ",");
	}
}
