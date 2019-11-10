package com.hotent.org.api.impl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.GroupType;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgRelManager;
import com.hotent.org.persistence.manager.OrgReldefManager;
import com.hotent.org.persistence.manager.RoleManager;
import com.hotent.org.persistence.manager.UserManager;
import com.hotent.org.persistence.manager.UserRoleManager;
import com.hotent.org.persistence.model.OrgReldef;

/**
 * 用户与组关系的实现类：通过用户找组，通过组找人等
 * 
 * @author Administrator
 *
 */
@Service
public class DefaultUserGroupService implements IUserGroupService {
	@Resource
	UserManager userManager;
	@Resource
	OrgManager orgManager;
	@Resource
	OrgRelManager orgRelManager;
	@Resource
	UserRoleManager userRoleManager;
	@Resource
	RoleManager roleManager;
	@Resource
	OrgReldefManager orgReldefManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<IGroup> getGroupsByGroupTypeUserId(String groupType,String userId) {
		if (groupType.equals(GroupTypeConstant.ORG.key())) {
			return (List) orgManager.getOrgListByUserId(userId);
		}
		if (groupType.equals(GroupTypeConstant.ROLE.key())) {
			return (List) roleManager.getListByUserId(userId);
		}
		if (groupType.equals(GroupTypeConstant.POSITION.key())) {
			return (List) orgRelManager.getListByUserId(userId);
		}
		if (groupType.equals(GroupTypeConstant.JOB.key())) {
			return (List) orgReldefManager.getListByUserId(userId);
		}
		return null;
	}

	@Override
	public Map<String, List<IGroup>> getAllGroupByAccount(String account) {
		Map<String, List<IGroup>> listMap = new HashMap<String, List<IGroup>>();
		List<IGroup> listOrg = (List) orgManager.getOrgListByAccount(account);
		if (BeanUtils.isNotEmpty(listOrg)) {
			listMap.put(GroupTypeConstant.ORG.key(), listOrg);
		}
		List<IGroup> listRole = (List) roleManager.getListByAccount(account);
		if (BeanUtils.isNotEmpty(listRole)) {
			listMap.put(GroupTypeConstant.ROLE.key(), listRole);
		}
		List<IGroup> listOrgRel = (List) orgRelManager.getListByAccount(account);
		if (BeanUtils.isNotEmpty(listOrgRel)) {
			listMap.put(GroupTypeConstant.POSITION.key(), listOrgRel);
		}
		List<IGroup> listOrgRelDef = (List)orgReldefManager.getListByAccount(account);
		if (BeanUtils.isNotEmpty(listOrgRelDef)) {
			listMap.put(GroupTypeConstant.JOB.key(), listOrgRelDef);
		}
		return listMap;
	}

	@Override
	public Map<String, List<IGroup>> getAllGroupByUserId(String userId) {
		Map<String, List<IGroup>> listMap = new HashMap<String, List<IGroup>>();
		List<IGroup> listOrg = (List) orgManager.getOrgListByUserId(userId);
		if (BeanUtils.isNotEmpty(listOrg)) {
			listMap.put(GroupTypeConstant.ORG.key(), listOrg);
		}
		List<IGroup> listRole = (List) roleManager.getListByUserId(userId);
		if (BeanUtils.isNotEmpty(listRole)) {
			listMap.put(GroupTypeConstant.ROLE.key(), listRole);
		}
		List<IGroup> listOrgRel = (List) orgRelManager.getListByUserId(userId);
		if (BeanUtils.isNotEmpty(listOrgRel)) {
			listMap.put(GroupTypeConstant.POSITION.key(), listOrgRel);
		}
		List<IGroup> listOrgRelDef = (List)orgReldefManager.getListByUserId(userId);
		if (BeanUtils.isNotEmpty(listOrgRelDef)) {
			listMap.put(GroupTypeConstant.JOB.key(), listOrgRelDef);
		}
		return listMap;
	}

