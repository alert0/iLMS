package com.hotent.portal.persistence.manager;

import com.hotent.base.manager.api.Manager;
import com.hotent.portal.persistence.model.UserSetting;

/**
 * 
 * <pre> 
 * 描述：用户配置 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-01 17:18:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface UserSettingManager extends Manager<Long, UserSetting>{
	/**
	 * 根据用户Id获取用户个人设置记录
	 * @param userId
	 * @return 
	 */
	UserSetting getUserSettingByUserId(String userId);
	/**
	 * 登录时去获取登录用户的用户个人设置
	 * @param currentUserId
	 * @return
	 */
	UserSetting getDefaultUserSettingByUserId(String currentUserId);
	
}
