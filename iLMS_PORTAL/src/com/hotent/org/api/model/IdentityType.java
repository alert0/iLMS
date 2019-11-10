package com.hotent.org.api.model;

import java.io.Serializable;
/**
 * <pre> 
 * 描述：用户标识类型
 * 构建组：x5-org-api
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2013-11-18-下午12:05:10
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface IdentityType extends Serializable{
	
	/**
	 * 用户
	 */
	public static final String USER="user";
	
	/**
	 * 用户组
	 */
	public static final String GROUP="group";
	

	/**
	 * 返回用户标识类型
	 * @return 
	 * String
	 */
	String getIdentityType();
	
	
	
}
