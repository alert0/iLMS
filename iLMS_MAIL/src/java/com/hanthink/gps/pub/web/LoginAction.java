package com.hanthink.gps.pub.web;

import java.util.List;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.hanthink.gps.pub.service.LoginService;
import com.hanthink.gps.pub.service.PubSysParamServer;
import com.hanthink.gps.pub.vo.FactoryVO;
import com.hanthink.gps.pub.vo.LogCompanyVO;
import com.hanthink.gps.pub.vo.SupplierVO;
import com.hanthink.gps.pub.vo.UserVO;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.PasswordEncrypter;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;

/**
 * 登陆操作相关操作类
 * 
 * @author EBT
 * 
 */
public class LoginAction extends BaseActionSupport {

	private static final long serialVersionUID = -5985239321909398142L;

	private LoginService service;
	// 密码
	private String password;
	// 用户名
	private String userName;
	// 身份类型
	private String type;
	// 密码
	private String supplierPwd;
	// 确认密码
	private String confirmSupplierPwd;
	// 原密码
	private String originalPwd;
	// 随机码
	private String randCode;
	
	//用户工厂信息
	private List<FactoryVO> factoryVOs;
	
	//选择登录的工厂
	private String loginFactoryCode;
	
	//错误提示信息
	private String errMsg;
	
	/** 单点登录令牌 */
	private String rdtext;
	/** 单点登录用户名 */
	private String username;


	// 登录
	public String execute() {
		if (session != null) {
			session.removeAttribute(Constants.USER_KEY);
		}
		return INPUT;
	}

	/**
	 * 登录处理
	 */
	public String query() {
		
		// 判定已经存在的session请求是否已存在用户,有的话就设置过期,同时新new一个session
		UserVO preInfo = (UserVO) session.getAttribute(Constants.USER_KEY);
		//取得用户信息
		UserVO userVO = service.getUserInfoByUserName(userName);
		
		if (null == userVO) {
			// "用户不存在。"
			addActionError(String.format(getText(Constants.SW_E_M002)));
			return INPUT;
		}else if (null != preInfo && !preInfo.getUserName().equals(userVO.getUserName()) ) {
			// "已有用户登录,请先退出已登录的用户后再刷新页面登录。"
			addActionError(String.format(getText(Constants.SW_E_M003)));
			return INPUT;
		}else {
			// 判断用户的状态，取得相应的信息
			
			if (Constants.SYS_USER_STATUS_NO.equals(userVO.getUserStatus())) {
				// "该用户已被禁用。"
				addActionError(String.format(getText(Constants.SW_E_M004)));
				return INPUT;
			} else if (!PasswordEncrypter.isEncrypt(password, userVO.getUserPwd())) {
				// "密码输入错误。"
				addActionError(String.format(getText(Constants.SW_E_M005)));
				return INPUT;
			} else {
				// 登录成功
				session.setAttribute(Constants.USER_KEY, userVO);
				return SUCCESS;
				
			}
		}
		
	}
	
	/**
	 * 单点登录处理
	 * @return
	 * @author zuosl 2016-5-23
	 */
	public String ssoLogin(){
		
		//令牌验证
		String token = rdtext;
		String from = "GPS654321";
		String to = "GPS_SSO";
		String funcName = "Verify_IF01";
//		String endpoint = "http://172.18.40.133:8080/sso_webservice/services/SSOWebService?wsdl";
		String endpoint = PubSysParamServer.querySysParam("END_POINT_VERIFY_ADDR");
		if(StringUtil.isNullOrEmpty(endpoint)){
			LogUtil.info("获取单点登录认证地址失败:"+endpoint);
			addActionError(String.format(getText(Constants.MSG_ID_E_OPE_FAILED)));
			return INPUT;
		}
		Service service = new Service();
		Call call;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpoint);
			call.setOperationName("verifyUser");//WSDL里面描述的接口名称
			call.addParameter("from", org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);//接口的参数
			call.addParameter("to", org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);//接口的参数
			call.addParameter("ipaddr", org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);//接口的参数
			call.addParameter("token", org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);//接口的参数
			call.addParameter("funcName", org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);//接口的参数
			call.addParameter("userName", org.apache.axis.encoding.XMLType.XSD_DATE,
					javax.xml.rpc.ParameterMode.IN);//接口的参数
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型 
			String result = (String)call.invoke(new Object[]{from, to, getIpAddr(), token, funcName, username});
			
