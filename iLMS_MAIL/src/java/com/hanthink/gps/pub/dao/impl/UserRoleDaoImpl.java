package com.hanthink.gps.pub.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.dao.UserRoleDao;
import com.hanthink.gps.util.annotation.VoInsert;
import com.hanthink.gps.util.annotation.VoUpdate;
import com.hanthink.gps.pub.vo.RoleVO;
import com.hanthink.gps.pub.vo.UserVO;
/**
 * 用户角色dao
 * @author qzhang
 *
 */
public class UserRoleDaoImpl extends BaseDaoSupport implements UserRoleDao {
		
	/**
	 * 删除用户角色信息
	 * 
	 * @param user
	 *            用户角色信息
	 */
	@VoUpdate
	public int delete(UserVO user) {
		return this.executeDelete("pub.delete_userRole", user);
	}
	
	/**
	 * 新增用户角色信息
	 * 
	 * @param user
	 *            用户信息
	 */
	@VoInsert
	public void batchInsert(List<UserVO> userRoles){
		
		this.executeBatchInsert("pub.insert_insertUserRole", userRoles);
		
	}
	/**
	 * 新增用户信息
	 * 
	 * @param user
	 *            用户信息
	 */
	@SuppressWarnings("unchecked")
	public List<RoleVO> quueryRolesByUserId(String userId){
		// 更新用户信息
		return (List<RoleVO>)this.executeQueryForList("pub.select_userRole_by_userId", userId);
	}

	/**
	 * 新增用户工厂
	 */
	public void batchInsertUserFactory(List<UserVO> userFactory) {
		this.executeBatchInsert("pub.insert_insertUserFactory", userFactory);
	}
	
}
