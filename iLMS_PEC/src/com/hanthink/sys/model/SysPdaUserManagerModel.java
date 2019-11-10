package com.hanthink.sys.model;
import java.awt.image.BufferedImage;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：用户数据权限表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class SysPdaUserManagerModel extends AbstractModel<Integer>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5105878144469220951L;

	/**
	 * 当前登录人
	 */
	private String opeUser; 
	
	/**
	 * 主表  用户表
	 */
	/**
	* 用户ID
	*/
	private String userId; 
	
	/**
	* 用户名
	*/
	private String userName; 
	
	/**
	* 工厂
	*/
	private String factoryCode; 
	
	/**
	* 密码
	*/
	private String userPwd; 
	
	/**
	* 密码(厂内)
	*/
	private String userPwdPEC; 
	
	/**
	* 登录次数
	*/
	private String loginNum; 
	
	/**
	 * 登录IP
	 */
	private String loginIp;
	
	/**
	 * 登录时间
	 */
	private String loginTime;
	
	/**
	 * 创建人
	 */
	private String creationUser; 
	
	/**
	* 创建时间
	*/
	private java.util.Date creationTime; 
	
	/**
	* 最后修改人
	*/
	private String lastModifiedUser; 
	
	/**
	* 最后修改时间
	*/
	private String lastModifiedIp;
	
	/**
	* 最后修改时间
	*/
	private java.util.Date lastModifiedTime;
	
	/**
	* 标签二维码
	*/
	private BufferedImage QRCode;
	
	/**
	* 图片ID
	*/
	private String imageId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(String loginNum) {
		this.loginNum = loginNum;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getOpeUser() {
		return opeUser;
	}

	public void setOpeUser(String opeUser) {
		this.opeUser = opeUser;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public BufferedImage getQRCode() {
		return QRCode;
	}

	public void setQRCode(BufferedImage qrImg) {
		QRCode = qrImg;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getLastModifiedIp() {
		return lastModifiedIp;
	}

	public void setLastModifiedIp(String lastModifiedIp) {
		this.lastModifiedIp = lastModifiedIp;
	}

	public String getUserPwdPEC() {
		return userPwdPEC;
	}

	public void setUserPwdPEC(String userPwdPEC) {
		this.userPwdPEC = userPwdPEC;
	}
	
	
}