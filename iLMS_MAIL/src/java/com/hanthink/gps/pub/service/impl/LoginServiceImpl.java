package com.hanthink.gps.pub.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hanthink.gps.pub.dao.UserDao;
import com.hanthink.gps.pub.service.LoginService;
import com.hanthink.gps.pub.vo.FactoryVO;
import com.hanthink.gps.pub.vo.LogCompanyVO;
import com.hanthink.gps.pub.vo.MMenuVO;
import com.hanthink.gps.pub.vo.PageAuthVO;
import com.hanthink.gps.pub.vo.SupplierVO;
import com.hanthink.gps.pub.vo.UserVO;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.PasswordEncrypter;

public class LoginServiceImpl extends BaseServiceImpl implements LoginService {

	private UserDao userDao;

	/**
	 * 取得用户信息 2010-05-19 zhangye追加 物流公司场合判断
	 * 
	 * @param userNm
	 *            用户名
	 * @param userType
	 *            用户身份类型
	 * @return 用户信息
	 */
	public UserVO getUserInfoByNm(String userName, String userType) {
		UserVO userInfo = null;
		UserVO userComfirmInfo = null;
		// 如果是GAMC
		if (Constants.USER_TYPE_USER.equals(userType)) {
			userInfo = userDao.queryByNm(userName);
			if (userInfo != null) {
				userInfo.setUserType(Constants.USER_TYPE_USER);
				userInfo.setUserConfirmType(Constants.USER_UNCONFIRM_TYPE);
			}			
			userComfirmInfo = userDao.queryComfirmByNm(userName);
			if (userComfirmInfo != null){
				userInfo.setUserConfirmType(Constants.USER_CONFIRM_TYPE);
			}
		} else
		// 如果是供应商
		if (Constants.USER_TYPE_SUPPLIER.equals(userType)) {
			userInfo = userDao.querySupplierByNm(userName);
			if (userInfo != null) {
				userInfo.setUserType(Constants.USER_TYPE_SUPPLIER);
			}
			// 供应商没有查到,查询子用户
			if (userInfo == null) {
				userInfo = userDao.querySubSupplierByNm(userName);
				if (userInfo != null) {
					if (userInfo.getUserType().equals(Constants.RELATIVE_ID_SUP)) {//1 供应商
						//这个地方设置新的供应商子用户的类型(这个获取的是DB中的类型 中转,外协,供应商)					
						userInfo.setSubRoleType(userInfo.getUserType());					
						userInfo.setUserType(Constants.USER_TYPE_SUPPLIER);						
					}else{
						userInfo=null;
					}
				}
			}
		} else
		// 如果是物流公司			
		if (Constants.USER_TYPE_LOGISTICS.equals(userType)) {
			//先查子表
			userInfo = userDao.querySubSupplierByNm(userName);
			if (userInfo != null) {
				// 这个地方设置新的供应商子用户的类型(这个获取的是DB中的类型 中转,外协,供应商)
				if (userInfo.getUserType().equals(Constants.RELATIVE_ID_TRANSPORTOR) || //2 中转仓 
						userInfo.getUserType().equals(Constants.RELATIVE_ID_SUPPORTOR))//3 外协仓
				{
					userInfo.setSubRoleType(userInfo.getUserType());
					userInfo.setUserType(Constants.USER_TYPE_LOGISTICS);
					
					//公司代码 必须存在于父表
					userComfirmInfo = userDao.queryLogisticsByLogisticsId(userInfo.getUserId());
					if (userComfirmInfo != null) {
						userInfo.setUserType(Constants.USER_TYPE_LOGISTICS);
					}else{
						userInfo = null;
					}					
				}else{
					userInfo=null;
				}
			}
						
		}
		return userInfo;
	}

	/**
	 * 密码错误次数
	 * 
	 * @param userId
	 *            用户ID
	 * @param userType
	 *            用户身份类型
	 */
	public void updatePwdWrongNum(String userId, String userType) {
		// GAMC
		if (Constants.USER_TYPE_USER.equals(userType)) {
			userDao.updatePwdWrongNum(userId);
		}
		// 供应商
		if (Constants.USER_TYPE_SUPPLIER.equals(userType)) {
			userDao.updateSupplierPwdWrongNum(userId);
		}
		// 物流公司 20100521 zhangye追加
		if (Constants.USER_TYPE_LOGISTICS.equals(userType)) {
			userDao.updateLogisticsPwdWrongNum(userId);
		}

	}

