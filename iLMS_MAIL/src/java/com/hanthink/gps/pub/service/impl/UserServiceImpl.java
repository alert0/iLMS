package com.hanthink.gps.pub.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.dao.UserDao;
import com.hanthink.gps.pub.dao.UserRoleDao;
import com.hanthink.gps.pub.service.UserService;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.PasswordEncrypter;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.exception.BizException;
import com.hanthink.gps.pub.vo.UserVO;

public class UserServiceImpl extends BaseServiceImpl implements UserService {

	private UserDao userDao;
	private UserRoleDao userRoleDao;
	
	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 新增用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @param roleIds 角色ID
	 */
	public void insert(UserVO user, List<String> roleIds, List<String> factories){
		
		// 用户登录名chechk
		if(userDao.queryByNm(user.getUserName()) != null){
			throw new BizException(Constants.MSG_ID_E_USERNAME_REPEAT); 
		}
		// 登录状态
		user.setLoginStatus(Constants.LOGIN_STATUS_OFFLINE);
		
		//新增GAMC用户
		userDao.insert(user);
		
		//新增用户角色
		if(null != roleIds && 0 < roleIds.size()){
			List<UserVO> userRoles = new ArrayList<UserVO>();
			for(String roleId : roleIds){
				UserVO userRole = new UserVO();
				userRole.setUserName(user.getUserName());
				userRole.setRoleId(roleId);
				userRole.setEntryId(user.getEntryId());
				userRoles.add(userRole);
			}
			userRoleDao.batchInsert(userRoles);
		}
		
		//新增用户工厂
		if(null != factories && 0 < factories.size()){
			List<UserVO> userFactory = new ArrayList<UserVO>();
			for(String code : factories){
				UserVO uf = new UserVO();
				uf.setUserName(user.getUserName());
				uf.setCurLoginFactory(code);
				uf.setEntryId(user.getEntryId());
				userFactory.add(uf);
			}
			userRoleDao.batchInsertUserFactory(userFactory);
		}
		
	}

	/**
	 * 禁用用户
	 * @param user
	 *            用户信息
	 */
	public void disable(UserVO user) {
		// 禁用标记
		user.setDelFlg(Constants.DELETE_STATUS_YES);
		userDao.update(user);
	}
	/**
	 *用户可用处理
	 * 
	 * @param user
	 *            用户信息
	 * @param roleIds 角色ID
	 */
	public void usable(UserVO user){
		// 禁用标记
		user.setDelFlg(Constants.DELETE_STATUS_NO);
		userDao.update(user);
	}

	/**
	 * 根据用户名，取得用户信息
	 * 
	 * @param userId
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO queryByUserId(String userId) {
		UserVO user = userDao.queryByUserId(userId);
		// 角色信息
		user.setRoles(userRoleDao.quueryRolesByUserId(userId));
		return user;
	}
	
	/**
	 * 根据用户登录名，取得用户信息
	 * @param userName
	 * @return 用户信息
	 */
	public UserVO queryByUserName(String userName, String userType){
		UserVO user = userDao.queryByUserName(userName, userType);
		
		return user;
	}

	/**
	 * 根据指定的开始条数和查询条数，取得用户信息
	 * @param userInfo 查询条件
	 * @param start 开始记录
	 * @param limit 查询条数
	 * @return 查询结果
	 */
	public PageObject queryForPage(UserVO userInfo, int start, int limit) {
		return userDao.queryForPage(userInfo, start, limit);
	}

