package com.hanthink.gps.pub.dao;

import com.hanthink.gps.pub.vo.FactoryVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.PrivilegeVO;
import com.hanthink.gps.pub.vo.RolePrivilegeVO;
import com.hanthink.gps.pub.vo.RoleVO;
import com.hanthink.gps.pub.vo.UserVO;
/**
 * 角色Dao
 * @author qzhang
 */
public interface RoleDao {
	/**
	 * 根据指定的开始条数和查询条数，取得用户信息。
	 * 
	 * @param start
	 *            开始记录
	 * @param limit
	 *            查询条数
	 * @return 查询结果
	 */
	public PageObject queryForPage(RoleVO queryRole, int start, int limit);
	/**
	 * 根据角色ID，取得角色信息。
	 * @param queryRole 角色
	 * @return 查询结果
	 */
	public RoleVO queryByRoleId(RoleVO queryRole);
	/**
	 * 新增角色
	 * @param roleVo 角色
	 * @return 查询结果
	 */
	public RoleVO insert(RoleVO role);
	/**
	 * 更新角色
	 * @param roleVo 角色
	 * @return 查询结果
	 */
	public int update(RoleVO role);
	/**
	 * 供应商角色管理页面的查询
	 * @param role
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageObject querySupRoleForPage(RoleVO role, int start, int limit);
	/**
	 * 供应商角色新增
	 * @param role
	 * @return
	 */
	public RoleVO insertSupRole(RoleVO role);
	/**
	 * 判断角色名称是否已经存在
	 * @param role
	 * @return
	 */
	public boolean isNameExit(RoleVO role);
	/**
	 * 修改供应商角色
	 * @param role
	 * @return
	 */
	public int updateSupRole(RoleVO role);
	public RoleVO queryBySupRoleId(RoleVO roleVo);
	
	/**
	 * 删除原有权限
	 * @param privilegeVo
	 * @return
	 */
	public int deleteOldPrivilege(String roleId);
	
	/**
	 * 新增权限
	 * @param privilegeVo
	 * @return
	 */
	public void addPrivilege(RolePrivilegeVO privilegeVo);
	
	/**
	 * 根据指定的开始条数和查询条数，取得工厂信息。
	 * 
	 * @param start
	 *            开始记录
	 * @param limit
	 *            查询条数
	 * @return 查询结果
	 */
	public PageObject queryFactoryForPage(FactoryVO factoryVO, int start, int limit);
	
	/**
	 * 根据指定的开始条数和查询条数，取得用户信息。
	 * 
	 * @param start
	 *            开始记录
	 * @param limit
	 *            查询条数
	 * @return 查询结果
	 */
	public PageObject queryUserInfoForPage(UserVO userVO, int start, int limit);
	
	/**
	 * 根据指定的开始条数和查询条数，取得用户信息。
	 * 
	 * @param start
	 *            开始记录
	 * @param limit
	 *            查询条数
	 * @return 查询结果
	 */
	public PageObject querySupplyForPage(UserVO userVO, int start, int limit);
	
	
	/**
	 * 新增工厂
	 * 
	 * @param factoryVO
	 *            工厂
	 * @return 查询结果
	 */
	public FactoryVO insertFactory(FactoryVO factoryVO);
	
	/**
	 * 根据指定的开始条数和查询条数，取得工厂信息。
	 * 
	 * @param start
	 *            开始记录
	 * @param limit
	 *            查询条数
	 * @return 查询结果
	 */
	public PageObject queryUserFactoryForPage(FactoryVO factoryVO, int start, int limit);
	
	/**
	 * 删除用户与工厂关系
	 * @param factoryCode 工厂代码
	 * @return
	 */
	public int deleteUserFactory(FactoryVO factoryVO);
	
	/**
	 * 新增用户与工厂关系
	 * @param factoryVO 
	 */
	public int insertUserFactory(FactoryVO factoryVO);
	
	/**
	 * 新增用户与供应商关系
	 * @param userVO 
	 */
	public int insertUserSupply(UserVO userVO);
	
	/**
	 * 删除用户与供应商关系
	 * @param userVO 
	 * @return
	 */
	public int deleteUserSupply(UserVO userVO);
	
	/**
	 * 更新工厂信息
	 * @param factoryVO 
	 * @return 查询结果
	 */
	public int updateFactory(FactoryVO factoryVO);
	
}


