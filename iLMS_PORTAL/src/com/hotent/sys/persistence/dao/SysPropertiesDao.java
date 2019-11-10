package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.SysProperties;

/**
 * 
 * <pre> 
 * 描述：SYS_PROPERTIES DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-07-28 09:19:53
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysPropertiesDao extends Dao<String, SysProperties> {
	
	/**
	 * 分组列表。
	 * @return
	 */
	List<String> getGroups();
	
	
	/**
	 * 判断别名是否存在。
	 * @param sysProperties
	 * @return
	 */
	boolean isExist(SysProperties sysProperties);
}
