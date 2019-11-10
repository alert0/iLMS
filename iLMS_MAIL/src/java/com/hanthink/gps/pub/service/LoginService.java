package com.hanthink.gps.pub.service;

import java.util.List;

import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.pub.vo.FactoryVO;
import com.hanthink.gps.pub.vo.LogCompanyVO;
import com.hanthink.gps.pub.vo.MMenuVO;
import com.hanthink.gps.pub.vo.PageAuthVO;
import com.hanthink.gps.pub.vo.SupplierVO;
import com.hanthink.gps.pub.vo.UserVO;

public interface LoginService extends BaseService {

	/**
	 * 取得用户信息
	 * 
	 * @param userNm
	 *            用户名
	 * @param userType
	 *            用户身份类型
	 * @return 用户信息
	 */
	public UserVO getUserInfoByNm(String userName, String userType);

	/**
	 * 密码错误次数
	 * 
	 * @param userId
	 *            用户ID
	 * @param userType
	 *            用户身份类型
	 */
	public void updatePwdWrongNum(String userId, String userType);

	/**
	 * 更新用户登录信息
	 * 
	 * @param userVO
	 *            用户登录信息
	 * @param userType
	 *            用户身份类型
	 */
	public void updateLoginInfo(UserVO userVO, String userType);
	/**
	 * 更新用户退出信息
	 * 
	 */
	public void updateExit(UserVO userVO);

	/**
	 * 获取菜单
	 * 
	 * @param param
	 *            参数
	 * @param type
	 *            身份类型
	 * @param subRoleType 子用户帐号登录类型
	 * @return
	 */
	public List<MMenuVO> getMenuList(String param, String type,String subRoleType);

	/**
	 * 更改用户密码
	 * 
	 * @param userVO
	 * @return
	 */
	public int changePwd(UserVO userVO, String originalPwd);

	/**
	 * 获取页面权限信息
	 * 
	 * @param userType
	 *            用户类型
	 * 
	 * @param userId
	 *            用户ID
	 * @return 页面权限信息
	 */
	public String queryPagesAuth(String userType, String userId);

	/**
	 * 查询PDA界面权限菜单
	 * @param userId
	 * @return
	 * @author zuosl  2015-11-12
	 */
	public List<MMenuVO> queryPdaMenuList(String userId);

	/**
	 * 根据用户名称查询用户信息
	 * @param userName
	 * @return 
	 * @return UserVO
	 * @author  2016-3-14 
	 */
	public UserVO getUserInfoByUserName(String userName);

	/**
	 * 更新系统用户登录信息
	 * @param loginInfo 
	 * @return void
	 * @author  2016-3-14 
	 */
	public void updateSysUserLoginInfo(UserVO loginInfo);

	/**
	 * 根据用户名查询菜单界面权限
	 * @param userName
	 * @return Object
	 * @author  2016-3-14 
	 */
	public List<MMenuVO> queryMenuListByUserName(String userName);

	/**
	 * 根据用户名查询界面的按钮、数据等权限信息
	 * @param userName
	 * @return String
	 */
	public String queryPagesAuthByUserName(String userName);

	public List<FactoryVO> queryFactoryInfoByUserName(String userName);

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
	 * 根据用户的一级模块查询顶级模块信息
	 * @param firstMouldIdList
	 * @return
	 * @author zuosl 2016-5-6
	 */
	public List<MMenuVO> queryUserZeroMenuList(List<String> firstMouldIdList);
}
