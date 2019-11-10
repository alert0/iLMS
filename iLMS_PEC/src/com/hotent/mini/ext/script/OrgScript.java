                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    package com.hotent.mini.ext.script;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hotent.base.core.engine.script.IUserScript;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.mybatis.CommonDao;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.helper.identity.BpmIdentityConverter;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.model.IdentityType;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgReldefManager;
import com.hotent.org.persistence.manager.OrgUserManager;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgReldef;
import com.hotent.org.persistence.model.OrgUser;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.util.ContextUtil;

@Component
public class OrgScript implements IUserScript  {
	
	@Resource
	OrgManager orgManager;
	@Resource
	OrgUserManager orgUserManager;
	@Resource
	IUserService userService;
	@Resource
	OrgReldefManager orgReldefManager;
	private List<OrgUser> orgUsers;
	@Resource
	CommonDao<?> commonDao;
	@Resource
	UserManager userManager;
	
	
	
	/**
	 * 将用户列表转换成BpmIdentity列表
	 * @param list
	 * @return
	 */
	private Set<BpmIdentity> convertUserList(List<IUser> list){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		if(BeanUtils.isNotEmpty(list)){
			BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
			List<BpmIdentity> listIdentity = bpmIdentityConverter.convertUserList(list);
			identitys = new LinkedHashSet<BpmIdentity>(listIdentity);
		}
		return identitys;
	}
	
	
	/**
	 * 获取当前用户的上级部门的负责人
	 * @param isMain 是否主负责人
	 * @return
	 */
	public Set<BpmIdentity> getChargesByPOrg(Boolean isMain){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		IGroup group = ContextUtil.getCurrentGroup();
		if(BeanUtils.isNotEmpty(group)){
			String orgId =  group.getParentId();
			if(StringUtil.isNotEmpty(orgId)){
				List<OrgUser> orgUsers = orgUserManager.getChargesByOrgId(orgId,isMain);
				if(BeanUtils.isNotEmpty(orgUsers)){
					List<IUser> list = new ArrayList<IUser>();
					for (OrgUser orgUser : orgUsers) {
						IUser iUser = userService.getUserById(orgUser.getUserId());
						list.add(iUser);
					}
					identitys = convertUserList(list);
				}
			}
		}
		return identitys;
	}
	
	/**
	 * 获取发起人的上级部门的（主）负责人
	 * @param isMain 是否主负责人
	 * @return
	 */
	public Set<BpmIdentity> getChargesByStartPOrg(Boolean isMain){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
		String userId =  (String) taskCmd.getVariables().get(BpmConstants.START_USER);
		Org org =  orgManager.getMainGroup(userId); 
		if(BeanUtils.isNotEmpty(org)){
			String orgId =  org.getParentId();
			if(StringUtil.isNotEmpty(orgId)){
				List<OrgUser> orgUsers = orgUserManager.getChargesByOrgId(orgId,isMain);
				if(BeanUtils.isNotEmpty(orgUsers)){
					List<IUser> list = new ArrayList<IUser>();
					for (OrgUser orgUser : orgUsers) {
						IUser iUser = userService.getUserById(orgUser.getUserId());
						list.add(iUser);
					}
					identitys = convertUserList(list);
				}
			}
		}
		return identitys;
	}
	
