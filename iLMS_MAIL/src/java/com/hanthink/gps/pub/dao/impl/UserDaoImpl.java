package com.hanthink.gps.pub.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.dao.UserDao;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.PasswordEncrypter;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.annotation.VoInsert;
import com.hanthink.gps.util.annotation.VoUpdate;
import com.hanthink.gps.util.sequence.SeqManager;
import com.hanthink.gps.util.sequence.SeqType;
import com.hanthink.gps.pub.vo.FactoryVO;
import com.hanthink.gps.pub.vo.LogCompanyVO;
import com.hanthink.gps.pub.vo.MMenuVO;
import com.hanthink.gps.pub.vo.PageAuthVO;
import com.hanthink.gps.pub.vo.SupplierVO;
import com.hanthink.gps.pub.vo.UserVO;

/**
 * 用户相关操作 包括取用户信息,更新密码错误次数,更新密码,更新用户登录信息,获取菜单信息,获取页面权限 和对用户的查询插入更新操作
 */
public class UserDaoImpl extends BaseDaoSupport implements UserDao {

	/****************************** 取用户信息 *******************************/

	/**
	 * 根据用户名，取得系统用户信息
	 * 
	 * @param userNm
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO queryByNm(String userName) {
		return (UserVO) this.executeQueryForObject("pub.select_user_by_userName", userName);
	}
	
	/**
	 * 根据用户名，取得用户信息
	 * 
	 * @param userNm
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO queryComfirmByNm(String userName) {
		return (UserVO) this.executeQueryForObject(
				"pub.select_confirmuser_by_userName", userName);
	}

	/**
	 * 根据用户名，取得供应商用户信息
	 * 
	 * @param userNm
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO querySupplierByNm(String userName) {
		return (UserVO) this.executeQueryForObject(
				"pub.select_supplier_by_userName", userName);
	}

	/**
	 * 根据用户名，取得物流公司用户信息 2010-05-19 zhangye追加
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO queryLogisticsByNm(String userName) {
		return (UserVO) this.executeQueryForObject(
				"pub.select_logistics_by_userName", userName);
	}
	
	public UserVO queryLogisticsByLogisticsId(String logisticsId) {
		return (UserVO) this.executeQueryForObject(
				"pub.select_logistics_by_logisticsId", logisticsId);
	}
	
	/**
	 * 根据用户名，取得供应商子用户信息
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO querySubSupplierByNm(String userName) {
		return (UserVO) this.executeQueryForObject(
				"pub.select_subsupplier_by_userName", userName);
	}

	/*************************** 更新密码错误次数 ****************************/

	/**
	 * 更新GAMC密码错误次数
	 * 
	 * @param userId
	 *            用户ID
	 * @param userType
	 *            用户身份类型
	 */
	public int updatePwdWrongNum(String userId) {
		return this.executeUpdate("pub.update_user_pwdWrongNum", userId);
	}

	/**
	 * 更新供应商错误次数
	 * 
	 * @param userId
	 *            用户ID
	 * @param userType
	 *            用户身份类型
	 */
	public int updateSupplierPwdWrongNum(String userId) {
		return this.executeUpdate("pub.update_supplier_pwdWrongNum", userId);
	}

	/**
	 * 更新物流公司密码错误次数 
	 * 
	 * @param userId
	 *            用户ID
	 * @param userType
	 *            用户身份类型
	 */
	public int updateLogisticsPwdWrongNum(String userId) {
		return this.executeUpdate("pub.update_supplier_pwdWrongNum", userId);
	}

	/***************************** 更新密码 **********************************/

	/**
	 * 更新GAMC密码
	 * 
	 * @param userInfo
	 * @return
	 */
	@VoUpdate
	public int changeUserPwd(UserVO userInfo) {
		return this.executeUpdate("pub.update_user_pwd", userInfo);
	}

	/**
	 * 更新供应商密码
	 * 
	 * @param userInfo
	 * @return
	 */
	@VoUpdate
	public int changeSupplierPwd(UserVO userInfo) {
		return this.executeUpdate("pub.update_supplier_pwd", userInfo);
	}

