package com.hanthink.gps.pub.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.dao.RolePrivilegeDao;
import com.hanthink.gps.pub.vo.RolePrivilegeVO;
import com.hanthink.gps.pub.vo.RoleVO;

/**
 * 角色权限管理数据管理层 <br>
 * 处理角色和权限的检索,修改,插入等操作
 * 
 * @author jxia
 * 
 */
public class RolePrivilegeDaoImpl extends BaseDaoSupport implements
		RolePrivilegeDao {
	/**
	 * 根据角色ID,检索角色权限信息
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 检索结果
	 */
	@SuppressWarnings("unchecked")
	public List<RolePrivilegeVO> queryByRoleId(String roleId) {
		return (List<RolePrivilegeVO>) executeQueryForList(
				"pub.select_rolePrivilege_by_roleId", roleId);
	}

	/**
	 * 根据角色ID,检索操作权限树
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 检索结果
	 */
	@SuppressWarnings("unchecked")
	public List<RolePrivilegeVO> queryOperPrivsByRoleId(String roleId) {
		return (List<RolePrivilegeVO>) executeQueryForList(
				"pub.select_operPrivileges_by_roleId", roleId);
	}

	/**
	 * 新增角色权限配置
	 * 
	 * @param rp
	 *            角色权限
	 * @return 检索结果
	 */
	public RolePrivilegeVO insert(RolePrivilegeVO rp) {
		return (RolePrivilegeVO) executeInsert("pub.insert_rolePrivilege",
				rp);
	}
	/**
	 * 判断权限是否已经插入
	 *  zhangye 20100915 追加
	 * @param rp 角色权限
	 * @return 检索结果
	 */
	public int selectRoleExist(RolePrivilegeVO rp){
		return  executeQueryForList("pub.select_roleExist",
				rp).size();
	}
	/**
	 * 删除角色权限配置
	 * 
	 * @param rp
	 *            角色权限
	 * @return 检索结果
	 */
	public int delete(RolePrivilegeVO rp) {
		return executeDelete("pub.delete_rolePrivilege", rp);
	}

	/**
	 * 检查指定的角色是不是已使用了
	 * 
	 * @param vo
	 * @return
	 */
	public RoleVO selectInUseRole(RoleVO vo)
	{
		return (RoleVO) executeQueryForObject(
				"pub.select_in_use_roleId", vo);
	}
	@SuppressWarnings("unchecked")
	public List<RolePrivilegeVO> querySupRolePrivilege(RoleVO vo) {
		return (List<RolePrivilegeVO>) executeQueryForList(
				"pub.select_operPrivileges_by_suproleId", vo);
	}
}
