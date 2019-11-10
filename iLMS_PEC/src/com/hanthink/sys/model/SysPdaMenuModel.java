package com.hanthink.sys.model;
import com.hotent.base.core.model.AbstractModel;


 /**
 * PDA菜单管理
 * <pre> 
 * 描述：PDA菜单管理 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public class SysPdaMenuModel extends AbstractModel<Integer>{
	
	/**  
	 * <p>Description: long</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0  
	 */  
	private static final long serialVersionUID = 146710511966733061L;

	/**
	 * 当前登录人
	 */
	private String opeUser;
	/**
	 * 主表 PDA用户信息表
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
	 * 用户密码
	 */
	private String userPwd;
	
	/**
	 * 副表 PDA菜单表
	 */
	/**
	 * 菜单ID
	 */
	private String menuId;
	
	/**
	 * 菜单名称
	 */
	private String menuName;
	
	/**
	 * 菜单描述
	 */
	private String menuDesc;
	
	/**
	 * 工厂
	 */
	private String factoryCode;

	
	
	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getOpeUser() {
		return opeUser;
	}

	public void setOpeUser(String opeUser) {
		this.opeUser = opeUser;
	}

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

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
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
	

}