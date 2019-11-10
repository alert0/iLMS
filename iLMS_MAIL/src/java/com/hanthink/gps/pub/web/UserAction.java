package com.hanthink.gps.pub.web;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;

import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.web.BaseActionSupport;
import com.hanthink.gps.pub.service.UserService;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.logger.Logger;
import com.hanthink.gps.util.mail.MailService;
import com.hanthink.gps.pub.vo.UserVO;
import com.opensymphony.xwork2.util.logging.LoggerUtils;

/**
 * 用户
 * 
 * @author hcsang
 */
public class UserAction extends BaseActionSupport {

	private static final long serialVersionUID = 5764590602050265175L;

	// 登录名
	private static final int MAX_LENGTH_USERNAME = 15;
	// 密码最小长度
	private static final int MIN_LENGTH_USERPWD = 6;
	// 密码最大长度
	private static final int MAX_LENGTH_USERPWD = 20;
	// 用户名
	private static final int MAX_LENGTH_NAME = 15;
	// 电话
	private static final int MAX_LENGTH_TEL = 20;
	// 手机
	private static final int MAX_LENGTH_MOBILE = 20;
	// 传真
	private static final int MAX_LENGTH_FAX = 20;
	// 邮箱
	private static final int MAX_LENGTH_EMAIL = 64;
	
	private MailService mailService;
	private SimpleMailMessage message;
	
	// 用户信息
	private UserVO user;
	
	// 用户角色列表
	private List<String> roleIds;
	
	//用户工厂列表
	private List<String> factories;
	
	// 解锁用户列表
	private List<String> userIds;
	
	// 检索条件
	private UserVO queryInfo;
	
	private UserService service;

