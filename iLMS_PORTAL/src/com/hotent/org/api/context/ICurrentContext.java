package com.hotent.org.api.context;

import java.util.Locale;

import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;

/**
 * 获取上下文对象数据。
 * <pre> 
 * 描述：获取上下文对象数据。
 * 构建组：x5-org-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2013-12-20-上午9:09:21
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface ICurrentContext {
	
	/**
	 * 当前岗位
	 */
	public static final String CURRENT_ORG = "current_org";
	
	/**
	 * 获取当当前登录用户
	 * @return  User
	 */
	IUser getCurrentUser();
	
	/**
	 * 获取当前执行人
	 * @return  String
	 */
	String getCurrentUserId();
	/**
	 * 获取当前组织
	 */
	IGroup getCurrentGroup();
	
	/**
	 * 清理当前用户 
	 */
	void clearCurrentUser();
	
	/**
	 * 设置当前用户。
	 * @param user 
	 */
	void setCurrentUser(IUser user);
	
	/**
	 * 根据用户帐号设置上下文用户。
	 * @param account	帐号。
	 */
	void setCurrentUserByAccount(String account);
	/**
	 * 获取当前组织。
	 */
	void setCurrentGroup(IGroup group);
	
	/**
	 * 
	 *获取当前Locale。
	 * @return  Locale
	 */
	Locale getLocale();
	
	/**
	 * 
	 * 设置上下文local。
	 * @param local 
	 */
	void setLocale(Locale local );
	
	/**
	 * 
	 * 清除上下文local。 
	 */
	void clearLocale();
	
}
