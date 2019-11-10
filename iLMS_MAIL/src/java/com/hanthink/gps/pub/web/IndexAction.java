package com.hanthink.gps.pub.web;

import java.util.ArrayList;
import java.util.List;

import com.hanthink.gps.pub.service.LoginService;
import com.hanthink.gps.pub.service.PubSysParamServer;
import com.hanthink.gps.pub.vo.FactoryVO;
import com.hanthink.gps.pub.vo.MMenuVO;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.StringUtil;

public class IndexAction extends BaseActionSupport {
	private static final long serialVersionUID = -778787857166906293L;
	private LoginService loginService;
	private String loginNum;
	private String loginTime;
	private String loginIp;
	private String loginId;
	private String supplierNo;
	private String parentNo;
	private String curLoginFactory; //当前登录工厂

	@SuppressWarnings("unchecked")
	public String init() {
//		long start = new Date().getTime();
//		LogUtil.info(userInfo.getUserName()+"**********初始化界面开始**********");
		
		if(Constants.USER_TYPE_SUPPLIER.equals(userInfo.getUserType())
				&& Constants.SUP_ACTIVE_STATUS_YES.equals(userInfo.getActiveStatus()) ){
			return "unregist";
		}
		
		List<MMenuVO> userMenuList = loginService.queryMenuListByUserName(userInfo.getUserName());
		session.setAttribute(Constants.USER_ROLE_MENU_KEY, userMenuList);
		loginNum = userInfo.getLoginNum();
		loginTime = userInfo.getLoginTime();
		loginIp = userInfo.getLoginIp();
		
//		curLoginFactory = null == userInfo.getCurLoginFactory() ? "" : userInfo.getCurLoginFactory();
//		if(!StringUtil.isNullOrEmpty(curLoginFactory) && Constants.USER_TYPE_USER.equals(userInfo.getUserType())){
//			List<FactoryVO> userFactoryVOs = loginService.queryFactoryInfoByUserName(userInfo.getUserName());
//			if(null != userFactoryVOs){
//				for(FactoryVO fvo : userFactoryVOs){
//					if(fvo.getFactoryCode().equals(userInfo.getCurLoginFactory())){
//						curLoginFactory = fvo.getFactoryName();
//						break;
//					}
//				}
//			}
//		}
//		session.setAttribute(Constants.USER_PAGES_AUTH_KEY, 
//				loginService.queryPagesAuthByUserName(userInfo.getUserName()));
		curLoginFactory = "";
		session.setAttribute(Constants.USER_PAGES_AUTH_KEY, "");
		
		//顶级模块
		List<MMenuVO> zeroMenuList = null;
		
		List<String> firstMouldIdList = new ArrayList<String>();
		if(null != userMenuList && 0 < userMenuList.size()){
			for(MMenuVO vo : userMenuList){
				firstMouldIdList.add(vo.getMenuId());
			}
			zeroMenuList = loginService.queryUserZeroMenuList(firstMouldIdList);
		}else{
			zeroMenuList = new ArrayList<MMenuVO>();
		}
		session.setAttribute(Constants.USER_ROLE_ZERO_MENU_KEY, zeroMenuList);
		
//		session.setAttribute("PAGE_QUERY_SIZE", PubSysParamServer.querySysParam("PAGE_SIZE"));
		session.setAttribute("PAGE_QUERY_SIZE", "50");
//		long end = new Date().getTime();
//		LogUtil.info(userInfo.getUserName()+"**********初始化界面结束,耗时："+(end-start)+"ms");
		
		return "index";
	}

	/**
	 * 设定loginService
	 * 
	 * @param loginService
	 *            loginService
	 */
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	/**
	 * 获取 supplierNo
	 * 
	 * @return supplierNo
	 */
	public String getSupplierNo() {
		return supplierNo;
	}

	/**
	 * 设定supplierNo
	 * 
	 * @param supplierNo
	 *            supplierNo
	 */
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	/**
	 * 获取 loginService
	 * 
	 * @return loginService
	 */
	public LoginService getLoginService() {
		return loginService;
	}

	/**
	 * 获取 loginId
	 * 
	 * @return loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * 设定loginId
	 * 
	 * @param loginId
	 *            loginId
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * 获取 loginNum
	 * 
	 * @return loginNum
	 */
	public String getLoginNum() {
		return loginNum;
	}

	/**
	 * 设定loginNum
	 * 
	 * @param loginNum
	 *            loginNum
	 */
	public void setLoginNum(String loginNum) {
		this.loginNum = loginNum;
	}

	/**
	 * 获取 loginTime
	 * 
	 * @return loginTime
	 */
	public String getLoginTime() {
		return loginTime;
	}

	/**
	 * 设定loginTime
	 * 
	 * @param loginTime
	 *            loginTime
	 */
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * 获取 loginIp
	 * 
	 * @return loginIp
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * 设定loginIp
	 * 
	 * @param loginIp
	 *            loginIp
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * 获取 parentNo
	 * 
	 * @return parentNo
	 */
	public String getParentNo() {
		return parentNo;
	}

	/**
	 * 设定parentNo
	 * 
	 * @param parentNo
	 *            parentNo
	 */
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public String getCurLoginFactory() {
		return curLoginFactory;
	}
	public void setCurLoginFactory(String curLoginFactory) {
		this.curLoginFactory = curLoginFactory;
	}
	
	
}