	/**
	 * 更新物流公司密码 zhangye 20100519追加
	 * 
	 * @param userInfo
	 * @return
	 */
	@VoUpdate
	public int changeLogisticsPwd(UserVO userInfo) {
		return this.executeUpdate("pub.update_logistics_pwd", userInfo);
	}
	/**
	 * 更新供应商用户密码
	 * zhangye 20100907追加
	 * @param userInfo
	 * @return
	 */
	@VoUpdate
	public int changeSubSupplierPwd(UserVO userInfo){
		return this.executeUpdate("pub.update_supplierUser_pwd", userInfo);
	}
	/***************************** 更新用户登录信息 ***************************/
	
	/**
	 * 更新GAMC登录信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public int updateUserLoginInfo(UserVO user) {
		return this.executeUpdate("pub.update_user_loginInfo", user);
	}
	/**
	 * 更新GAMC退出信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public int updateExit(UserVO user) {
		return this.executeUpdate("pub.update_user_exit", user);
	}

	/**
	 * 更新供应商登录信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public int updateSupplierLoginInfo(UserVO user) {
		return this.executeUpdate("pub.update_supplier_loginInfo", user);
	}

	/**
	 * 更新物流公司登录信息 
	 * 
	 * @param userInfo
	 * @return
	 */
	public int updateLogisticsLoginInfo(UserVO user) {
		return this.executeUpdate("pub.update_logistics_loginInfo", user);
	}
	
	/***************************** 获取菜单信息*******************************/
	
	/**
	 * 获取GAMC菜单信息
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MMenuVO> getMenuList(MMenuVO param) {
		return (List<MMenuVO>) executeQueryForList("pub.select_menuList",
				param);
	}

	/**
	 * 获取供应商菜单信息
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MMenuVO> getSupplierMenuList(MMenuVO param) {
		return (List<MMenuVO>) executeQueryForList(
				"pub.select_supplierMenuList", param);
	}
	
	/**
	 * 获取供应商子用户菜单信息
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MMenuVO> getSubSupplierMenuList(MMenuVO param) {
		return (List<MMenuVO>) executeQueryForList(
				"pub.select_subSuppliermenuList", param);
	}

	/**
	 * 获取物流公司菜单信息
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MMenuVO> getLogisticsMenuList(MMenuVO param) {
		return (List<MMenuVO>) executeQueryForList(
				"pub.select_logisticsMenuList", param);
	}
	
	/***************************** 获取页面权限*******************************/
	
	/**
	 * 获取GAMC页面权限
	 * 
	 * @param userId
	 *            用户ID
	 * @return 页面权限信息
	 */
	@SuppressWarnings("unchecked")
	public List<PageAuthVO> queryPagesAuth(String userId) {
		return (List<PageAuthVO>) this.executeQueryForList(
				"pub.select_user_pages_auth", userId);
	}
	
	/**
	 * 获取供应商页面权限
	 * 
	 * @param userId
	 *            用户ID
	 * @return 页面权限信息
	 */
	@SuppressWarnings("unchecked")
	public List<PageAuthVO> querySupplierPagesAuth(String userId) {
		return (List<PageAuthVO>) this.executeQueryForList(
				"pub.select_supplier_pages_auth", userId);
	}
	
	/**
	 * 获取物流公司页面权限 2010-05-20 zhangye追加
	 * 
	 * @param userId
	 *            用户ID
	 * @return 页面权限信息
	 */
	@SuppressWarnings("unchecked")
	public List<PageAuthVO> queryLogisticsPagesAuth(String userId) {
		return (List<PageAuthVO>) this.executeQueryForList(
				"pub.select_logistics_pages_auth", userId);
	}
	
	/***************************** 查询用户操作*******************************/
	
	/**
	 * 根据指定的开始条数和查询条数，取得用户信息
	 * 
	 * @param userInfo
	 *            查询条件
	 * @param start
	 *            开始记录
	 * @param limit
	 *            查询条数
	 * @return 查询结果
	 */
	public PageObject queryForPage(UserVO userInfo, int start, int limit) {
		return this.executeQueryForPage("pub.select_queryGAMCUserInfo", userInfo, start, limit);
	}
	
