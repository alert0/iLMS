package com.hotent.portal.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.portal.persistence.model.SysLayoutSetting;

/**
 * 
 * <pre> 
 * 描述：布局设置 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-07 16:18:52
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysLayoutSettingDao extends Dao<String, SysLayoutSetting> {
	/**
	 * 通过布局Id获取布局设置
	 * @param layoutId
	 * @return
	 */
	SysLayoutSetting getByLayoutId(String layoutId);
}
