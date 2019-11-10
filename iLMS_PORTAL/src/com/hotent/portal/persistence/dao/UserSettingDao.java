package com.hotent.portal.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.portal.persistence.model.UserSetting;

/**
 * 
 * <pre> 
 * 描述：用户配置 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-01 17:18:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface UserSettingDao extends Dao<Long, UserSetting> {
	/**
	 * 根据用户Id获取用户个人设置记录
	 * @param userId
	 * @return
	 */
	UserSetting getUserSettingByUserId(String userId);
}
