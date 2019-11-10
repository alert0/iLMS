package com.hotent.mobile.weixin.api;
import java.util.List;

import com.hotent.org.persistence.model.User;

public interface IWXUserService {
	/**
	 * 新增用户
	 * @param org
	 * @throws Exception 
	 */
	public void create(User sysUser);
	/**
	 * 更新用户
	 * @param org
	 * @throws Exception 
	 */
	public void update(User sysUser);
	
	/**
	 * 删除用户
	 * @param orgId
	 */
	public void delete(String userId);
	/**
	 * 批量删除用户
	 * @param userIds
	 * @throws Exception
	 */
	void deleteAll(String userIds) ;
	/**
	 * 批量同步
	 * 已经存在、尚未绑定微信号的忽略
	 * @param sysUserList
	 * @throws Exception
	 */
	void addAll(List<User> sysUserList);

	/**
	 * 通讯录同步
	 * @param lAryId
	 */
	void syncUserToWx(String[] lAryId);
}