	/**
	 * 根据组类别和用户账号获取所有组
	 */
	@Override
	public List<IGroup> getGroupsByGroupTypeAccount(String groupType,String account) {
		List<IGroup> listMap = new ArrayList<IGroup>();
		if (groupType.equals(GroupTypeConstant.ORG.key())) {
			return  (List) orgManager.getOrgListByAccount(account);
		}
		if (groupType.equals(GroupTypeConstant.ROLE.key())) {
			return (List) roleManager.getListByAccount(account);
		}
		if (groupType.equals(GroupTypeConstant.POSITION.key())) {
			return (List) orgRelManager.getListByAccount(account);
		}
		if (groupType.equals(GroupTypeConstant.JOB.key())) {
			return (List) orgReldefManager.getListByAccount(account);
		}
		return listMap;
	}

	/**
	 * 根据用户ID获取所有组
	 */
	@Override
	public List<IGroup> getGroupsByUserId(String userId) {
		List<IGroup> listMap = new ArrayList<IGroup>();
		List<IGroup> listOrg = (List) orgManager.getOrgListByUserId(userId);
		if (BeanUtils.isNotEmpty(listOrg)) {
			listMap.addAll(listOrg);
		}
		List<IGroup> listRole = (List) roleManager.getListByUserId(userId);
		if (BeanUtils.isNotEmpty(listRole)) {
			listMap.addAll(listRole);
		}
		List<IGroup> listOrgRel = (List) orgRelManager.getListByUserId(userId);
		if (BeanUtils.isNotEmpty(listOrgRel)) {
			listMap.addAll(listOrgRel);
		}
		List<IGroup> listOrgRelDef = (List)orgReldefManager.getListByUserId(userId);
		if (BeanUtils.isNotEmpty(listOrgRelDef)) {
			listMap.addAll(listOrgRelDef);
		}
		return listMap;
	}

	/**
	 * 根据账号获取所有组
	 */
	@Override
	public List<IGroup> getGroupsByAccount(String account) {
		List<IGroup> listMap = new ArrayList<IGroup>();
		List<IGroup> listOrg = (List) orgManager.getOrgListByAccount(account);
		if (BeanUtils.isNotEmpty(listOrg)) {
			listMap.addAll(listOrg);
		}
		List<IGroup> listRole = (List) roleManager.getListByAccount(account);
		if (BeanUtils.isNotEmpty(listRole)) {
			listMap.addAll(listRole);
		}
		List<IGroup> listOrgRel = (List) orgRelManager.getListByAccount(account);
		if (BeanUtils.isNotEmpty(listOrgRel)) {
			listMap.addAll(listOrgRel);
		}
		List<IGroup> listOrgRelDef = (List)orgReldefManager.getListByAccount(account);
		if (BeanUtils.isNotEmpty(listOrgRelDef)) {
			listMap.addAll(listOrgRelDef);
		}
		return listMap;
	}

	/**
	 * 根据组类别和组ID获取组定义
	 */
	@Override
	public IGroup getById(String groupType, String groupId) {
		if (groupType.equals(GroupTypeConstant.ORG.key())) {
			return orgManager.get(groupId);
		}
		if (groupType.equals(GroupTypeConstant.ROLE.key())) {
			return roleManager.get(groupId);
		}
		if (groupType.equals(GroupTypeConstant.POSITION.key())) {
			return orgRelManager.get(groupId);
		}
		if (groupType.equals(GroupTypeConstant.JOB.key())) {
			return orgReldefManager.get(groupId);
		}
		return null;
	}

	/**
	 * 根据组类别和组编码获取组定义
	 */
	@Override
	public IGroup getByCode(String groupType, String code) {
		if (groupType.equals(GroupTypeConstant.ORG.key())) {
			return orgManager.getByCode(code);
		}
		if (groupType.equals(GroupTypeConstant.ROLE.key())) {
			return roleManager.getByAlias(code);
		}
		if (groupType.equals(GroupTypeConstant.POSITION.key())) {
			return orgRelManager.getByCode(code);
		}
		if (groupType.equals(GroupTypeConstant.JOB.key())) {
			return orgReldefManager.getByCode(code);
		}
		return null;
	}

	/**
	 * 获取所有组类型
	 */
	@Override
	public List<GroupType> getGroupTypes() {
		List<GroupType> list = new ArrayList<GroupType>();
		for (GroupTypeConstant e : GroupTypeConstant.values()) {
			GroupType type = new GroupType(e.key(), e.label());
			list.add(type);
		}
		return list;
	}

}