	/**
	 * 根据用户Id，取得用户信息
	 * 
	 * @param userNm
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO queryByUserId(String userId) {
		return (UserVO) this.executeQueryForObject(
				"pub.select_user_by_userId", userId);
	}
	
	/**
	 * 根据用户登录名，取得用户信息
	 * @param userName
	 * @return 用户信息
	 */
	@SuppressWarnings("unchecked")
	public UserVO queryByUserName(String userName, String userType){
		//TODO
		if(Constants.USER_TYPE_USER.equals(userType)){
			UserVO returnUser = (UserVO)this.executeQueryForObject("pub.select_queryGAMCUserByPk", userName);
			
			//角色信息 TODO
			
			//工厂信息
			returnUser.setFactories((List<FactoryVO>) this.executeQueryForList("select_queryFactoryInfoByUserName", userName));
			return returnUser;
		}
		else {
			return null;
		}
	}

	/***************************** 插入用户操作*******************************/

	/**
	 * 新增用户信息
	 * 
	 * @param user
	 *            用户信息
	 */
	@VoInsert
	public Object insert(UserVO user) {
		// 删除状态:未删除
		user.setDelFlg(Constants.DELETE_STATUS_NO);
		// 密码加密处理
		user.setUserPwd(PasswordEncrypter.encrypt(user.getUserPwd()));
		
		if(Constants.USER_TYPE_USER.equals(user.getUserType())){
			//新增GAMC用户
			this.executeInsert("pub.insert_gamc_user", user);
		}else if(Constants.USER_TYPE_SUPPLIER.equals(user.getUserType())
				|| Constants.USER_TYPE_LOGISTICS.equals(user.getUserType())){
			//新增供应商用户
			this.executeInsert("pub.insert_supplier_user", user);
		}
		
		// 新增系统用户
		user.setUserStatus(Constants.SYS_USER_STATUS_YES);
		return this.executeInsert("pub.insert_sys_user", user);
	}

	/***************************** 更新用户操作*******************************/
	/**
	 * 更新用户信息
	 * 
	 * @param user
	 *            用户信息
	 */
	@VoUpdate
	public int update(UserVO user) {
		
		//更新系统用户信息
		this.executeUpdate("pub.update_sys_user", user);
		
		if(!StringUtil.isNullOrEmpty(user.getUserType()) 
				&& Constants.USER_TYPE_USER.equals(user.getUserType())){
			
			//更新广乘用户
			return this.executeUpdate("pub.update_update_gamc_user", user);
			
		}else if(!StringUtil.isNullOrEmpty(user.getUserType()) 
				&& Constants.USER_TYPE_SUPPLIER.equals(user.getUserType())){
			
			//更新供应商用户 
			return this.executeUpdate("pub.update_update_supplier_user", user);
			
		}
		else {
			return 0;
		}
	}
	
	@VoUpdate
	public int remove(UserVO user) {
		
		//GAMC用户删除
		if(null != user.getUserType() && Constants.USER_TYPE_USER.equals(user.getUserType() )){
			
			this.executeDelete("pub.delete_gamc_user", user);
			
			//删除用户工厂
			this.executeDelete("pub.delete_user_factory", user);
			
		}else if(null != user.getUserType() && Constants.USER_TYPE_SUPPLIER.equals(user.getUserType() )){
			//供应商用户删除
			this.executeDelete("pub.delete_supplier_user", user);
		}
		
		//系统用户删除
		return this.executeUpdate("pub.delete_sys_user", user);
	}

	/**
	 * 检索有供应商关系匹配的用户List(发邮件使用)
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserVO> selectUserMailList(UserVO param) {
		return (List<UserVO>)this.executeQueryForList("pub.selectGAMCMailList", param);
	}
	
	
	/**
	 * 根据用户名称查询用户信息
	 * @param userName
	 * @return UserVO
	 * @author zuosl  2016-3-14 下午02:14:58
	 */
	public UserVO queryUserInfoByUserName(String userName) {
		return (UserVO)this.executeQueryForObject("pub.select_sys_user_by_userName", userName);
	}
	
