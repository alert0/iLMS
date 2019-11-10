package com.hanthink.gps.pub.dao;

import java.util.List;

import com.hanthink.gps.pub.vo.FactoryVO;
import com.hanthink.gps.pub.vo.LogCompanyVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.MMenuVO;
import com.hanthink.gps.pub.vo.PageAuthVO;
import com.hanthink.gps.pub.vo.SupplierVO;
import com.hanthink.gps.pub.vo.UserVO;
/**
 * 用户相关操作
 * 包括取用户信息,更新密码错误次数,更新密码,更新用户登录信息,获取菜单信息,获取页面权限
 * 和对用户的查询插入更新操作
 */
public interface UserDao {
	
	/****************************** 取用户信息 *******************************/
	
	/**
	 * 根据用户名，取得GAMC用户信息
	 * 
	 * @param userNm
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO queryByNm(String userName);
	/**
	 * 根据用户名，取得数量审核人员信息
	 * 
	 * @param userNm
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO queryComfirmByNm(String userName);

	/**
	 * 根据用户名，取得供应商用户信息
	 * 
	 * @param userNm
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO querySupplierByNm(String userName);
	
	/**
	 * 根据用户名，取得物流公司用户信息
	 * 2010-05-19 zhangye追加
	 * @param userName
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO queryLogisticsByNm(String userName);
	
	/**
	 * 根据logisticsId，取得物流公司用户信息
	 * 2013-2-17 pengq 追加
	 * @param userName
	 *            用户名
	 * @return 用户信息
	 */	
	public UserVO queryLogisticsByLogisticsId(String logisticsId);
	
	/**
	 * 根据用户名，取得供应商用户的用户信息
	 * 2010-09-07 zhangye追加
	 * @param userName
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO querySubSupplierByNm(String userName);
	
	/*************************** 更新密码错误次数 ****************************/
	
	/**
	 * 更新GAMC密码错误次数
	 * 
	 * @param userId
	 *            用户ID
	 * @param userType
	 *            用户身份类型
	 */
	public int updatePwdWrongNum(String userId);
	
	/**
	 * 更新供应商密码错误次数
	 * 
	 * @param userId
	 *            用户ID
	 * @param userType
	 *            用户身份类型
	 */
	public int updateSupplierPwdWrongNum(String userId);
	
	/**
	 * 更新物流公司密码错误次数
	 * zhangye 20100521追加
	 * @param userId
	 *            用户ID
	 * @param userType
	 *            用户身份类型
	 */
	public int updateLogisticsPwdWrongNum(String userId);
	
	/***************************** 更新密码**********************************/
	
	/**
	 * 更新GAMC密码
	 * 
	 * @param userInfo
	 * @return
	 */
	public int changeUserPwd(UserVO userInfo);
	
	/**
	 * 更新供应商密码
	 * 
	 * @param userInfo
	 * @return
	 */
	public int changeSupplierPwd(UserVO userInfo);
	
	/**
	 * 更新物流公司密码
	 * zhangye 20100519追加
	 * @param userInfo
	 * @return
	 */
	public int changeLogisticsPwd(UserVO userInfo);
	
	/**
	 * 更新供应商用户密码
	 * zhangye 20100907追加
	 * @param userInfo
	 * @return
	 */
	public int changeSubSupplierPwd(UserVO userVO);
	
	
	/***************************** 更新用户登录信息***************************/
	/**
	 * 更新用户登录信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public int updateUserLoginInfo(UserVO user);
	/**
	 * 更新用户退出信息
	 * GAMC
	 * @param userInfo
	 * @return
	 */
	public int updateExit(UserVO user);
	
	/**
	 * 更新供应商登录信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public int updateSupplierLoginInfo(UserVO user);
	
	/**
	 * 更新物流公司登录信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public int updateLogisticsLoginInfo(UserVO user);

	/***************************** 获取菜单信息*******************************/
	
	/**
	 * 获取GAMC菜单信息
	 * 
	 * @param param
	 * @return
	 */
	public List<MMenuVO> getMenuList(MMenuVO param);

	/**
	 * 获取供应商菜单信息
	 * 
	 * @param param
	 * @return
	 */
	public List<MMenuVO> getSupplierMenuList(MMenuVO param);
	
