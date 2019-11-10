package com.hanthink.gps.pub.dao;

import java.util.List;

import com.hanthink.gps.pub.vo.RolePrivilegeVO;
import com.hanthink.gps.pub.vo.RoleVO;

/**
 * 角色权限管理数据管理层
 * <br> 处理角色和权限的检索,修改,插入等操作
 * @author jxia
 *
 */
public interface RolePrivilegeDao {
	/**
	 * 根据角色ID,检索角色权限信息
	 * @param roleId 角色ID
	 * @return 检索结果
	 */
	public List<RolePrivilegeVO> queryByRoleId(String roleId);
	/**
	 * 根据角色ID,检索操作权限树
	 * @param roleId 角色ID
	 * @return 检索结果
	 */
	public List<RolePrivilegeVO> queryOperPrivsByRoleId(String roleId);
	/**
	 * 判断权限是否已经插入
	 *  zhangye 20100915 追加
	 * @param rp 角色权限
	 * @return 检索结果
	 */
	public int selectRoleExist(RolePrivilegeVO rp);
	/**
	 * 新增角色权限配置
	 * @param rp 角色权限
	 * @return 检索结果
	 */
	public RolePrivilegeVO insert(RolePrivilegeVO rp);
	
	/**
	 * 删除角色权限配置
	 * @param rp 角色权限
	 * @return 检索结果
	 */
	public int delete(RolePrivilegeVO rp);
	
	/**
	 * 检索指定的角色是不是已使用
	 * @param vo
	 * @return
	 */
	public RoleVO selectInUseRole(RoleVO vo);
	
	public List<RolePrivilegeVO> querySupRolePrivilege(RoleVO vo);
}
