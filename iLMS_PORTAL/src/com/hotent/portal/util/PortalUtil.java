package com.hotent.portal.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.web.RequestContext;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.portal.persistence.manager.SysIndexLayoutManageManager;
import com.hotent.portal.persistence.manager.UserSettingManager;
import com.hotent.portal.persistence.model.UserSetting;
import com.hotent.sys.api.system.PropertyService;
import com.hotent.sys.util.ContextUtil;

/**
 * 当前用户皮肤管理工具类
 * @author mikel
 */
public class PortalUtil {

	private static final String CURRENT_SKIN = "current_skin";
	private static final String CURRENT_USER_SETTING = "current_user_setting";
	private static final String CURRENT_USER_MOBILELAYOUT = "current_user_MOBILELAYOUT";
	
	
	/**
	 * 取得当前用户皮肤
	 * @return
	 * @throws Exception 
	 */
	public static String getCurrentUserSkin(HttpServletRequest request) {
		String skin = (String) RequestContext.getHttpServletRequest().getSession().getAttribute(CURRENT_SKIN);
		if (skin == null && ContextUtil.getCurrentUser() != null) {
			skin = setCurrentUserSkin(request);
		}
		return skin;
	}

	/**
	 * 取得当前用户皮肤
	 * @return
	 * @throws Exception 
	 */
	public static String setCurrentUserSkin(HttpServletRequest request) {
		return (String) RequestContext.getHttpServletRequest().getSession().getAttribute(CURRENT_SKIN);
	}
	
	/**
	 * 删除当前皮肤。
	 */
	public static void removeCurrentSkin(){
		RequestContext.getHttpServletRequest().getSession().removeAttribute(CURRENT_SKIN);
	}
	


	/**
	 * 获取模板路径。
	 * @return
	 */
	public static String getTemplatePath(){
		return FileUtil.getClassesPath() +"template" + File.separator;
	}
	
	/**
	 * 获取首页模板路径。
	 * @return
	 */
	public static String getIndexTemplatePath(){
		return getTemplatePath() + File.separator+"index"+File.separator;
	}
	
	/**
	 * 设置当前用户设置对象
	 * @param userSetting
	 */
	public static void setCurrentUserSetting(UserSetting userSetting){
		RequestContext.getHttpServletRequest().getSession().setAttribute(CURRENT_USER_SETTING, userSetting);
	}
	
	public static UserSetting getCurrentUserSetting(){
		UserSetting userSetting = (UserSetting) RequestContext.getHttpServletRequest().getSession().getAttribute(CURRENT_USER_SETTING);
		if(userSetting == null && ContextUtil.getCurrentUser() != null){
			UserSettingManager userSettingManager = AppUtil.getBean(UserSettingManager.class);
			userSetting = userSettingManager.getDefaultUserSettingByUserId(ContextUtil.getCurrentUserId());
			if (userSetting.getId() == null) {
				userSetting.setId(UniqueIdUtil.getUId());
				userSettingManager.create(userSetting);
			} else {
				userSettingManager.update(userSetting);
			}
			setCurrentUserSetting(userSetting);
		}
		return userSetting;
	}
	
	/**
	 * 设置当前用户的手机布局
	 */
	public static void setCurrentUserMobileLayout(String layoutId){
		RequestContext.getHttpServletRequest().getSession().setAttribute(CURRENT_USER_MOBILELAYOUT, layoutId);
	}
	public static String getCurrentUserMobileLayout(){
		Object layoutId = RequestContext.getHttpServletRequest().getSession().getAttribute(CURRENT_USER_MOBILELAYOUT);
		if(layoutId == null && ContextUtil.getCurrentUser() != null){
			SysIndexLayoutManageManager manager = AppUtil.getBean(SysIndexLayoutManageManager.class);
			manager.getMobileLayoutId(ContextUtil.getCurrentUserId());
			layoutId = RequestContext.getHttpServletRequest().getSession().getAttribute(CURRENT_USER_MOBILELAYOUT);
		}
		if (layoutId == null) {
			layoutId = 0;
		}
		return layoutId.toString();
	}
}