	/**
	 * 获取物流公司菜单信息
	 * 
	 * @param param
	 * @return
	 */
	public List<MMenuVO> getLogisticsMenuList(MMenuVO param);

	/**
	 * 获取供应商子用户菜单信息
	 * 
	 * @param param
	 * @return
	 */
	public List<MMenuVO> getSubSupplierMenuList(MMenuVO param);
	/***************************** 获取页面权限*******************************/
	
	/**
	 * 获取GAMC页面权限
	 * 
	 * @param userId 用户ID
	 * @return 页面权限信息
	 */
	public List<PageAuthVO> queryPagesAuth(String userId);
	
	/**
	 * 获取供应商页面权限
	 * 
	 * @param userId 用户ID
	 * @return 页面权限信息
	 */
	public List<PageAuthVO> querySupplierPagesAuth(String userId);
	
	/**
	 * 获取物流公司页面权限
	 * 2010-05-20 zhangye追加
	 * @param userId 用户ID
	 * @return 页面权限信息
	 */
	public List<PageAuthVO> queryLogisticsPagesAuth(String userId);
	
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
	public PageObject queryForPage(UserVO userInfo, int start, int limit);
	
	/**
	 * 根据用户Id，取得用户信息
	 * 
	 * @param userNm
	 *            用户名
	 * @return 用户信息
	 */
	public UserVO queryByUserId(String userId);
	
	/**
	 * 根据用户登录名，取得用户信息
	 * @param userName
	 * @param userType 
	 * @return 用户信息
	 */
	public UserVO queryByUserName(String userName, String userType);
	
	/***************************** 插入用户操作*******************************/
	
	/**
	 * 新增用户信息
	 * 
	 * @param user
	 *            用户信息
	 */
	public Object insert(UserVO user);
	
	/***************************** 更新用户操作*******************************/
	
	/**
	 * 更新用户信息
	 * 
	 * @param user
	 *            用户信息
	 */
	public int update(UserVO user);
	/**
	 * 用户信息删除
	 * @param user
	 * @return 
	 */
	public int remove(UserVO user);

	/**
	 * 检索有供应商关系匹配的用户List(发邮件使用)
	 * @param param
	 * @return
	 */
	public List<UserVO> selectUserMailList(UserVO param);
	
	/**
	 * 根据用户名称查询用户信息
	 * @param userName
	 * @return UserVO
	 * @author zuosl  2016-3-14 
	 */
	public UserVO queryUserInfoByUserName(String userName);
	
	/**
	 * 更新系统用户登录信息
	 * @param loginInfo 
	 * @return void
	 * @author  2016-3-14 
	 */
	public void updateSysUserLoginInfo(UserVO loginInfo);
	
	/**
	 * 根据用户名查询菜单数据
	 * @param userName
	 * @return 
	 * @return List<MMenuVO>
	 */
	public List<MMenuVO> queryMenuListByUserName(String userName);
	
	/**
	 * 根据用户名查询界面权限
	 * @param userName
	 * @return List<PageAuthVO>
	 */
	public List<PageAuthVO> queryPagesAuthUserName(String userName);
	
	public List<FactoryVO> queryFactoryInfoByUserName(String userName);
	
	/**
	 * 删除用户工厂
	 * @param user
	 */
	public int deleteUserFactory(UserVO user);
	
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
	 * 根据用户名查询供应商的信息
	 * @param userName
	 * @return
	 * @author zuosl 2016-3-25
	 */
	public SupplierVO querySupplierInfoByUserName(String userName);
	
	/**
	 * 根据用户名查询物流公司信息
	 * @param userName
	 * @return
	 * @author zuosl 2016-3-25
	 */
	public LogCompanyVO queryLogCompanyInfoByUserName(String userName);
	
	/**
	 * 修改用户密码
	 * @param userVO
	 * @return
	 */
	public int updateUserPwd(UserVO userVO);
	
	/**
	 * 根据用户的一级模块查询顶级模块信息
	 * @param firstMouldIdList
	 * @return
	 * @author zuosl 2016-5-6
	 */
	public List<MMenuVO> queryUserZeroMenuList(List<String> firstMouldIdList);
	

}