	/**
	 * 更新用户登录信息
	 * 
	 * @param userVO
	 *            用户登录信息
	 * @param userType
	 *            用户身份类型
	 */
	public void updateLoginInfo(UserVO userVO, String userType) {
		// GAMC
		if (Constants.USER_TYPE_USER.equals(userType)) {
			userDao.updateUserLoginInfo(userVO);
		}
		// 供应商
		if (Constants.USER_TYPE_SUPPLIER.equals(userType)) {
			userDao.updateSupplierLoginInfo(userVO);
		}
		// 物流公司
		if (Constants.USER_TYPE_LOGISTICS.equals(userType)) {
			userDao.updateLogisticsLoginInfo(userVO);
		}
	}

	/**
	 * 更新用户退出信息
	 */
	public void updateExit(UserVO userVO) {
		// GAMC
		userDao.updateExit(userVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ustcsoft.gsl.services.comm.CommService#getMenuList(com.ustcsoft.gsl
	 * .vo.MMenuVO)
	 */
	public List<MMenuVO> getMenuList(String userId, String type,
			String subRoleType) {
		MMenuVO param = new MMenuVO();
		List<MMenuVO> allList = userDao.getMenuList(param);
		List<MMenuVO> menuList = new ArrayList<MMenuVO>();
		
		if(null == allList || 0 >= allList.size()){
			return menuList;
		}
		
		List<MMenuVO> topList = new ArrayList<MMenuVO>();
		List<MMenuVO> secList = new ArrayList<MMenuVO>();
		List<MMenuVO> thiList = new ArrayList<MMenuVO>();
		
		for(MMenuVO menuVO : allList){
			if("1".equals(menuVO.getMenuLevel())){
				topList.add(menuVO);
			}else if("2".equals(menuVO.getMenuLevel())){
				secList.add(menuVO);
			}else if("3".equals(menuVO.getMenuLevel())){
				thiList.add(menuVO);
			}
		}
		
		if(null == topList || 0 >= topList.size()
				|| null == secList || 0 >= secList.size()
				|| null == thiList || 0 >= thiList.size() ){
			return menuList;
		}
		
		for(MMenuVO menuVO1 : topList){
			List<MMenuVO> menuList2 = new ArrayList<MMenuVO>();
			for(MMenuVO menuVO2 : secList){
				if(menuVO1.getMenuId().equals(menuVO2.getParentId())){
					List<MMenuVO> menuList3 = new ArrayList<MMenuVO>();
					for(MMenuVO menuVO3 : thiList){
						if(menuVO2.getMenuId().equals(menuVO3.getParentId())){
							menuList3.add(menuVO3);
						}
					}
					menuVO2.setMenuList(menuList3);
					menuList2.add(menuVO2);
				}
			}
			menuVO1.setMenuList(menuList2);
			menuList.add(menuVO1);
		}
		
		return menuList;

//		MMenuVO param = new MMenuVO();
//		param.setMenuLevel("1");
//		param.setType(type);
//		param.setUserId(userId);
//		param.setSubRoleType(subRoleType);
//		List<MMenuVO> topList = new ArrayList<MMenuVO>();
//		if (Constants.USER_TYPE_USER.equals(type)) {
//			topList = userDao.getMenuList(param);
//			// 这个判断必须在供应商之前
//		} else if (!StringUtil.isNullOrEmpty(subRoleType)) {
//			topList = userDao.getSubSupplierMenuList(param);
//		} else if (Constants.USER_TYPE_SUPPLIER.equals(type)) {
//			topList = userDao.getSupplierMenuList(param);
//		} else {
//			topList = userDao.getLogisticsMenuList(param);
//		}
//		if (topList != null && topList.size() > 0) {
//			// 二级菜单糅合
//			for (int i = 0; i < topList.size(); i++) {
//				param.setMenuLevel("2");
//				param.setParentId(topList.get(i).getMenuId());
//				List<MMenuVO> secondList = null;
//				if (Constants.USER_TYPE_USER.equals(type)) {
//					secondList = userDao.getMenuList(param);
//					// 这个判断必须在供应商之前
//				} else if (!StringUtil.isNullOrEmpty(subRoleType)) {
//					secondList = userDao.getSubSupplierMenuList(param);
//				} else if (Constants.USER_TYPE_SUPPLIER.equals(type)) {
//					secondList = userDao.getSupplierMenuList(param);
//				} else {
//					secondList = userDao.getLogisticsMenuList(param);
//				}
//				// List<MMenuVO> secondList = ((Constants.USER_TYPE_USER
//				// .equals(type)) ? userDao.getMenuList(param) :
//				// ((Constants.USER_TYPE_SUPPLIER.equals(type)) ? userDao
//				// .getSupplierMenuList(param)
//				// :userDao.getLogisticsMenuList(param)));
//				if (secondList != null && secondList.size() > 0) {
//					for (int j = 0; j < secondList.size(); j++) {
//						param.setMenuLevel("3");
//						param.setParentId(secondList.get(j).getMenuId());
//						List<MMenuVO> thirdList = null;
//						if (Constants.USER_TYPE_USER.equals(type)) {
//							thirdList = userDao.getMenuList(param);
//							// 这个判断必须在供应商之前
//						} else if (!StringUtil.isNullOrEmpty(subRoleType)) {
//							thirdList = userDao.getSubSupplierMenuList(param);
//						} else if (Constants.USER_TYPE_SUPPLIER.equals(type)) {
//							thirdList = userDao.getSupplierMenuList(param);
//						} else {
//							thirdList = userDao.getLogisticsMenuList(param);
//						}
//
//						// ((Constants.USER_TYPE_USER
//						// .equals(type)) ? userDao.getMenuList(param)
//						// : ((Constants.USER_TYPE_SUPPLIER.equals(type)) ?
//						// userDao
//						// .getSupplierMenuList(param)
//						// :userDao.getLogisticsMenuList(param)));
//						// 把三级菜单放入二级菜单子目录中去
//						secondList.get(j).setMenuList(thirdList);
//					}
//				}
//				// 把二级菜单放入一级菜单子目录中去
//				topList.get(i).setMenuList(secondList);
//			}
//		}
//		return topList;
	}

	public UserDao getUserDao() {
		return userDao;

	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 密码变更
	 * 
	 * 如果原密码错误,返回 2, 修改ok 返回 1, 失败返回0
	 */
	public int changePwd(UserVO userVO, String originalPwd) {
		
		UserVO queryUser = userDao.queryUserInfoByUserName(userVO.getUserName());
		
		//原始密码错误
		if (null != originalPwd && null != queryUser && originalPwd.equals(queryUser.getUserPwd())){
			userVO.setStatus(queryUser.getStatus());
			if(1 == userDao.updateUserPwd(userVO) ){
				return 1;
			}
			return 2;
		}
		
		
		return 0;
		
		// GAMC
//		if (Constants.USER_TYPE_USER.equals(userVO.getUserType())) {
//			UserVO vo = userDao.queryByNm(userVO.getUserName());
//			// 原密码匹配
//			if (vo != null && vo.getUserPwd().equals(originalPwd)) {
//				return userDao.changeUserPwd(userVO);
//			} else {
//				return 2;
//			}
//			// 供应商
//		} else if (Constants.USER_TYPE_SUPPLIER.equals(userVO.getUserType())) {
//			// 如果是供应商子用户的情况
//			if (Constants.RELATIVE_ID_SUP.equals(userVO.getSubRoleType())) {
//				UserVO vo = userDao.querySubSupplierByNm(userVO.getLoginId());
//				// 原密码匹配
//				if (vo != null && vo.getUserPwd().equals(originalPwd)) {
//					return userDao.changeSubSupplierPwd(userVO);
//				} else {
//					return 2;
//				}
//			} else {
//				UserVO vo = userDao.querySupplierByNm(userVO.getLoginId());
//				// 原密码匹配
//				if (vo != null && vo.getUserPwd().equals(originalPwd)) {
//					return userDao.changeSupplierPwd(userVO);
//				} else {
//					return 2;
//				}
//			}
//			// 物流公司
//		} else if (Constants.USER_TYPE_LOGISTICS.equals(userVO.getUserType())) {
//			// 如果是供应商子用户的情况
//			if (Constants.RELATIVE_ID_TRANSPORTOR.equals(userVO.getSubRoleType())||
//					Constants.RELATIVE_ID_SUPPORTOR.equals(userVO.getSubRoleType())) {
//				UserVO vo = userDao.querySubSupplierByNm(userVO.getLoginId());
//				// 原密码匹配
//				if (vo != null && vo.getUserPwd().equals(originalPwd)) {
//					return userDao.changeSubSupplierPwd(userVO);
//				} else {
//					return 2;
//				}
//			} else {
//				UserVO vo = userDao.queryLogisticsByNm(userVO.getUserId());
//				// 原密码匹配
//				if (vo != null && vo.getUserPwd().equals(originalPwd)) {
//					return userDao.changeLogisticsPwd(userVO);
//				} else {
//					return 2;
//				}
//			}
//		}
//		return 0;
	}

	/**
	 * 获取页面权限信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 页面权限信息
	 */
	public String queryPagesAuth(String userType, String userId) {
		List<PageAuthVO> pagesAuth;
		// 身份类型:用户
//		if (Constants.USER_TYPE_USER.equals(userType)) {
//			pagesAuth = userDao.queryPagesAuth(userId);
//			// 身份类型:供应商
//		} else if (Constants.USER_TYPE_SUPPLIER.equals(userType)) {
//			pagesAuth = userDao.querySupplierPagesAuth(userId);
//		}
//		// 物流公司
//		else {
//			pagesAuth = userDao.queryLogisticsPagesAuth(userId);
//		}
		pagesAuth = userDao.queryPagesAuth(userId);
		// 页面权限信息
		StringBuilder sbAuth = new StringBuilder("{");
		// 页面ID
		String menuId = "";
		for (PageAuthVO pageAuth : pagesAuth) {
			if (!menuId.equals(pageAuth.getMenuId())) {
				if (!"".equals(menuId))
					sbAuth.append(",',");
				menuId = pageAuth.getMenuId();

				sbAuth.append(String.format("'%1$s':',%2$s", menuId, pageAuth
						.getOperId()));
			} else {
				sbAuth.append(",").append(pageAuth.getOperId());
			}
		}
		if ("{".equals(sbAuth.toString())) {
			sbAuth.append("}");
		} else {
			sbAuth.append(",'}");
		}
		return sbAuth.toString();
	}

	/**
	 * <p>查询PDA界面权限菜单</p>
	 * @param userId
	 * @return
	 * @author zuosl  2015-11-12
	 */
	public List<MMenuVO> queryPdaMenuList(String userId) {
		MMenuVO param = new MMenuVO();
		param.setMenuLevel("9");
		param.setUserId(userId);
		return userDao.getMenuList(param);
	}

	/**
	 * 根据用户名称查询用户信息
	 * @param userName
	 * @return
	 */
	public UserVO getUserInfoByUserName(String userName) {
		//只允许admin用户登录
		if(!Constants.SUPER_USER.equals(userName)){
			return null;
		}
//		return userDao.queryUserInfoByUserName(userName);
		UserVO userVO = new UserVO();
		userVO.setUserName(Constants.SUPER_USER);
		userVO.setName("超级管理员");
//		userVO.setUserPwd(PasswordEncrypter.encrypt("admin"));
		userVO.setUserPwd(PasswordEncrypter.encrypt(Constants.ADMIN_PWD));
		userVO.setUserType(Constants.USER_TYPE_USER);
		userVO.setUserStatus(Constants.SYS_USER_STATUS_YES);
		userVO.setLoginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return userVO;
	}

	/**
	 * 更新系统用户登录信息
	 * @param loginInfo 
	 * @return void
	 * @author  2016-3-14 
	 */
	public void updateSysUserLoginInfo(UserVO loginInfo) {
		userDao.updateSysUserLoginInfo(loginInfo);
	}

	public List<MMenuVO> queryMenuListByUserName(String userName) {
		
//		List<MMenuVO> allList = userDao.queryMenuListByUserName(userName);
		
		//直接定义邮件服务应用菜单
		List<MMenuVO> allList = initEmailMenuList();
		
		//----------------
		
		List<MMenuVO> menuList = new ArrayList<MMenuVO>();
		List<MMenuVO> topList = new ArrayList<MMenuVO>();
		List<MMenuVO> secList = new ArrayList<MMenuVO>();
		List<MMenuVO> thiList = new ArrayList<MMenuVO>();
		if(null == allList || 0 >= allList.size()){
			return menuList;
		}
		
		for(MMenuVO menuVO : allList){
			if("1".equals(menuVO.getMenuLevel())){
				topList.add(menuVO);
			}else if("2".equals(menuVO.getMenuLevel())){
				secList.add(menuVO);
			}else if("3".equals(menuVO.getMenuLevel())){
				thiList.add(menuVO);
			}
		}
		
		if(null == topList || 0 >= topList.size()
				|| null == secList || 0 >= secList.size()
				|| null == thiList || 0 >= thiList.size() ){
			return menuList;
		}
		
		for(MMenuVO menuVO1 : topList){
			List<MMenuVO> menuList2 = new ArrayList<MMenuVO>();
			for(MMenuVO menuVO2 : secList){
				if(menuVO1.getMenuId().equals(menuVO2.getParentId())){
					List<MMenuVO> menuList3 = new ArrayList<MMenuVO>();
					for(MMenuVO menuVO3 : thiList){
						if(menuVO2.getMenuId().equals(menuVO3.getParentId())){
							menuList3.add(menuVO3);
						}
					}
					menuVO2.setMenuList(menuList3);
					menuList2.add(menuVO2);
				}
			}
			menuVO1.setMenuList(menuList2);
			menuList.add(menuVO1);
		}
		return menuList;
	}

	/**
	 * 直接定义邮件服务菜单目录
	 * @return
	 * @author ZUOSL 
	 * @date 2018-5-2 下午12:10:39
	 */
	private List<MMenuVO> initEmailMenuList() {
		List<MMenuVO> allList = new ArrayList<MMenuVO>();
		MMenuVO topmenuvo1 = new MMenuVO();
		topmenuvo1.setMenuLevel("1");
		topmenuvo1.setMenuId("timerjob");
		topmenuvo1.setMenuName("定时任务管理");
		topmenuvo1.setUrl("timertop01");
		topmenuvo1.setParentId("firstmli1");
		topmenuvo1.setSort("10");
		allList.add(topmenuvo1);
		
		MMenuVO secmenuvo1 = new MMenuVO();
		secmenuvo1.setMenuLevel("2");
		secmenuvo1.setMenuId("mailserverid");
		secmenuvo1.setMenuName("邮件服务管理");
		secmenuvo1.setUrl("mailserv01");
		secmenuvo1.setParentId("timerjob");
		secmenuvo1.setSort("10");
		allList.add(secmenuvo1);
		
		MMenuVO menuvo1 = new MMenuVO();
		menuvo1.setMenuLevel("3");
		menuvo1.setMenuId("mailtimer");
		menuvo1.setMenuName("邮件定时器管理");
		menuvo1.setUrl("email/pub_msg_timer.action");
		menuvo1.setParentId("mailserverid");
		menuvo1.setSort("10");
		allList.add(menuvo1);
		
		MMenuVO menuvo2 = new MMenuVO();
		menuvo2.setMenuLevel("3");
		menuvo2.setMenuId("mailusergroup");
		menuvo2.setMenuName("分组人员维护");
		menuvo2.setUrl("email/pub_msg_group.action");
		menuvo2.setParentId("mailserverid");
		menuvo2.setSort("10");
		allList.add(menuvo2);
		
		return allList;
	}

	/**
	 * 根据用户名查询界面的按钮、数据等权限信息
	 * @param userName
	 * @return String
	 */
	public String queryPagesAuthByUserName(String userName) {
		
		//根据用户名查询界面权限
		List<PageAuthVO> pagesAuth = userDao.queryPagesAuthUserName(userName);
		if(null == pagesAuth || 0 >= pagesAuth.size()){
			return "{}";
		}
		
		// 页面权限信息
		StringBuilder sbAuth = new StringBuilder("{");
		
		// 页面ID
		String menuId = "";
		for (PageAuthVO pageAuth : pagesAuth) {
			if (!menuId.equals(pageAuth.getMenuId())) {
				if (!"".equals(menuId)){
					sbAuth.append(",',");
				}
				menuId = pageAuth.getMenuId();
				sbAuth.append(String.format("'%1$s':',%2$s", menuId, pageAuth.getOperId()));
			} else {
				sbAuth.append(",").append(pageAuth.getOperId());
			}
		}
		if ("{".equals(sbAuth.toString())) {
			sbAuth.append("}");
		} else {
			sbAuth.append(",'}");
		}
		return sbAuth.toString();
	}

	public List<FactoryVO> queryFactoryInfoByUserName(String userName) {
		return userDao.queryFactoryInfoByUserName(userName);
	}

	/**
	 * 根据用户名查询供应商的信息
	 * @param userName
	 * @return
	 * @author zuosl 2016-3-25
	 */
	public SupplierVO querySupplierInfoByUserName(String userName) {
		return userDao.querySupplierInfoByUserName(userName);
	}

	/**
	 * 根据用户名查询物流公司信息
	 * @param userName
	 * @return
	 * @author zuosl 2016-3-25
	 */
	public LogCompanyVO queryLogCompanyInfoByUserName(String userName) {
		return userDao.queryLogCompanyInfoByUserName(userName);
	}

	/**
	 * 根据用户的一级模块查询顶级模块信息
	 * @param firstMouldIdList
	 * @return
	 * @author zuosl 2016-5-6
	 */
	public List<MMenuVO> queryUserZeroMenuList(List<String> firstMouldIdList) {
//		return userDao.queryUserZeroMenuList(firstMouldIdList);
		List<MMenuVO> list = new ArrayList<MMenuVO>();
		MMenuVO mvo = new MMenuVO();
		mvo.setMenuLevel("0");
		mvo.setMenuId("firstmli1");
		mvo.setMenuName("系统管理");
		list.add(mvo);
		return list;
	}
}