	/**
	 * 更新系统用户登录信息
	 * @param loginInfo 
	 * @return void
	 * @author  2016-3-14 
	 */
	public void updateSysUserLoginInfo(UserVO loginInfo) {
		this.executeUpdate("pub.update_updateSysUserLoginInfo", loginInfo);
	}
	
	/**
	 * 根据用户名称查询菜单数据
	 * @param userName
	 * @return 
	 * @return List<MMenuVO>
	 */
	@SuppressWarnings("unchecked")
	public List<MMenuVO> queryMenuListByUserName(String userName) {
		
		//用户名为超级用户时查询所有菜单
		if(Constants.SUPER_USER.equals(userName)){
			return (List<MMenuVO>)this.executeQueryForList("pub.select_queryAllMenuList", userName);
		}else{
			return (List<MMenuVO>)this.executeQueryForList("pub.select_queryMenuListByUserName", userName);
		}
		
	}
	
	/**
	 * 根据用户名查询界面权限
	 * @param userName
	 * @return List<PageAuthVO>
	 */
	@SuppressWarnings("unchecked")
	public List<PageAuthVO> queryPagesAuthUserName(String userName) {
		//用户名为超级用户时查询所有界面权限
		if(Constants.SUPER_USER.equals(userName)){
			return (List<PageAuthVO>)this.executeQueryForList("pub.select_queryAllPagesAuth", userName);
		}else{
			return (List<PageAuthVO>)this.executeQueryForList("pub.select_queryPagesAuthUserName", userName);
		}
	}

	@SuppressWarnings("unchecked")
	public List<FactoryVO> queryFactoryInfoByUserName(String userName) {
		
		//用户名为超级用户时查询所有工厂
		if(Constants.SUPER_USER.equals(userName)){
			return (List<FactoryVO>)this.executeQueryForList("pub.select_queryFactoryInfoByAll");
		}else{
			return (List<FactoryVO>)this.executeQueryForList("pub.select_queryFactoryInfoByUserName", userName);
		}
	}

	/**
	 * 删除用户工厂
	 * @param user
	 */
	public int deleteUserFactory(UserVO user) {
		return this.executeDelete("pub.delete_deleteUserFactory", user);
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
		if(Constants.SUP_ROLE_TYPE_SUPPLIER.equals(queryInfo.getRoleType())){
			return this.executeQueryForPage("pub.select_querySupSupplierUserList", queryInfo, start, limit);
		}else{
			return this.executeQueryForPage("pub.select_queryLogSupplierUserList", queryInfo, start, limit);
		}
	}

	/**
	 * 根据用户名查询供应商的信息
	 * @param userName
	 * @return
	 * @author zuosl 2016-3-25
	 */
	public SupplierVO querySupplierInfoByUserName(String userName) {
		return (SupplierVO) this.executeQueryForObject("pub.select_querySupplierInfoByUserName", userName);
	}

	/**
	 * 根据用户名查询物流公司信息
	 * @param userName
	 * @return
	 * @author zuosl 2016-3-25
	 */
	public LogCompanyVO queryLogCompanyInfoByUserName(String userName) {
		return (LogCompanyVO) this.executeQueryForObject("pub.select_queryLogCompanyInfoByUserName", userName);
	}

	/**
	 * 修改用户密码
	 * @param userVO
	 */
	public int updateUserPwd(UserVO userVO) {
		return this.executeUpdate("pub.update_sys_userpwd", userVO);
	}

	/**
	 * 根据用户的一级模块查询顶级模块信息
	 * @param firstMouldIdList
	 * @return
	 * @author zuosl 2016-5-6
	 */
	@SuppressWarnings("unchecked")
	public List<MMenuVO> queryUserZeroMenuList(List<String> firstMouldIdList) {
		return (List<MMenuVO>) this.executeQueryForList("pub.select_queryUserZeroMenuListByFirstMould", firstMouldIdList);
	}
}
