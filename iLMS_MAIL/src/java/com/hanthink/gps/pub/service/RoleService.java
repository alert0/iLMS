package com.hanthink.gps.pub.service;

import java.util.List;

import com.hanthink.gps.pub.vo.FactoryVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.RoleVO;
import com.hanthink.gps.pub.vo.TreeVO;
import com.hanthink.gps.pub.vo.UserVO;

public interface RoleService extends BaseService{
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
	 * 检索角色的权限配置信息
	 * 
	 * @param roleVo
	 *            角色
	 * @return 查询结果
	 */
	public String queryRolePrivilege(RoleVO role);
	
	/**
	 * 新增角色
	 * 
	 * @param roleVo
	 *            角色
	 * @return 查询结果
	 */
	public RoleVO insert(RoleVO role);
	
	/**
	 * 新增工厂
	 * 
	 * @param factoryVO
	 *            工厂
	 * @return 查询结果
	 */
	public FactoryVO insert(FactoryVO factoryVO);
	
	/**
	 * 删除角色
	 * 
	 * @param roleVo
	 *            角色
	 * @return 查询结果 0:未更新 1：更新成功 2：更新角色使用中
	 */
	public int delete(RoleVO role);
	
	/**
	 * 根据角色ID，取得角色信息。
	 * 
	 * @param queryRole
	 *            角色
	 * @return 查询结果
	 */
	public RoleVO queryByRoleId(RoleVO queryRole);
	
	/**
	 * 更新角色
	 * 
	 * @param roleVo
	 *            角色
	 * @return 查询结果
	 */
	public int update(RoleVO role);
	
	/**
	 * 更新角色的权限配置信息
	 * 
	 * @param roleVo
	 *            角色
	 * @return 查询结果
	 */
	public void updatePrivilege(List<TreeVO> rolePrivs);
	
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
	 * 根据指定的开始条数和查询条数，取得供应商信息。
	 * 
	 * @param start
	 *            开始记录
	 * @param limit
	 *            查询条数
	 * @return 查询结果
	 */
	public PageObject querySupplyForPage(UserVO userVO, int start, int limit);
	
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
	 * 新增、删除用户与工厂关系
	 * @param list
	 */
	public int modifyUserFactory(List<FactoryVO> list);
	
	/**
	 * 新增、删除用户与供应商关系
	 * @param list
	 */
	public int modifyUserSupplier(List<UserVO> list);
	
	/**
	 * 更新工厂信息
	 * @param factoryVO 
	 * @return 查询结果
	 */
	public int updateFactory(FactoryVO factoryVO);

}
