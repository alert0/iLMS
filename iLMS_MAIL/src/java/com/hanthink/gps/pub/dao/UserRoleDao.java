package com.hanthink.gps.pub.dao;

import java.util.List;

import com.hanthink.gps.pub.vo.RoleVO;
import com.hanthink.gps.pub.vo.UserVO;

/**
 * 用户角色
 * @author qzhang
 */
public interface UserRoleDao {

	/**
	 * 删除用户角色信息
	 * 
	 * @param user
	 *            用户角色信息
	 */
	public int delete(UserVO user);

	/**
	 * 新增用户信息
	 * 
	 * @param user
	 *            用户信息
	 */
	public void batchInsert(List<UserVO> userRoles);	
	/**
	 * 根据用户id,检索用户角色信息
	 * 
	 * @param user
	 *            用户信息
	 */
	public List<RoleVO> quueryRolesByUserId(String userId);

	
	public void batchInsertUserFactory(List<UserVO> userFactory);
}
