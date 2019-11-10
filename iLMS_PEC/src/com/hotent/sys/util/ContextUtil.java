package com.hotent.sys.util;

import java.util.Locale;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.org.api.context.ICurrentContext;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.model.IUser;



/**
 * 获取上下文数据对象的工具类。
 * <pre> 
 * 
 * 构建组：x5-org-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2013-12-20-上午9:38:46
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class ContextUtil  {

	private static ContextUtil contextUtil;
	
	private ICurrentContext currentContext;
	
	public void setCurrentContext(ICurrentContext _currentContext){
		contextUtil=this;
		contextUtil.currentContext=_currentContext;
	}
	
	/**
	 * 获取当前执行人
	 * @return 
	 * User
	 * @exception 
	 * @since  1.0.0
	 */
	public static IUser getCurrentUser(){
		return contextUtil.currentContext.getCurrentUser();
	}
	public static String getCurrentUserId(){
		return contextUtil.currentContext.getCurrentUserId();
	}
	/**获取当前组织*/
	public static IGroup getCurrentGroup(){
		return contextUtil.currentContext.getCurrentGroup();
	}
	
	/**获取当前组织Id。组织为空则返回空*/
	public static String getCurrentGroupId(){
		IGroup iGroup =  getCurrentGroup();
		if(BeanUtils.isNotEmpty(iGroup)){
			return iGroup.getGroupId();
		}else{
			return "";
		}
	}
	/**
	 * 获取当前Locale。
	 * @return 
	 * Locale
	 * @exception 
	 * @since  1.0.0
	 */
	public static Locale getLocale(){
		return contextUtil.currentContext.getLocale();
	}

	/**
	 * 清除当前执行人。
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void clearCurrentUser(){
		contextUtil.currentContext.clearCurrentUser();
		
	}
	
	/**
	 * 设置当前执行人。
	 * @param user 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void setCurrentUser(IUser user){
		contextUtil.currentContext.setCurrentUser(user);
	}
	
	
	public static void setCurrentUserByAccount(String account){
		contextUtil.currentContext.setCurrentUserByAccount(account);
	}
	
	
	/**
	 * 设置当前组织(岗位)。
	 * @param user 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void setCurrentOrg(IGroup group){
		contextUtil.currentContext.setCurrentGroup(group);
	}
	
	/**
	 * 设置Locale。
	 * @param locale 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void setLocale(Locale locale){
		contextUtil.currentContext.setLocale(locale);
	}
	
	/**
	 * 清除Local。
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void cleanLocale(){
		contextUtil.currentContext.clearLocale();
	}

	public static void clearAll() {
		cleanLocale();
		clearCurrentUser();  
	}
}
