package com.hotent.mini.ext.script;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.hotent.base.core.engine.script.IUserScript;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.helper.identity.BpmIdentityConverter;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgUserManager;
import com.hotent.org.persistence.manager.SysUserRelManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgUser;
import com.hotent.org.persistence.model.SysUserRel;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.api.model.SysType;
import com.hotent.sys.persistence.manager.SysTypeManager;
import com.hotent.sys.util.ContextUtil;

/**
 * 根据用户关系或组织关系获取人员
 * @author zhangxw
 *
 */

@Component
public class UserRelScript implements IUserScript {
	
	@Resource
	OrgManager orgManager;
	@Resource
	OrgUserManager orgUserManager;
	@Resource
	IUserService userService;
	@Resource
	SysUserRelManager sysUserRelManager;
	@Resource
	SysTypeManager sysTypeManager;
	
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
	 * 根据用户关系获取人员列表
	 * @param userId 用户ID
	 * @param level 级别
	 * @param typeId 关系类型ID
	 * @return
	 */
	private List<IUser> getSuperUserByRel(String userId,String level, String typeId){
		List<IUser> list = new ArrayList<IUser>();
		//先根据id找，如果 没有再根据key找，防止业务系统看不到id时，可以通过key查找
		SysType sysType = sysTypeManager.get(typeId);
		if(BeanUtils.isEmpty(sysType)){
			sysType = sysTypeManager.getByKey(typeId);
		}
		if(BeanUtils.isNotEmpty(sysType)){
			List<SysUserRel> sysUserRels = sysUserRelManager.getSuperUser(userId, level, sysType.getId());
			if(BeanUtils.isNotEmpty(sysUserRels)){
				for (SysUserRel sysUserRel : sysUserRels) {
					IUser user = new User();
					user.setUserId(sysUserRel.getUserId());
					user.setFullname(sysUserRel.getUserName());
					list.add(user);
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取组织负责人(包含主负责人)或主负责人
	 * @param orgId  组织ID
	 * @param isMain 是否主负责人
	 * @return
	 */
	public Set<BpmIdentity> getChargesByOrgId(String orgId,Boolean isMain){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isNotEmpty(orgId)){
			String[] orgIds = orgId.split(",");
			List<IUser> list = new ArrayList<IUser>();
			for (String id : orgIds) {
				List<OrgUser> orgUsers = orgUserManager.getChargesByOrgId(id,isMain);
				if(BeanUtils.isNotEmpty(orgUsers)){
					for (OrgUser orgUser : orgUsers) {
						IUser iUser = userService.getUserById(orgUser.getUserId());
						list.add(iUser);
					}
				}
			}
			if(BeanUtils.isNotEmpty(list)){
				identitys = convertUserList(list);
			}
		}
		return identitys;
	}
	
	/**
	 * 获取上级组织负责人(包含主负责人)或主负责人
	 * @param orgId  组织ID
	 * @param isMain 是否主负责人
	 * @return
	 */
	public Set<BpmIdentity> getUpChargesByOrgId(String orgId,Boolean isMain){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		if(StringUtil.isNotEmpty(orgId)){
			Org org =  orgManager.get(orgId);
			if(BeanUtils.isNotEmpty(org)){
				identitys = getChargesByOrgId(org.getParentId(),isMain);
			}
		}
		return identitys;
	}
	
	
	/**
	 * 获取上一节点执行人的组织负责人
	 * @param isMain 是否主负责人
	 * @return
	 */
	public Set<BpmIdentity> getChargesByPreNode(Boolean isMain){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		OrgUser orgUser = orgUserManager.getOrgUserMaster(ContextUtil.getCurrentUserId());
		if(BeanUtils.isNotEmpty(orgUser)){
			identitys = getChargesByOrgId(orgUser.getOrgId(),isMain);
		}
		return identitys;
	}
	
	/**
	 * 通过上一节点执行人获取汇报线上级 人员列表(参数选填)
	 * @param level 级别
	 * @param typeId 类型ID
	 * @return
	 */
	public Set<BpmIdentity> getByRelPreNode(String level,String typeId){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		List<IUser> list = getSuperUserByRel(ContextUtil.getCurrentUser().getUserId(), level, typeId);
		identitys = convertUserList(list);
		return identitys;
	}
	
	/**
	 * 通过发起人获取汇报线上级 人员列表(参数选填)
	 * @param level 级别
	 * @param typeId 类型ID
	 * @return
	 */
	public Set<BpmIdentity> getByRelStartUser(String level,String typeId){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
		if(BeanUtils.isNotEmpty(taskCmd)){
			String userId =  (String) taskCmd.getVariables().get(BpmConstants.START_USER);
			List<IUser> list = getSuperUserByRel(userId, level, typeId);
			identitys = convertUserList(list);
		}
		return identitys;
	}
	
	
	/**
	 * 通过上一节点执行人获取汇报线上级 人员列表
	 * @param level 级别
	 * @param typeId 类型ID
	 * @return
	 */
	public Set<BpmIdentity> getByRelPreNode(){
		return getByRelPreNode(null,null);
	}
	
	/**
	 * 通过发起人获取汇报线上级 人员列表
	 * @param level 级别
	 * @param typeId 类型ID
	 * @return
	 */
	public Set<BpmIdentity> getByRelStartUser(){
		return getByRelStartUser(null,null);
	}
	
	/**
	 * 获取子表字段(人员选择器)作为节点审批人员。
	 * @param tableName	表明
	 * @param field		字段名
	 * @return
	 */
	public Set<BpmIdentity> getSubFieldUser(String tableName,String field){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		ActionCmd cmd = ContextThreadUtil.getActionCmd();
		String json= cmd.getBusData();
		JSONObject jsonObj=JSONObject.fromObject(json);
		Iterator it = jsonObj.keys();
		List<IUser> userList = new ArrayList<IUser>();
		while(it.hasNext()){
			String key = (String) it.next();
			JSONObject mainTable = jsonObj.getJSONObject(key);
			if(BeanUtils.isNotEmpty(mainTable)){
				JSONArray subTable = mainTable.getJSONArray("sub_"+tableName);
				for (Object object : subTable) {
					JSONObject subData = (JSONObject) object;
					String fieldValue = subData.getString(field);
					if(StringUtil.isNotEmpty(fieldValue)){
						String[] ids = fieldValue.split(",");
						for (String id : ids) {
							IUser iUser = userService.getUserById(id);
							if(BeanUtils.isNotEmpty(iUser)){
								userList.add(iUser);
							}
						}
					}
				}
			}
		}
		if(BeanUtils.isNotEmpty(userList)){
			identitys.addAll(convertUserList(userList));
		}
		return identitys;
	}
	/**
	 * 获取子表字段(部门选择器，组织负责人)作为节点审批人员。
	 * @param tableName	表明
	 * @param field		字段名
	 * @return
	 */
	public Set<BpmIdentity> getSubFieldOrg(String tableName,String field,boolean isMain){
		Set<BpmIdentity> identitys = new LinkedHashSet<BpmIdentity>();
		ActionCmd cmd = ContextThreadUtil.getActionCmd();
		String json= cmd.getBusData();
		JSONObject jsonObj=JSONObject.fromObject(json);
		Iterator it = jsonObj.keys();
		while(it.hasNext()){
			String key = (String) it.next();
			JSONObject mainTable = jsonObj.getJSONObject(key);
			if(BeanUtils.isNotEmpty(mainTable)){
				JSONArray subTable = mainTable.getJSONArray("sub_"+tableName);
				for (Object object : subTable) {
					JSONObject subData = (JSONObject) object;
					String fieldValue = subData.getString(field);
					if(StringUtil.isNotEmpty(fieldValue)){
						String[] orgIds = fieldValue.split(",");
						for (String orgId : orgIds) {
							Set<BpmIdentity> orgIdentitys = getChargesByOrgId(orgId, isMain);
							identitys.addAll(orgIdentitys);
						}
					}
				}
			}
		}
		return identitys;
	}
	
}
