package com.hanthink.gps.pub.service;

import java.util.List;

import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.UserVO;

public interface UserService extends BaseService {
	/**
	 * 新增用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @param roleIds 角色ID
	 * @param factories 
	 */
	public void insert(UserVO user,List<String> roleIds, List<String> factories);

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @param factories 
	 */
	public void update(UserVO user,List<String> roleIds, List<String> factories);

	/**
	 * 禁用用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @param roleIds 角色ID
	 */
	public void disable(UserVO user);
	
	/**
	 *用户可用处理
	 * 
	 * @param user
	 *            用户信息
	 * @param roleIds 角色ID
	 */
	public void usable(UserVO user);

	/**
	 * 根据用户名，取得用户信息
	 * 
	 * @param userId
	 * @return 用户信息
	 */
	public UserVO queryByUserId(String userId);
	
	/**
	 * 根据用户登录名，取得用户信息
	 * @param userName
	 * @return 用户信息
	 */
	public UserVO queryByUserName(String userName, String userType);
	
	/**
	 * 根据指定的开始条数和查询条数，取得用户信息
	 * @param userInfo 查询条件
	 * @param start 开始记录
	 * @param limit 查询条数
	 * @return 查询结果
	 */
	public PageObject queryForPage(UserVO userInfo, int start, int limit);
	
	/**
	 * 用户解锁
	 * @param userIds 待解锁用户id列表
	 */
	public void unlock(List<String> userIds);
	/**
	 * 用户删除
	 * @param user
	 */
	public void remove(UserVO user);

	/**
	 * 查询供应商用户信息
	 * @param queryInfo
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-3-21
	 */
	public PageObject querySupplierUserForPage(UserVO queryInfo, int start,
			int limit);

	/**
	 * 新增供应商用户信息
	 * @param user;
	 * @param roleIds
	 * @author zuosl 2016-3-21
	 */
	public void insertSupplierUser(UserVO user, List<String> roleIds);

	/**
	 * 更新供应商用户信息
	 * @param user
	 * @param roleIds
	 * @author zuosl 2016-3-22
	 */
	public void updateSupplier(UserVO user, List<String> roleIds);
}