	/**
	 * 判断发起人是否包含某职务
	 * @param orgReldefCode
	 * @return
	 */
	public boolean isContainsJob(String orgReldefCode){
		ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
		String userId =  (String) taskCmd.getVariables().get(BpmConstants.START_USER);
		List<OrgReldef> reldefs = orgReldefManager.getListByUserId(userId);
		
		for (OrgReldef orgReldef : reldefs) {
			if(orgReldefCode.equals(orgReldef.getCode())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断当前用户主部门是否有上级 
	 * @param orgReldefCode
	 * @return
	 */
	public boolean isSupOrgByCurrMain(int level){
        ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
        String userId =  (String) taskCmd.getVariables().get(BpmConstants.START_USER);
        OrgUser orgUser = orgUserManager.getOrgUserMaster(userId);
        if(BeanUtils.isNotEmpty(orgUser)){
        	String levelGroupId = orgUser.getOrgId();
            Org org = orgManager.get(levelGroupId);
            while(level>0) {
                org = orgManager.get(org.getParentId());
                if(BeanUtils.isNotEmpty(org)){
                	levelGroupId = org.getId();
                	--level;
                }else{
                	return false;
                }
            }
            IGroup group = ContextUtil.getCurrentGroup();
            String groupId = group.getGroupId();
            if(levelGroupId.equals(groupId)){
                return false;
            }
            if(StringUtil.isNotZeroEmpty(group.getParentId())){
                return true;
            }
        }
        return false;
    }
	
	public boolean isSupOrgByCurrMain(){
       IGroup group = ContextUtil.getCurrentGroup();
       if(StringUtil.isNotZeroEmpty(group.getParentId())){
           return true;
       }
       return false;
    }
	
	
	/**
	 * 获取当前用户的部门的直接（主）负责人（负责人中可能包含自己）
	 * @param isMain 是否主负责人
	 * @return
	 */
	public Set<BpmIdentity> getChargesByOrg(Boolean isMain){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		IGroup group = ContextUtil.getCurrentGroup();
		if(BeanUtils.isNotEmpty(group)){
			List<OrgUser> orgUsers = orgUserManager.getChargesByOrgId(group.getGroupId(),isMain);
			if(BeanUtils.isNotEmpty(orgUsers)){
				List<IUser> list = new ArrayList<IUser>();
				for (OrgUser orgUser : orgUsers) {
					IUser iUser = userService.getUserById(orgUser.getUserId());
					list.add(iUser);
				}
				identitys = convertUserList(list);
			}
		}
		return identitys;
	}
	
	/**
	 * 获取发起人的部门的直接（主）负责人（负责人中可能包含自己）
	 * @param isMain 是否主负责人
	 * @return
	 */
	public Set<BpmIdentity> getChargesByStartOrg(Boolean isMain){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
		String userId =  (String) taskCmd.getVariables().get(BpmConstants.START_USER);
		Org org =  orgManager.getMainGroup(userId); 
		if(BeanUtils.isNotEmpty(org)){
			List<OrgUser> orgUsers = orgUserManager.getChargesByOrgId(org.getGroupId(),isMain);
			if(BeanUtils.isNotEmpty(orgUsers)){
				List<IUser> list = new ArrayList<IUser>();
				for (OrgUser orgUser : orgUsers) {
					IUser iUser = userService.getUserById(orgUser.getUserId());
					list.add(iUser);
				}
				identitys = convertUserList(list);
			}
		}
		return identitys;
	}
	
	
	
	/**
	 * 根据组织别名获取组织负责人
	 * @param isMain 是否主负责人
	 * @return
	 */
	public Set<BpmIdentity> getChargesByOrgCode(String code,Boolean isMain){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		Org org =  orgManager.getByCode(code);
		if(BeanUtils.isNotEmpty(org)){
			List<OrgUser> orgUsers = orgUserManager.getChargesByOrgId(org.getGroupId(),isMain);
			if(BeanUtils.isNotEmpty(orgUsers)){
				List<IUser> list = new ArrayList<IUser>();
				for (OrgUser orgUser : orgUsers) {
					IUser iUser = userService.getUserById(orgUser.getUserId());
					list.add(iUser);
				}
				identitys = convertUserList(list);
			}
		}
		return identitys;
	}
	
	/**
	 * 根据角色别名、组织别名获取组织中角色为该角色的(主)负责人（如果isMain传null，则获取该组织下角色为该角色的所有人）
	 * @param isMain 是否主负责人
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Set<BpmIdentity> getChargesByOrgRoleCode(String roleCode,String orgCode,Boolean isMain){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isNotEmpty(roleCode)&&StringUtil.isNotEmpty(orgCode)){
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM sys_org_user where org_id_ in(select id_ FROM sys_org where code_=\""+orgCode+"\") ");
			if(BeanUtils.isNotEmpty(isMain)){
				if(isMain){
					sql.append(" and is_charge_='2' ");
				}else{
					sql.append(" and is_charge_!='0' ");
				}
			}
			sql.append(" and user_id_ in(select user_id_ FROM sys_user_role where role_id_ in(select ID_ from sys_role where alias_=\""+roleCode+"\")) ");
			try {
				orgUsers = commonDao.query(sql.toString());
			} catch (Exception e) {}
			if(BeanUtils.isNotEmpty(orgUsers)){
				List<IUser> list = new ArrayList<IUser>();
				for (Object obj : orgUsers) {
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(obj);
					IUser iUser = userService.getUserById(json.getString("USER_ID_"));
					list.add(iUser);
				}
				identitys = convertUserList(list);
			}
		}
		return identitys;
	}
	
	/**
	 * 根据组织编码和职务编码获取人员
	 * @param orgCode 组织编码
	 * @param redDefCode 职务编码
	 * @return
	 */
	public Set<BpmIdentity> getByOrgRelDefCode(String orgCode,String redDefCode){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isNotEmpty(orgCode)&&StringUtil.isNotEmpty(redDefCode)){
			List<User> users = new ArrayList<User>();
			StringBuilder sql = new StringBuilder();
			sql.append(" select * from sys_user where id_ in ( ");
			
			sql.append(" select DISTINCT user_id_ from sys_org_user where rel_id_ in( ");
			sql.append(" select a.id_ from sys_org_rel a ");
			sql.append(" left join sys_org_reldef b on a.rel_def_id_= b.ID_ ");
			sql.append(" LEFT JOIN sys_org c on a.org_id_ = c.id_  ");
			sql.append(" where b.code_='"+redDefCode+"'  and c.code_='"+orgCode+"' ");
			sql.append(" ) ");
			sql.append(" ) ");
			try {
				users = commonDao.query(sql.toString());
			} catch (Exception e) {}
			if(BeanUtils.isNotEmpty(users)){
				for (Object obj : users) {
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(obj);
					BpmIdentity bpmIdentity = new DefaultBpmIdentity();
					bpmIdentity.setType(IdentityType.USER);
					bpmIdentity.setId(json.getString("ID_"));
					bpmIdentity.setName(json.getString("FULLNAME_"));
					identitys.add(bpmIdentity);
				}
			}
		}
		return identitys;
	}
	
	/**
	 * 根据组织编码和岗位编码获取人员
	 * @param orgCode
	 * @param relCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Set<BpmIdentity> getByOrgRelCode(String orgCode,String relCode){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isNotEmpty(orgCode)&&StringUtil.isNotEmpty(orgCode)){
			StringBuilder sql = new StringBuilder();
			sql.append(" select * from sys_org_user a LEFT JOIN sys_org b on a.org_id_= b.id_ LEFT JOIN sys_org_rel c on a.rel_id_=c.id_ ");
			sql.append(" where b.code_='"+orgCode+"' and c.rel_code_='"+relCode+"' ");
			try {
				orgUsers = commonDao.query(sql.toString());
			} catch (Exception e) {}
			if(BeanUtils.isNotEmpty(orgUsers)){
				List<IUser> list = new ArrayList<IUser>();
				for (Object obj : orgUsers) {
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(obj);
					IUser iUser = userService.getUserById(json.getString("USER_ID_"));
					list.add(iUser);
				}
				identitys = convertUserList(list);
			}
		}
		return identitys;
	}
	
}
