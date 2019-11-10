package com.hotent.sys.persistence.manager;

import java.util.List;
import java.util.Map;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.SysProperties;

/**
 * 
 * <pre> 
 * 描述：SYS_PROPERTIES 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-07-28 09:19:53
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysPropertiesManager extends Manager<String, SysProperties>{
	
	
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
	
	
	
	/**
	 * 重新读取属性配置。
	 * @return
	 */
	Map<String,String>  reloadProperty();
	
	
	
	
	
	
}