	/**
	 * 新增GAMC用户
	 */
	public void insertUser() {
		// 画面输入项验证
		validateChk(false);
		if (isInvalid()){
			return;
		}
		// 新增用户处理
		user.setEntryId(userInfo.getUserName());
		user.setUserType(Constants.USER_TYPE_USER);
		service.insert(user, roleIds, factories);
		LogUtil.info(userInfo.getUserName()+"新增GAMC用户"+user.getUserName());
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 新增供应商用户
	 */
	public void insertSupplierUser(){
		
		user.setEntryId(userInfo.getUserName());
		if(Constants.SUP_ROLE_TYPE_SUPPLIER.equals(user.getRoleType())){
			user.setUserType(Constants.USER_TYPE_SUPPLIER);
		}else if(Constants.SUP_ROLE_TYPE_TRANSIT.equals(user.getRoleType())
				|| Constants.SUP_ROLE_TYPE_WX.equals(user.getRoleType())){
			user.setUserType(Constants.USER_TYPE_LOGISTICS);
		}else{
			addError("角色类型错误");
		}
		if (isInvalid()){
			return;
		}
		service.insertSupplierUser(user, roleIds);
		LogUtil.info(userInfo.getUserName()+"新增供应商用户"+user.getUserName());
		write(Constants.MSG_SUCCESS);
		
	}

	/**
	 * 查询GAMC用户信息
	 */
	public void queryUserForPage() {
		PageObject o = service.queryForPage(queryInfo, start, limit);
		writeJson(o);
	}

	
	public void queryById() {
		// 根据用户Id，取得用户信息
		writeJson(service.queryByUserId(queryInfo.getUserId()), true);
	}
	
	/**
	 * 根据用户名查询GAMC用户信息
	 */
	public void queryByUserName(){
		// 根据用户登录名，取得用户信息
		writeJson(service.queryByUserName(queryInfo.getUserName(), Constants.USER_TYPE_USER), true);
	}

	/**
	 * 修改用户
	 */
	public void update() {
		String departmentIdString=request.getParameter("departmentId");
		if(StringUtil.isNullOrEmpty(departmentIdString)){
			user.setDepartmentId(null);
		}
		else{
			user.setDepartmentId(departmentIdString);
		}
		// 输入check
		validateChk(true);
		if (isInvalid()){
			return;
		}
		// 更新用户信息
		user.setUserType(Constants.USER_TYPE_USER);
		user.setModifyId(userInfo.getUserName());
		service.update(user, roleIds, factories);
		LogUtil.info(userInfo.getUserName()+"修改GAMC用户"+user.getUserName());
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 修改供应商用户
	 * @author zuosl 2016-3-22
	 */
	public void updateSupplier(){
		user.setEntryId(userInfo.getUserName());
		if(StringUtil.isNullOrEmpty(user.getUserName())){
			addError("用户名为空");
		}
		if (isInvalid()){
			return;
		}
		user.setModifyId(userInfo.getUserName());
		service.updateSupplier(user, roleIds);
		LogUtil.info(userInfo.getUserName()+"修改供应商用户"+user.getUserName());
		write(Constants.MSG_SUCCESS);
		
	}

	/**
	 * 禁用
	 */
	public void disable() {
		
		// 删除用户信息
		service.disable(user);
		write(Constants.MSG_SUCCESS);
	}

	/**
	 * 可用
	 */
	public void usable() {
		// 删除用户信息
		service.usable(user);
		write(Constants.MSG_SUCCESS);
	}

	/**
	 * 解锁
	 */
	public void unlock() {
		
		// 删除用户信息
		service.unlock(userIds);
		write(Constants.MSG_SUCCESS);
	}

	/**
	 * 验证画面输入内容
	 * 
	 * @param isUpdate
	 *            更新用户
	 */
	public void validateChk(boolean isUpdate) {

		// 必须输入项check
		if (!isUpdate && StringUtil.isNullOrEmpty(user.getUserName())) {
			addError(Constants.MSG_ID_E_REQUEST, "登录名");
		}
		if (!isUpdate && StringUtil.isNullOrEmpty(user.getUserPwd())) {
			addError(Constants.MSG_ID_E_REQUEST, "密码");
		}
//		if (StringUtil.isNullOrEmpty(user.getDepartmentId())) {
//			addError(Constants.MSG_ID_E_REQUEST, "部门");
//		}
		if (StringUtil.isNullOrEmpty(user.getTel())) {
			addError(Constants.MSG_ID_E_REQUEST, "电话");
		}
		if (StringUtil.isNullOrEmpty(user.getEmail())) {
			addError(Constants.MSG_ID_E_REQUEST, "电子邮箱");
		}

		// 长度check
		if (!isUpdate
				&& StringUtil.getStrLength(user.getUserName()) > MAX_LENGTH_USERNAME) {
			addError(Constants.MSG_ID_E_MAXLENGTH, "登录名", MAX_LENGTH_USERNAME);
		}
		if (StringUtil.getStrLength(user.getName()) > MAX_LENGTH_NAME) {
			addError(Constants.MSG_ID_E_MAXLENGTH, "姓名", MAX_LENGTH_NAME);
		}
		if (!isUpdate
				&& StringUtil.getStrLength(user.getUserPwd()) > MAX_LENGTH_USERPWD) {
			addError(Constants.MSG_ID_E_MAXLENGTH, "密码", MAX_LENGTH_USERPWD);
		}
		if (!isUpdate
				&& StringUtil.getStrLength(user.getUserPwd()) < MIN_LENGTH_USERPWD) {
			addError(Constants.MSG_ID_E_MINLENGTH, "密码", MIN_LENGTH_USERPWD);
		}

		if (StringUtil.getStrLength(user.getUserName()) > MAX_LENGTH_USERNAME) {
			addError(Constants.MSG_ID_E_MAXLENGTH, "登录名", MAX_LENGTH_USERNAME);
		}
		if (StringUtil.getStrLength(user.getTel()) > MAX_LENGTH_TEL) {
			addError(Constants.MSG_ID_E_MAXLENGTH, "电话", MAX_LENGTH_TEL);
		}
		if (StringUtil.getStrLength(user.getMobile()) > MAX_LENGTH_MOBILE) {
			addError(Constants.MSG_ID_E_MAXLENGTH, "手机", MAX_LENGTH_MOBILE);
		}
		if (StringUtil.getStrLength(user.getFax()) > MAX_LENGTH_FAX) {
			addError(Constants.MSG_ID_E_MAXLENGTH, "传真", MAX_LENGTH_FAX);
		}
		if (StringUtil.getStrLength(user.getEmail()) > MAX_LENGTH_EMAIL) {
			addError(Constants.MSG_ID_E_MAXLENGTH, "电子邮箱", MAX_LENGTH_EMAIL);
		}
		// // 输入类型check
		// if (StringUtil.getStrLength(user.getEmail()) > MAX_LENGTH_EMAIL) {
		// addError(Constants.MSG_ID_E_MAXLENGTH,"电子邮箱",MAX_LENGTH_EMAIL);
		// }
	}
	
	/**
	 * 用户信息删除
	 */
	public void deleteUser(){
		user.setUserType(Constants.USER_TYPE_USER);
		service.remove(user);
		LogUtil.info(userInfo.getUserName() + "删除GAMC用户" + user.getUserName() );
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 删除供应商用户信息
	 * @author zuosl 2016-3-22
	 */
	public void deleteSupplierUser(){
		user.setUserType(Constants.USER_TYPE_SUPPLIER);
		service.remove(user);
		LogUtil.info(userInfo.getUserName() + "删除供应商用户" + user.getUserName() );
		write(Constants.MSG_SUCCESS);
	}
	
	
	/**
	 * 查询供应商用户信息
	 * @author zuosl 2016-3-21
	 */
	public void querySupplierUserForPage(){
		if(null == queryInfo || StringUtil.isNullOrEmpty(queryInfo.getRoleType())){
			addError("角色类型获取失败");
		}
		if (isInvalid()){
			return;
		}
		PageObject o = service.querySupplierUserForPage(queryInfo, start, limit);
		writeJson(o);
	}

	
	public void setService(UserService service) {
		this.service = service;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public UserVO getQueryInfo() {
		return queryInfo;
	}

	public void setQueryInfo(UserVO queryInfo) {
		this.queryInfo = queryInfo;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	/**
	 * 获取 mailService
	 * 
	 * @return mailService
	 */
	public MailService getMailService() {
		return mailService;
	}

	/**
	 * 设定mailService
	 * 
	 * @param mailService
	 *            mailService
	 */
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * 获取 message
	 * 
	 * @return message
	 */
	public SimpleMailMessage getMessage() {
		return message;
	}

	/**
	 * 设定message
	 * 
	 * @param message
	 *            message
	 */
	public void setMessage(SimpleMailMessage message) {
		this.message = message;
	}

	/**
	 * 获取 service
	 * 
	 * @return service
	 */
	public UserService getService() {
		return service;
	}

	public List<String> getFactories() {
		return factories;
	}

	public void setFactories(List<String> factories) {
		this.factories = factories;
	}
	
	

}