			LogUtil.info("单点登录结果RESULT/USERNAME："+result+"/"+username);
			if ("OK".equals(result)) {
				return ssoLoginCheck(username);
			} else {
				addActionError(String.format(getText(Constants.MSG_ID_E_OPE_FAILED)));
				return INPUT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e);
			addActionError(String.format(getText(Constants.MSG_ID_E_OPE_FAILED)));
			return INPUT;
		}
		
		
	}
	
	/**
	 * 单点登录验证检查
	 * @return
	 * @author zuosl 2016-5-23
	 */
	private String ssoLoginCheck(String ssoUserName) {
		UserVO preInfo = (UserVO) session.getAttribute(Constants.USER_KEY);
		//取得用户信息
		UserVO userVO = service.getUserInfoByUserName(ssoUserName);
		
		if (null == userVO) {
			// "用户不存在。"
			addActionError(String.format(getText(Constants.SW_E_M002)));
			return INPUT;
		}else if (null != preInfo && !preInfo.getUserName().equals(userVO.getUserName()) ) {
			// "已有用户登录,请先退出已登录的用户后再刷新页面登录。"
			addActionError(String.format(getText(Constants.SW_E_M003)));
			return INPUT;
		}else {
			// 判断用户的状态，取得相应的信息
			
			if (Constants.SYS_USER_STATUS_NO.equals(userVO.getUserStatus())) {
				// "该用户已被禁用。"
				addActionError(String.format(getText(Constants.SW_E_M004)));
				return INPUT;
			} 
//			else if (!PasswordEncrypter.isEncrypt(password, userVO.getUserPwd())) {
//				// "密码输入错误。"
//				addActionError(String.format(getText(Constants.SW_E_M005)));
//				return INPUT;
//			} 
			else {
				// 登录成功
				
				boolean isOnlyFactory = true;
				List<FactoryVO> userFactoryVOs = null;
				
				//根据不同用户类型查询用户相关信息
				if(Constants.USER_TYPE_SUPPLIER.equals(userVO.getUserType())){
					
					SupplierVO supVO = service.querySupplierInfoByUserName(ssoUserName);
					
					if(null == supVO || StringUtil.isNullOrEmpty(supVO.getSupplierNo())
							|| StringUtil.isNullOrEmpty(supVO.getActiveStatus())){
						addActionError(String.format(getText(Constants.MSG_ID_E_NOT_SUPPLIER_INFO)));
						return INPUT;
					}else if(Constants.SUP_ACTIVE_STATUS_NO.equals(supVO.getActiveStatus())
							|| Constants.SUP_ACTIVE_STATUS_DISABLE.equals(supVO.getActiveStatus())){
						addActionError(String.format(getText(Constants.MSG_ID_E_SUPPLIER_NOACTIVATE), supVO.getSupplierNo()));
						return INPUT;
					}
					
					userVO.setActiveStatus(supVO.getActiveStatus());
					userVO.setParentNo(supVO.getParentNo());
					userVO.setSupplierName(supVO.getSupplierName());
					userVO.setSupplierNo(supVO.getSupplierNo());
					
				}else if(Constants.USER_TYPE_USER.equals(userVO.getUserType())){
					
					userFactoryVOs = service.queryFactoryInfoByUserName(ssoUserName);
					if(null != userFactoryVOs && 1 == userFactoryVOs.size()){
						userVO.setCurLoginFactory(userFactoryVOs.get(0).getFactoryCode());
					}else if(null != userFactoryVOs && 1 < userFactoryVOs.size()){
						isOnlyFactory = false;
					}else{
						addActionError(String.format(getText(Constants.MSG_ID_E_USER_NOT_FACTORY)));
						return INPUT;
					}
					
					//根据用户名查询其它用户个人信息 
					
				}else if(Constants.USER_TYPE_LOGISTICS.equals(userVO.getUserType())){
					
					LogCompanyVO companyVO = service.queryLogCompanyInfoByUserName(ssoUserName);
					
					if(null == companyVO || StringUtil.isNullOrEmpty(companyVO.getCompanyNo())
							|| StringUtil.isNullOrEmpty(companyVO.getStatus() )  ){
						addActionError(String.format(getText(Constants.MSG_ID_E_NOT_SUPPLIER_INFO)));
						return INPUT;
					}else if(!Constants.ACTIVATE_STATUS_YES.equals(companyVO.getStatus())){
						addActionError(String.format(getText(Constants.MSG_ID_E_SUPPLIER_NOACTIVATE), companyVO.getCompanyNo()));
						return INPUT;
					}
					
					userVO.setSupplierNo(companyVO.getCompanyNo());
					
				}
				
				// 更新登录次数
				userVO.setLoginNum(String.valueOf(Integer.parseInt(userVO.getLoginNum()) + 1)); 
				
				//更新用户登录信息
				UserVO loginInfo = new UserVO();
				loginInfo.setLoginIp(getIpAddr()); // 登录IP
				loginInfo.setUserName(userVO.getUserName());
				service.updateSysUserLoginInfo(loginInfo);
				
				
				if(!isOnlyFactory){
					
					this.setFactoryVOs(userFactoryVOs);
					
					session.setAttribute(Constants.USER_KEY_TEMP, userVO);
					return "factory";
				}else{
					session.setAttribute(Constants.USER_KEY, userVO);
					return SUCCESS;
				}
				
			}
		}
	}

	/**
	 * 选择工厂后登录
	 * @return
	 * @author zuosl 2016-3-17
	 */
	public String factoryLogin(){
		
		if(StringUtil.isNullOrEmpty(loginFactoryCode)){
			return ERROR;
		}
		
		UserVO userVO = (UserVO) session.getAttribute(Constants.USER_KEY_TEMP);
		if(null != userVO){
			userVO.setCurLoginFactory(loginFactoryCode);
			session.setAttribute(Constants.USER_KEY, userVO);
//			session.removeAttribute(Constants.USER_KEY_TEMP);
			return SUCCESS;
		}else if(null != session.getAttribute(Constants.USER_KEY)){
			UserVO uvo = (UserVO) session.getAttribute(Constants.USER_KEY);
			uvo.setCurLoginFactory(loginFactoryCode);
			session.setAttribute(Constants.USER_KEY, uvo);
		}
		return LOGIN;
	}
	

	/**
	 * 密码修改
	 */
	public void changePwd() {
		
		if (supplierPwd != null && supplierPwd.equals(confirmSupplierPwd)) {
			UserVO vo = new UserVO();
			vo.setUserType(userInfo.getUserType());
			vo.setUserId(userInfo.getUserId());
			vo.setLoginId(userInfo.getLoginId());
			vo.setUserPwd(PasswordEncrypter.encrypt(supplierPwd));
			vo.setUserName(userInfo.getUserName());
			vo.setSubRoleType(userInfo.getSubRoleType());
			int result = service.changePwd(vo, PasswordEncrypter.encrypt(originalPwd));
			LogUtil.info(userInfo.getUserName()+"修改个人密码");
			if (result == 1) {
				write(Constants.MSG_SUCCESS);
			} else if (result == 2) {
				addError(Constants.MSG_ID_E_ORIGINAL_PWD_FAIL);
				writeError();
			} else {
				addError(Constants.MSG_ID_E_MODIFY_PWD_FAIL);
				writeError();
			}
		}
	}

	/**
	 * 退出处理
	 * 
	 * @return 登录画面
	 */
	public String exit() {
		/////////qiaoqiming 20100903 start
//		UserVO loginInfo = new UserVO();
//		// 更新用户id
//		if(userInfo != null){
//			loginInfo.setUserId(userInfo.getUserId());
//			service.updateExit(loginInfo);
//		}
		/////////qiaoqiming 20100903 end
		session.removeAttribute(Constants.USER_KEY);
		return INPUT;
	}

	/**
	 * 验证画面输入内容
	 */
	public void validateQuery() {
		// 用户名
		if (StringUtil.isNullOrEmpty(userName)) {
			// "请输入用户名。"
			addFieldError("userName", String.format(getText(Constants.SW_E_M008)));
		}
		// 密码
		if (StringUtil.isNullOrEmpty(password)) {
			// "请输入密码。"
			addFieldError("password", String.format(getText(Constants.SW_E_M009)));
		}

	}

	public void setService(LoginService service) {
		this.service = service;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取 supplierPwd
	 * 
	 * @return supplierPwd
	 */
	public String getSupplierPwd() {
		return supplierPwd;
	}

	/**
	 * 设定supplierPwd
	 * 
	 * @param supplierPwd
	 *            supplierPwd
	 */
	public void setSupplierPwd(String supplierPwd) {
		this.supplierPwd = supplierPwd;
	}

	/**
	 * 获取 confirmSupplierPwd
	 * 
	 * @return confirmSupplierPwd
	 */
	public String getConfirmSupplierPwd() {
		return confirmSupplierPwd;
	}

	/**
	 * 设定confirmSupplierPwd
	 * 
	 * @param confirmSupplierPwd
	 *            confirmSupplierPwd
	 */
	public void setConfirmSupplierPwd(String confirmSupplierPwd) {
		this.confirmSupplierPwd = confirmSupplierPwd;
	}

	/**
	 * 获取 service
	 * 
	 * @return service
	 */
	public LoginService getService() {
		return service;
	}

	/**
	 * 获取 originalPwd
	 * 
	 * @return originalPwd
	 */
	public String getOriginalPwd() {
		return originalPwd;
	}

	/**
	 * 设定originalPwd
	 * 
	 * @param originalPwd
	 *            originalPwd
	 */
	public void setOriginalPwd(String originalPwd) {
		this.originalPwd = originalPwd;
	}

	/**
	 * 获取 randCode
	 * 
	 * @return randCode
	 */
	public String getRandCode() {
		return randCode;
	}

	/**
	 * 设定randCode
	 * 
	 * @param randCode
	 *            randCode
	 */
	public void setRandCode(String randCode) {
		this.randCode = randCode;
	}

	
	public List<FactoryVO> getFactoryVOs() {
		return factoryVOs;
	}
	public void setFactoryVOs(List<FactoryVO> factoryVOs) {
		this.factoryVOs = factoryVOs;
	}

	public String getLoginFactoryCode() {
		return loginFactoryCode;
	}
	public void setLoginFactoryCode(String loginFactoryCode) {
		this.loginFactoryCode = loginFactoryCode;
	}

	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * 获取单点登录令牌
	 * @return the rdtext 单点登录令牌
	 */
	public String getRdtext() {
		return rdtext;
	}

	/**
	 * 设置单点登录令牌
	 * @param rdtext the rdtext 单点登录令牌
	 */
	public void setRdtext(String rdtext) {
		this.rdtext = rdtext;
	}

	/**
	 * 获取单点登录用户名
	 * @return the username 单点登录用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置单点登录用户名
	 * @param username the username 单点登录用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
}