	/**
	 * 更新GAMC用户信息
	 * 
	 * @param user
	 *            用户信息
	 */
	public void update(UserVO user,List<String> roleIds, List<String> factories) {
		// 更新user
		user.setUserPwd(StringUtil.isNullOrEmpty(user.getUserPwd())?user.getUserPwd():PasswordEncrypter.encrypt(user.getUserPwd()));
		userDao.update(user);
		
		
		//角色更新
		if(null != roleIds && 0 < roleIds.size()){
			// 删除原用户角色
			userRoleDao.delete(user);
			
			// 插入用户角色
			List<UserVO> userRoles = new ArrayList<UserVO>();
			for(String roleId : roleIds){
				UserVO userRole = new UserVO();
				userRole.setUserName(user.getUserName());
				userRole.setRoleId(roleId);
				userRoles.add(userRole);
			}
			userRoleDao.batchInsert(userRoles);
		}
		
		//工厂更新
		if(null != factories && 0 < factories.size()){
			//删除用户工厂
			userDao.deleteUserFactory(user);
			
			//插入用户工厂
			List<UserVO> userFactory = new ArrayList<UserVO>();
			for(String code : factories){
				UserVO uf = new UserVO();
				uf.setUserName(user.getUserName());
				uf.setCurLoginFactory(code);
				uf.setEntryId(user.getEntryId());
				userFactory.add(uf);
			}
			userRoleDao.batchInsertUserFactory(userFactory);
		}
	}
	
	/**
	 * 用户解锁
	 * @param userIds 待解锁用户id列表
	 */
	public void unlock(List<String> userIds){
		UserVO user;
		for(String userId: userIds){
			user = new UserVO();
			// 用户ID
			user.setUserId(userId);
			// 密码错误次数
			user.setPwdWrongNum(String.valueOf(0));
			// 登录状态
			user.setLoginStatus(Constants.LOGIN_STATUS_OFFLINE);
			userDao.update(user);
		}
	}
	/**
	 * 用户信息删除
	 */
	public void remove (UserVO user){
		//删除用户角色信息
		userRoleDao.delete(user);
		
		//删除用户信息
		userDao.remove(user);
		
	}

	/**
	 * 查询供应商用户信息
	 * @param queryInfo
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-3-21
	 */
	public PageObject querySupplierUserForPage(UserVO queryInfo, int start,
			int limit) {
		return userDao.querySupplierUserForPage(queryInfo, start, limit);
	}

	/**
	 * 新增供应商用户信息
	 * @param user;
	 * @param roleIds
	 * @author zuosl 2016-3-21
	 */
	public void insertSupplierUser(UserVO user, List<String> roleIds) {
		// 用户登录名chechk
		if(userDao.queryByNm(user.getUserName()) != null){
			throw new BizException(Constants.MSG_ID_E_USERNAME_REPEAT); 
		}
		
		//新增供应商或物流公司用户
		userDao.insert(user);
		
		//新增用户角色
		if(null != roleIds && 0 < roleIds.size()){
			List<UserVO> userRoles = new ArrayList<UserVO>();
			for(String roleId : roleIds){
				UserVO userRole = new UserVO();
				userRole.setUserName(user.getUserName());
				userRole.setRoleId(roleId);
				userRole.setEntryId(user.getEntryId());
				userRoles.add(userRole);
			}
			userRoleDao.batchInsert(userRoles);
		}
	}

	/**
	 * 更新供应商用户信息
	 * @param user
	 * @param roleIds
	 * @author zuosl 2016-3-22
	 */
	public void updateSupplier(UserVO user, List<String> roleIds) {
		// 更新user
		user.setUserPwd(StringUtil.isNullOrEmpty(user.getUserPwd())?user.getUserPwd():PasswordEncrypter.encrypt(user.getUserPwd()));
		user.setUserType(Constants.USER_TYPE_SUPPLIER);
		userDao.update(user);
		
		//角色更新
		if(null != roleIds && 0 < roleIds.size()){
			// 删除原用户角色
			userRoleDao.delete(user);
			
			// 插入用户角色
			List<UserVO> userRoles = new ArrayList<UserVO>();
			for(String roleId : roleIds){
				UserVO userRole = new UserVO();
				userRole.setUserName(user.getUserName());
				userRole.setRoleId(roleId);
				userRoles.add(userRole);
			}
			userRoleDao.batchInsert(userRoles);
		}
		
	}
}
