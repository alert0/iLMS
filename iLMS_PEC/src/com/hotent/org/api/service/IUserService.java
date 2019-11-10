package com.hotent.org.api.service;

import java.util.List;

import com.hotent.org.api.model.IUser;
/**
 * <pre> 
 * 描述：用户服务接口类
 * 构建组：x5-org-api
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2013-11-18-下午12:18:54
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface IUserService {
	
	/**
	 * 根据用户ID获取用户的对象。
	 * @param userId	用户ID
	 * @return	
	 */
	IUser getUserById(String userId);
	
	
	/**
	 * 根据用户帐号获取用户对象。
	 * @param account
	 * @return
	 */
	IUser getUserByAccount(String account);
	
	
	/**
	 * 根据组织id和组织类型获取用户列表。
	 * <pre>
	 * 	根据组织类型获取该组织下的人员：
	 *  比如：给定角色ID和类型为角色，获取这个角色下的人员列表。
	 * </pre>
	 * @param groupId		组织列表
	 * @param groupType		组织类型
	 * @return
	 */
	List<IUser> getUserListByGroup(String groupType, String groupId);
 
	/**
	 *获取登录的用户
	 */
	IUser getLoginUser(String account);
 

	/**
	 * 根据id字符串获取用户列表。
	 * @param userIds
	 * @return
	 */
	List<IUser> getUserByIds(String[] idsArr);

	
}
